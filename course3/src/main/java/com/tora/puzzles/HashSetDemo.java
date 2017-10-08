package com.tora.puzzles;

import java.util.HashSet;
import java.util.Set;

class Student {
    int rollNumber;
    Student(int n){
        rollNumber = n;
    }
}
public class HashSetDemo {
    public static void main(String[] args) {
        Set<Student> students = new HashSet<>();
        students.add(new Student(1));
        students.add(new Student(3));
        students.add(new Student(4));
        students.add(new Student(1));
        students.add(new Student(3));
        System.out.println(students.size());
    }
}
