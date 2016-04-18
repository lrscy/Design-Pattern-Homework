package FireUnit;

import FireUnit.AttackComponent.AttackComponent;
import FireUnit.BasicComponent.BasicComponent;
import FireUnit.HealthComponent.Death;
import FireUnit.HealthComponent.HealthComponent;
import FireUnit.HealthComponent.Healthy;
import FireUnit.HealthComponent.Injured;
import Global.Position;

import java.io.*;

public class FireUnit {
    private String id;
    private String troopName;
    private String unitName = null;
    private String weaponName = null;
    private String hashCode = null;
    private Position position;
    private int health;
    private AttackComponent attackComponent;
    private HealthComponent healthComponent;
    private transient BasicComponent basicComponent;

    public FireUnit( String id, String troopName, Position position, int health,
                     AttackComponent attackComponent, BasicComponent basicComponent ) {
        this.id = id;
        this.troopName = troopName;
        setPosition( position );
        setHealthValue( health );
        this.attackComponent = attackComponent;
        this.basicComponent = basicComponent;
        unitName = basicComponent.getName();
        weaponName = attackComponent.getName();
    }

    public FireUnit( AttackComponent attackComponent, BasicComponent basicComponent ) {
        this.attackComponent = attackComponent;
        this.basicComponent = basicComponent;
        troopName = basicComponent.getName();
        weaponName = attackComponent.getName();
    }

    public String getID() { return id; }

    public String getTroopName() { return troopName; }

    public String getUnitName() {
        if( unitName == null ) unitName = basicComponent.getName();
        return unitName;
    }

    public String getWeaponName() {
        if( weaponName == null ) weaponName = attackComponent.getName();
        return weaponName;
    }

    public String getHealthStatus() { return healthComponent.getHealthStatus(); }

    public String getHashCode() {
        hashCode = basicComponent.getHashCode() + attackComponent.getHashCode();
        return hashCode;
    }

    public void setPosition( int x, int y ) {
        position.setX( x );
        position.setY( y );
    }

    public void setPosition( Position position ) { this.position = position; }

    public Position getPosition() { return position; }

    public void setAttackComponent( AttackComponent attackComponent ) { this.attackComponent = attackComponent; }

    public AttackComponent getAttackComponent() { return attackComponent; }

    public void setBasicComponent( BasicComponent basicComponent ) { this.basicComponent = basicComponent; }

    public BasicComponent getBasicComponent() { return basicComponent; }

    public HealthComponent getHealthComponent() { return healthComponent; }

    public int getMoveRange() { return basicComponent.maxMoveRange(); }

    public int getAttackRange() { return basicComponent.maxAttackRange(); }

    public int getAttackValue() { return basicComponent.attackEffect(); }

    public int getDefenceValue() { return basicComponent.defenceEffect(); }

    public void setHealthValue( int health ) {
        this.health = health;
        if( health >= 60 ) healthComponent = new Healthy();
        else if( health >= 0 ) healthComponent = new Injured();
        else healthComponent = new Death();
        healthComponent.setHealthStatus( this );
    }

    public int getHealthValue() { return health; }

    public FireUnit deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( bao );
        oos.writeObject( this );
        ByteArrayInputStream bis = new ByteArrayInputStream( bao.toByteArray() );
        ObjectInputStream ois = new ObjectInputStream( bis );
        FireUnit fu = ( FireUnit )ois.readObject();
        fu.setBasicComponent( this.basicComponent );
        // TODO: toString 检测
        return fu;
    }

    @Override
    public boolean equals( Object obj ) {
        boolean ret = false;
        if( obj instanceof FireUnit ) {
            FireUnit fireUnit = ( FireUnit )obj;
            if( getHashCode().equals( fireUnit.getHashCode() ) ) ret = true;
        }
        return ret;
    }

    @Override
    public int hashCode() { return 0; }

    @Override
    public String toString() {
        return troopName + "\n兵种：" + unitName + "\n武器：" + weaponName + "\n生命值：" + Integer.toString( health );
    }
}
