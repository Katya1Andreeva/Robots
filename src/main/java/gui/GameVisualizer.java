package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final RobotController controller;
    public GameVisualizer(RobotController controller) {
        this.controller = controller;
    }

    public static int round(double value)
    {
        return (int)(value + 0.5);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        controller.getRobotModel().draw(g2d, controller.getRobotModel().getRobotDirection(), 5);
        controller.getTargetModel().draw(g2d, controller.getTargetModel().getPositionX(),
                controller.getTargetModel().getPositionY());
    }
    
    public static void fillOval(Graphics g, double centerX, double centerY, int diam1, int diam2)
    {
        g.fillOval((int) centerX - diam1 / 2,
                   (int) centerY - diam2 / 2,
                   diam1,
                   diam2);
    }
    
    public static void drawOval(Graphics g, double centerX, double centerY, int diam1, int diam2)
    {
        g.drawOval((int) centerX - diam1 / 2,
                   (int)centerY - diam2 / 2,
                      diam1,
                      diam2);
    }
}
