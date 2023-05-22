package MVC;

import gui.RobotController;

import java.awt.*;

public abstract class Entity {
    private double positionX;
    private double positionY;

    public Entity(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;

    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getPositionY() {
        return positionY;
    }

    public abstract void draw(Graphics2D g, double x, double y);
}
