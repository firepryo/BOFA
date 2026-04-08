public class ValuationCalculator {
    private static final double FACE_VALUE = 100.0;

    // AI = (coupon_rate / frequency) * accrued_fraction * face_value
    public static double calculateAccruedInterest(Bond bond) {
        double periodLength = 12.0 / bond.getFrequency();
        double accruedFraction = bond.getMonthsSinceCoupon() / periodLength;
        return (bond.getCoupon() * FACE_VALUE / bond.getFrequency()) * accruedFraction;
    }

    // Dirty Price = Clean Price + AI
    public static double calculateDirtyPrice(double cleanPrice, double accruedInterest) {
        return cleanPrice + accruedInterest;
    }

    // PV = Position * Dirty Price
    public static double calculatePV(int positionQuantity, double dirtyPrice) {
        return positionQuantity * dirtyPrice;
    }
}