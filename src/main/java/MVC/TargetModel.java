package MVC;

import gui.RobotController;

import java.awt.*;
import java.awt.geom.AffineTransform;
import gui.GameVisualizer;

import static gui.GameVisualizer.drawOval;
import static gui.GameVisualizer.fillOval;

public class TargetModel extends Entity{


    public TargetModel(double positionX, double positionY) {
        super( positionX, positionY);


    }
    @Override
    public void draw(Graphics2D g, double x, double y) {
        AffineTransform t = AffineTransform.getRotateInstance(
                0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, 5, 5);
    }
}
