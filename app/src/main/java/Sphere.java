public class Sphere implements Shape {
    Vector centre;
    double radius;

    public Sphere(Vector centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public Vector[] intersect(Line line) {
        return this.intersect(line.getParametricLine());
    }

    public Vector[] intersect(ParametricLine line) {
        double calculationPart1 = line.direction.dotProduct(line.start.add(centre.scalarMultiple(-1)));
        double calculationPart2 = line.start.add(centre.scalarMultiple(-1)).squaredNorm() - radius*radius;
        double distance1 = -calculationPart1 - Math.sqrt(Math.pow(calculationPart1,2) - calculationPart2);
        double distance2 = -calculationPart1 + Math.sqrt(Math.pow(calculationPart1,2) - calculationPart2);
        return new Vector[] {line.start.add(line.direction.scalarMultiple(distance1)), line.start.add(line.direction.scalarMultiple(distance2))};
    }

    public Vector nearestIntersect(Line line) {
        return nearestIntersect(line.getParametricLine());
    }

    public Vector nearestIntersect(ParametricLine line) {
        return intersect(line)[0];
    }
}