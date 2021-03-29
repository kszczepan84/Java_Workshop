package pl.coderslab;

import jdk.dynalink.StandardOperation;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {
    static String[][] tasks;

    //metoda wywietlajaca menu glowne

    static String optionMenu() {
        String[] tab = {ConsoleColors.BLUE + "Please select an option:", ConsoleColors.RESET + "add", "remove", "list", "exit"};
        String str = "";
        for (int i = 0; i < tab.length; i++) {
            str += tab[i] + "\n";
        }
        return str;
    }

    public static void main(String[] args) {


//tutaj pobieramy z pliku csv tablice o nazwie taskTab
        String[][] taskTab = new String[0][0];
        File file = new File("tasks.csv");
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder sb = new StringBuilder();
            String line;
            int counter = 0;
            do {
                line = scanner.nextLine();
                sb.append(line).append("delimiter");
                counter++;
            } while (scanner.hasNextLine());
            String str = sb.toString();
            String[] arrOfstr = str.split("delimiter|,");
            taskTab = new String[counter][];
            for (int i = 0; i < counter; i++) {
                taskTab[i] = new String[3];
            }
            int count = 0;
            for (int i = 0; i < taskTab.length; i++) {
                for (int j = 0; j < taskTab[i].length; j++) {
                    taskTab[i][j] = arrOfstr[count++];
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }
        tasks = taskTab;
//        System.out.println(Arrays.deepToString(tasks));
        // wlaczamy interfejs menu glownego
        System.out.println(optionMenu());
//        Wlaczamy interfejs wyboru
        InputMenu();
    }

    // wyswietlamy opcje programu
    public static void InputMenu() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            switch (line) {
                case "add":
                    addTask();
                    System.out.print(optionMenu());
                    break;
                case "remove":
                    remove();
                    System.out.print(optionMenu());
                    break;
//                    chyba blad bo wczytuje i pokazuje wiersze z pliku to jest blad bo powinien pokazywac tylko z tablicy - do poprawy
                case "list":
                    System.out.print(printableArray(tasks));
                    System.out.println(optionMenu());
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED+"Have a nice day!");
                    writeToFile();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Command does not exist. Please try again and write correct:");
                    break;
            }
        }
    }


//metoda zmienia tablice z zadaniami na String i wyswietla

    public static String printableArray(String[][] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(ConsoleColors.BLUE + i + ": ").append(ConsoleColors.RESET);
            for (int j = 0; j < arr[i].length; j++) {
                sb.append(arr[i][j]).append(", ");
                if (j == 2) {
                    sb.append("\n");
                }
            }
        }
        String printable = sb.toString();
        return printable;
    }
//komenda usuwajaca wybrane zadanie z listy

    public static void remove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(printableArray(tasks));
        System.out.println(String.format("Select number to delete ( from '0' to '%s' ): ", tasks.length - 1));
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println(String.format("It is not a number, please select a number:"));
        }
        int pos = scanner.nextInt();
        if (pos < tasks.length && pos >= 0) {
            tasks = ArrayUtils.remove(tasks, pos);
        }
//            do {
//                System.out.println("Please select correct answer");
//                String answer = scanner.nextLine();
//                switch (answer) {
//                    case "yes":
//                        System.out.println("Are you sure to remove? (yes/no)')");
//                        String yesOrNot = scanner.nextLine();
//                        if (yesOrNot.equals("yes")) {
//                            ArrayUtils.remove(tasks, pos);
//                        }
//                        break;
//                    case "no":
//                        System.out.println("Returning to main menu");
//                        break;
//                    default:
//                        System.out.println("Please select correct answer");
//                        break;
//                }
//            }while ( true );

    }


//komenda dodajaca pobierajaca dane i dodajaca task do listy

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type a task description:");
        String desc = scanner.nextLine();
        System.out.println("Please insert the date:");
        String date = scanner.nextLine();
        System.out.println("Please type true or false:");
        String trueorfalse = scanner.nextLine();
        String[] arr = {desc, date, trueorfalse};
//        System.out.println(Arrays.toString(arr));
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = desc;
        tasks[tasks.length - 1][1] = date;
        tasks[tasks.length - 1][2] = trueorfalse;
//        System.out.println(Arrays.deepToString(tasks));
//        od tego miejsca moj kod ktory nie wiadoo dlaczego nie dziala
//        tasks = Arrays.copyOf(tasks, tasks.length + 1);
//        tasks[tasks.length - 1] = new String[tasks[0].length];
//        for (int i = 0; i < tasks[i].length; i++) {
//            tasks[tasks.length - 1][i] = arr[i];
//            System.out.println(Arrays.deepToString(tasks));
//        do tego miejsca

    }

    public static String arrToString(String[][] convertToString) {
        String line = "";
        int counter = 0;
        for (int i = 0; i < convertToString.length; i++) {
            line += String.join(",", convertToString[i]);
            for (int j = 0; j < convertToString[i].length; j++) {
                counter++;
                if (counter == convertToString[i].length) {
                    line += "\n";
                    counter = 0;
                }
            }
        }
        return line;
    }

    public static void writeToFile() {
        FileWriter fileWriter;
        String line = arrToString(tasks);
        try {
            fileWriter = new FileWriter(("tasks.csv"));
            BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(line);
                bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    public class WriteToFile {
//        public static void main(String[] args) {
//            try {
//                FileWriter myWriter = new FileWriter("filename.txt");
//                myWriter.write("Files in Java might be tricky, but it is fun enough!");
//                myWriter.close();
//                System.out.println("Successfully wrote to the file.");
//            } catch (IOException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();













