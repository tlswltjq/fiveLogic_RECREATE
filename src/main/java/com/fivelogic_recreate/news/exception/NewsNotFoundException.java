package com.fivelogic_recreate.news.exception;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException() {
        super("News not found");
    }
}
