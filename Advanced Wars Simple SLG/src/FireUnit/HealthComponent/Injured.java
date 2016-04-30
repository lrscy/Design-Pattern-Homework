package FireUnit.HealthComponent;

import FireUnit.AttackComponent.WeaknessDecorator;
import FireUnit.FireUnit;

import java.io.Serializable;

public class Injured implements HealthComponent, Serializable {
    private String healthStatus = "受伤";

    public void setHealthStatus( FireUnit fireUnit ) {
        fireUnit.setAttackComponent( new WeaknessDecorator( fireUnit.getAttackComponent() ) );
    }

    public String getHealthStatus() { return healthStatus; }
}
