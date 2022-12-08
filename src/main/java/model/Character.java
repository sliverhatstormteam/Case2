package model;

import java.io.Serializable;

public class Character implements Serializable {

    private int id;
    private String name;
    private int age;
    private int hp;
    private int atk;


    public Character() {
    }

    public Character(int id, String name, int age, int hp, int atk) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hp = hp;
        this.atk = atk;
    }

    public Character(String name, int age, int atk, int hp) {
        this.name = name;
        this.age = age;
        this.atk = atk;
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", hp=" + hp +
                ", atk=" + atk +
                '}';
    }


}
