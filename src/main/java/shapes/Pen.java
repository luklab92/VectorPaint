package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Pen extends Shape{
    private double startX;
    private double startY;


    public Pen(double x, double y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void draw(GraphicsContext context) {
context.fillRect(startX,startY,context.getLineWidth(),context.getLineWidth());
    }

    @Override
    public String getData() {
        return null;
    }
}
