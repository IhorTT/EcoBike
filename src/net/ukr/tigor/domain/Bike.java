package net.ukr.tigor.domain;

import java.util.Objects;

public class Bike {

    private TypeOfBike typeOfBike;
    private String brand;
    private boolean newBike;

    public Bike(TypeOfBike typeOfBike, String brand) {
        this.typeOfBike = typeOfBike;
        this.brand = brand;
    }

    public TypeOfBike getTypeOfBike() {
        return typeOfBike;
    }

    public void setTypeOfBike(TypeOfBike typeOfBike) {
        this.typeOfBike = typeOfBike;
    }

    public String  getBrand() {
        return brand;
    }

    public void setBrand(String  brand) {
        this.brand = brand;
    }

    public boolean equalsByParams(Object obj){
        Bike bike = (Bike) obj;
        return getTypeOfBike() == bike.getTypeOfBike() &&
                Objects.equals(getBrand(), bike.getBrand());
    }

    public boolean isNewBike() {
        return newBike;
    }

    public void setNewBike(boolean newBike) {
        this.newBike = newBike;
    }

    public String getResultStringForFile(){
        return "";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bike)) return false;
        Bike bike = (Bike) o;
        return getTypeOfBike() == bike.getTypeOfBike() &&
                Objects.equals(getBrand(), bike.getBrand());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTypeOfBike(), getBrand());
    }
}
