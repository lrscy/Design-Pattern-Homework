package FireUnit.BasicComponent;

/**
 * Description: 士兵基础属性产品簇
 */
public class FactoryOfSoldierUnit implements AbstractFactoryOfFireUnit {
    @Override
    public String getName() {
        return "步兵";
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
