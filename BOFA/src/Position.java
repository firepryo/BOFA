public class Position {
    private int quantity;
    private double presentValue;

    public Position() {
        this.quantity = 0;
        this.presentValue = 0.0;
    }

    public void addQuantity(int amount) { this.quantity += amount; }
    public void subtractQuantity(int amount) { this.quantity -= amount; }

    public int getQuantity() { return quantity; }

    public void setPresentValue(double pv) { this.presentValue = pv; }
    public double getPresentValue() { return presentValue; }
}