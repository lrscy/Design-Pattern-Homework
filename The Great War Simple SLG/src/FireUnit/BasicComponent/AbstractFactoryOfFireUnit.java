package FireUnit.BasicComponent;

public interface AbstractFactoryOfFireUnit {
    public String getName();

    public WayOfMove CreateWayOfMoving();

    public WayOfAttack CreateWayOfAttack();

    public WayOfDefence CreateWayOfDefence();
}
