package Observer;

/**
 * Description: 具体广播平台
 */
public class ConcreteAllyControlCenter extends AllyControlCenter {
    @Override
    public void notifyObservers( String id ) {
        for( Observer obs : fireUnits ) {
            if( !obs.getID().equals( id ) ) {
                obs.help();
            }
        }
    }
}
