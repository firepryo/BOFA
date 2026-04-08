import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Load Data
        Map<String, Bond> bonds = CsvDataLoader.loadBonds("data/bonds.csv");
        List<TradeEvent> events = CsvDataLoader.loadEvents("data/events.csv");

        // 2. Initialize Portfolio
        Portfolio portfolio = new Portfolio(bonds);

        System.out.println("System Initialized. " + bonds.size() + " bonds and " + events.size() + " events loaded.");

        // 3. Run Interactive Console
        Scanner scanner = new Scanner(System.in);
        int eventIndex = 0;
        boolean running = true;

        while (running) {
            System.out.println("1. Process Next Event");
            System.out.println("2. Process All Remaining Events");
            System.out.println("3. Show Snapshot");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (eventIndex < events.size()) {
                        TradeEvent ev = events.get(eventIndex);
                        portfolio.processEvent(ev);
                        System.out.println("Processed Event " + ev.getEventId());
                        eventIndex++;
                    } else {
                        System.out.println("No more events.");
                    }
                    break;
                case "2":
                    while (eventIndex < events.size()) {
                        portfolio.processEvent(events.get(eventIndex));
                        eventIndex++;
                    }
                    System.out.println("All events processed.");
                    break;
                case "3":
                    portfolio.printSnapshot();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
        scanner.close();
    }
}