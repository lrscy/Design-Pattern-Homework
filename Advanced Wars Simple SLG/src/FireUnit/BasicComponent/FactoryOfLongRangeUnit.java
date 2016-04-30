package FireUnit.BasicComponent;

public class FactoryOfLongRangeUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "远程部队";

    public String getName() { return unitName; }

    public WayOfMove CreateWayOfMoving() { return new MoveByVehicle(); }

    public WayOfAttack CreateWayOfAttack() { return new AttackByRocket(); }

    public WayOfDefence CreateWayOfDefence() { return new DefenceBySoldier(); }
}
