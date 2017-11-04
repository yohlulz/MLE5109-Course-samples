package com.tora.puzzles;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Nov 04, 2017
 */
public class SerialKiller {

    public static void main(String[] args) {
        Sub sub = new Sub(666);
        sub.checkInvariant();
        Sub copy = (Sub) deepCopy(sub);
        copy.checkInvariant();
    }

    static public Object deepCopy(Object obj) {
        try {
            //copies the object via serialization
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            new ObjectOutputStream(bos).writeObject(obj);
            ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
            return new ObjectInputStream(bin).readObject();

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    static class Super implements Serializable {
        final Set<Super> set = new HashSet<>();
    }

    static final class Sub extends Super {
        private int id;

        public Sub(int id) {
            this.id = id;
            set.add(this); // Establish invariant
        }

        public void checkInvariant() {
            if (!set.contains(this)) throw new AssertionError("invariant violated");
        }

        public int hashCode() {
            return id;
        }

        public boolean equals(Object o) {
            return (o instanceof Sub) && (id == ((Sub) o).id);
        }
    }
}

