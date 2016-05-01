package FireUnit.BasicComponent;

public class FactoryOfSoldierUnit implements AbstractFactoryOfFireUnit {
    private String unitName = "步兵";

    @Override
    public String getName() {
        return unitName;
    }

    @Override
    public WayOfMove CreateWayOfMoving() {
        return new MoveByWalk();
    }

    @Override
    public WayOfAttack CreateWayOfAttack() {
        return new AttackByGun();
    }

    @Override
    public WayOfDefence CreateWayOfDefence() {
        return new DefenceBySoldier();
    }
}
