package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 攻击属性 依据武器策略不同产生不同的攻击力
 */
public class AttackAttrOfFireUnit extends AttackComponent implements Serializable {
    private String weaponName = null;
    private WeaponOfFireUnit weapon;

    @Override
    public String getName() {
        if( weaponName == null ) weaponName = weapon.getName();
        return weaponName;
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
    public int getAttackDamage( int attackDamage ) {
        return weapon.attackEnhance( attackDamage );
    }

    @Override
    public String getHashCode() {
        String hashCode = "0";
        return weapon.getHashCode() + hashCode;
    }
}

