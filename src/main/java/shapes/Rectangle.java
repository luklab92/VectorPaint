package shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Shape{
    private double x;
    private double w;
    private double h;
    private double y;
    private double tempx1;
    private double tempx2;
    private double tempy1;
    private double tempy2;


    public Rectangle(double x1, double y1, double x2, double y2) {
        this.x = Math.min(x1,x2);
        this.y = Math.min(y1,y2);
        this.w = Math.abs(x1-x2);
        this.h = Math.abs(y1-y2);
        this.tempx1=x1;
        this.tempy1=y1;
        this.tempx2=x2;
        this.tempy2=y2;
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Rectangle;");
        builder.append(tempx1).append(";");
        builder.append(tempy1).append(";");
        builder.append(tempx2).append(";");
        builder.append(tempy2).append(";");
        builder.append(getFillColor()).append(";");
        builder.append(getStrokeColor()).append(";");
        return builder.toString();
    }

    public void draw(GraphicsContext context) {
        context.strokeRect(x,y,w,h);
        context.fillRect(x,y,w,h);
    }

}
