package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 装饰属性 人手不足 攻击力减半
 */
public class WeaknessDecorator extends AttackComponentDecorator implements Serializable {
    public WeaknessDecorator( AttackComponent attackComponent ) { super( attackComponent ); }

    @Override
    public int getAttackDamage( int attackDamage ) {
        double factor = 0.5;
        return ( int )( super.getAttackDamage( attackDamage ) * factor ); }

    @Override
    public String getHashCode() {
        String hashCode = "3";
        return super.getHashCode().substring( 0, 1 ) + hashCode;
    }
}
