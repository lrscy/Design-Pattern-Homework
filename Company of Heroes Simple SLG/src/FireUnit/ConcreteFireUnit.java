package FireUnit;

public class ConcreteFireUnit {
    private String name;
    private WayOfMove wayOfMove;
    private WayOfAttack wayOfAttack;
    private WayOfDefence wayOfDefence;

    public String getName() {
        return name;
    }

    public ConcreteFireUnit( String name, WayOfMove wayOfMove,
                             WayOfAttack wayOfAttack, WayOfDefence wayOfDefence ) {
        this.name = name;
        this.wayOfMove = wayOfMove;
        this.wayOfAttack = wayOfAttack;
        this.wayOfDefence = wayOfDefence;
    }

    ;
}
