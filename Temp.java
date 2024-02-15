import core.data.*; 

public class Temp {
    public static void main(String[] args) {
        DataSource ds = DataSource.connectAs("CSV", "Temp.java");
        ds.load();
        ds.printUsageString();
        
    }
}
