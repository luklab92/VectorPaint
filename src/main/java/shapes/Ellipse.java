package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Shape {
    private double centerX;
    private double centerY;
    private double r;
    private double h;
    private double tempx1;
    private double tempx2;
    private double tempy1;
    private double tempy2;

    public Ellipse(double x1, double y1, double x2, double y2) {
        this.centerX = Math.min(x1,x2);
        this.centerY = Math.min(y1,y2);
        this.r = Math.abs((x2-x1));
        this.h = Math.abs((y2-y1));
        this.tempx1=x1;
        this.tempy1=y1;
        this.tempx2=x2;
        this.tempy2=y2;
    }

    public void draw(GraphicsContext context) {
context.strokeOval(centerX,centerY,r,h);
context.fillOval(centerX,centerY,r,h);
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Ellipse;");
        builder.append(tempx1).append(";");
        builder.append(tempy1).append(";");
        builder.append(tempx2).append(";");
        builder.append(tempy2).append(";");
        builder.append(getFillColor()).append(";");
        builder.append(getStrokeColor()).append(";");
        return builder.toString();
    }
    }
