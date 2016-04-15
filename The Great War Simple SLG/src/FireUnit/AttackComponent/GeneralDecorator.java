package FireUnit.AttackComponent;

public class GeneralDecorator extends AttackComponentDecorator {
    private String hashCode = "2";
    private double factor = 2;

    public GeneralDecorator( AttackComponent attackComponent ) {
        super( attackComponent );
    }

    public int getAttackDamage() {
        return (int) ( super.getAttackDamage() * factor );
    }

    public String getHashCode() {
        return hashCode + super.getHashCode().substring( 2 );
    }
}