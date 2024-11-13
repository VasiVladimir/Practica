public class Engine {
    private String type;
    private int power; // in horsepower

    public Engine(String type, int power) throws InvalidEnginePowerException {
        if (power <= 0) {
            throw new InvalidEnginePowerException("Мощность двигателя не может быть отрицательной или равной нулю.");
        }
        this.type = type;
        this.power = power;
    }

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getPower() { return power; }
    public void setPower(int power) throws InvalidEnginePowerException {
        if (power <= 0) {
            throw new InvalidEnginePowerException("Мощность двигателя не может быть отрицательной или равной нулю.");
        }
        this.power = power;
    }
}