package geom;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LightningBolt {
    static public GeneralPath createBolt(double startX, double startY, double endX, double endY){
        List<Point2D.Double> path = new LinkedList<Point2D.Double>();
        path.add(new Point2D.Double(startX, startY));
        path.add(new Point2D.Double(endX, endY));
        path = halveSegment(path, 0);
        return pathToGeneralPath(path);
    }

    /**
     * Inserts a randomly displaced point into a given path at a given position
     * @param path the path consisting of all points
     * @param index the position of the path, where to add the new point
     * @return the updated path (containing the newly added point at the specified position)
     */
    static private List<Point2D.Double> halveSegment(List<Point2D.Double> path, int index){
        Point2D.Double previousPoint = path.get(index);
        Point2D.Double nextPoint = path.get(index + 1);
        double newX = (previousPoint.x + (nextPoint.x * Math.random() - previousPoint.x));
        double newY = (previousPoint.y + (nextPoint.y * Math.random() - previousPoint.y));
        Point2D.Double newPoint = new Point2D.Double(newX, newY);
        path.add(index + 1, newPoint);
        return path;
    }

    static private GeneralPath pathToGeneralPath(List<Point2D.Double> path){
        GeneralPath generalPath = new GeneralPath();
        Boolean first = true;
        for(Point2D.Double point: path){
            if(first){
                generalPath.moveTo(point.x, point.y);
                first = false;
            } else {
                generalPath.lineTo(point.x, point.y);
            }
        }
        return generalPath;
    }
}
