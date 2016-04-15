package Factory;

import FireUnit.AttackComponent.AttackComponent;
import FireUnit.BasicComponent.BasicComponent;
import FireUnit.FireUnit;
import Global.Position;

public class FactoryOfFireUnit {
    private String id = null;
    private String troopName = null;
    private Position position;

    public FactoryOfFireUnit( String id, String troopName, Position position ) {
        this.id = id;
        this.troopName = troopName;
        this.position = new Position( position.getX(), position.getY() );
    }

    public FireUnit produce( AttackComponent attackComponent, BasicComponent basicComponent ) {
        // TODO: 去对象池中找是否有一样的先拿来用。
        // TODO: 生成随机ID，生成新的对象放入对象池中
        FireUnit fireUnit = null;
        // TODO: 提示地图更新
        return fireUnit;
    }
}
