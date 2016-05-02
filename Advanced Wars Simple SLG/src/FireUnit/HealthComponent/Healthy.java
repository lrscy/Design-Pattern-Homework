package FireUnit.HealthComponent;

import FireUnit.AttackComponent.GeneralDecorator;
import FireUnit.FireUnit;

import java.io.Serializable;

/**
 * Description: 健康状态
 */
public class Healthy implements HealthComponent, Serializable {
    @Override
    public void setHealthStatus( FireUnit fireUnit ) {
        if( fireUnit.getAttackComponent() instanceof GeneralDecorator ) return ;
        fireUnit.setAttackComponent( new GeneralDecorator( fireUnit.getAttackComponent() ) );
    }

    @Override
    public String getHealthStatus() {
        return "健康";
    }
}
