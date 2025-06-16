package com.megaproject.mega_api_web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Data
@Entity
public class Concurso {

    @Id
    private Integer id;

    private LocalDate data;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "dezenas", columnDefinition = "integer[]")
    private Integer[] dezenas;
}
