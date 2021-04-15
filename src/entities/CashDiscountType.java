package entities;

public enum CashDiscountType {
    PERCENTAGE ("Percentage"),
    FLATAMOUNT ("Flat Amount");

    private String type;

    CashDiscountType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }

    public static CashDiscountType fromString(String text) {
        for (CashDiscountType cashDiscountType : CashDiscountType.values()) {
            if (cashDiscountType.type.equalsIgnoreCase(text)) {
                return cashDiscountType;
            }
        }
        return null;
    }
}
