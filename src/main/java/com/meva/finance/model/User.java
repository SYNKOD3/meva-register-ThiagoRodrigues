package com.meva.finance.model;

import com.meva.finance.enums.Genre;
import com.meva.finance.request.UserRegistryData;
import com.meva.finance.request.UserUpdateData;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table (name = "user_meva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String cpf;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate birth;

    private String state;
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_family")
    private Family family;


    public User(UserRegistryData data, Family family) {
        if (data == null) throw new IllegalArgumentException("Todos os dados precisam estar preenchidos!");
        this.cpf = data.cpf();
        this.name = data.name();
        this.genre = data.genre();
        this.birth = data.birth();
        this.state = data.state();
        this.city = data.city();
        this.family = family;
    }

    public void updateInformation(@Valid UserUpdateData data){
        if(data.name() != null) {
            this.name = data.name();
        }

        if(data.state() != null) {
            this.state = data.state();
        }

        if(data.city() != null) {
            this.city = data.city();
        }
    }
}
