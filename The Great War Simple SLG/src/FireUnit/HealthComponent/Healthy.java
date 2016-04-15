package FireUnit.HealthComponent;

import FireUnit.AttackComponent.AttackAttrOfFireUnit;
import FireUnit.AttackComponent.GeneralDecorator;
import FireUnit.FireUnit;

public class Healthy implements HealthComponent {
    private String healthStatus = "健康";

    public void setHealthStatus( FireUnit fireUnit ) {
        fireUnit.setAttackComponent( new GeneralDecorator( new AttackAttrOfFireUnit() ) );
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}
