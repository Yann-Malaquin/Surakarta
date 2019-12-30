package fr.surakarta.grid;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Node extends Circle {


    public Node(double x, double y){
        super(x,y,15, Color.BLACK);
    }


    public String toString(){
        return getCenterX() +";"+ getCenterY();
    }
}
