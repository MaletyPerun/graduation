package ru.topjava.error;

public class ErrorInfo {

    private final String url;
    private final ErrorType errorType;
    private final String typeMessage;
    private final String[] details;

    public ErrorInfo(CharSequence url, ErrorType errorType, String typeMessage, String... details) {
        this.url = url.toString();
        this.errorType = errorType;
        this.typeMessage = typeMessage;
        this.details = details;
    }
}
