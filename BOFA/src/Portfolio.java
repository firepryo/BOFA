import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Portfolio {
    private final Map<String, Bond> bondReference;
    private final Map<String, Double> latestCleanPrices;
    private final Map<TradeKey, Position> positions;
    private int currentEventId;

    public Portfolio(Map<String, Bond> bondReference) {
        this.bondReference = bondReference;
        this.latestCleanPrices = new HashMap<>();
        this.positions = new HashMap<>();
        this.currentEventId = 0;
    }

    public void processEvent(TradeEvent event) {
        this.currentEventId = event.getEventId();

        // 1. Update the market price
        latestCleanPrices.put(event.getBondId(), event.getCleanPrice());

        // 2. Locate or create the position for this specific desk/trader/bond
        TradeKey key = new TradeKey(event.getDesk(), event.getTrader(), event.getBondId());
        positions.putIfAbsent(key, new Position());
        Position pos = positions.get(key);

        // 3. Apply the trade
        if (event.getBuySell().equalsIgnoreCase("BUY")) {
            pos.addQuantity(event.getQuantity());
        } else {
            pos.subtractQuantity(event.getQuantity());
        }

        // 4. Recalculate $PV$ for ALL positions holding the bond that just changed price
        recalculatePresentValue(event.getBondId());
    }

    private void recalculatePresentValue(String bondId) {
        Bond bond = bondReference.get(bondId);
        double cleanPrice = latestCleanPrices.get(bondId);

        double accruedInterest = ValuationCalculator.calculateAccruedInterest(bond);
        double dirtyPrice = ValuationCalculator.calculateDirtyPrice(cleanPrice, accruedInterest);

        for (Map.Entry<TradeKey, Position> entry : positions.entrySet()) {
            if (entry.getKey().bondId.equals(bondId)) {
                Position pos = entry.getValue();
                double newPv = ValuationCalculator.calculatePV(pos.getQuantity(), dirtyPrice);
                pos.setPresentValue(newPv);
            }
        }
    }

    public void printSnapshot() {
        System.out.println("\n=== PORTFOLIO SNAPSHOT (Event " + currentEventId + ") ===");
        double totalPv = 0.0;

        for (Map.Entry<TradeKey, Position> entry : positions.entrySet()) {
            TradeKey k = entry.getKey();
            Position p = entry.getValue();
            if (p.getQuantity() != 0) {
                System.out.printf("Desk: %s | Trader: %s | Bond: %s | Pos: %d | PV: $%,.2f%n",
                        k.desk, k.trader, k.bondId, p.getQuantity(), p.getPresentValue());
                totalPv += p.getPresentValue();
            }
        }
        System.out.printf("TOTAL PORTFOLIO PV: $%,.2f%n\n", totalPv);
    }

    // --- Private Inner Class for Composite Keys ---
    private static class TradeKey {
        private final String desk;
        private final String trader;
        private final String bondId;

        public TradeKey(String desk, String trader, String bondId) {
            this.desk = desk;
            this.trader = trader;
            this.bondId = bondId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TradeKey tradeKey = (TradeKey) o;
            return desk.equals(tradeKey.desk) && trader.equals(tradeKey.trader) && bondId.equals(tradeKey.bondId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(desk, trader, bondId);
        }
    }
}