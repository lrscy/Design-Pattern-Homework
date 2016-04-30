package FireUnit.BasicComponent;

public class FactoryOfArmourUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "装甲车";

    public String getName() {
        return unitName;
    }

    public WayOfMove CreateWayOfMoving() {
        return new MoveByVehicle();
    }

    public WayOfAttack CreateWayOfAttack() {
        return new AttackByCannon();
    }

    public WayOfDefence CreateWayOfDefence() {
        return new DefenceByArmour();
    }
}
