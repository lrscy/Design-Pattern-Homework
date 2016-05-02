package FireUnit.AttackComponent;

/**
 * Description: 武器策略
 */
public interface WeaponOfFireUnit {
    String getName();

    int attackEnhance( int attackDamage );

    String getHashCode();
}
