package com.runner;

// use to display static element (background or number of life)

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class StaticThing {
    private double longueur;
    private double largeur;
    private double x;
    private double y;

    private ImageView imageview;

    public StaticThing(String fileName, double x, double y,double longueur, double largeur, String imagePath){
        this.longueur=longueur;
        this.largeur=largeur;
        this.x = x;
        this.y = y;
        Image spriteSheet = new Image(imagePath);
        this.imageview = new ImageView(spriteSheet);
        this.imageview.setX(x);
        this.imageview.setY(y);


    }
    public ImageView getImageview() {return imageview;}

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
