package FireUnit.HealthComponent;

import FireUnit.FireUnit;

public class Death implements HealthComponent {
    private String healthStatus = "死亡";

    // TODO: 对象池删除该FireUnit
    public void setHealthStatus( FireUnit fireUnit ) {
        ;
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}
