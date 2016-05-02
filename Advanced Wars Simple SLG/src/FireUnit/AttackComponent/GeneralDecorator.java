package FireUnit.AttackComponent;

import java.io.Serializable;

/**
 * Description: 装饰属性 将军的激励 攻击力翻倍
 */
public class GeneralDecorator extends AttackComponentDecorator implements Serializable {
    public GeneralDecorator( AttackComponent attackComponent ) {
        super( attackComponent );
    }

    @Override
    public int getAttackDamage() {
        double factor = 2;
        return ( int )( super.getAttackDamage() * factor );
    }

    @Override
    public String getHashCode() {
        String hashCode = "2";
        return super.getHashCode().substring( 0, 1 ) + hashCode;
    }
}
