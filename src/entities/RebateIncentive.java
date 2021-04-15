package entities;

import java.util.Date;
import java.util.List;

public class RebateIncentive extends Incentive{
    private String type;
    private double amount;

    public RebateIncentive(String id, String dealerId, Date startDate, Date endDate, String title, String description,
                           String disclaimer, List<PaymentType> validPaymentTypes, CarCategory carCategory, String make,
                           String model, String startYear, String endYear, String thresholdMiles, String type, double amount) {
        super(id, dealerId, startDate, endDate, title, description, disclaimer, validPaymentTypes, carCategory, make, model, startYear, endYear, thresholdMiles);
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
