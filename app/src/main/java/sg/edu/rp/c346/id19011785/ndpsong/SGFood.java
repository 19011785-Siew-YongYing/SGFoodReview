package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SGFood implements Serializable {

    private int id;
    private String name;
    private String desc;
    private double price;
    private int stars;

    public SGFood(int id, String name, String desc, double price, int stars) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.stars = stars;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        String starsStr = "";
        for (int i = 0; i < stars; i++){
            starsStr += "*";
        }

        return starsStr;
    }
}
