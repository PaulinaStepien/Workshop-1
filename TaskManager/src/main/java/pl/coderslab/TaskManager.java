package pl.coderslab;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {


    public static void main(String[] args) {
        loadTasksToArray("tasks.csv");
        showOptions();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String input = scanner.hasNext();
            switch (input) {
                case "exit" :
                    break;
            }
        }


    }

    public static void showOptions() {
        String[] options = {"add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option: " + ConsoleColors.RESET);
        for (String option : options) {
            System.out.println(option);
        }
    }


    public static String[][] loadTasksToArray(String fileName) {
        Path pathTasks = Paths.get(fileName);
        String[][] tasks = null;
        if (!Files.exists(pathTasks)) {
            System.out.print("Plik źródłowy nie istnieje");
            System.exit(0);
        }
        try {
            List<String> strings = Files.readAllLines(pathTasks);
            tasks = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < tasks.length; i++) {
                String[] splitLine = strings.get(i).split(",");
                for (int j = 0; j < 3; j++) {
                    tasks[i][j] = splitLine[j];
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return tasks;
    }





}
