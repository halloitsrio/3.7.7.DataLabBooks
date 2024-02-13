import core.data.*; 

public class Temp {
    public static void main(String[] args) {
        DataSource ds = DataSource.connect("https://www.kaggle.com/datasets/shivamb/netflix-shows");
        ds.load();
        ds.printUsageString();
        
    }
}
