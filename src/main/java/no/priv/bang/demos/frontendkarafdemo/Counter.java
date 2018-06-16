package no.priv.bang.demos.frontendkarafdemo;

public class Counter {

    private int value;
    private int delta;

    public Counter(int value, int delta) {
        this.value = value;
        this.delta = delta;
    }

    public Counter() {
        this(0, 0);
    }

    public int getValue() {
        return value;
    }

    public int getDelta() {
        return delta;
    }

    @Override
    public String toString() {
        return "Counter [value=" + value + ", delta=" + delta + "]";
    }

}
