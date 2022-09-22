package com.tinvio.accounting.model.enums;

public enum HttpResponseStatus {
    OK, FAILED, CREATED;

    HttpResponseStatus() {
    }

    @Override
    public String toString() {
         return this.name().toLowerCase();
    }
}
