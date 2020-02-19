package Paint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shapes.*;

import java.io.*;
import java.util.*;

public class PaintController {
    private double startX;
    private double endX;
    private double startY;
    private double endY;
    private List<Shape> shapeList = new ArrayList<>();


    @FXML
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
    @FXML
    private Button PenTool;
    @FXML
    private Slider sliderButton;
    @FXML
    private Button RubberTool;

    private Shape currentShape;
    private Tool currentTool = Tool.LINE;

    public void initialize() {
        fillColorPicker.setValue(Color.GREEN);
        strokeColorPicker.setValue(Color.BLUE);
        sliderButton.setValue(2);
        refreshCanvas();
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                startX = event.getX();
                startY = event.getY();
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
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
               //applyShape();
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
        currentShape.setLineWidth(sliderButton.getValue());
        currentShape.setStrokeColor(strokeColorPicker.getValue());
        rubberSetColorTool();
    }

    private void rubberSetColorTool() {
        if (currentTool == Tool.RUBBER) {
            currentShape.setFillColor(Color.WHITE);
        }
    }

    private Shape createShape() {
        switch (currentTool) {
            default:
            case LINE:
                return new Line(startX, startY, endX, endY);
            case RECTANGLE:
                return new Rectangle(startX, startY, endX, endY);
            case TRIANGLE:
                return new Triangle(startX, startY, endX, endY);
            case ELLIPSE:
                return new Ellipse(startX, startY, endX, endY);
            case CIRCLE:
                return new Circle(startX, startY, endX, endY);
            case STAR:
                return new Star(startX, startY, endX, endY);
            case PEN:
                return new Pen(startX,startY);
            case RUBBER:
                return new Rubber(startX,startY);
        }
    }

    private void refreshCanvas() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.BLACK);
        context.setLineWidth(1);
        context.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapeList) {
            shape.drawShape(context);
        }
        if (currentShape != null) currentShape.drawShape(context);
    }

    public void clearWindow() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.BLACK);
        context.setLineWidth(1);
        context.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        shapeList.clear();
    }

    @FXML
    public void changeTool(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == LineTool) {
            currentTool = Tool.LINE;
        } else if (source == rectTool) {
            currentTool = Tool.RECTANGLE;
        } else if (source == CircleTool) {
            currentTool = Tool.CIRCLE;
        } else if (source == StarTool) {
            currentTool = Tool.STAR;
        } else if (source == EllipseTool) {
            currentTool = Tool.ELLIPSE;
        } else if (source == TriangleTool) {
            currentTool = Tool.TRIANGLE;
        } else if (source == PenTool) {
            currentTool = Tool.PEN;
        } else if (source == RubberTool) {
            currentTool = Tool.RUBBER;
        }
        else {
            throw new IllegalStateException("Unsupported tool");
        }
        System.out.println(currentTool);
    }

    @FXML
    public void removeLast() {
        if (shapeList.size() > 1) {
            shapeList.remove(shapeList.get(shapeList.size() - 1));
            refreshCanvas();
        } else clearWindow();
    }

    @FXML
    public void handleSave() {
        Optional<String> reduce = shapeList.stream()
                .map(shape -> shape.getData())
                .reduce((acc, text) -> acc + "\n" + text);
        if (reduce.isPresent()) {
            System.out.println(reduce.get());
            File file = setFileChooserOptions().showSaveDialog(new Stage());
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

    public void loadAction() throws FileNotFoundException {
        File file = setFileChooserOptions().showOpenDialog(new Stage());
        List<String[]> temp = new ArrayList<>();
        if (file != null) {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                if(sc.hasNextLine())temp.add(sc.nextLine().split(";"));
                else temp.add(sc.next().split(";"));
            }
        }
        readFile(temp);
    }


    private void readFile(List<String[]> list){
        for (String[] s: list) {
            isStringEquals(s[0]);
            setValues(s[1],s[2],s[3],s[4]);
            String tempFill = s[5].substring(2,8);
            String tempStroke = s[6].substring(2,8);
            currentShape = createShape();
            currentShape.setFillColor(getColor(tempFill));
            currentShape.setStrokeColor(getColor(tempStroke));
            applyShape();
            refreshCanvas();
        }
    }
    private Paint getColor(String inputColor) {
        return Color.valueOf(inputColor);
    }

    private void isStringEquals(String input) {
        if (input.equals("Triangle")) currentTool = Tool.TRIANGLE;
        else if (input.equals("Star")) currentTool = Tool.STAR;
        else if (input.equals("Rectangle")) currentTool = Tool.RECTANGLE;
        else if (input.equals("Circle")) currentTool = Tool.CIRCLE;
        else if (input.equals("Ellipse")) currentTool = Tool.ELLIPSE;
        else if (input.equals("Line")) currentTool = Tool.LINE;
        else if (input.equals("Pen")) currentTool = Tool.PEN;
        else if (input.equals("Rubber")) currentTool= Tool.RUBBER;
    }
    private void setValues(String inputX1, String inputY1, String inputX2, String inputY2) {
        startX = Double.parseDouble(inputX1);
        startY = Double.parseDouble(inputY1);
        endX = Double.parseDouble(inputX2);
        endY = Double.parseDouble(inputY2);
    }

    private FileChooser setFileChooserOptions(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("YOLO files (*.yolo)", "*.yolo");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser;
    }
}

    /*public void choiceBoxSet() {
        ComboBox<String> cb = new ComboBox<>(FXCollections.observableArrayList(
                "1", "2", "3", "5", "8", "10")
        );
        final int[] lineWidth = {1, 2, 3, 5, 8, 10};
        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number oldValue, Number newValue) {
                currentShape.setLineWidth(lineWidth[newValue.intValue()]);
                System.out.println();
            }
        });
        System.out.println("zmiana" + currentShape.getLineWidth());
        cb.setTooltip(new Tooltip("Select Line Width"));

    }*/
