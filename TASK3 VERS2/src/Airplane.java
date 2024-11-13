import java.util.List;

public class Airplane {
    private List<Wing> wings;
    private Chassis chassis;
    private List<Engine> engines;
    private String route;

    public Airplane(List<Wing> wings, Chassis chassis, List<Engine> engines) throws InsufficientEnginesException {
        if (engines == null || engines.isEmpty()) {
            throw new InsufficientEnginesException("Самолет должен иметь как минимум один двигатель.");
        }
        this.wings = wings;
        this.chassis = chassis;
        this.engines = engines;
    }

    // Methods
    public void fly() throws InsufficientEnginesException {
        if (engines == null || engines.isEmpty()) {
            throw new InsufficientEnginesException("Не могу летать без двигателей.");
        } else {
            System.out.println("Самолет летит.");
        }
    }

    public void setRoute(String route) throws RouteNotSetException {
        if (route == null || route.isEmpty()) {
            throw new RouteNotSetException("Маршрут не может быть пустым.");
        }
        this.route = route;
    }

    public void printRoute() throws RouteNotSetException {
        if (route == null || route.isEmpty()) {
            throw new RouteNotSetException("Маршрут не задан.");
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
    public void setEngines(List<Engine> engines) throws InsufficientEnginesException {
        if (engines == null || engines.isEmpty()) {
            throw new InsufficientEnginesException("Самолет должен иметь как минимум один двигатель.");
        }
        this.engines = engines;
    }
}