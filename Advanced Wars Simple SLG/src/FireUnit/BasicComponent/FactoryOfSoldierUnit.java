package FireUnit.BasicComponent;

public class FactoryOfSoldierUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "步兵";

    public String getName() { return unitName; }

    public WayOfMove CreateWayOfMoving() { return new MoveByWalk(); }

    public WayOfAttack CreateWayOfAttack() { return new AttackByGun(); }

    public WayOfDefence CreateWayOfDefence() { return new DefenceBySoldier(); }
}
