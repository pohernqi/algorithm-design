package Q2;
import java.io.IOException;


public class HeapMain {
    public static void main(String[] args) throws IOException {

        Heapsort.processFile("Set1.csv", "Q2/Heap1.csv");
        System.out.println("");

        Heapsort.processFile("Set2.csv", "Q2/Heap2.csv");
        System.out.println("");

        Heapsort.processFile("Set3.csv", "Q2/Heap3.csv");
        System.out.println("");

        Heapsort.processFile("Set4.csv", "Q2/Heap4.csv");
        System.out.println("");

        Heapsort.processFile("Set5.csv", "Q2/Heap5.csv");
        System.out.println("");

        Heapsort.processFile("Set6.csv", "Q2/Heap6.csv");
    }
}