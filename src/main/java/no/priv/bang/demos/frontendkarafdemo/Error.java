package no.priv.bang.demos.frontendkarafdemo;

public record Error(int status, String message) {

    public static Builder with() {
        return new Builder();
    }

    public static class Builder {
        int status = -1;
        String message = "";

        private Builder() {}

        public Error build() {
            return new Error(status, message);
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

    }
}
