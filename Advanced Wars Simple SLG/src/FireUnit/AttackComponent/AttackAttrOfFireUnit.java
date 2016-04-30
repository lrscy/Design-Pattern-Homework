package FireUnit.AttackComponent;

import java.io.Serializable;

public class AttackAttrOfFireUnit extends AttackComponent implements Serializable {
    private String hashCode = "0";
    private String weaponName = null;
    private int attackDamage;
    private WeaponOfFireUnit weapon;

    public String getName() {
        if( weaponName == null ) weaponName = weapon.getName();
        return weaponName;
    }

    public void setAttackDamage( int attackDamages ) { this.attackDamage = attackDamages; }

    public void setWeapon( WeaponOfFireUnit weapon ) { this.weapon = weapon; weaponName = weapon.getName(); }

    public WeaponOfFireUnit getWeapon() { return weapon; }

    public int getAttackDamage() { return weapon.attackEnhance( attackDamage ); }

    public String getHashCode() { return hashCode + weapon.getHashCode(); }
}

