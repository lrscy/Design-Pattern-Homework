package FireUnit.AttackComponent;

import java.io.Serializable;

public class AttackAttrOfFireUnit extends AttackComponent implements Serializable {
    private String hashCode = "0";
    private String weaponName = null;
    private int attackDamage;
    private WeaponOfFireUnit weapon;

    @Override
    public String getName() {
        if( weaponName == null ) weaponName = weapon.getName();
        return weaponName;
    }

    @Override
    public void setAttackDamage( int attackDamages ) {
        this.attackDamage = attackDamages;
    }

    @Override
    public void setWeapon( WeaponOfFireUnit weapon ) {
        this.weapon = weapon;
        weaponName = weapon.getName();
    }

    @Override
    public WeaponOfFireUnit getWeapon() {
        return weapon;
    }

    @Override
    public int getAttackDamage() {
        return weapon.attackEnhance( attackDamage );
    }

    @Override
    public String getHashCode() {
        return weapon.getHashCode() + hashCode;
    }
}

