package Q1;
import java.io.IOException;

public class DataSet2 {
    public static void main(String[] args) throws IOException {
        
        Star[] myStars = new Star[20];
        
        for (int i = 0; i < myStars.length; i++) {
            
            myStars[i] = new Star();
            myStars[i].setName(i+1);
        }

        new Star(myStars);
    }
}
