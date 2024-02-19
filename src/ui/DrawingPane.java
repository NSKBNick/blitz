package ui;

import geom.LightningBolt;

import javax.swing.*;
import java.awt.*;

public class DrawingPane extends JPanel {

    LightningBolt bolt = null;
    JButton button = new JButton("Blitz");

    public DrawingPane(){
        super();
        setLayout(new BorderLayout());
        button.addActionListener(e -> drawLightning());
        add(button, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void drawLightning(){
        if(bolt == null || bolt.isGroundReached()){
            bolt = new LightningBolt(getRootPane().getWidth() / 2.0,0.0, getRootPane().getHeight());
        } else {
            bolt.addRandomSegment();
            System.out.println(bolt + ": " + bolt.getYMax());
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("paint...");
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.getWidth(), this.getHeight() - button.getHeight());

        g.setColor(new Color(179, 255, 255));
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
        g2d.setStroke(stroke);

        if(bolt != null) {
            System.out.println("Drawing " + bolt);
            bolt.drawSteppedLeader(g2d);
            if (bolt.isGroundReached()){
                g.setColor(Color.WHITE);
                g2d.setStroke(stroke);
                bolt.drawMainBolt(g2d);
            }
        }

    }
}
