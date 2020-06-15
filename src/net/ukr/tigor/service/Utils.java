package net.ukr.tigor.service;

import net.ukr.tigor.domain.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

public class Utils {

    private static Catalog catalog = Catalog.getInstance();
    private static File file;

    public static void setFile(File file) {
        Utils.file = file;
    }

    public static void welcomePage() {
        System.out.println("Eco bikes menu");
        System.out.println("1 - Show the entire EcoBike catalog");
        System.out.println("2 – Add a new folding bike");
        System.out.println("3 – Add a new speedelec");
        System.out.println("4 – Add a new e-bike");
        System.out.println("5 – Find the first item of a particular brand");
        System.out.println("6 – Write to file");
        System.out.println("7 – Stop the program");
    }

    public static void doCommand(String userAnswer) {

        switch (userAnswer) {
            case "1":
                showCatalog();
                break;
            case "2":
                try {
                    inputNewBike("folding bike");
                } catch (IllegalAccessException | IOException e) {
                    System.out.println("Failed to do this command.");
                }
                break;
            case "3":
                try {
                    inputNewBike("speedelec");
                } catch (IllegalAccessException | IOException e) {
                    System.out.println("Failed to do this command.");
                }
                break;
            case "4":
                try {
                    inputNewBike("e-bike");
                } catch (IllegalAccessException | IOException e) {
                    System.out.println("Failed to do this command.");
                }
                break;
            case "5":
                try {
                    showFirstItem();
                } catch (IllegalAccessException | IOException e) {
                    System.out.println("Failed to found bike.");
                }
                break;
            case "6":
                writeChangesToFile();
                break;
        }
    }

    /**
     * for input data from keyboard
     * @param question- question question to show for user
     * @return - user entered value
     * @throws IOException - execution forwarding
     */
    private static String getUserAnswer(String question) throws IOException {
        System.out.println(question);
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        return r.readLine();
    }

