package model;

import java.io.Serializable;

public class Land implements Serializable {
    private int id;
    private String name;
    private String address;


    public Land(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Land{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
