package fr.surakarta.piece;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends Circle {

    private boolean selected;
    private Color color;

    public Piece(double x, double y, Color _color) {
        super(x, y, 10, _color);
        this.color = _color;
        setStroke(Color.WHITE);
        setStrokeWidth(3);
    }

    public void select() {
        selected = !selected;

        if (selected) {
            setStroke(Color.YELLOW);
        } else {
            this.setStroke(Color.WHITE);
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public String toString() {
        return getCenterX() + ";" + getCenterY();
    }
}
