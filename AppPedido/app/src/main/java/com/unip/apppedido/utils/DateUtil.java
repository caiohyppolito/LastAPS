package com.unip.apppedido.utils;

import java.util.Calendar;

public class DateUtil {
    public static int getYearCurrent() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
}
