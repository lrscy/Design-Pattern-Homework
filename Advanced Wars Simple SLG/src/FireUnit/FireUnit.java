package FireUnit;

import Canvas.Hint;
import FireUnit.AttackComponent.AttackComponent;
import FireUnit.BasicComponent.BasicComponent;
import FireUnit.HealthComponent.Death;
import FireUnit.HealthComponent.HealthComponent;
import FireUnit.HealthComponent.Healthy;
import FireUnit.HealthComponent.Injured;
import Global.Position;
import Observer.AllyControlCenter;
import Observer.Observer;

import java.io.*;

/**
 * Description: 描述一个火力单元
 */
public class FireUnit implements Observer, Serializable {
    private String id = null;
    private String troopName = null;
    private String unitName = null;
    private String weaponName = null;
    private String hashCode = null; // 通过哈希码判断元素相等
    private Position position = null;
    private int attackDamage;
    private int health;
    private AttackComponent attackComponent = null;
    private HealthComponent healthComponent = null;
    // 基础部分不序列化保证基础部分仅由基础享元模式工厂生产而来
    private transient BasicComponent basicComponent = null;

    public FireUnit( String id, String troopName, Position position, int health,
                     AttackComponent attackComponent, BasicComponent basicComponent ) {
        this.id = id;
        this.troopName = troopName;
        setPosition( position );
        this.attackComponent = attackComponent;
        this.basicComponent = basicComponent;
        attackDamage = basicComponent.attackEffect();
        setHealthValue( health );
        unitName = basicComponent.getName();
        weaponName = attackComponent.getName();
    }

    public FireUnit( AttackComponent attackComponent, BasicComponent basicComponent ) {
        this.attackComponent = attackComponent;
        this.basicComponent = basicComponent;
        troopName = basicComponent.getName();
        weaponName = attackComponent.getName();
    }

    /**
     * Description: 附加属性(除了三个基础部分以外的属性)拷贝
     * @param fu    源火力单元
     */
    public void attrCopy( FireUnit fu ) {
        id = fu.getID();
        troopName = fu.getTroopName();
        unitName = fu.getUnitName();
        weaponName = fu.getWeaponName();
        position = fu.getPosition();
        health = fu.getHealthValue();
        hashCode = fu.getHashCode();
    }

    @Override
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

    public void setPosition( Position position ) { this.position = position; }

    public Position getPosition() { return position; }

    public void setAttackComponent( AttackComponent attackComponent ) { this.attackComponent = attackComponent; }

    public AttackComponent getAttackComponent() { return attackComponent; }

    private void setBasicComponent( BasicComponent basicComponent ) { this.basicComponent = basicComponent; }

    public int getMoveRange() { return basicComponent.maxMoveRange(); }

    public int getAttackRange() { return basicComponent.maxAttackRange(); }

    public int getAttackValue() { return attackComponent.getAttackDamage( attackDamage ); }

    public int getDefenceValue() { return basicComponent.defenceEffect(); }

    /**
     * Description: 状态模式实现 依据当前健康值确定火力单元状态
     * @param health    当前健康值
     */
    public void setHealthValue( int health ) {
        this.health = health;
        if( health >= 60 ) healthComponent = new Healthy();
        else if( health > 0 ) healthComponent = new Injured();
        else healthComponent = new Death();
        healthComponent.setHealthStatus( this );
    }

    public int getHealthValue() { return health; }

    /**
     * Description: 通过序列化进行火力单元的深克隆
     * @return 克隆后的火力单元
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public FireUnit deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( bao );
        oos.writeObject( this );
        oos.close();
        ByteArrayInputStream bis = new ByteArrayInputStream( bao.toByteArray() );
        ObjectInputStream ois = new ObjectInputStream( bis );
        FireUnit fu = ( FireUnit )ois.readObject();
        ois.close();
        // 基础部分统一使用享元工厂生成的基础部分的引用
        fu.setBasicComponent( this.basicComponent );
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

    /**
     * Description: 广播该火力单元已受伤
     * @param acc   广播平台
     */
    @Override
    public void beAttacked( AllyControlCenter acc ) { acc.notifyObservers( id ); }

    @Override
    public void help() {
        Hint.getInstance().setText( troopName + unitName + "部队受伤！坐标( " + position + " )" );
    }
}
