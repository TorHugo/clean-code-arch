package com.dev.torhugo.clean.code.arch.domain.utils;

import java.util.UUID;

public class IdentifierUtils {

    private IdentifierUtils() {
        throw new UnsupportedOperationException("IdentifierUtils is a utility class and should not be instantiated.");
    }

    /**
     * Generate UUID for models identifier.
     *
     * @return UUID
     */
    public static UUID generateIdentifier(){
        return UUID.randomUUID();
    }

}
