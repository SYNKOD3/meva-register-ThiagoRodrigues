package com.meva.finance.request;

import jakarta.validation.constraints.NotNull;

public record CreateFamilyRequest(

        @NotNull
        Long idFamily,

        String description
) {
}
