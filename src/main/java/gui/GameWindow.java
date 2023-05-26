package gui;

import log.Localization;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{

    public GameWindow(RobotController controller)
    {
        super("Working protocols",
                true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(controller.getVisualizer(), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
