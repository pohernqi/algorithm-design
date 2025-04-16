package Q2;
import java.util.ArrayList;
//import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Heapsort {

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
            int[] A = list.stream().mapToInt(i -> i).toArray();

            heapSort(A);

            // write the sorted array to a CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (int value : A) {
                    writer.println(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    private static void heapify(int[] A, int arraySize, int j) {
        int max;
        int left = 2 * j + 1;
        int right = 2 * j + 2;

        if (left < arraySize && A[left] > A[j])
            max = left;
        else
            max = j;

        if (right < arraySize && A[right] > A[max])
            max = right;

        if (max != j) {
            int temp = A[j];
            A[j] = A[max];
            A[max] = temp;
            heapify(A, arraySize, max);
        }
    }

    private static void heapSort(int[] A) {

        int arraySize = A.length;

        // Record the start time before building the heap
        long startTime = System.currentTimeMillis();

        // Build heap (rearrange array)
        for (int j = arraySize / 2 - 1; j >= 0; j--)
            heapify(A, arraySize, j);
        // Record the end time after building the heap
        long endTime = System.currentTimeMillis();

        // Calculate the time taken to build the heap
        System.out.println("Time taken to insert all data into the priority queue: " + (endTime - startTime) + " ms");

        // Record the start time before dequeuing the data
        long startTime1 = System.currentTimeMillis();

        // One by one extract an element from heap
        for (int i = arraySize - 1; i >= 0; i--) {
            // Move current root to end
            int temp = A[0];
            A[0] = A[i];
            A[i] = temp;

            // call max heapify on the reduced heap
            heapify(A, i, 0);
        }
        // Record the end time after dequeuing the data
        long endTime1 = System.currentTimeMillis();

        // Calculate the time taken to dequeue the data
        System.out.println("Time taken to dequeue the data: " + (endTime1 - startTime1) + " ms");
    }
}