    /**
     * organizes a bike request from the user to search the directory
     * search occurs in a separate thread
     */
    private static void showFirstItem() throws IOException, IllegalAccessException {

        System.out.println("Enter your search options");
        String typeBike = getUserAnswer("Type of bike (1 - folding, 2 - speedelec, 3 - e-bike):");
        String brand = getUserAnswer("Brand:");

        Bike usersBike = createBike(typeBike, brand, true);
        Thread thread = new Thread(new FindBikeThread(usersBike), "FindingBike");
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * for input new bike from user
     * @param typeBike - type of bike
     */
    private static void inputNewBike(String typeBike) throws IOException, IllegalAccessException {
        if (catalog.isLockForChange()) {
            System.out.println("Catalog is busy searching. Try later.");
            return;
        }
        System.out.println("Creation " + typeBike + ":");
        String brand = getUserAnswer("Enter brand:");
        Bike bike = createBike(typeBike, brand, false);
        bike.setNewBike(true);
        catalog.getCatalogOfBikes().add(bike);
        catalog.setChanged(true);
        System.out.println(typeBike + " successfully added");
    }

    /**
     *
     * @param typeBike - type of bike
     * @param brand - btand of bike
     * @param forSearch - if method used to search
     * @return new bike from user request
     */
    private static Bike createBike(String typeBike, String brand, boolean forSearch) throws IOException, IllegalAccessException {
        Bike bike = null;
        switch (typeBike) {
            case "folding bike":
            case "1":
                bike = new Folding(TypeOfBike.FOLDING, brand);
                break;
            case "speedelec":
            case "2":
                bike = new Speedelec(TypeOfBike.SPEEDELEC, brand);
                break;
            case "e-bike":
            case "3":
                bike = new Elec(TypeOfBike.ELEC, brand);
                break;
        }
        fillFields(bike, forSearch);
        return bike;
    }

    /**
     *
     * @param bike - bike for filling of fields
     * @param forSearc - if method used to search
     */
    private static void fillFields(Object bike, boolean forSearc) throws IOException, IllegalAccessException {

        String hint = "";
        Field[] fields = bike.getClass().getDeclaredFields();
        for (Field field : fields) {
            Class<?> fldClass = field.getType();
            if (fldClass.equals(int.class)) {
                hint = " (positive integer" + (forSearc ? " or empty" : "") + ")";
            } else if (fldClass.equals(String.class)) {
                hint = " (string" + (forSearc ? " or empty" : "") + ")";
            } else if (fldClass.equals(boolean.class)) {
                hint = " (logical true/false" + (forSearc ? " or empty" : "") + ")";
            }
            String prompt = "Enter " + field.getName() + hint + ": ";
            setFieldFromUserInput(bike, field, prompt, forSearc);
        }
    }

    /**
     * interactive field filling
     * @param bike - new created bike
     * @param field - field to fill
     * @param prompt - data entry request
     * @param forSearch - if method used to search
     */
    private static void setFieldFromUserInput(Object bike, Field field, String prompt, boolean forSearch) throws IllegalAccessException, IOException {

        boolean wrongInput = false;

        String usersData = getUserAnswer(prompt);
        if (forSearch && usersData.isEmpty()) {
            return;
        }

        field.setAccessible(true);
        Class<?> fldClass = field.getType();
        if (fldClass.equals(int.class)) {
            int intData = 0;
            try {
                intData = Integer.parseInt(usersData);
                wrongInput = intData < 1;
            } catch (NumberFormatException e) {
                wrongInput = true;
            }
            if (!wrongInput) {
                field.setInt(bike, intData);
            }

        } else if (fldClass.equals(String.class)) {
            field.set(bike, usersData);

        } else if (fldClass.equals(boolean.class)) {
            boolean boolData = Boolean.valueOf(usersData);
            field.setBoolean(bike, boolData);
        }
        if (wrongInput) {
            System.out.println("Wrong data type. Try again");
            setFieldFromUserInput(bike, field, prompt, forSearch);
        }
    }

    /**
     * displays a catalog of bikes of 20 items per page
     */
    private static void showCatalog() {
        List<Bike> bikes = catalog.getCatalogOfBikes();
        int countLineInPage = 20;
        int totalPages = bikes.size() / countLineInPage;
        if ((totalPages == 0) && (bikes.size() % countLineInPage > 0)) {
            totalPages = 1;
        } else if (bikes.size() % countLineInPage > 0) {
            totalPages = bikes.size() / countLineInPage + 1;
        }
        int numberLines = 20;
        int numberPages = 1;
        for (Bike bike : bikes) {
            if (numberLines == countLineInPage) {
                System.out.println("======================= page " + numberPages + " from " + totalPages + " =======================");
                numberLines = 0;
                numberPages += 1;
            }
            System.out.println(bike);
            numberLines += 1;
        }
    }

    /**
     * load catalog from file
     */
    public static void loadCatalog() throws IOException {
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String str;
            int lineNumber = 1;
            for (; (str = bf.readLine()) != null; ) {

                String[] arrFields = str.split("; ");
                if (arrFields.length == 7) {

                    Bike bikeFromTxt = getBikeFromTxt(arrFields);
                    if (bikeFromTxt != null) {
                        catalog.getCatalogOfBikes().add(bikeFromTxt);
                    } else {
                        System.out.println("Unknown type of bike in line " + lineNumber);
                    }
                } else {
                    System.out.println("Unknown format in line " + lineNumber);
                }
                lineNumber += 1;
            }
        }
    }

    /**
     * save changes from catalog to file
     */
    private static void writeChangesToFile() {
        for (Bike bike : catalog.getCatalogOfBikes()) {
            if (bike.isNewBike()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                    pw.println(bike.getResultStringForFile());
                    bike.setNewBike(false);
                } catch (IOException e) {
                    System.out.println("Failed to save data to file");
                }
            }
        }
        catalog.setChanged(false);
        System.out.println("Data saved to file successfully ");
    }

    /**
     * creates a new bike from an array of strings
     * @param arrFields - array of strings
     * @return - new created bike
     */
    private static Bike getBikeFromTxt(String[] arrFields) {

        if (arrFields[0].contains("SPEEDELEC")) {
            return new Speedelec(TypeOfBike.SPEEDELEC, arrFields);
        }

        if (arrFields[0].contains("E-BIKE")) {
            return new Elec(TypeOfBike.ELEC, arrFields);
        }

        if (arrFields[0].contains("FOLDING BIKE")) {
            return new Folding(TypeOfBike.FOLDING, arrFields);
        }
        return null;
    }


    public static boolean isChangesInData() {
        return catalog.isChanged();
    }
}
