package FireUnit.HealthComponent;

import Battlefield.Battlefield;
import ConnectionPool.PoolManager;
import FireUnit.FireUnit;

import java.io.Serializable;

/**
 * Description: 死亡状态
 */
public class Death implements HealthComponent, Serializable {
    @Override
    public void setHealthStatus( FireUnit fireUnit ) {
        PoolManager.getInstance().release( fireUnit.getID() );
        Battlefield.getInstance().removeFireUnit( fireUnit );
        PoolManager.getInstance().release( fireUnit.getID() );
    }

    @Override
    public String getHealthStatus() {
        return "死亡";
    }
}
