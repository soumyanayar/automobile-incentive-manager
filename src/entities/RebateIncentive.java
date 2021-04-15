package entities;

import java.util.Date;
import java.util.HashMap;

public class RebateIncentive extends Incentive{
    public HashMap<String, Double> getRebateMap() {
        return rebateMap;
    }

    public void setRebateMap(HashMap<String, Double> rebateMap) {
        this.rebateMap = rebateMap;
    }

    private HashMap<String, Double> rebateMap;

    public RebateIncentive(String id, IncentiveType incentiveType, String dealerId, Date startDate, Date endDate, String title, String description,
                           String disclaimer, CarCategory carCategory, String make,
                           String model, String startYear, String endYear, String thresholdMiles, HashMap<String, Double> rebateMap) {
        super(id, incentiveType, dealerId, startDate, endDate, title, description, disclaimer, carCategory, make, model, startYear, endYear, thresholdMiles);
        this.rebateMap = rebateMap;
    }
}
