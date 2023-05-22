package MVC;

import gui.RobotController;
import gui.GameVisualizer;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class RobotModel extends Entity{

    private volatile double robotDirection;

    public RobotModel(double positionX, double positionY,  double robotDirection) {
        super(positionX, positionY);
        this.robotDirection  = robotDirection;
    }

    @Override
    public void draw(Graphics2D g, double direction, double diam)
    {
        int robotCenterX = GameVisualizer.round(getPositionX());
        int robotCenterY = GameVisualizer.round(getPositionY());
        AffineTransform t = AffineTransform.getRotateInstance(
                direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        GameVisualizer.fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        GameVisualizer.drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        GameVisualizer.fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        GameVisualizer.drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }

    public double getRobotDirection() {
        return this.robotDirection;
    }

}
