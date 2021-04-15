package entities;

import java.util.Date;
import java.util.List;

public class LeasingIncentive extends Incentive{
    private int months;
    private double signingPay;
    private double monthlyPay;

    public LeasingIncentive(String id, IncentiveType incentiveType, String dealerId, Date startDate, Date endDate,
                            String title, String description, String disclaimer, CarCategory carCategory, String make,
                            String model, String startYear, String endYear, String thresholdMiles, int months,
                            double signingPay, double monthlyPay) {
        super(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carCategory,
                make, model, startYear, endYear, thresholdMiles);
        this.months = months;
        this.signingPay = signingPay;
        this.monthlyPay = monthlyPay;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public double getSigningPay() {
        return signingPay;
    }

    public void setSigningPay(double signingPay) {
        this.signingPay = signingPay;
    }

    public double getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(double monthlyPay) {
        this.monthlyPay = monthlyPay;
    }
}
