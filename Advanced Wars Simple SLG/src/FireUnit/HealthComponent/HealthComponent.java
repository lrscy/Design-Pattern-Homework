package FireUnit.HealthComponent;

import FireUnit.FireUnit;

/**
 * Description: 抽象健康状态
 */
public interface HealthComponent {
    void setHealthStatus( FireUnit fireUnit );

    String getHealthStatus();
}
