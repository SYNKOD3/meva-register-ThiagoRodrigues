package com.meva.finance.model;

import com.meva.finance.request.CreateFamilyRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "family")
@Getter
@Setter
@NoArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_family")
    private Long idFamily;

    private String description;

    public Family(CreateFamilyRequest dto) {
        this.idFamily = (dto.idFamily() == 0) ? null : dto.idFamily();
        this.description = dto.description();
    }

}
