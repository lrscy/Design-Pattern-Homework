package FireUnit.BasicComponent;

public class FactoryOfArmourUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "装甲车";

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
        return new AttackByCannon();
    }

    @Override
    public WayOfDefence CreateWayOfDefence() {
        return new DefenceByArmour();
    }
}
