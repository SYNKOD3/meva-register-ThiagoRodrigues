package com.meva.finance.request;

import jakarta.validation.constraints.NotNull;

public record FamilyRequest(

        @NotNull
        Long idFamily,

        String description
) {
}
