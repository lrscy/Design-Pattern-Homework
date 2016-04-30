package FireUnit.AttackComponent;

import java.io.Serializable;

public class WeaknessDecorator extends AttackComponentDecorator implements Serializable {
    private String hashCode = "3";
    private double factor = 0.5;

    public WeaknessDecorator( AttackComponent attackComponent ) { super( attackComponent ); }

    public int getAttackDamage() { return ( int )( super.getAttackDamage() * factor ); }

    public String getHashCode() { return hashCode + super.getHashCode().substring( 2 ); }
}
