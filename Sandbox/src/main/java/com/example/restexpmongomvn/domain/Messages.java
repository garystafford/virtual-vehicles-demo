package com.example.restexpmongomvn.domain;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author gstafford
 */
public class Messages {

    private static final String BUNDLE_NAME = "dependinject.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Messages() {
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
