package com.galaxy.andromeda.util;

import com.galaxy.andromeda.enums.ProductType;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class AndromedaUtil {

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public static int pickValue(int voValue, int entityValue) {
        return voValue == 0 ? entityValue : voValue;
    }

    public static LocalDate pickValue(LocalDate voValue, LocalDate entityValue) {
        return voValue == null ? entityValue : voValue;
    }

    public static String pickValue(String voValue, String entityValue) {
        return StringUtils.isEmpty(voValue) ? entityValue : voValue;
    }

    public static ProductType pickValue(ProductType voValue, ProductType entityValue) {
        return voValue == null ? entityValue : voValue;
    }
}
