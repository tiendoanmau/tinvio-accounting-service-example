package com.tinvio.accounting.utils;

import com.tinvio.accounting.constant.Constants;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    public static final Timestamp getDueDate() {
        Instant now = Instant.now();
        Instant dueDate = now.plus(Constants.INVOICE_DUE_DATE_PLUS_DAYS, ChronoUnit.DAYS);
        return Timestamp.from(dueDate);
    }

}