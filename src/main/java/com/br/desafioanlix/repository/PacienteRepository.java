package com.br.desafioanlix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.br.desafioanlix.model.Paciente;


import java.util.List;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByNomeContaining(String nome);
    Optional<Paciente> findByCpf(String cpf);
}