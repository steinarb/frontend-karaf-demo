package no.priv.bang.demos.frontendkarafdemo;

public record Counter(int value, int delta) {

    public static Builder with() {
        return new Builder();
    }

    public static class Builder {
        private int value = 0;
        private int delta = 0;

        private Builder() {}

        public Counter build() {
            return new Counter(value, delta);
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
