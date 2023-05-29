package com.example.movie.utils;

import java.io.File;

class FileStructure {
    public static void main(String[] args) {
        String folderPath = "D:\\graduate\\project";
        File folder = new File(folderPath);
        printFileStructure(folder, 0);
    }

    public static void printFileStructure(File folder, int level) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                for (int i = 0; i < level; i++) {
                    System.out.print("    ");
                }

                System.out.println(file.getName());

                if (file.isDirectory()) {
                    printFileStructure(file, level + 1);
                }
            }
        }
    }
}
