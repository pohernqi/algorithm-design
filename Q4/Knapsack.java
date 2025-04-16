package Q4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Knapsack {
    public static void main(String[] args) {
        String csvFile = "./dataSet2.csv";
        String line = "";
        String csvSplitBy = ",";

        ArrayList<Integer> num_of_stars = new ArrayList<>();
        ArrayList<Integer> stars_weight = new ArrayList<>();
        ArrayList<Integer> stars_profit = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] star = line.split(csvSplitBy);

                num_of_stars.add(Integer.parseInt(star[0].trim()));
                stars_weight.add(Integer.parseInt(star[4].trim()));
                stars_profit.add(Integer.parseInt(star[5].trim()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of Stars: " + num_of_stars);
        System.out.println("Stars Weight: " + stars_weight);
        System.out.println("Stars Profit: " + stars_profit);

        int maxCapacity = 800;  // Maximum capacity
        int[][] knapsackTable = new int[num_of_stars.size() + 1][maxCapacity + 1];  // Table row and column 21 x 801
        ArrayList<Integer> selectedStars = new ArrayList<>();
        int[] result = knapsack(stars_weight, stars_profit, num_of_stars, maxCapacity, knapsackTable, selectedStars);

        System.out.println("Total profit: " + result[0]);
        System.out.println("Total weight: " + result[1]);

        saveResults("Q4/Knapsack.csv", knapsackTable, selectedStars, result[0], result[1], num_of_stars, stars_weight, stars_profit);
    }

    public static int[] knapsack(ArrayList<Integer> weights, ArrayList<Integer> profits, ArrayList<Integer> stars, int maxCapacity, int[][] knapsackTable, ArrayList<Integer> selectedStars) {
        int n = weights.size(); // Number of items

        // Create table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= maxCapacity; w++) {
                // Condition 1, if bag capacity =0 or no items, profit is 0
                if (i == 0 || w == 0) {
                    knapsackTable[i][w] = 0;
                }
                // Condition 2, items weight <= bag weight
                else if (weights.get(i - 1) <= w) {
                    knapsackTable[i][w] = Math.max(profits.get(i - 1) + knapsackTable[i - 1][w - weights.get(i - 1)], knapsackTable[i - 1][w]);
                }
                // Condition 3, items weight > bag weight
                else {
                    knapsackTable[i][w] = knapsackTable[i - 1][w];
                }
            }
        }

        // To find out which items are included, we need to backtrack
        int profit_result = knapsackTable[n][maxCapacity];
        int w = maxCapacity;
        int totalWeight = 0;

        for (int i = n; i > 0 && profit_result > 0; i--) {
            // If the profit is different, then this item was included
            if (profit_result != knapsackTable[i - 1][w]) {
                selectedStars.add(i - 1);

                // Since this item is included, its weight is subtracted
                profit_result -= profits.get(i - 1);
                w -= weights.get(i - 1);

                // Add weight of the selected item
                totalWeight += weights.get(i - 1);
            }
        }

        System.out.println("Selected stars: " + selectedStars);

        return new int[]{knapsackTable[n][maxCapacity], totalWeight};
    }

    public static void saveResults(String filename, int[][] knapsackTable, ArrayList<Integer> selectedStars, int totalProfit, int totalWeight, ArrayList<Integer> num_of_stars, ArrayList<Integer> stars_weight, ArrayList<Integer> stars_profit) {
        try (FileWriter writer = new FileWriter(filename)) {
            
            writer.write("item/W,");
    
            // Write the header row (weights)
            for (int j = 0; j < knapsackTable[0].length; j++) {
                writer.write(j + ",");
            }
            writer.write("\n");
    
            // Write the table with items as rows
            for (int i = 0; i < knapsackTable.length; i++) {
                if (i == 0) {
                    writer.write(0 + ","); // For the first row (no items)
                } else {
                    writer.write(num_of_stars.get(i - 1) + ",");
                }
                for (int j = 0; j < knapsackTable[i].length; j++) {
                    writer.write(knapsackTable[i][j] + ",");
                }
                writer.write("\n");
            }

            writer.write("\nSelected stars:\n");
            for (int i : selectedStars) {
                writer.write(String.format("Star Name: %d, Weight: %d, Profit: %d\n", num_of_stars.get(i), stars_weight.get(i), stars_profit.get(i)));
            }

            writer.write("\nTotal profit: " + totalProfit + "\n");
            writer.write("Total weight: " + totalWeight + "\n");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
