package Global.Iterator;

import java.util.ArrayList;
import java.util.List;

public class ConcreteIterator implements Iterator {
    private List list = new ArrayList();
    private int cursor = 0;

    public ConcreteIterator( List list ) { this.list = list; }

    @Override
    public Object next() {
        Object obj = null;
        if( hasNext() ) obj = list.get( cursor++ );
        return obj;
    }

    @Override
    public boolean hasNext() { return cursor < list.size(); }
}
