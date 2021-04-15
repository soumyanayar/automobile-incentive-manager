package entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class LoanIncentive extends Incentive {
    private double apr;
    private int months;

    public LoanIncentive(String id, IncentiveType incentiveType, String dealerId, Date startDate, Date endDate, String title, String description,
                         String disclaimer, CarCategory carCategory, HashSet<String> carVINs, double apr, int months) {
        super(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carCategory, carVINs);
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
