package Q1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SetOne {
    
    SetOne () throws IOException {

        FileWriter writer = new FileWriter("./Set1.csv");
        Random random = new Random();

        int[] seedRef = {1, 2, 2, 1, 3, 0, 1, 8, 7, 4};

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            
            switch (random.nextInt(4)) {

                case 0:
                    generateT(random, seedRef, writer);
                    break;
                case 1:
                    generateH(random, seedRef, writer);
                    break;
                case 2:
                    generateTh(random, seedRef, writer);
                    break;
                case 3:
                    generateTenTh(random, seedRef, writer);
                    break;
                default:
                    break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to Generate Set 1: " + (endTime - startTime) + " ms");

        writer.close();
    }

    void generateT(Random random, int[] seedRef, FileWriter writer) throws IOException { //for 10s

        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length];

        writer.append(String.valueOf(temp));
        writer.append(String.valueOf(temp2) + "\n");
    }

    void generateH(Random random, int[] seedRef, FileWriter writer) throws IOException { //for 100s

        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp3 = seedRef[Math.abs(random.nextInt()) % seedRef.length];

        writer.append(String.valueOf(temp));
        writer.append(String.valueOf(temp2));
        writer.append(String.valueOf(temp3) + "\n");
    }

    void generateTh(Random random, int[] seedRef, FileWriter writer) throws IOException { //for 1,000s
  
        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp3 = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp4 = seedRef[Math.abs(random.nextInt()) % seedRef.length];

        writer.append(String.valueOf(temp));
        writer.append(String.valueOf(temp2));
        writer.append(String.valueOf(temp3));
        writer.append(String.valueOf(temp4) + "\n");
    }

    void generateTenTh(Random random, int[] seedRef, FileWriter writer) throws IOException { //for 10,000s

        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];  // 1st Digit
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length]; // 2nd Digit
        int temp3 = seedRef[Math.abs(random.nextInt()) % seedRef.length]; // 3rd Digit
        int temp4 = seedRef[Math.abs(random.nextInt()) % seedRef.length]; // 4th Digit
        int temp5 = seedRef[Math.abs(random.nextInt()) % seedRef.length]; // 5th Digit

        writer.append(String.valueOf(temp));
        writer.append(String.valueOf(temp2));
        writer.append(String.valueOf(temp3));
        writer.append(String.valueOf(temp4));
        writer.append(String.valueOf(temp5) + "\n");
    }
}
