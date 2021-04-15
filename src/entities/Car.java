package entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jasmineshin
 */
public class Car {
    
    private String id;
    private String make;
    private String model;
    private int year;
    private double msrp;
    private String color;
    private String location;
    private int mileage;
    private String dealerId;
    private CarCategory carCategory;

    //engine
    //picture
    
    public Car(String id, String make, String model, int year, double msrp,
                String color, String location, int mileage, String dealerId, CarCategory carCategory){
        this.id = id;
        this.make = make;
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
    public String getID() {
        return this.id;
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
    public void setID(String id){
        this.id = id;
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

    public void setDealerId() {
        this.dealerId = dealerId;
    }
}
