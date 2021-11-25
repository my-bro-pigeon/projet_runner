package com.runner;

public class Hero extends AnimatedThing{
    public double nbFireball;

    public Hero(String fileName, double longueur, double hauteur, double x, double y, String imagePath, double positionX, double positionY,double nbFireball) {
        super(fileName, longueur, hauteur, x, y, imagePath, positionX, positionY);
        this.nbFireball = nbFireball;
    }

    public double getNbFireball() {
        return nbFireball;
    }

    public void setNbFireball(double nbFireball) {
        this.nbFireball = nbFireball;
    }
}
