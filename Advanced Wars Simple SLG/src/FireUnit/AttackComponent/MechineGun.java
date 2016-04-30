package FireUnit.AttackComponent;

import java.io.Serializable;

public class MechineGun implements WeaponOfFireUnit, Serializable {
    private String hashCode = "1";
    private String weaponName = "重机枪";
    private int damageEnhance = 5;

    public String getName() { return weaponName; }

    public int attackEnhance( int basicAttackDamage ) { return basicAttackDamage + damageEnhance; }

    public String getHashCode() { return hashCode; }
}
