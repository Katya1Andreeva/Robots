package MVC;

import gui.RobotController;
import gui.GameVisualizer;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static MVC.Constants.*;

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
        GameVisualizer.fillOval(g, robotCenterX, robotCenterY, BODY_HEIGHT, BODY_WIDTH);
        g.setColor(Color.BLACK);
        GameVisualizer.drawOval(g, robotCenterX, robotCenterY, BODY_HEIGHT, BODY_WIDTH);
        g.setColor(Color.WHITE);
        GameVisualizer.fillOval(g, robotCenterX + EYE_SHIFT,
                                robotCenterY ,
                                EYE_DIAMETER,
                                EYE_DIAMETER);
        g.setColor(Color.BLACK);
        GameVisualizer.drawOval(g, robotCenterX + EYE_SHIFT,
                                robotCenterY,
                                EYE_DIAMETER,
                                EYE_DIAMETER);
    }

    public void setRobotDirection(double robotDirection) {
        this.robotDirection = robotDirection;
    }

    public double getRobotDirection() {
        return this.robotDirection;
    }

}
