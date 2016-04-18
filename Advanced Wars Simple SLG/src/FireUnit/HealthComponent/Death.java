package FireUnit.HealthComponent;

import ConnectionPool.PoolManager;
import FireUnit.FireUnit;

public class Death implements HealthComponent {
    private String healthStatus = "死亡";

    public void setHealthStatus( FireUnit fireUnit ) {
        PoolManager.getInstance().release( fireUnit.getID() );
    }

    public String getHealthStatus() { return healthStatus; }
}
