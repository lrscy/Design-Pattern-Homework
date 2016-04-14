package FireUnit;

public interface AbstractFactoryOfFireUnit {
    public WayOfMove CreateWayOfMoving();

    public WayOfAttack CreateWayOfAttack();

    public WayOfDefence CreateWayOfDefence();
}
