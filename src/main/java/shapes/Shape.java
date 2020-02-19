package shapes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Shape {  //klasa abstrakcyjna - tworzymy funkcje ktora nie wiadomo jaki obiekt tworzy
    private Paint fillColor = Color.PINK;
    private Paint strokeColor = Color.CYAN;
    private double lineWidth = 5;

    protected Shape() {
    }

    public abstract void draw(GraphicsContext context);

    public abstract String getData();

    public void drawShape(GraphicsContext context) {
        context.setLineWidth(getLineWidth());
        context.setStroke(getStrokeColor());
        context.setFill(getFillColor());
        draw(context);

    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }


    public void setFillColor(Paint fillColor) {
        this.fillColor = fillColor;
    }

    public Paint getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Paint strokeColor) {
        this.strokeColor = strokeColor;
    }
    public Paint getFillColor() {
        return fillColor;
    }
}
