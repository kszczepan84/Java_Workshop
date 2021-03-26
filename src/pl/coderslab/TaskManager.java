package pl.coderslab;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.Console;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {
    public static void main(String[] args) {

        System.out.println(menuDisplay());
//        druga opcja :)
//        System.out.println( ConsoleColors.BLUE+"Please select an option:");
//        System.out.println(ConsoleColors.RESET+"add");
//        System.out.println("remove");
//        System.out.println("list");
//        System.out.println("exit");
    }


    public static String menuDisplay() {
//            pierwsza opcja
        String[] tab = {ConsoleColors.BLUE + "Please select an option:", ConsoleColors.RESET + "add", "remove", "list", "exit"};
        String str = "";
        for (int i = 0; i < tab.length; i++) {
            str += tab[i] + "\n";
        }
        return str;
    }

}
