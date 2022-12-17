package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {
        final String filename = "tasks.csv";
        final String[] options = {"add", "remove", "list", "exit"};
        String[][] tasks;

        tasks = loadTasksToArray(filename);
        showOptions(options);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask(tasks);
                    break;
                case "remove":
                    removeTask(tasks, getTheNumber());
                    break;
                case "list":
                    listTask(tasks);
                    break;
                case "exit":
                    exitTask(tasks, "tasks.csv");
                    break;
                default:
                    System.out.println("Please select a correct option");
            }
        }
    }


    public static void showOptions(String[] tab) {
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option: " + ConsoleColors.RESET);
        for (String option : tab) {
            System.out.println(option);
        }
    }

    public static String[][] loadTasksToArray(String fileName) {
        Path pathTasks = Paths.get(fileName);
        if (!Files.exists(pathTasks)) {
            System.out.print("Plik źródłowy nie istnieje");
            System.exit(0);
        }
        String[][] tasks = null;
        try {
            List<String> strings = Files.readAllLines(pathTasks);
            tasks = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] splitLine = strings.get(i).split(",");
                for (int j = 0; j < splitLine.length; j++) {
                    tasks[i][j] = splitLine[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public static void addTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please add task description:");
        String description = scanner.nextLine();
        System.out.print("Please add task due date:");
        String date = scanner.nextLine();
        System.out.print("Is your task important: true/false");
        String importance = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = date;
        tasks[tasks.length - 1][2] = importance;

    }

    public static boolean isNumberGreaterEqualZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getTheNumber() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Number to remove: ");
        String idToRemove = scanner.nextLine();
        while (!isNumberGreaterEqualZero(idToRemove)) {
            System.out.print("Please give a correct number: ");
            scanner.nextLine();
        }
        return Integer.parseInt(idToRemove);
    }

    public static void removeTask(String[][] tasks, int index) {
        try {
            if (index < tasks.length) {
                String[][] newTasks = ArrayUtils.remove(tasks, index);
                System.out.print("Element succesfully deleted.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Element not exist");
        }
    }

    public static void listTask(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void exitTask(String[][] tasks, String filename) {
        Path dir = Paths.get(filename);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            lines[i] = String.join(",", tasks[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ConsoleColors.RED_BOLD + "Bye, bye." + ConsoleColors.RESET);
    }
}
