public class Vector {
    final double xCoord;
    final double yCoord;
    final double zCoord;

    Vector(double xCoord, double yCoord, double zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    public double norm() {
        return Math.sqrt(squaredNorm());
    }

    public double squaredNorm() {
        return xCoord*xCoord + yCoord*yCoord + zCoord*zCoord;
    }

    public double dotProduct(Vector vector2) {
        return this.xCoord*vector2.xCoord + this.yCoord*vector2.yCoord + this.zCoord*vector2.zCoord;
    }
    
    public Boolean equals(Vector vector2) {
        return equals(vector2, 1e-13);
    }
    
    public Boolean equals(Vector vector2, double errorMargin) {
        return Math.abs(this.xCoord - vector2.xCoord) < errorMargin && Math.abs(this.yCoord - vector2.yCoord) < errorMargin 
            && Math.abs(this.zCoord - vector2.zCoord) < errorMargin;
    }

    public Vector add(Vector vector2) {
        return new Vector(this.xCoord + vector2.xCoord, this.yCoord + vector2.yCoord, this.zCoord + vector2.zCoord);
    }

    public Vector subtract(Vector vector2) {
        return this.add(vector2.scalarMultiple(-1));
    }

    public Vector scalarMultiple(double lambda) {
        return new Vector(this.xCoord * lambda, this.yCoord * lambda, this.zCoord * lambda);
    }

    public double distance(Vector vector2) {
        return this.subtract(vector2).norm();
    }

    public Vector perpendicularVector(Shape shape) {
        if (shape instanceof Sphere) {
            return perpendicularVector((Sphere) shape);
        }
        return null;
    }

    public Vector perpendicularVector(Sphere sphere) {
        Line perpendicularLine = new Line(sphere.centre, this);
        return perpendicularLine.getParametricLine().direction;
    }

    public Vector horizontalPivotAround(Vector pivotPoint, double angle) {
        double distanceToNewLocation = 2 * Math.sin(-angle/2);
        Vector direction = new Vector(pivotPoint.xCoord - xCoord, 0, pivotPoint.zCoord - zCoord).horizontalRotate(Math.PI/2 + angle/2);
        return this.add(direction.scalarMultiple(distanceToNewLocation));
    }

    public Vector horizontalRotate(double angle) {
        double newXCoord = Math.cos(angle) * xCoord - Math.sin(angle) * zCoord;
        double newZCoord = Math.sin(angle) * xCoord + Math.cos(angle) * zCoord;
        return new Vector(newXCoord, yCoord, newZCoord);
    }

    public Vector verticalPivotAround(Vector pivotPoint, double angle) {
        double distanceToNewLocation = 2 * Math.sin(-angle/2) * distance(pivotPoint);
        Vector direction = pivotPoint.subtract(this).verticalRotate(Math.PI/2 + angle/2);
        return this.add(direction.scalarMultiple(distanceToNewLocation/distance(pivotPoint)));
    }

    public Vector verticalRotate(double angle) { //Add axis perpendicular to rotation as input.
        double horizontalLength = Math.sqrt(xCoord*xCoord + zCoord*zCoord);
        double newYCoord = Math.cos(angle) * yCoord - Math.sin(angle) * horizontalLength;
        double horizontalScalar = Math.sin(angle) * yCoord/horizontalLength + Math.cos(angle);
        return new Vector(xCoord * horizontalScalar, newYCoord, zCoord * horizontalScalar);
    }
}
