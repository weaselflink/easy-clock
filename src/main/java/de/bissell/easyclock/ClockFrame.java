package de.bissell.easyclock;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockFrame extends JFrame implements ActionListener {

    public static final String CLOCK_FONT = "Courier New";

    private final JLabel label;
    private Settings settings;

    public ClockFrame() {
        settings = Settings.loadSettings();

        label = createLabel();
        label.setText(getTimeString());
        getContentPane().add(label);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        Rectangle bounds = getBounds();
        setBounds(settings.x, settings.y, bounds.width, bounds.height);

        MouseAdapter adapter = new ClockMouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
        addMouseWheelListener(adapter);

        Timer timer = new Timer(100, this);
        timer.setRepeats(true);
        timer.start();
    }

    private JLabel createLabel() {
        Font font = new Font(CLOCK_FONT, Font.BOLD, settings.fontSize);

        JLabel label = new JLabel();

        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.red);
        label.setBackground(Color.black);
        label.setOpaque(true);
        label.setBorder(new EmptyBorder(10, 10, 10, 10));

        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        label.setText(getTimeString());
    }

    private String getTimeString() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

    private void resizeLabelFont(int diff) {
        settings.fontSize = Math.max(10, settings.fontSize + diff * 5);
        label.setFont(new Font(CLOCK_FONT, Font.BOLD, settings.fontSize));
        pack();
    }

    private void saveSettingsAndExit() {
        settings.save();

        System.exit(0);
    }

    private class ClockMouseAdapter extends MouseAdapter {

        private Point dragStart = null;

        @Override
        public void mousePressed(MouseEvent e) {
            dragStart = new Point(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                saveSettingsAndExit();
            }

            dragStart = null;
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            resizeLabelFont(e.getWheelRotation());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Rectangle bounds = getBounds();

            int diffX = e.getX() - dragStart.x;
            int diffY = e.getY() - dragStart.y;

            settings.x = bounds.x + diffX;
            settings.y = bounds.y + diffY;
            setBounds(settings.x, settings.y, bounds.width, bounds.height);
        }
    }
}
