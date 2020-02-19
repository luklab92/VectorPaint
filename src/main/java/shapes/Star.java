package shapes;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Star extends Shape{
    private List<Point2D> pointsList = new ArrayList<>();
    private double tempx1;
    private double tempx2;
    private double tempy1;
    private double tempy2;


    public Star(double x1, double y1, double x2, double y2) {
        double centerX = Math.abs(x1 + x2) / 2;
        double centerY = Math.abs(y1 + y2) / 2;
        double radius;
        double numberOfArmsValue = 5;
        int outsideVertexValue = 2;
        int insideVertexValue = 4;
        for (int i = 0; i <numberOfArmsValue*2; i++) {
            if (isEven(i)) {
                radius= Math.abs(x2-x1)/insideVertexValue;
            }
            else radius = Math.abs(x2-x1)/outsideVertexValue;
            double pointVertexX = centerX +Math.sin(i*Math.PI/numberOfArmsValue)*radius;
            double pointvertexY = centerY +Math.cos(i*Math.PI/numberOfArmsValue)*radius;
            Point2D point = new Point2D(pointVertexX, pointvertexY);
            pointsList.add(point);
        }
        this.tempx1=x1;
        this.tempy1=y1;
        this.tempx2=x2;
        this.tempy2=y2;
    }
    private boolean isEven(int counter) {
        return counter%2==1;
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Star;");
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
        move(context);
        context.closePath();
        context.stroke();
        context.fill();
    }

    private void move(GraphicsContext context){
        context.moveTo(pointsList.get(0).getX(),pointsList.get(0).getY());
        for (int i = 1; i <pointsList.size(); i++) {
            context.lineTo(pointsList.get(i).getX(),pointsList.get(i).getY());
        }
    }

}
