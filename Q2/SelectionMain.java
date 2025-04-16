package Q2;
import java.io.IOException;

public class SelectionMain {
    public static void main(String[] args) throws IOException {

        //don run all at once maybe will make your laptop crash
        Selectionsort.processFile("Set1.csv", "Q2/Selection1.csv");
        System.out.println("");

        Selectionsort.processFile("Set2.csv", "Q2/Selection2.csv");
        System.out.println("");

        Selectionsort.processFile("Set3.csv", "Q2/Selection3.csv");
        System.out.println("");

        Selectionsort.processFile("Set4.csv", "Q2/Selection4.csv");
        System.out.println("");

        Selectionsort.processFile("Set5.csv", "Q2/Selection5.csv");
        System.out.println("");
        
        Selectionsort.processFile("Set6.csv", "Q2/Selection6.csv");
    }
}