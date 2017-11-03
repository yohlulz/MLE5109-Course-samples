package com.tora;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.tora.Utils.DEV_NULL;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public enum PathConsumer implements Consumer<Path> {
    BUFFERED_IO() {
        @Override
        public void accept(Path path) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
                 PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(DEV_NULL.toFile())))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.println(line);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    },

    BUFFERED_IO_50_THREADS() {
        @Override
        public void accept(Path path) {
            new ThreadedConsumer(50, path, BUFFERED_IO);
        }
    },

    UNBUFFERED_IO() {
        @Override
        public void accept(Path path) {
            try (FileReader reader = new FileReader(path.toFile());
                 PrintWriter writer = new PrintWriter(new FileWriter(DEV_NULL.toFile()))) {
                int character;
                while ((character = reader.read()) != -1) {
                    writer.write(character);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    },

    STREAM_NIO() {
        @Override
        public void accept(Path path) {
            try (Stream<String> lines = Files.lines(path);
                 PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(DEV_NULL.toFile())))) {
                lines.forEach(writer::println);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    },

    STREAM_NIO_50_THREADS() {
        @Override
        public void accept(Path path) {
            new ThreadedConsumer(50, path, STREAM_NIO);
        }
    },

    NIO_ASYNC_CHANNEL() {
        @Override
        public void accept(Path path) {
            final CountDownLatch waitLatch = new CountDownLatch(1);
            try (AsynchronousFileChannel infileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
                 PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(DEV_NULL.toFile())))) {
                final ByteBuffer byteBuffer = ByteBuffer.allocate(8192); /* default buffer size for buffered reader */
                read(infileChannel, 0, writer, byteBuffer, waitLatch);

                /* wait until operation is done before exiting */
                waitLatch.await();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void read(AsynchronousFileChannel fileChannel, Integer position, PrintWriter writer, ByteBuffer buffer, CountDownLatch waitLatch) {
            fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (result > 0) {
                        writer.print(new String(attachment.array()));
                        attachment.clear();
                        read(fileChannel, position + result, writer, attachment, waitLatch);
                    } else {
                        waitLatch.countDown();
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
        }

    }
}
