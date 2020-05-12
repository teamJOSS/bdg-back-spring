package com.joss.bundaegi.utils;

import com.joss.bundaegi.config.WebConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class MessageUtils {
    private static ReloadableResourceBundleMessageSource resources = new WebConfig().messageSource();

    public static String getMessage(String messageCode) {
        return resources.getMessage(messageCode,null ,Locale.getDefault());
    }

    public static String getMessage(String messageCode, Object[] objs) {
        return resources.getMessage(messageCode, objs, Locale.getDefault());
    }
}
