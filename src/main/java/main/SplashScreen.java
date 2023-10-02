package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SplashScreen extends JWindow {
    private final JWindow WINDOW;
    private long startTime;
    private int minimumMilliseconds;
    private JLabel label = new JLabel("", SwingConstants.CENTER);

    public SplashScreen() throws IOException {
        WINDOW = new JWindow();
        Image splash = ImageIO.read(getClass().getResource("/splashScreen.png"));
        label.setIcon(new ImageIcon(splash));
        WINDOW.getContentPane().add(label);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WINDOW.setBounds((int) ((screenSize.getWidth() - splash.getWidth(null)) / 2),
                (int) ((screenSize.getHeight() - splash.getHeight(null)) / 2),
                splash.getWidth(null), splash.getHeight(null));
    }

    public void show(int minimumMilliseconds) {
        System.out.println("Show Splash");
        this.minimumMilliseconds = minimumMilliseconds;
        WINDOW.setVisible(true);
        startTime = System.currentTimeMillis();
    }

    public void hide() {
        System.out.println("Hide Splash");
        long elapsedTime = System.currentTimeMillis() - startTime;
        try {
            Thread.sleep(Math.max(minimumMilliseconds - elapsedTime, 0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WINDOW.setVisible(false);
    }

}
