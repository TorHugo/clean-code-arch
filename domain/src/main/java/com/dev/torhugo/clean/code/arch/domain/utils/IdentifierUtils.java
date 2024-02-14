package com.dev.torhugo.clean.code.arch.domain.utils;

import java.util.UUID;

public class IdentifierUtils {

    /**
     * Generate UUID for models identifier.
     *
     * @return UUID
     */
    public static UUID generateIdentifier(){
        return UUID.randomUUID();
    }

}
