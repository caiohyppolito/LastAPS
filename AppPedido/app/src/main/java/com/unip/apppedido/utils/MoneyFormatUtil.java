package com.unip.apppedido.utils;

import java.util.Locale;

public class MoneyFormatUtil {
    public static String formatToMoney(double money){
        return "R$ " + String.format(Locale.getDefault(), "%.2f", money);
    }
}
