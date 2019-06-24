package com.algorithmia.development;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.exception.ExceptionUtils;


class SerializableException<T extends RuntimeException> {
    String message;
    String stackTrace;
    String errorType;

    SerializableException(T e) {

        String exceptionAsString = ExceptionUtils.getStackTrace(e);
        message = e.getMessage();
        stackTrace = exceptionAsString;
        errorType = e.getClass().toString();
    }

    String getJsonOutput() {
        JsonObject inner = new JsonObject();
        inner.addProperty("message", this.message);
        inner.addProperty("stacktrace", this.stackTrace);
        inner.addProperty("error_type", this.errorType);
        JsonObject node = new JsonObject();
        node.add("error", inner);
        return node.toString();
    }
}
