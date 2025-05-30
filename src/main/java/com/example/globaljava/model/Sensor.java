package com.example.globaljava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date dataSensor;

    @NotNull
    private LocalTime horario;

    @NotNull
    private int temperatura;

    @NotNull
    private int umidade;

    @NotNull
    private int vento;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Funcionario funcionario;
}
