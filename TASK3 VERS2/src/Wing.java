public class Wing {
    private double length;
    private double area;

    public Wing(double length, double area) throws InvalidWingLengthException {
        if (length <= 0 || area <= 0) {
            throw new InvalidWingLengthException("Длина или площадь крыла недопустимы.");
        }
        this.length = length;
        this.area = area;
    }

    // Getters and setters
    public double getLength() { return length; }
    public void setLength(double length) throws InvalidWingLengthException {
        if (length <= 0) {
            throw new InvalidWingLengthException("Длина крыла не может быть отрицательной или равной нулю.");
        }
        this.length = length;
    }

    public double getArea() { return area; }
    public void setArea(double area) throws InvalidWingLengthException {
        if (area <= 0) {
            throw new InvalidWingLengthException("Площадь крыла не может быть отрицательной или равной нулю.");
        }
        this.area = area;
    }
}