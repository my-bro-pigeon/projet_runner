package com.runner;

import javafx.geometry.Rectangle2D;

public class LifeBar extends StaticThing{
    public double nbCoeur;

    public LifeBar(String fileName, double x, double y,double longueur, double largeur, String imagePath, double nbCoeur){
        super(fileName, x,y,longueur,largeur,imagePath);
        this.nbCoeur = nbCoeur;
        this.getImageview().setViewport(new Rectangle2D(0,4, (longueur/6)*nbCoeur+5, largeur));


    }

    public double getNbCoeur(){return this.nbCoeur;}
}
