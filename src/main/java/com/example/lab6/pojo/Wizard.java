package com.example.lab6.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document("Wizard")
public class Wizard {
    @Id
    private String _id;
    private String sex;
    private String name;
    private String school;
    private String house;
    private double money;
    private String position;
    public Wizard(){

    }
    public Wizard(String _id, String sex, String name, String school, String house, double money, String position){
        this._id = _id;
        this.sex = sex;
        this.name = name;
        this.school = school;
        this.house = house;
        this.money = money;
        this.position = position;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getHouse() {
        return house;
    }

    public double getMoney() {
        return money;
    }

    public String getPosition() {
        return position;
    }
}
