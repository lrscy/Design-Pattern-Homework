package FactoryOfFireUnit;

import FireUnit.AttackComponent.*;
import FireUnit.BasicComponent.BasicComponent;
import FireUnit.BasicComponent.FactoryOfBasicComponent;
import FireUnit.FireUnit;
import Global.IDGenerator;
import Global.Position;

public class FactoryOfFireUnit {
    private static FactoryOfFireUnit factoryOfFireUnit = null;

    private FactoryOfFireUnit() {}

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

    public FireUnit produceFireUnit( String troopName, String category, Position position ) {
        FireUnit fu = null;
        String id = IDGenerator.getInstance().getID();
        BasicComponent bc = null;
        AttackComponent ac = null;
        switch( category ) {
            case "1":
            case "19":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new SubMechineGun() );
                ac.setAttackDamage( bc.attackEffect() );
            case "2":
            case "20":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new MechineGun() );
                ac.setAttackDamage( bc.attackEffect() );
            case "3":
            case "21":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfSoldierUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new ShoulderMissiles() );
                ac.setAttackDamage( bc.attackEffect() );
            case "4":
            case "22":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfArmourUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new IFV() );
                ac.setAttackDamage( bc.attackEffect() );
            case "5":
            case "23":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfArmourUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new Tank() );
                ac.setAttackDamage( bc.attackEffect() );
            case "7":
            case "25":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfLongRangeUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new Cannon() );
                ac.setAttackDamage( bc.attackEffect() );
            case "8":
            case "26":
                bc = FactoryOfBasicComponent.getInstance().getBasicComponent( "FactoryOfLongRangeUnit" );
                ac = new AttackAttrOfFireUnit();
                ac.setWeapon( new RocketGun() );
                ac.setAttackDamage( bc.attackEffect() );
        }
        fu = new FireUnit( id, troopName, position, 100, ac, bc );
        return fu;
    }
}
