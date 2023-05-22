package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import MVC.Constants;
import MVC.RobotModel;
import MVC.TargetModel;

public class RobotController  {
    private RobotModel robot;

    private  TargetModel target;
    private final GameVisualizer visualizer;
    private final Timer m_timer = initTimer();



    public RobotController() {
        this.robot = new RobotModel(100, 100, 0);
        this.target = new TargetModel(100, 100);
        this.visualizer = new GameVisualizer(this);

        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        visualizer.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                target.setPositionX(e.getPoint().x);
                target.setPositionY(e.getPoint().y);
                visualizer.repaint();
            }
        });
        visualizer.setDoubleBuffered(true);
    }

    private static Timer initTimer()
    {
        return new Timer("events generator", true);
    }

    public GameVisualizer getVisualizer() {
        return this.visualizer;
    }

    public RobotModel getRobotModel() {
        return robot;
    }
    public TargetModel getTargetModel() {
        return target;
    }


    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(visualizer::repaint);
    }

    protected void onModelUpdateEvent() {
        moveRobot((int) target.getPositionX(), (int) target.getPositionY(), Constants.DURATION);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    private static double asNormalizedRadians(double angle)
    {

        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public void moveRobot(int targetPositionX, int targetPositionY, double duration)
    {
        double distance = distance(targetPositionX, targetPositionY,
                robot.getPositionX(), robot.getPositionY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = Constants.MAX_VELOCITY;
        double angleToTarget = angleTo(robot.getPositionX(),
                robot.getPositionY(), targetPositionX, targetPositionY);
        double angularVelocity = 0;

        double angle = asNormalizedRadians(angleToTarget - robot.getRobotDirection());

        if (angle < Math.PI / 2) {
            angularVelocity = Constants.MAX_ANGULAR_VELOCITY;
        } else if (angle > Math.PI / 2) {
            angularVelocity = -Constants.MAX_ANGULAR_VELOCITY;
        }

        velocity = applyLimits(velocity, 0, Constants.MAX_VELOCITY);
        angularVelocity = applyLimits(angularVelocity, -Constants.MAX_ANGULAR_VELOCITY,
                Constants.MAX_ANGULAR_VELOCITY);
        double newX = robot.getPositionX() + velocity / angularVelocity *
                (Math.sin(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.sin(robot.getRobotDirection()));
        if (!Double.isFinite(newX))
        {
            newX = robot.getPositionX() +
                    velocity * duration * Math.cos(robot.getRobotDirection());
        }
        double newY = robot.getPositionY() - velocity / angularVelocity *
                (Math.cos(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.cos(robot.getRobotDirection()));
        if (!Double.isFinite(newY))
        {
            newY = robot.getPositionY() +
                    velocity * duration * Math.sin(robot.getRobotDirection());
        }
        robot.setPositionX(newX);
        robot.setPositionY(newY);
        double newDirection =
                asNormalizedRadians(robot.getRobotDirection() +
                        angularVelocity * duration);
        robot.setRobotDirection(newDirection);


    }

}
