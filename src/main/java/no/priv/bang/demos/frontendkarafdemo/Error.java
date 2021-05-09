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

    public static ErrorBuilder with() {
        return new ErrorBuilder();
    }

    public static class ErrorBuilder {
        int status = -1;
        String message = "";

        private ErrorBuilder() {}

        public Error build() {
            Error error = new Error();
            error.status = this.status;
            error.message = this.message;
            return error;
        }

        public ErrorBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

    }
}
