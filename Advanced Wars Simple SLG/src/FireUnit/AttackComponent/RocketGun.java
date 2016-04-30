package FireUnit.AttackComponent;

import java.io.Serializable;

public class RocketGun implements WeaponOfFireUnit, Serializable {
    private String hashCode = "6";
    private String weaponName = "火箭炮";
    private int damageEnhance = 20;

    public String getName() { return weaponName; }

    public int attackEnhance( int basicAttackDamage ) { return basicAttackDamage + damageEnhance; }

    public String getHashCode() { return hashCode; }
}
