package FireUnit.AttackComponent;

import java.io.Serializable;

public class Cannon implements WeaponOfFireUnit, Serializable {
    private String hashCode = "5";
    private String weaponName = "远程火炮";
    private int damageEnhance = 0;

    @Override
    public String getName() {
        return weaponName;
    }

    @Override
    public int attackEnhance( int basicAttackDamage ) {
        return basicAttackDamage + damageEnhance;
    }

    @Override
    public String getHashCode() {
        return hashCode;
    }
}
