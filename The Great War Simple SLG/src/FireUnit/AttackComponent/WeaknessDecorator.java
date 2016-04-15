package FireUnit.AttackComponent;

public class WeaknessDecorator extends AttackComponentDecorator {
    private String hashCode = "3";
    private double factor = 0.5;

    public WeaknessDecorator( AttackComponent attackComponent ) {
        super( attackComponent );
    }

    public int getAttackDamage() {
        return (int) ( super.getAttackDamage() * factor );
    }

    public String getHashCode() {
        return hashCode + super.getHashCode().substring( 2 );
    }
}
