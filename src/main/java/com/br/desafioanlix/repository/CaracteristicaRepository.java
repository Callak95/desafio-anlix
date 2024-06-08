package com.br.desafioanlix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.br.desafioanlix.model.Caracteristica;

import java.time.LocalDate;
import java.util.List;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    List<Caracteristica> findByPacienteId(Long pacienteId);
    List<Caracteristica> findByPacienteIdAndTipo(Long pacienteId, String tipo);
    List<Caracteristica> findByPacienteIdAndTipoAndDataBetween(Long pacienteId, String tipo, LocalDate startDate, LocalDate endDate);
    List<Caracteristica> findByPacienteIdAndTipoAndValorBetween(Long pacienteId, String tipo, String startValue, String endValue);
    List<Caracteristica> findByData(LocalDate data);
}