package net.ukr.tigor.domain;

import java.util.Objects;

public class Folding extends Bike {

    private int sizeOfwhels;
    private int numberOfGears;
    private int weight;
    private boolean lightsFrontBack;
    private String color;
    private int price;

    public Folding(TypeOfBike typeOfBike, String brand) {

        super(typeOfBike, brand);

    }

    public Folding(TypeOfBike typeOfBike, String[] arrFields) {

        this(typeOfBike, arrFields[0].substring(13));

        this.sizeOfwhels = Integer.parseInt(arrFields[1]);
        this.numberOfGears = Integer.parseInt(arrFields[2]);
        this.weight = Integer.parseInt(arrFields[3]);
        this.lightsFrontBack = Boolean.valueOf(arrFields[4]);
        this.color = arrFields[5];
        this.price = Integer.parseInt(arrFields[6]);
    }


    public int getSizeOfwhels() {
        return sizeOfwhels;
    }

    public void setSizeOfwhels(int sizeOfwhels) {
        this.sizeOfwhels = sizeOfwhels;
    }

    public int getNumberOfGears() {
        return numberOfGears;
    }

    public void setNumberOfGears(int numberOfGears) {
        this.numberOfGears = numberOfGears;
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

        str = getTypeOfBike().label + " " + getBrand() + "; " + sizeOfwhels + "; "
                + numberOfGears + "; " + weight + "; " + lightsFrontBack + "; " + color + "; " + price;

        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folding)) return false;
        if (!super.equals(o)) return false;
        Folding folding = (Folding) o;
        return getSizeOfwhels() == folding.getSizeOfwhels() &&
                getNumberOfGears() == folding.getNumberOfGears() &&
                getWeight() == folding.getWeight() &&
                isLightsFrontBack() == folding.isLightsFrontBack() &&
                getPrice() == folding.getPrice() &&
                Objects.equals(getColor(), folding.getColor());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getSizeOfwhels(), getNumberOfGears(), getWeight(), isLightsFrontBack(), getColor(), getPrice());
    }

    @Override
    public String toString() {
        return "FOLDING BIKE " + getBrand() + " with " + numberOfGears + " gear(s) and" + ((lightsFrontBack) ? "" : " no") + " head/tail light" + '\n'
                + "Price: " + price + " euros";

    }
}
