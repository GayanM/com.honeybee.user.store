package com.honeybee.user.store.util;

import org.wso2.carbon.user.api.Property;


import java.util.ArrayList;

import static com.honeybee.user.store.util.HoneybeeUserStoreConstants.UMConfProperty.*;

public class HoneybeeUserStoreConstants {

    public static final ArrayList<Property> CLIMATE_UM_OPTIONAL_PROPERTIES = new ArrayList<Property>();

    public static final class UMConfProperty {
        public static final String ALGORITHM_NAME = "AlgorithmName";
        public static final String ITERATION_COUNT = "IterationCount";
        public static final String KEY_LENGTH = "KeyLength";
    }

    public static final String DEFAULT_ALGORITHM_NAME = "PBKDF2WithHmacSHA1";
    public static final String DEFAULT_ITERATION_COUNT = "5";
    public static final String DEFAULT_KEY_LENGTH = "128";

    static {
        setOptionalProperty(ALGORITHM_NAME, "Algorithm Name", DEFAULT_ALGORITHM_NAME,
                "PBKDF2 Algorithm Name");
        setOptionalProperty(ITERATION_COUNT, "Iteration Count", DEFAULT_ITERATION_COUNT,
                "Password Hashing Iteration Count");
        setOptionalProperty(KEY_LENGTH, "Key Legth", DEFAULT_KEY_LENGTH,
                "PBKDF2 Symmetric Key Length");

    }

    private static void setOptionalProperty(String name, String displayName, String value,
                                    String description) {
        Property property = new Property(name, value, description, null);
        CLIMATE_UM_OPTIONAL_PROPERTIES.add(property);

    }
}

