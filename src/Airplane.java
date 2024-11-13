import java.util.List;

public class Airplane {
    private List<Wing> wings;
    private Chassis chassis;
    private List<Engine> engines;
    private String route;

    public Airplane(List<Wing> wings, Chassis chassis, List<Engine> engines) {
        this.wings = wings;
        this.chassis = chassis;
        this.engines = engines;
    }

    // Methods
    public void fly() {
        if (engines.isEmpty()) {
            System.out.println("Не могу летать без двигателей.");
        } else {
            System.out.println("Самолет летит.");
        }
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void printRoute() {
        if (route == null || route.isEmpty()) {
            System.out.println("Маршрут не задан.");
        } else {
            System.out.println("Текущий маршрут: " + route);
        }
    }

    // Getters and setters for components
    public List<Wing> getWings() { return wings; }
    public void setWings(List<Wing> wings) { this.wings = wings; }

    public Chassis getChassis() { return chassis; }
    public void setChassis(Chassis chassis) { this.chassis = chassis; }

    public List<Engine> getEngines() { return engines; }
    public void setEngines(List<Engine> engines) { this.engines = engines; }
}