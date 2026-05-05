import java.util.Scanner;

class Circle {
    private double xCenter;
    private double yCenter;
    private double radius;
    
    public Circle(double xCenter, double yCenter, double radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius; 
    }

    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    public double getDiameter() {
        return 2 * radius;
    }

    public double getXCenter() { return xCenter; }
    public double getYCenter() { return yCenter; }
    public double getRadius() { return radius; }
}