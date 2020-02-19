package shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rubber extends Shape {
    private double startX;
    private double startY;

    public Rubber(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
    }


    @Override
    public void draw(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.setStroke(Color.WHITE);
        context.fillRect(startX,startY,context.getLineWidth(),context.getLineWidth());
    }

    @Override
    public String getData() {
        return null;
    }
}
