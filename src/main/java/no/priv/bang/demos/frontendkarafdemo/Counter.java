package no.priv.bang.demos.frontendkarafdemo;

public class Counter {

    private int value;
    private int delta;

    private Counter() {}

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

    public static Builder with() {
        return new Builder();
    }

    public static class Builder {
        private int value = 0;
        private int delta = 0;

        private Builder() {}

        public Counter build() {
            var counter = new Counter();
            counter.value = this.value;
            counter.delta = this.delta;
            return counter;
        }

        public Builder value(int value) {
            this.value = value;
            return this;
        }

        public Builder delta(int delta) {
            this.delta = delta;
            return this;
        }

    }

}
