package FireUnit.HealthComponent;

import FireUnit.AttackComponent.AttackAttrOfFireUnit;
import FireUnit.AttackComponent.GeneralDecorator;
import FireUnit.FireUnit;

import java.io.Serializable;

public class Healthy implements HealthComponent, Serializable {
    private String healthStatus = "健康";

    @Override
    public void setHealthStatus( FireUnit fireUnit ) {
        fireUnit.setAttackComponent( new GeneralDecorator( fireUnit.getAttackComponent() ) );
    }

    @Override
    public String getHealthStatus() {
        return healthStatus;
    }
}
