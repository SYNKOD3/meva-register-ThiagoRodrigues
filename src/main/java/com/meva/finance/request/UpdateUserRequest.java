package com.meva.finance.request;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(

        @NotNull
        String cpf,

        String name,

        String state,

        String city
) {
}
