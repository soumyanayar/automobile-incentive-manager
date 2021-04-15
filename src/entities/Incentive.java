package entities;

import java.util.Date;
import java.util.List;

public abstract class Incentive {
    private String id;
    private String dealerId;
    private Date startDate;
    private Date endDate;
    private String title;
    private String description;
    private String disclaimer;
    private List<PaymentType> validPaymentTypes;
    private CarCategory carCategory;
    private String make;
    private String model;
    private String startYear;
    private String endYear;
    private String thresholdMiles;

    public Incentive(String id, String dealerId, Date startDate, Date endDate, String title, String description,
                     String disclaimer, List<PaymentType> validPaymentTypes, CarCategory carCategory, String make,
                     String model, String startYear, String endYear, String thresholdMiles) {
        this.id = id;
        this.dealerId = dealerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.disclaimer = disclaimer;
        this.validPaymentTypes = validPaymentTypes;
        this.carCategory = carCategory;
        this.make = make;
        this.model = model;
        this.startYear = startYear;
        this.endYear = endYear;
        this.thresholdMiles = thresholdMiles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<PaymentType> getValidPaymentTypes() {
        return validPaymentTypes;
    }

    public void setValidPaymentTypes(List<PaymentType> validPaymentTypes) {
        this.validPaymentTypes = validPaymentTypes;
    }

    public CarCategory getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getThresholdMiles() {
        return thresholdMiles;
    }

    public void setThresholdMiles(String thresholdMiles) {
        this.thresholdMiles = thresholdMiles;
    }
}
