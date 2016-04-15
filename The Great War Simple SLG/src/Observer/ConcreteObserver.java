package Observer;

import FireUnit.FireUnit;

public class ConcreteObserver implements Observer {
    private String id = null;
    private FireUnit fireUnit = null;

    public ConcreteObserver( FireUnit fireUnit ) {
        this.fireUnit = fireUnit;
        this.id = fireUnit.getID();
    }

    public String getID() {
        return id;
    }

    public FireUnit getFireUnit() {
        return fireUnit;
    }

    public void beAttacked( AllyControlCenter acc ) {
        acc.notifyObservers( id );
    }

    @Override
    public boolean equals( Object obj ) {
        boolean ret = false;
        if( obj instanceof ConcreteObserver ) {
            ConcreteObserver obs = (ConcreteObserver) obj;
            if( id.equals( obs.getID() ) && fireUnit.equals( obs.getFireUnit() ) ) ret = true;
        }
        return ret;
    }
}
