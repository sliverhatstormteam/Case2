package model;

import java.io.Serializable;

public class ItemKinds implements Serializable {
    private String nameOfKinds;
    private String kindsDescription;

    public ItemKinds(String nameOfKinds, String kindsDescription) {
        this.nameOfKinds = nameOfKinds;
        this.kindsDescription = kindsDescription;
    }

    public ItemKinds() {
    }

    public String getNameOfKinds() {
        return nameOfKinds;
    }

    public void setNameOfKinds(String nameOfKinds) {
        this.nameOfKinds = nameOfKinds;
    }

    public String getKindsDescription() {
        return kindsDescription;
    }

    public void setKindsDescription(String kindsDescription) {
        this.kindsDescription = kindsDescription;
    }
}
