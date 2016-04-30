package FireUnit.AttackComponent;

import java.io.Serializable;

public class ShoulderMissiles implements WeaponOfFireUnit, Serializable {
    private String hashCode = "2";
    private String weaponName = "肩扛式火箭弹";
    private int damageEnhance = 10;

    public String getName() { return weaponName; }

    public int attackEnhance( int basicAttackDamage ) { return basicAttackDamage + damageEnhance; }

    public String getHashCode() { return hashCode; }
}
