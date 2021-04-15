package entities;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class Incentive {
    private String id;
    private IncentiveType incentiveType;
    private String dealerId;
    private Date startDate;
    private Date endDate;
    private String title;
    private String description;
    private String disclaimer;
    private CarCategory carCategory;
    private HashSet<String> carVINs;

    public Incentive(String id, IncentiveType incentiveType, String dealerId, Date startDate, Date endDate, String title, String description,
                     String disclaimer, CarCategory carCategory, HashSet<String> carVINs) {
        this.id = id;
        this.incentiveType = incentiveType;
        this.dealerId = dealerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.disclaimer = disclaimer;
        this.carCategory = carCategory;
        this.carVINs = carVINs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IncentiveType getIncentiveType() {
        return incentiveType;
    }

    public void getIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public void setIncentiveType(IncentiveType incentiveType) {
        this.incentiveType = incentiveType;
    }

    public HashSet<String> getCarVINList() {
        return carVINs;
    }

    public void setCarVINList(HashSet<String> carVINs) {
        this.carVINs = carVINs;
    }
}
