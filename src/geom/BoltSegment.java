package geom;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

public class BoltSegment {

    public static final int INTERMEDIATE_POINTS = 20;

    public BoltSegment successor = null;
    private GeneralPath path;
    public int order = 0;

    public Point2D.Double endPoint = new Point2D.Double(0.0 ,0.0);
    public BoltSegment(double startX, double startY,
                       double endX, double endY,
                       int intermediatePoints, int order){
        path = createBoltSegmentPath(startX, startY, endX, endY, intermediatePoints);
        endPoint.x = endX;
        endPoint.y  = endY;
        this.order = order;
    }

    public GeneralPath getPath(){
        return path;
    }
    static public GeneralPath createBoltSegmentPath(double startX, double startY,
                                                    double endX, double endY,
                                                    int intermediatePoints){
        List<Point2D.Double> path = new LinkedList<>();
        path.add(new Point2D.Double(startX, startY));
        path.add(new Point2D.Double(endX, endY));
        for(int i=0;i<intermediatePoints;i++) {
            path = addRandomPoint(path, i,
                    1.0 / (intermediatePoints + 1));
        }
        // remove point before last point as it has the same y coordinate as the last point
        //path.remove(path.size() - 2);
        return pathToGeneralPath(path);
    }

    /**
     * Inserts a randomly displaced point into a given path at a given position
     * @param path the path consisting of all points
     * @param index the position of the path, where to add the new point
     * @return the updated path (containing the newly added point at the specified position)
     */
    static private List<Point2D.Double> addRandomPoint(List<Point2D.Double> path,
                                                       int index, double yDelta){
        Point2D.Double startPoint = path.get(0);
        Point2D.Double endPoint = path.get(path.size()-1);
        double newX = startPoint.x + (endPoint.x - startPoint.x) * yDelta * (index + 1) + 20 * randomDeviate(yDelta);
        double newY = startPoint.y + (endPoint.y - startPoint.y) * yDelta * (index + 1);
        Point2D.Double newPoint = new Point2D.Double(newX, newY);
        path.add(index + 1, newPoint);
        return path;
    }

    public static double randomDeviate(double delta) {
        return  randomDev() * delta;
    }

    public static double randomDev(){
        return 1 - 2 * Math.random();
    }
    static private GeneralPath pathToGeneralPath(List<Point2D.Double> path){
        GeneralPath generalPath = new GeneralPath();
        boolean first = true;
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

    public String toString(){
        return "SEG(" + order + "): " + "( " + endPoint.x + " | " + endPoint.y + " )" ;
    }
}
