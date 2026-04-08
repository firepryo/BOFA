
public class Bond {
    private final String bondId;
    private final double coupon;
    private final int frequency;
    private final int monthsSinceCoupon;

    public Bond(String bondId, double coupon, int frequency, int monthsSinceCoupon) {
        this.bondId = bondId;
        this.coupon = coupon;
        this.frequency = frequency;
        this.monthsSinceCoupon = monthsSinceCoupon;
    }

    public String getBondId() { return bondId; }
    public double getCoupon() { return coupon; }
    public int getFrequency() { return frequency; }
    public int getMonthsSinceCoupon() { return monthsSinceCoupon; }
}