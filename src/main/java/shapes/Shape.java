package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class Shape {  //klasa abstrakcyjna - tworzymy funkcje ktora nie wiadomo jaki obiekt tworzy
    private Paint fillColor = Color.PINK;
    private Paint strokeColor = Color.CYAN;

    protected Shape() {
    }

    public abstract void draw(GraphicsContext context);

    public abstract String getData();

    public void drawShape(GraphicsContext context) {
        context.setLineWidth(5);
        context.setStroke(getStrokeColor());
        context.setFill(getFillColor());
        draw(context);
    }

    public Shape(Paint fillColor, Paint strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
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
