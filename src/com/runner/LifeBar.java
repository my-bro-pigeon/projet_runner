package com.runner;

import javafx.geometry.Rectangle2D;

/* Cette classe sert à afficher le nombre de vies symbolisé par les pokeballs grisées ou non en haut à droite
(comme durant les combats dans les jeux sur DS)*/

public class LifeBar extends StaticThing{
    public double nbCoeur;

    public LifeBar(String fileName, double x, double y,double longueur, double largeur, String imagePath, double nbCoeur){
        super(fileName, x,y,longueur,largeur,imagePath);
        this.nbCoeur = nbCoeur;
        this.getImageview().setViewport(new Rectangle2D(0,150-25*nbCoeur, longueur, largeur));


    }

    public double getNbCoeur(){return this.nbCoeur;}

    public void setNbCoeur(double nbCoeur) {
        this.nbCoeur = nbCoeur;
    }

    public void updateNbCoeur(){
        nbCoeur--;
        this.getImageview().setViewport(new Rectangle2D(0,150-25*nbCoeur, this.getLongueur(), this.getLargeur()));
    }

}
