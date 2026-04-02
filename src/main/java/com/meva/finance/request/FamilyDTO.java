package com.meva.finance.request;

import jakarta.validation.constraints.NotNull;

public record FamilyDTO(

        @NotNull
        Long idFamily,

        String description
) {
}
