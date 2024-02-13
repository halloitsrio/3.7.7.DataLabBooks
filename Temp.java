import core.data.*; 

public class Temp {
    public static void main(String[] args) {
        DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/KATL.xml");
        ds.load();
        float temp = ds.fetchFloat("temp_f");
        System.out.println("Temperature: " + temp);
        
    }
}
