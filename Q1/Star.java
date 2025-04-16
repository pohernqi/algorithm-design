package Q1;
// import java.io.BufferedReader;
// import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Random;

public class Star {

    public Star() {}

    public Star(Star[] myStars) throws IOException {

        long startTime = System.currentTimeMillis();

        Star.setStarProperties(myStars); // Set All Star Properties x, y, z, weight & profit

        Star.cyclicConnect(myStars); //20 Edges Used
        Star.connect2(myStars); //20 Edges Used, Left 14
        Star.connect3(myStars); // 14 Edges Used, All 54 Edges used
        
        FileWriter writer = new FileWriter("./dataSet2.csv");
        writer.write("Star Name, x, y, z, weight, profit\n");

        for (Star star : myStars) {
            
            System.out.println(star);

            writer.append(String.valueOf(star.getName()) + ", " + String.valueOf(star.getX()) + ", " + String.valueOf(star.getY()) + 
            ", "+ String.valueOf(star.getZ()) + ", " + String.valueOf(star.getWeight()) + ", " + String.valueOf(star.getProfit()) + "\n");

        }

        outputConnectedStarsToCSV(myStars, "connected_stars.csv");
        writer.close();

        long endTime = System.currentTimeMillis();
        System.out.println("\nTime taken to Generate dataset: " + (endTime - startTime) + " ms");

        int totalWeight = 0;

        for (Star star : myStars) {
            
            totalWeight += star.getWeight();
        }

        System.out.println("Total Weight: " + totalWeight);
        // System.out.println(Star.edgeCount);

    }

    private int name;
    private int x;
    private int y;
    private int z;
    private int weight;
    private int profit;
    private ArrayList<Star> connectedStars = new ArrayList<>();

    static int edgeCount;
    final static int TOTAL_EDGE = 54;
    
    public int getName() {
        return name;
    }
    public void setName(int name) {
        this.name = name;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getProfit() {
        return profit;
    }
    public void setProfit(int profit) {
        this.profit = profit;
    }
    public ArrayList<Star> getConnectedStars() {
        return connectedStars;
    }
    public void addConnectedStars(Star connectedStars) {
        this.connectedStars.add(connectedStars);
    }
    public static int getEdgeCount() {
        return edgeCount;
    }
    public static void setEdgeCount(int edgeCount) {
        Star.edgeCount = edgeCount;
    }
    public static int getTotalEdge() {
        return TOTAL_EDGE;
    }

    @Override
    public String toString() {
        return "Star name = " + name + ", x = " + x + ", y = " + y + ", z = " + z + ", weight = " + weight + ", profit = " + profit
                + ", connectedStarsName = [" + connectedStarsName() + "]";
    }

    // @Override
    // public String toString() {
    //     return "Star = " + name + ", connectedStar = [" + connectedStarsName() + "]";
    // }

    public static void outputConnectedStarsToCSV(Star[] myStars, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {

            writer.append("Star Name,Connected Star Name\n");
        
            for (int i = 0; i < myStars.length; i++) {
               // List<Star> connectedStars = myStars[i].getConnectedStars(); // Method to get connected stars
                for (Star star : myStars[i].getConnectedStars()) {
                  //  int connectedIndex = getIndexOfStar(myStars, star);
                    writer.append(i+1 + "," + star.getName() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    public static int calculateDistance(double[] pos1, double[] pos2) {
        double distance = Math.sqrt(Math.pow(pos2[0] - pos1[0], 2) + Math.pow(pos2[1] - pos1[1], 2) + Math.pow(pos2[2] - pos1[2], 2));
        BigDecimal bd = new BigDecimal(distance);
        bd = bd.setScale(0, RoundingMode.HALF_UP);  // Set scale to 0 for rounding to nearest whole number
        return bd.intValue();  // Return the rounded value as an integer
    }

    private String connectedStarsName() {

        StringBuilder connectedStarsName = new StringBuilder();

        for (Star star : connectedStars) {
            
            // System.out.print(star.getName() + ", ");
            connectedStarsName.append(star.getName() + ", ");
        }

        return connectedStarsName.toString();
    }

    public static void cyclicConnect(Star[] myStars) {
        
        for (int i = 0; i < 20; i++)
        {

            if (i == 19) {
    
                myStars[i].addConnectedStars(myStars[0]);
                myStars[i].addConnectedStars(myStars[i-1]);
                Star.edgeCount+=1;
            }
            else if (i >= 1)
            {
                myStars[i].addConnectedStars(myStars[i+1]);
                myStars[i].addConnectedStars(myStars[i-1]);
                Star.edgeCount+=1;
            }
            else if (i == 0)
            {
                myStars[i].addConnectedStars(myStars[i+1]);
                myStars[i].addConnectedStars(myStars[19]);
                Star.edgeCount+=1;
            }
        }
    }

    public static void connect2(Star[] myStars) {
        
        for (int i = 0; i < 20; i++)
        {

            if (i == 19) {
    
                myStars[i].addConnectedStars(myStars[1]);
                Star.edgeCount+=1;
            }
            else if (i == 18)
            {
                myStars[i].addConnectedStars(myStars[0]);
                Star.edgeCount+=1;
            }
            else if (i >= 0)
            {
                myStars[i].addConnectedStars(myStars[i+2]);
                Star.edgeCount+=1;
            }
        }
    }

    public static void connect3(Star[] myStars) {
        
        for (int i = 0; i < 14; i++)
        {   
            myStars[i].addConnectedStars(myStars[i+3]);
            Star.edgeCount+=1;
        }
    }

    public static void setStarProperties(Star[] myStars) { // Set x, y, z, weight & Profit for each stars

        for (int i = 0; i < myStars.length; i++) {
            
            myStars[i].setX(genRandomValue());
            myStars[i].setY(genRandomValue());
            myStars[i].setZ(genRandomValue());
            myStars[i].setWeight(genRandomWeight());
            myStars[i].setProfit(genRandomValue());
        }
    }

    private static int genRandomValue() { // Generate random value for x, y, z & Profit Only

        // Ern Qi 1221301874 + Teng Hui 1211102289 + Xin Thong 1211104274 = 3633307961
        int[] seedRef = {0, 1, 3, 6, 7, 9};

        Random random = new Random();

        switch (random.nextInt(2)) {

            case 0:
                return generateT(random, seedRef);
            case 1:
                return generateH(random, seedRef);
            default:
                return 0;
        }
    }

    private static int genRandomWeight() { // Generate random value for Weight Only

        // Ern Qi 1221301874 + Teng Hui 1211102289 + Xin Thong 1211104274 = 3633307961
        int[] seedRef = {1, 3, 6, 7, 9}; // no '0' to keep the total weight >= 800 

        Random random = new Random();

        return generateT(random, seedRef);
    }

    private static int generateT(Random random, int[] seedRef) { //for 10s

        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length];

        return Integer.parseInt(String.valueOf(temp) + String.valueOf(temp2));
    }

    private static int generateH(Random random, int[] seedRef) { //for 100s

        int temp = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp2 = seedRef[Math.abs(random.nextInt()) % seedRef.length];
        int temp3 = seedRef[Math.abs(random.nextInt()) % seedRef.length];

        return Integer.parseInt(String.valueOf(temp) + String.valueOf(temp2) + String.valueOf(temp3)); //
    }
}

   
   

   
