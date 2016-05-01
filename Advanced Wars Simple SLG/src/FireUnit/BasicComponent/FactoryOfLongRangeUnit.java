package FireUnit.BasicComponent;

public class FactoryOfLongRangeUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "远程部队";

    @Override
    public String getName() {
        return unitName;
    }

    @Override
    public WayOfMove CreateWayOfMoving() {
        return new MoveByVehicle();
    }

    @Override
    public WayOfAttack CreateWayOfAttack() {
        return new AttackByRocket();
    }

    @Override
    public WayOfDefence CreateWayOfDefence() {
        return new DefenceBySoldier();
    }
}
