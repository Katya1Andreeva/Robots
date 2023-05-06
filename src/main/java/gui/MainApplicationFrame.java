package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;

import log.Localization;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    private Localization lok = new Localization();
    private final RobotController controller;
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        this.controller = new RobotController();

        GameWindow gameWindow = new GameWindow(controller);
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeProgram();
            }
        });

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    protected void closeProgram() {

        UIManager.put("OptionPane.yesButtonText", lok.getStringResource("answer_yes"));
        UIManager.put("OptionPane.noButtonText", lok.getStringResource("answer_no"));

        int response = JOptionPane.showConfirmDialog(MainApplicationFrame.this,
                lok.getStringResource("questionForExit"),
                lok.getStringResource("titleExit"),
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                    new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }


    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu(lok.getStringResource("lookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        lok.addElement(lookAndFeelMenu, "lookAndFeelMenu");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem(
                    lok.getStringResource("systemLookAndFeel"),
                                      KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
            lok.addElement(systemLookAndFeel, "systemLookAndFeel");
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(
                    lok.getStringResource("crossplatform"),
                                                KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
            lok.addElement(crossplatformLookAndFeel, "crossplatform");
        }

        JMenu testMenu = new JMenu(lok.getStringResource("test"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        lok.addElement(testMenu, "test");
        {
            JMenuItem addLogMessageItem = new JMenuItem(
                    lok.getStringResource("messageLog"),
                                           KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
            lok.addElement(addLogMessageItem, "messageLog");
        }


        JMenu exitMenu = new JMenu(lok.getStringResource("exitMen"));
        exitMenu.setMnemonic(KeyEvent.VK_A);
        exitMenu.getAccessibleContext().setAccessibleDescription(
                "Выход");
        lok.addElement(exitMenu, "exitMen");
        {
            JMenuItem exitMessageItem = new JMenuItem(
                                        lok.getStringResource("exitButton"),
                                        KeyEvent.VK_B);
            exitMessageItem.addActionListener((event) -> {
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                        new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            });
            exitMenu.add(exitMessageItem);
            lok.addElement(exitMessageItem, "exitButton");

        }


        JMenu settingsMenu = new JMenu(lok.getStringResource("settings"));
        settingsMenu.setMnemonic(KeyEvent.VK_V);
        settingsMenu.getAccessibleContext().setAccessibleDescription(
                "Настройки");
        lok.addElement(settingsMenu, "settings");

        JMenu changeLanguageMenu = new JMenu(lok.getStringResource("changeLanguage"));
        settingsMenu.add(changeLanguageMenu);
        lok.addElement(changeLanguageMenu, "changeLanguage");

        {
            JMenuItem enLanguageButton = new JMenuItem(
                    lok.getStringResource("english"),
                    KeyEvent.VK_X);
            enLanguageButton.addActionListener((event) -> {
                lok.changeLanguage("en");
            });
            changeLanguageMenu.add(enLanguageButton);
            lok.addElement(enLanguageButton, "english");

            JMenuItem ruLanguageButton = new JMenuItem(
                    lok.getStringResource("russian"),
                    KeyEvent.VK_L);
            ruLanguageButton.addActionListener((event) -> {
                lok.changeLanguage("ru");
            });
            changeLanguageMenu.add(ruLanguageButton);
            lok.addElement(ruLanguageButton, "russian");

        }
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(settingsMenu);
        menuBar.add(exitMenu);
        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
