package net.ukr.tigor.domain;

import java.util.Objects;

public class Elec extends Bike {

    private int maxSpeed;
    private int weight;
    private boolean lightsFrontBack;
    private int batteryCapacity;
    private String color;
    private int price;

    public Elec(TypeOfBike typeOfBike, String brand) {
        super(typeOfBike, brand);
    }

    public Elec(TypeOfBike typeOfBike, String[] arrFields) {
        this(typeOfBike, arrFields[0].substring(7));
        this.maxSpeed = Integer.parseInt(arrFields[1]);
        this.weight = Integer.parseInt(arrFields[2]);
        this.lightsFrontBack = Boolean.getBoolean(arrFields[3]);
        this.batteryCapacity = Integer.parseInt(arrFields[4]);
        this.color = arrFields[5];
        this.price = Integer.parseInt(arrFields[6]);
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isLightsFrontBack() {
        return lightsFrontBack;
    }

    public void setLightsFrontBack(boolean lightsFrontBack) {
        this.lightsFrontBack = lightsFrontBack;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String getResultStringForFile() {
        String str = "";

        str = getTypeOfBike().label + " " + getBrand() + "; " + maxSpeed + "; "
                + weight + "; " + lightsFrontBack + "; " + batteryCapacity + "; " + color + "; " + price;
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Elec)) return false;
        if (!super.equals(o)) return false;
        Elec elec = (Elec) o;
        return getMaxSpeed() == elec.getMaxSpeed() &&
                getWeight() == elec.getWeight() &&
                isLightsFrontBack() == elec.isLightsFrontBack() &&
                getBatteryCapacity() == elec.getBatteryCapacity() &&
                getPrice() == elec.getPrice() &&
                Objects.equals(getColor(), elec.getColor());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getMaxSpeed(), getWeight(), isLightsFrontBack(), getBatteryCapacity(), getColor(), getPrice());
    }

    @Override
    public String toString() {
        return "E-BIKE " + getBrand() + " with " + batteryCapacity + " mAh and" + ((lightsFrontBack) ? "" : " no") + " head/tail light" + '\n'
                + "Price: " + price + " euros";
    }
}
