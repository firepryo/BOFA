public class TradeEvent {
    private final int eventId;
    private final String desk;
    private final String trader;
    private final String bondId;
    private final String buySell;
    private final int quantity;
    private final double cleanPrice;

    public TradeEvent(int eventId, String desk, String trader, String bondId, String buySell, int quantity, double cleanPrice) {
        this.eventId = eventId;
        this.desk = desk;
        this.trader = trader;
        this.bondId = bondId;
        this.buySell = buySell;
        this.quantity = quantity;
        this.cleanPrice = cleanPrice;
    }

    public int getEventId() { return eventId; }
    public String getDesk() { return desk; }
    public String getTrader() { return trader; }
    public String getBondId() { return bondId; }
    public String getBuySell() { return buySell; }
    public int getQuantity() { return quantity; }
    public double getCleanPrice() { return cleanPrice; }
}
