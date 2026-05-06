package org.myproject.hzhzhz2;

// Базовая фабрика
abstract class ShapeFactory {
    public abstract ShapeProduct createProduct();
}

// Фабрика кругов
class CircleFactory extends ShapeFactory {
    public ShapeProduct createProduct() { return new CircleProduct(); }
}

// Фабрика линий (ТО ЧТО ГОРЕЛО КРАСНЫМ)
class LineFactory extends ShapeFactory {
    public ShapeProduct createProduct() { return new LineProduct(); }
}

// Фабрика прямоугольников
class RectangleFactory extends ShapeFactory {
    public ShapeProduct createProduct() { return new RectangleProduct(); }
}
