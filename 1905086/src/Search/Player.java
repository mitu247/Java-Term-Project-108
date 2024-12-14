package Search;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Player implements Serializable {
    private String name;
    private String country;
    private String club;
    private String position;
    private String price;
    private int age;
    private int number;
    private double height;
    private double weeklySalary;
    public Player(){

    }
    public Player(String name,String country,int age,double height,String club,String position,int number,double weeklySalary){
        this.name=name;
        this.country=country;
        this.age=age;
        this.height=height;
        this.club=club;
        this.position=position;
        this.number=number;
        this.weeklySalary=weeklySalary;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setCountry(String country){
        this.country=country;
    }
    public String getCountry(){
        return country;
    }
    public void setAge(int age){
        this.age=age;
    }
    public int getAge(){
        return age;
    }
    public void setHeight(double height){
        this.height=height;
    }
    public double getHeight(){
        return height;
    }
    public void setClub(String club){
        this.club=club;
    }
    public String getClub(){
        return club;
    }
    public void setPosition(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
    public void setNumber(int number){
        this.number=number;
    }
    public int getNumber(){
        return number;
    }
    public void setWeeklySalary(double weeklySalary){
        this.weeklySalary=weeklySalary;
    }
    public double getWeeklySalary(){
        return weeklySalary;
    }
    public String toString(){
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);

        return "Name         : "+name+
                "\nCountry      : "+country+
                "\nAge          : "+age+" in years" +
                "\nHeight       : "+ height+" in meters" +
                "\nClub         : "+ club+
                "\nPosition     : "+position+
                "\nNumber       : "+number+
                "\nWeeklySalary : "+df.format(weeklySalary)+"\n";
    }

    public void setPrice(String fixedPrice) {
        this.price=fixedPrice;
    }

    public String getPrice() {
        return price;
    }
}
