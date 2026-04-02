package com.meva.finance.request;

import jakarta.validation.constraints.NotNull;

public record UserUpdateData(

        @NotNull
        String cpf,

        String name,

        String state,

        String city
) {
}
