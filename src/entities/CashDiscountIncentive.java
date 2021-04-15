package entities;

import java.util.Date;
import java.util.List;

public class CashDiscountIncentive extends Incentive {
    private double value;
    private CashDiscountType cashDiscountType;

    public CashDiscountIncentive(String id, String dealerId, Date startDate, Date endDate, String title,
                                 String description, String disclaimer, List<PaymentType> validPaymentTypes,
                                 CarCategory carCategory, String make, String model, String startYear,
                                 String endYear, String thresholdMiles, double value, CashDiscountType cashDiscountType) {
        super(id, dealerId, startDate, endDate, title, description, disclaimer, validPaymentTypes, carCategory, make,
                model, startYear, endYear, thresholdMiles);
        this.value = value;
        this.cashDiscountType = cashDiscountType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public CashDiscountType getCashDiscountType() {
        return cashDiscountType;
    }

    public void setCashDiscountType(CashDiscountType cashDiscountType) {
        this.cashDiscountType = cashDiscountType;
    }
}
