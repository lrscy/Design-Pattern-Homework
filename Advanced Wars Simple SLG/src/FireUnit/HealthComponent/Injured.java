package FireUnit.HealthComponent;

import FireUnit.AttackComponent.WeaknessDecorator;
import FireUnit.FireUnit;

import java.io.Serializable;

/**
 * Description: 受伤状态
 */
public class Injured implements HealthComponent, Serializable {
    @Override
    public void setHealthStatus( FireUnit fireUnit ) {
        if( fireUnit.getAttackComponent() instanceof WeaknessDecorator ) return ;
        fireUnit.setAttackComponent( new WeaknessDecorator( fireUnit.getAttackComponent() ) );
    }

    @Override
    public String getHealthStatus() {
        return "受伤";
    }
}
