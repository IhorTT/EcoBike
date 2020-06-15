package net.ukr.tigor;

import net.ukr.tigor.service.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input a file name to read the source data:");
        String fileName = scanner.nextLine();
//        String fileName = "ecobike.txt";
        File file = new File(fileName);
        if (!file.exists()){
            System.out.println("File doesn't exist!");
            return;
        }
        Utils.setFile(file);
        try {
            Utils.loadCatalog();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Utils.welcomePage();

        while (true) {
            System.out.println(">");
            String text = scanner.nextLine();
            if (text.equals("7")) {
                if (Utils.isChangesInData()) {
                    System.out.println("You have unsaved data! Save the data before exit");
                } else {
                    break;
                }
            }
            Utils.doCommand(text);
            Utils.welcomePage();
        }
        scanner.close();
    }
}
