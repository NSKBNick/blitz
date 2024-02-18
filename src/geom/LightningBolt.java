package geom;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.ConcurrentLinkedQueue;

import static geom.BoltSegment.INTERMEDIATE_POINTS;
import static geom.BoltSegment.randomDeviate;

public class LightningBolt {

    final static public int NUM_SEGMENTS = 50;
    private final double yGround;
    private final double deltaY;
    private final ConcurrentLinkedQueue<BoltSegment> segments = new ConcurrentLinkedQueue <>();

    public LightningBolt(double startX, double startY, double yGround){
        BoltSegment initialSegment = new BoltSegment(startX, startY, startX, yGround / NUM_SEGMENTS, INTERMEDIATE_POINTS, 0);
        this.yGround = yGround;
        this.deltaY = yGround / NUM_SEGMENTS;
        segments.add(initialSegment);
    }

    public void draw(Graphics2D g2d){
        for(BoltSegment segment: segments) {
            g2d.draw(segment.getPath());
        }
    }

    public void addRandomSegment(){
        for(BoltSegment segment: segments){
            int numberOfNewSegments = (int) (Math.random() * Math.random() *2.999);
            if(segment.successor == null && segment.order < 4 && notToFarFromMainEnd(segment) ){
             for(int i=0; i < numberOfNewSegments; i++) {
                addNewSegment(segment);
             }
            }
        }
    }

    public boolean isGroundReached(){
        return getYMax() >= yGround;
    }

    public double getYMax(){
        double yMax = 0;
        for(BoltSegment segment: segments){
            if(segment.endPoint.y > yMax)
                yMax = segment.endPoint.y;
        }
        return yMax;
    }

    public String toString(){
        return "Bolt(" + segments.size() + "), maxY = " + getYMax();
    }

    private void addNewSegment(BoltSegment segment){
        int newOrder = segment.order + 1;
        Point2D.Double newStart = segment.endPoint;
        Point2D.Double newEnd = new Point2D.Double();
        newEnd.y = newStart.y + Math.pow(0.9, newOrder) * deltaY;
        newEnd.x = newStart.x + randomDeviate(deltaY);
        BoltSegment newSegment = new BoltSegment(newStart.x, newStart.y, newEnd.x, newEnd.y, INTERMEDIATE_POINTS, newOrder);
        segment.successor = newSegment;
        segments.add(newSegment);
        normalizeOrder();
    }

    private void normalizeOrder(){
        getSegmentWithYMax().order = 0;
    }

    private BoltSegment getSegmentWithYMax(){
        double yMax = getYMax();
        for(BoltSegment segment: segments){
            if(segment.endPoint.y == yMax){
                return segment;
            }
        }
        return null;  // should never be reached
    }
    private boolean notToFarFromMainEnd (BoltSegment segment){

        return   segment.endPoint.y > getYMax()/2;
    }
}