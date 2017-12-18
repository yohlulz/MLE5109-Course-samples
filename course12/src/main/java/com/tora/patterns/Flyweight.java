package com.tora.patterns;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Code example from https://refactoring.guru/design-patterns/flyweight
 *
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Dec 12, 2017
 */
public class Flyweight {
    /**
     * Contains state unique for each tree - unshared flyweight
     */
    static class Tree {
        private int x;
        private int y;
        private TreeType type;

        public Tree(int x, int y, TreeType type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public void draw(Graphics g) {
            type.draw(g, x, y);
        }
    }

    /**
     * State shared by several trees - shared flyweight
     */
    static class TreeType {
        private String name;
        private Color color;
        private String otherTreeData;

        public TreeType(String name, Color color, String otherTreeData) {
            this.name = name;
            this.color = color;
            this.otherTreeData = otherTreeData;
        }

        public void draw(Graphics g, int x, int y) {
            g.setColor(Color.BLACK);
            g.fillRect(x - 1, y, 3, 5);
            g.setColor(color);
            g.fillOval(x - 5, y - 10, 10, 10);
        }
    }

    /**
     * Flyweight factory
     */
    static class TreeFactory {
        private static final Map<String, TreeType> treeTypes = new HashMap<>();

        public static TreeType getTreeType(String name, Color color, String otherTreeData) {
            return treeTypes.computeIfAbsent(name, key -> new TreeType(key, color, otherTreeData));
        }
    }

    static class Forest extends JFrame {
        private List<Tree> trees = new ArrayList<>();

        public void plantTree(int x, int y, String name, Color color, String otherTreeData) {
            trees.add(new Tree(x, y,
                    TreeFactory.getTreeType(name, color, otherTreeData)));
        }

        @Override
        public void paint(Graphics graphics) {
            trees.forEach(tree -> tree.draw(graphics));
        }
    }

    static int CANVAS_SIZE = 500;
    static int TREES_TO_DRAW = 1000000;
    static int TREE_TYPES = 2;

    public static void main(String[] args) {
        Forest forest = new Forest();
        for (int i = 0; i < Math.floor(TREES_TO_DRAW / TREE_TYPES); i++) {
            forest.plantTree(random(0, CANVAS_SIZE), random(0, CANVAS_SIZE),
                    "Summer Oak", Color.GREEN, "Oak texture stub");
            forest.plantTree(random(0, CANVAS_SIZE), random(0, CANVAS_SIZE),
                    "Autumn Oak", Color.ORANGE, "Autumn Oak texture stub");
        }
        forest.setSize(CANVAS_SIZE, CANVAS_SIZE);
        forest.setVisible(true);

        System.out.println(TREES_TO_DRAW + " trees drawn");
        System.out.println("---------------------");
        System.out.println("Memory usage:");
        System.out.println("Tree size (8 bytes) * " + TREES_TO_DRAW);
        System.out.println("+ TreeTypes size (~30 bytes) * " + TREE_TYPES + "");
        System.out.println("---------------------");
        System.out.println("Total: " + ((TREES_TO_DRAW * 8 + TREE_TYPES * 30) / 1024 / 1024) +
                "MB (instead of " + ((TREES_TO_DRAW * 38) / 1024 / 1024) + "MB)");
    }

    private static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
