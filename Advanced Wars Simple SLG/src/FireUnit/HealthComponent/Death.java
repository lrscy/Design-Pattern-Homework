package FireUnit.HealthComponent;

import Battlefield.Battlefield;
import ConnectionPool.PoolManager;
import FireUnit.FireUnit;

import java.io.Serializable;

public class Death implements HealthComponent, Serializable {
    private String healthStatus = "死亡";

    @Override
    public void setHealthStatus( FireUnit fireUnit ) {
        PoolManager.getInstance().release( fireUnit.getID() );
        Battlefield.getInstance().removeFireUnit( fireUnit );
        PoolManager.getInstance().release( fireUnit.getID() );
    }

    @Override
    public String getHealthStatus() {
        return healthStatus;
    }
}
