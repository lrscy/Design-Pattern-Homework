package Memeto;

/**
 * Description: 备忘录角色
 */
public class Memento {
    private LastAction lastAction = null;

    public Memento( LastAction lastAction ) { this.lastAction = lastAction; }

    public LastAction getLastAction() { return lastAction; }
}
