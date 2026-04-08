import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvDataLoader {

    public static Map<String, Bond> loadBonds(String filepath) {
        Map<String, Bond> bonds = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Bond bond = new Bond(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                bonds.put(bond.getBondId(), bond);
            }
        } catch (IOException e) {
            System.err.println("Error reading bonds: " + e.getMessage());
        }
        return bonds;
    }

    public static List<TradeEvent> loadEvents(String filepath) {
        List<TradeEvent> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                events.add(new TradeEvent(
                        Integer.parseInt(data[0]), data[1], data[2], data[3], data[4],
                        Integer.parseInt(data[5]), Double.parseDouble(data[6])
                ));
            }
        } catch (IOException e) {
            System.err.println("Error reading events: " + e.getMessage());
        }
        return events;
    }
}