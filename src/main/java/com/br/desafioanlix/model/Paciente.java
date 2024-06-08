package com.br.desafioanlix.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int idade;
    private String cpf;
    private String rg;

    @JsonProperty("data_nasc")
    private String dataNasc;

    private String sexo;
    private String signo;
    private String mae;
    private String pai;
    private String email;
    private String senha;
    private String cep;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;

    @JsonProperty("telefone_fixo")
    private String telefoneFixo;

    private String celular;
    private String altura;
    private int peso;

    @JsonProperty("tipo_sanguineo")
    private String tipoSanguineo;

    private String cor;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Caracteristica> caracteristicas = new ArrayList<>();
}
