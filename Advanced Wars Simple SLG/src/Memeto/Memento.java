package Memeto;

public class Memento {
    private LastAction lastAction = null;

    public Memento( LastAction lastAction ) { this.lastAction = lastAction; }

    public String getState() { return lastAction.getState(); }

    public LastAction getLastAction() { return lastAction; }

    public void setLastAction( LastAction lastAction ) { this.lastAction = lastAction; }
}
