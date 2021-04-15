package entities;

import java.util.Date;
import java.util.HashSet;

public class CashDiscountIncentive extends Incentive {
    private double value;
    private CashDiscountType cashDiscountType;

    public CashDiscountIncentive(String id, IncentiveType incentiveType, String dealerId, Date startDate, Date endDate,
                                 String title, String description, String disclaimer, CarCategory carCategory,
                                 HashSet<String> carVINs, double value, CashDiscountType cashDiscountType) {
        super(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carCategory, carVINs);
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
