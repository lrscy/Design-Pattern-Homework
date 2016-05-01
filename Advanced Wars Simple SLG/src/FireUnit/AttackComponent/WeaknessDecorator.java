package FireUnit.AttackComponent;

import java.io.Serializable;

public class WeaknessDecorator extends AttackComponentDecorator implements Serializable {
    private String hashCode = "3";
    private double factor = 0.5;

    public WeaknessDecorator( AttackComponent attackComponent ) { super( attackComponent ); }

    @Override
    public int getAttackDamage() { return ( int )( super.getAttackDamage() * factor ); }

    @Override
    public String getHashCode() {
        return super.getHashCode().substring( 0, 1 ) + hashCode;
    }
}
