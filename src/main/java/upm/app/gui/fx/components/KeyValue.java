package upm.app.gui.fx.components;

public class KeyValue<T> {
    private final T key;
    private final String displayValue;

    public KeyValue(T key, String displayValue) {
        this.key = key;
        this.displayValue = displayValue;
    }

    public T getKey() {
        return this.key;
    }

    public String getDisplayValue() {
        return this.displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}

