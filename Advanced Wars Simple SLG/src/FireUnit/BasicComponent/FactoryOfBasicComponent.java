package FireUnit.BasicComponent;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description: 享元工厂 产生基础部分
 */
public class FactoryOfBasicComponent implements Serializable {
    private static FactoryOfBasicComponent factoryOfBasicComponent = null;
    private HashMap<String, BasicComponent> fireUnits = null;   //  享元池

    private FactoryOfBasicComponent() {
        if( fireUnits == null ) fireUnits = new HashMap<>();
    }

    public static FactoryOfBasicComponent getInstance() {
        if( factoryOfBasicComponent == null ) {
            synchronized( FactoryOfBasicComponent.class ) {
                if( factoryOfBasicComponent == null ) {
                    factoryOfBasicComponent = new FactoryOfBasicComponent();
                }
            }
        }
        return factoryOfBasicComponent;
    }

    /**
     * Description: 通过享元池和反射机制生成基础部分内容并返回
     * @param unit  基础部分产品簇名
     * @return      具体基础部分
     */
    public BasicComponent getBasicComponent( String unit ) {
        BasicComponent tmpB = fireUnits.get( unit );
        AbstractFactoryOfFireUnit tmpA = null;
        if( tmpB == null ) {
            tmpB = new ConcreteBasicComponent();
            try {
                Class c = Class.forName( "FireUnit.BasicComponent." + unit );
                tmpA = ( AbstractFactoryOfFireUnit )c.newInstance();
            } catch( Exception e ) {
                System.out.println( "无法找到该类" );
            }
            if( tmpA != null ) tmpB.createFireUnit( tmpA );
        }
        return tmpB;
    }
}
