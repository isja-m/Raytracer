public interface Shape {
    Vector[] intersect(Line line);
    
    Vector nearestIntersect(Line line);
}