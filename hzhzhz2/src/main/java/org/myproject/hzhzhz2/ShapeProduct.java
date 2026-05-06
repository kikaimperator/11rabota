package org.myproject.hzhzhz2;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

// Общий интерфейс для всех фигур
interface ShapeProduct {
    void create(AnchorPane pane, double x, double y, Color color, double strokeWidth);
    void update(double x, double y);
}

// Реализация для Круга
class CircleProduct implements ShapeProduct {
    private Circle circle;
    public void create(AnchorPane pane, double x, double y, Color color, double strokeWidth) {
        circle = new Circle(x, y, 0);
        circle.setFill(color);
        circle.setStroke(Color.RED);
        circle.setStrokeWidth(strokeWidth);
        pane.getChildren().add(circle);
    }
    public void update(double x, double y) {
        double radius = Math.sqrt(Math.pow(circle.getCenterX() - x, 2) + Math.pow(circle.getCenterY() - y, 2));
        circle.setRadius(radius);
    }
}

// Реализация для Линии
class LineProduct implements ShapeProduct {
    private Line line;
    public void create(AnchorPane pane, double x, double y, Color color, double strokeWidth) {
        line = new Line(x, y, x, y);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);
        pane.getChildren().add(line);
    }
    public void update(double x, double y) {
        line.setEndX(x);
        line.setEndY(y);
    }
}

// Реализация для Прямоугольника
class RectangleProduct implements ShapeProduct {
    private Rectangle rect;
    private double startX, startY;
    public void create(AnchorPane pane, double x, double y, Color color, double strokeWidth) {
        startX = x; startY = y;
        rect = new Rectangle(x, y, 0, 0);
        rect.setFill(color);
        rect.setStroke(Color.RED);
        rect.setStrokeWidth(strokeWidth);
        pane.getChildren().add(rect);
    }
    public void update(double x, double y) {
        rect.setWidth(Math.abs(startX - x));
        rect.setHeight(Math.abs(startY - y));
        rect.setX(Math.min(startX, x));
        rect.setY(Math.min(startY, y));
    }
}
