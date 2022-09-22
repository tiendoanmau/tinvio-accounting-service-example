package com.tinvio.accounting.utils;

public final class CommonUtil {

    public static String getSymbolCurrency(String currency) {
        if("VND".equalsIgnoreCase(currency)) return "đ";
        if("SGD".equalsIgnoreCase(currency)) return "S$";
        if("IDR".equalsIgnoreCase(currency)) return "Rp";
        if("PHP".equalsIgnoreCase(currency)) return "₱";
        if("THB".equalsIgnoreCase(currency)) return "฿";
        return null;
    }

    public static String getCountryByCurrency(String currency) {
        if("VND".equalsIgnoreCase(currency)) return "VN";
        if("SGD".equalsIgnoreCase(currency)) return "SG";
        if("IDR".equalsIgnoreCase(currency)) return "ID";
        if("PHP".equalsIgnoreCase(currency)) return "PH";
        if("THB".equalsIgnoreCase(currency)) return "TH";
        return null;
    }
}
