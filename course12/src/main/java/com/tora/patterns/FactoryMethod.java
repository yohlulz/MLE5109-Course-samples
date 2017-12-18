package com.tora.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 06, 2017
 */
public class FactoryMethod {

    static abstract class MazeGame {
        private final List<Room> rooms = new ArrayList<>();

        MazeGame() {
            IntStream.rangeClosed(0, 10)
                    .mapToObj(this::makeRoom)
                    .forEach(rooms::add);
        }

        abstract protected Room makeRoom(int difficulty);
    }

    static class Room {
    }

    static class MagicMazeGame extends MazeGame {
        @Override
        protected Room makeRoom(int difficulty) {
            return new MagicRoom(difficulty);
        }

        private class MagicRoom extends Room {
            public MagicRoom(int difficulty) {
            }
        }
    }

    static class OrdinaryMazeGame extends MazeGame {
        @Override
        protected Room makeRoom(int difficulty) {
            return new OrdinaryRoom(difficulty);
        }

        private class OrdinaryRoom extends Room {
            public OrdinaryRoom(int difficulty) {
            }
        }
    }
}
