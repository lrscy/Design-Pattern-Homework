package FireUnit.BasicComponent;

import java.io.Serializable;
import java.util.HashMap;

public class FactoryOfBasicComponent implements Serializable {
    private static FactoryOfBasicComponent factoryOfBasicComponent = null;
    private HashMap<String, BasicComponent> fireUnits = null;

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
