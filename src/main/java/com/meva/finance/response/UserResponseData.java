package com.meva.finance.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meva.finance.enums.Genre;
import com.meva.finance.model.User;

import java.time.LocalDate;

public record UserResponseData(

        String name,
        Genre genre,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birth,

        String state,
        String city) {

    public UserResponseData(User user) {
        this(user.getName(), user.getGenre(), user.getBirth(), user.getState(), user.getCity());
    }
}
