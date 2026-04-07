package com.meva.finance.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meva.finance.enums.Genre;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CreateUserRequest(

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
    String cpf,

    @NotBlank(message = "O nome é obrigatório")
    String name,

    @NotNull(message = "O campo 'sexo' é obrigatório")
    Genre genre,

    @NotNull(message = "A data de nascimento é obrigatória no formato dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate birth,

    @NotBlank(message = "O campo 'estado' é obrigatório")
    String state,

    @NotBlank(message = "O campo 'cidade' é obrigatório")
    String city,

    @NotNull
    @Valid
    CreateFamilyRequest createFamilyRequest
    ) {
}