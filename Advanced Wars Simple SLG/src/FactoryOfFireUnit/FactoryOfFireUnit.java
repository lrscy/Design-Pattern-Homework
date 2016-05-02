package FactoryOfFireUnit;

import FireUnit.AttackComponent.*;
import FireUnit.BasicComponent.BasicComponent;
import FireUnit.BasicComponent.FactoryOfBasicComponent;
import FireUnit.FireUnit;
import Global.IDGenerator;
import Global.Position;

/**
 * Description: 火力单元工厂
 */
public class FactoryOfFireUnit {
    private static FactoryOfFireUnit factoryOfFireUnit = null;

    private FactoryOfFireUnit() {
    }

    public static FactoryOfFireUnit getInstance() {
        if( factoryOfFireUnit == null ) {
            synchronized( FactoryOfFireUnit.class ) {
                if( factoryOfFireUnit == null ) {
                    factoryOfFireUnit = new FactoryOfFireUnit();
                }
            }
        }
        return factoryOfFireUnit;
    }

    /**
     * Description: 依据需求返回相应的火力单元
     * @param troopName 所属部队
     * @param category  火力单元类别号
     * @param position  该火力单元的位置
     * @return  生成好的火力单元
     */
    public FireUnit produceFireUnit( String troopName, String category, Position position ) {
        FireUnit fu;
        // 获得全局统一的编号
        String id = IDGenerator.getInstance().getID();
        BasicComponent bc = null;
        AttackComponent ac = new AttackAttrOfFireUnit();
        switch( category ) {
            case "1":
            case "19":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac.setWeapon( new SubMechineGun() );
                break;
            case "2":
            case "20":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac.setWeapon( new MechineGun() );
                break;
            case "3":
            case "21":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac.setWeapon( new ShoulderMissiles() );
                break;
            case "4":
            case "22":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfArmourUnit" );
                ac.setWeapon( new IFV() );
                break;
            case "5":
            case "23":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfArmourUnit" );
                ac.setWeapon( new Tank() );
                break;
            case "7":
            case "25":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfLongRangeUnit" );
                ac.setWeapon( new Cannon() );
                break;
            case "8":
            case "26":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfLongRangeUnit" );
                ac.setWeapon( new RocketGun() );
        }
        // 最终生成火力单元
        fu = new FireUnit( id, troopName, position, 100, ac, bc );
        return fu;
    }
}
