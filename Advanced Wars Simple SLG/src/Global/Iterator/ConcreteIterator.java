package Global.Iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 具体迭代器
 */
class ConcreteIterator implements Iterator {
    private List list = new ArrayList();
    private int cursor = 0;

    ConcreteIterator( List list ) { this.list = list; }

    @Override
    public Object next() {
        Object obj = null;
        if( hasNext() ) obj = list.get( cursor++ );
        return obj;
    }

    @Override
    public boolean hasNext() { return cursor < list.size(); }
}
