package ui;

import geom.BoltSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class DrawingPane extends JPanel {

    public DrawingPane(){
        super();
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("paint...");
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.getWidth(), this.getHeight());

        g.setColor(Color.YELLOW);
        Graphics2D g2d = (Graphics2D) g;
        Stroke stroke = new BasicStroke(3, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
        g2d.setStroke(stroke);

        // get calculated lightning shape as general path and draw it
        GeneralPath lightningPath = BoltSegment.createBoltSegment(150,0,150,300,20);
        g2d.draw(lightningPath);

        // endless loop: This results in paint()-method being called again
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
}
