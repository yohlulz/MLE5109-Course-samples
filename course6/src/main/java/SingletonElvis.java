import java.io.*;
import java.nio.file.Paths;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 03, 2017
 */
public class SingletonElvis implements Serializable {

    private static final SingletonElvis INSTANCE = new SingletonElvis();

    private SingletonElvis() {
        System.out.println(this);
    }

    public static SingletonElvis getInstance() {
        return INSTANCE;
    }

//    private Object readResolve() throws ObjectStreamException {
//        return INSTANCE;
//    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final File fileLocation = Paths.get(System.getProperty("java.io.tmpdir"), "elvis.data").toFile();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileLocation))) {
            out.writeObject(SingletonElvis.getInstance());
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileLocation))) {
            final SingletonElvis elvis = (SingletonElvis) in.readObject();
            System.out.println(elvis);
            System.out.println(elvis.equals(SingletonElvis.getInstance()));
        }
    }
}
