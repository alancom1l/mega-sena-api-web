package com.megaproject.mega_api_web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EstatisticaNumero {

    @Id
    private Integer numero;

    private int vezesSorteado = 0;

    private Integer ultimoConcurso;

    private int atraso = 0;
}