package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Triangle extends Shape{
   private Point2D point1;
   private Point2D point2;
   private Point2D point3;
    private double tempx1;
    private double tempx2;
    private double tempy1;
    private double tempy2;

    public Triangle(double x1, double y1, double x2, double y2) {
       point1 = new Point2D(Math.min(x1,x2),Math.max(y1,y2));
       point2 = new Point2D(Math.max(x1,x2),Math.max(y1,y2));
       point3 = new Point2D((x1+x2)/2,Math.min(y1,y2));
       this.tempx1=x1;
       this.tempy1=y1;
       this.tempx2=x2;
       this.tempy2=y2;
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Triangle;");
        builder.append(tempx1).append(";");
        builder.append(tempy1).append(";");
        builder.append(tempx2).append(";");
        builder.append(tempy2).append(";");
        builder.append(getFillColor()).append(";");
        builder.append(getStrokeColor()).append(";");
        return builder.toString();
    }

    public void draw(GraphicsContext context) {
        context.beginPath();
        context.moveTo(point1.getX(),point1.getY());
        context.lineTo(point2.getX(),point2.getY());
        context.lineTo(point3.getX(),point3.getY());
        context.closePath();
        context.stroke();
        context.fill();
    }
}
