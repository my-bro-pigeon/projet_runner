package com.runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Ennemi extends AnimatedThing {
    private double position = 0;
    public Ennemi(String fileName, double longueur, double hauteur, double x, double y, String imagePath, double positionX, double positionY) {
        super(fileName, longueur, hauteur, x, y, imagePath, positionX, positionY);
        this.position=0;

    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }



    public void updateDracaufeu() {

        double xNmoinsUn = getX();
        setX(xNmoinsUn - 25);

        //animation du dracaufeu
        if (position >= 14 && position <= 15) {
            setPosx(283);
            setPosy(66);
            position++;
        }
        if (position >= 12 && position <= 13) {
            setPosx(188);
            setPosy(66);
            position++;
        }
        if (position >= 10 && position <= 11) {
            setPosx(96);
            setPosy(66);
            position++;
        }
        if (position >= 8 && position <= 9) {
            setPosx(0);
            setPosy(66);
            position++;
        }
        if (position >= 6 && position <= 7) {
            setPosx(264);
            setPosy(0);
            position++;
        }
        if (position >= 4 && position <= 5) {
            setPosx(176);
            setPosy(0);
            position++;
        }
        if (position >= 2 && position <= 3) {
            setPosx(88);
            setPosy(0);
            position++;
        }
        if (position <= 1 ) {
            setPosx(0);
            setPosy(0);
            position++;
        }

        if (position == 16) {
            position=0;
        }

        getImageView().setViewport(new Rectangle2D(getPosx(), getPosy(), getLongueur(), getHauteur()));


    }
}
