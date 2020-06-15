package net.ukr.tigor.domain;

public enum TypeOfBike {
    FOLDING("FOLDING BIKE"),SPEEDELEC("SPEEDELEC"),ELEC("E-BIKE");
    public final String label;
    TypeOfBike(String label) {
        this.label = label;
    }

    public static TypeOfBike valueOfLable(String label){
        for(TypeOfBike e: values()){
            if(e.label.equals(label)){
                return e;
            }
        }
        return null;
    }

    public String getLabel() {
        return label;
    }
}
