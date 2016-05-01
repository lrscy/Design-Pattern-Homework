package FireUnit.AttackComponent;

import java.io.Serializable;

public class GeneralDecorator extends AttackComponentDecorator implements Serializable {
    private String hashCode = "2";
    private double factor = 2;

    public GeneralDecorator( AttackComponent attackComponent ) {
        super( attackComponent );
    }

    @Override
    public int getAttackDamage() {
        return ( int )( super.getAttackDamage() * factor );
    }

    @Override
    public String getHashCode() {
        return super.getHashCode().substring( 0, 1 ) + hashCode;
    }
}
