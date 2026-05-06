package org.myproject.hzhzhz2;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class HelloController {

    // --- Поля паттерна Abstract Factory ---
    private ShapeFactory shapeFactory;
    private ShapeProduct currentProduct;

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    // --- 3D элементы ---
    private Rotate rotateXAxis;
    private Rotate rotateYAxis;
    private Translate translate;
    @FXML private Tab tabpane3;
    @FXML private Box box1 = new Box(200, 200, 200);
    @FXML private AnchorPane pane3 = new AnchorPane();
    private PhongMaterial material = new PhongMaterial();
    private PerspectiveCamera camera;
    private Group modelGroup = new Group();

    // --- 2D элементы UI ---
    @FXML private Circle Circle;
    @FXML private Line Line;
    @FXML private Rectangle Rectangle;
    @FXML private Slider Slider;
    @FXML private SplitMenuButton form;
    @FXML private SplitMenuButton color;
    @FXML private AnchorPane pane;

    // --- Вспомогательные переменные ---
    private Random random = new Random();
    private Color activeColor = Color.BLACK;
    private double mouseOldX, mouseOldY;
    private final double mouseSensitivity = 0.1;
    private final double movementSpeed = 10.0;

    // --- МЕТОДЫ ИЗ FXML (Исправление ошибки LoadException) ---

    @FXML
    void LineMousePressed(MouseEvent event) {
        Panepressed(event);
    }

    @FXML
    void LineMouseDragget(MouseEvent event) {
        PaneDragged(event);
    }

    @FXML
    void actionmenuCircle(ActionEvent event) {
        form.setText("Круг");
        shapeFactory = new CircleFactory();
    }

    @FXML
    void ActionmenuLine(ActionEvent event) {
        form.setText("Линия");
        shapeFactory = new LineFactory();
    }

    @FXML
    void Actionmenurectangle(ActionEvent event) {
        form.setText("Прямоугольник");
        shapeFactory = new RectangleFactory();
    }

    // --- МЕТОДЫ УПРАВЛЕНИЯ ЦВЕТОМ ---

    @FXML void Actionblack(ActionEvent event) { color.setText("чёрный"); activeColor = Color.BLACK; }
    @FXML void Actionred(ActionEvent event) { color.setText("красный"); activeColor = Color.RED; }
    @FXML void Actionblue(ActionEvent event) { color.setText("синий"); activeColor = Color.BLUE; }

    // --- ЛОГИКА РИСОВАНИЯ ЧЕРЕЗ ФАБРИКУ ---

    @FXML
    void Panepressed(MouseEvent event) {
        if (shapeFactory == null) shapeFactory = new CircleFactory();
        currentProduct = shapeFactory.createProduct();
        currentProduct.create(pane, event.getX(), event.getY(), activeColor, Slider.getValue());
    }

    @FXML
    void PaneDragged(MouseEvent event) {
        if (currentProduct != null) {
            currentProduct.update(event.getX(), event.getY());
        }
    }

    // --- 3D И ДРУГИЕ СОБЫТИЯ ---

    @FXML
    void ActionPressedpane3(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            mouseOldX = event.getSceneX(); mouseOldY = event.getSceneY();
        }
    }

    @FXML
    void ActionDraggedpane3(MouseEvent event) {
        if (event.isPrimaryButtonDown()) {
            rotateXAxis.setAngle(rotateXAxis.getAngle() - (event.getSceneY() - mouseOldY) * mouseSensitivity);
            rotateYAxis.setAngle(rotateYAxis.getAngle() + (event.getSceneX() - mouseOldX) * mouseSensitivity);
            mouseOldX = event.getSceneX(); mouseOldY = event.getSceneY();
        }
    }

    @FXML
    void Scrollpane3(ScrollEvent event) {
        translate.setZ(translate.getZ() + (event.getDeltaY() > 0 ? movementSpeed : -movementSpeed));
    }

    @FXML
    void OnScroll(ScrollEvent event) {
        Object target = event.getTarget();
        double delta = event.getDeltaY();
        if (target instanceof Circle c) c.setRadius(c.getRadius() + (delta > 0 ? 5 : -5));
        else if (target instanceof Rectangle r) {
            double f = delta > 0 ? 1.05 : 0.95;
            r.setWidth(r.getWidth() * f); r.setHeight(r.getHeight() * f);
        }
    }

    @FXML
    void OnMouseClick(MouseEvent event) {
        Circle.setFill(event.getButton() == MouseButton.PRIMARY ?
                generateRandomColor(random) : Paint.valueOf("linear-gradient(from 0% 0% to 100% 100%, 0xb9ff21ff 0%, 0xffffffff 100%)"));
    }

    private Color generateRandomColor(Random random) {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @FXML void PaneClick(MouseEvent event) {}
    @FXML void OnMouseExited(MouseEvent event) { Rectangle.setFill(Paint.valueOf("linear-gradient(from 0% 0% to 100% 100%, 0xb9ff21ff 0%, 0xffffffff 100%)")); }
    @FXML void OnMouseMoved(MouseEvent event) { Rectangle.setFill(generateRandomColor(random)); }

    @FXML
    void initialize() {
        Box();
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);
    }

    public void Box(){
        material.setDiffuseColor(Color.OLIVE);
        box1.setMaterial(material);
        rotateXAxis = new Rotate(0, Rotate.X_AXIS);
        rotateYAxis = new Rotate(0, Rotate.Y_AXIS);
        translate = new Translate();
        modelGroup.getTransforms().addAll(translate, rotateXAxis, rotateYAxis);
        modelGroup.getChildren().add(box1);
        pane3.getChildren().addAll(modelGroup, new AmbientLight(Color.WHITE));
    }
}
