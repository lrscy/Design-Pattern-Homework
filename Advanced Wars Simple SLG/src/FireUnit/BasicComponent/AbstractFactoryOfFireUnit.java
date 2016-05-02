package FireUnit.BasicComponent;

/**
 * Description: 基础属性抽象工厂
 */
interface AbstractFactoryOfFireUnit {
    String getName();

    WayOfMove CreateWayOfMoving();

    WayOfAttack CreateWayOfAttack();

    WayOfDefence CreateWayOfDefence();
}
