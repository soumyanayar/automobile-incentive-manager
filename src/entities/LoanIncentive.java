package entities;

import java.util.Date;
import java.util.List;

public class LoanIncentive extends Incentive {
    private double apr;
    private int months;

    public LoanIncentive(String id, String dealerId, Date startDate, Date endDate, String title, String description,
                         String disclaimer, List<PaymentType> validPaymentTypes, CarCategory carCategory, String make,
                         String model, String startYear, String endYear, String thresholdMiles, double apr, int months) {
        super(id, dealerId, startDate, endDate, title, description, disclaimer, validPaymentTypes, carCategory,
                make, model, startYear, endYear, thresholdMiles);
        this.apr = apr;
        this.months = months;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }
}
