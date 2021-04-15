package entities;

import java.sql.Blob;

public class Car {

    // instance variables
    private String stockNum;
    private String dealerId;
    private CarCategory carCategory;
    private int seatCount;
    private int mileage;
    private double msrp;
    private String VIN;
    private String color;
    private int rating;
    private String description;
    private String transmission;
    private String engine;
    private String fuel;
    private int year;
    private String make;
    private String model;
    private String type;
    private String location;
    private Blob image;

    // default constructor
    public Car(){
    }

    // constructor for MySQL query
    public Car(String stockID, String VIN, String dealerID, String make, String model,
               int year, CarCategory category, double price, String color, int mileage,
               Blob img, String incentiveID, String discountPrice, int rating){

        this.stockNum = stockID;
        this.VIN = VIN;
        this.dealerId = dealerID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.carCategory = category;
        this.msrp = price;
        this.color = color;
        this.mileage = mileage;
        this.image = img;
        this.rating = rating;
    }

    public Car(String stockNum , String make, String model, int year, double msrp,
               String color, String location, int mileage, String dealerId,
               CarCategory carCategory, int seatCount, String VIN, int rating,
               String description, String transmission, String engine, String fuel, String type){
        this.stockNum = stockNum;
        this.make = make;
        this.seatCount = seatCount;
        this.VIN = VIN;
        this.rating = rating;
        this.description = description;
        this.transmission = transmission;
        this.engine = engine;
        this.fuel = fuel;
        this.type = type;
        this.model = model;
        this.year = year;
        this.msrp = msrp;
        this.color = color;
        this.location = location;
        this.mileage = mileage;
        this.dealerId = dealerId;
        this.carCategory = carCategory;
    }

    /*******************  GETTERS  *******************/

    public String getstockNum() {
        return this.stockNum;
    }

    public Blob getImage(){
        return this.image;
    }

    public int getSeatCount(){
        return seatCount;
    }

    public String getVIN(){
        return this.VIN;
    }

    public int getRating(){
        return this.rating;
    }

    public String getDescription(){
        return this.description;
    }

    public String getTransmission(){
        return this.transmission;
    }

    public String getEngine(){
        return this.engine;
    }

    public String getFuel(){
        return this.fuel;
    }

    public String getType(){
        return this.type;
    }

    public String getMake(){
        return this.make;
    }

    public String getModel(){
        return this.model;
    }

    public int getYear(){
        return this.year;
    }

    public double getMSRP(){
        return this.msrp;
    }

    public String getColor(){
        return this.color;
    }

    public String getLocation(){
        return this.location;
    }

    public int getMileage(){
        return this.mileage;
    }

    public String getDealerId() {
        return this.dealerId;
    }

    public CarCategory getCarCategory() {
        return this.carCategory;
    }

    /*******************  SETTERS  *******************/
    public void setID(String stockNum){
        this.stockNum = stockNum;
    }

    public void setMake(String make){
        this.make = make;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setMSRP(double msrp){
        this.msrp = msrp;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setMileage(int mileage){
        this.mileage =  mileage;
    }

    public void setCarCategory(CarCategory carCategory) {
        this.carCategory = carCategory;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
    public void setSeatCount(int seatCount){
        this.seatCount = seatCount;
    }
    public void setVIN(String VIN){
        this.VIN = VIN;
    }
    public void setRating(int rating){
        this.rating = rating;
    }
    public void setDescription(String  description){
        this.description = description;
    }
    public void setTransmission(String transmission){
        this.transmission = transmission;
    }
    public void setEngine(String engine){
        this.engine = engine;
    }
    public void setFuel(String fuel){
        this.fuel = fuel;
    }
    public void setType(String type){
        this.type = type;
    }
}