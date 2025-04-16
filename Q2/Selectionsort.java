package Q2;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Selectionsort {

    public static void processFile(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

            String line;
            List<Integer> list = new ArrayList<>();// create a list to store the integers
            while ((line = reader.readLine()) != null) { // read all lines
                String[] stringValues = line.split(","); // split the line by comma

                // convert the string values to integers and add them to the list
                for (String stringValue : stringValues) {
                    list.add(Integer.parseInt(stringValue));
                }
            }
            // convert the list to an array
            int[] numbers = list.stream().mapToInt(i -> i).toArray();

            // Record the start time before inserting data into the priority queue
            long startTime = System.currentTimeMillis();
            selectionSort(numbers);
            ;
            // Record the end time after inserting data into the priority queue
            long endTime = System.currentTimeMillis();

            // Calculate the time taken to insert all data into the priority queue
            System.out.println("Time taken to sort all data: " + (endTime - startTime) + " ms");

            // write the sorted array to a CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (int value : numbers) {
                    writer.println(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selectionSort(int[] numbers) {
        int n = numbers.length;
        for (int i = 0; i < n - 1; i++) {
            int min = numbers[i];
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (numbers[j] < min) {
                    min = numbers[j];
                    minIndex = j;
                }
            }
            swap(numbers, i, minIndex);
        }
    }

    private static void swap(int[] numbers, int a, int b) {
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }

}
