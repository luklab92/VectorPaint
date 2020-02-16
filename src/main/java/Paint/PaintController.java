package Paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shapes.Line;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Triangle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PaintController {
private double startX;
private double endX;
private double startY;
private double endY;
private List<Shape> shapeList = new ArrayList<Shape>();    @FXML
    private Canvas canvas;

    @FXML
    private Button rectTool;
    @FXML
    private Button TriangleTool;
    @FXML
    private Button EllipseTool;
    @FXML
    private Button LineTool;
    @FXML
    private Button StarTool;
    @FXML
    private Button CircleTool;
    @FXML
    private ColorPicker fillColorPicker;
    @FXML
    private ColorPicker strokeColorPicker;


    private Shape currentShape;
    private Tool currentTool = Tool.LINE;

    public void initialize(){
        fillColorPicker.setValue(Color.GREEN);
        strokeColorPicker.setValue(Color.BLUE);
        refreshCanvas();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                startX = event.getX();
                startY = event.getY();
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                endX = event.getX();
                endY = event.getY();
                prepareShape();
                applyShape();
                refreshCanvas();
            }
        });
       canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
           public void handle(MouseEvent event) {
               endX = event.getX();
               endY = event.getY();
               prepareShape();
//               applyShape();
               refreshCanvas();
           }
       });
    }

    private void applyShape() {
        shapeList.add(currentShape);
//        currentShape = null;
    }

    private void prepareShape() {
        currentShape = createShape();
        currentShape.setFillColor(fillColorPicker.getValue());
        currentShape.setStrokeColor(strokeColorPicker.getValue());

    }

    private Shape createShape() {
        switch (currentTool) {
            default:
            case LINE: return new Line(startX, startY, endX, endY);
            case RECTANGLE: return new Rectangle(startX, startY, endX, endY);
            case TRIANGLE: return new Triangle(startX, startY, endX, endY);
        }
    }

    private void refreshCanvas() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.BLACK);
        context.strokeRect(0,0,canvas.getWidth(),canvas.getHeight());
        for (Shape shape: shapeList) {
            shape.drawShape(context);
        }
if (currentShape!=null) currentShape.drawShape(context);
    }

    public void clearWindow() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.BLACK);
        context.strokeRect(0,0,canvas.getWidth(),canvas.getHeight());
        shapeList.clear();
    }

    @FXML
    public void changeTool(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == LineTool) {
            currentTool = Tool.LINE;
        }
        else if (source == rectTool) {
            currentTool = Tool.RECTANGLE;
        }
        else if (source == CircleTool) {
            currentTool = Tool.CIRCLE;
        }
        else if (source == StarTool) {
            currentTool = Tool.STAR;
        }
        else if (source == EllipseTool) {
            currentTool = Tool.ELLIPSE;
        }
        else if (source == TriangleTool) {
            currentTool = Tool.TRIANGLE;
        }
        else { throw new IllegalStateException("Unsupported tool");
        }
        System.out.println(currentTool);
    }

                    @FXML
                    public void handleSave() {
                        Optional<String> reduce = shapeList.stream()
                                .map(shape -> shape.getData())
                                .reduce((acc, text) -> acc + "\n" + text);
                        if (reduce.isPresent()) {
                            System.out.println(reduce.get());
                            FileChooser fileChooser = new FileChooser();
                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("YOLO files (*.yolo)", "*.yolo");
                            fileChooser.getExtensionFilters().add(extFilter);
                            File file = fileChooser.showSaveDialog(new Stage());
                            if (file != null) {
                                saveTextToFile(reduce.get(), file);
                            }
                        }
                    }

                    private void saveTextToFile(String content, File file) {
                        try {
                            PrintWriter writer;
                            writer = new PrintWriter(file);
                            writer.println(content);
                            writer.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                FileChooser newFile = new FileChooser();
    }
