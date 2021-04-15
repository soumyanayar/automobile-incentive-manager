public enum IncentiveType {
    DISCOUNT ("Discount Incentive"),
    LOAN ("Loan Incentive"),
    REBATE ("Rebate Incentive"),
    LEASE ("Lease Incentive");

    private String type;

    IncentiveType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }
}
