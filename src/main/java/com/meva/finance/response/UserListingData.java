package com.meva.finance.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meva.finance.enums.Genre;
import com.meva.finance.model.Family;
import com.meva.finance.model.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserListingData(
        String cpf,
        String name,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birth,

        String state,
        String city,
        Genre genre,
        Long idFamily) {

    public UserListingData(User user) {
        this(user.getCpf(), user.getName(), user.getBirth(), user.getState(), user.getCity(), user.getGenre(), user.getFamily().getIdFamily());
    }
}

