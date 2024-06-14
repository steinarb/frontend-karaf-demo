package no.priv.bang.demos.frontendkarafdemo;

public class Error {
    int status;
    String message;

    private Error() {}

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static Builder with() {
        return new Builder();
    }

    public static class Builder {
        int status = -1;
        String message = "";

        private Builder() {}

        public Error build() {
            var error = new Error();
            error.status = this.status;
            error.message = this.message;
            return error;
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
