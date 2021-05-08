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

    public static CounterBuilder with() {
        return new CounterBuilder();
    }

    public static class CounterBuilder {
        private int value = 0;
        private int delta = 0;

        private CounterBuilder() {}

        public Counter build() {
            Counter counter = new Counter();
            counter.value = this.value;
            counter.delta = this.delta;
            return counter;
        }

        public CounterBuilder value(int value) {
            this.value = value;
            return this;
        }

        public CounterBuilder delta(int delta) {
            this.delta = delta;
            return this;
        }

    }

}
