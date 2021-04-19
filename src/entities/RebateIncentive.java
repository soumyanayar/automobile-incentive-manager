package entities;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class RebateIncentive extends Incentive{
    public HashMap<String, Double> getRebateMap() {
        return rebateMap;
    }

    public void setRebateMap(HashMap<String, Double> rebateMap) {
        this.rebateMap = rebateMap;
    }

    private HashMap<String, Double> rebateMap;

    public RebateIncentive(String id, String dealerId, Date startDate, Date endDate, String title, String description,
                           String disclaimer, HashSet<String> carVINs, HashMap<String, Double> rebateMap) {
        super(id, IncentiveType.REBATE, dealerId, startDate, endDate, title, description, disclaimer, carVINs);
        this.rebateMap = rebateMap;
    }
}
