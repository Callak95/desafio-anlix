package com.br.desafioanlix.service;

import com.br.desafioanlix.model.Caracteristica;
import com.br.desafioanlix.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository repository;

    public List<Caracteristica> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    public List<Caracteristica> findRecentesByPacienteId(Long pacienteId) {
        List<Caracteristica> caracteristicas = repository.findByPacienteId(pacienteId);
        return caracteristicas.stream()
                .collect(Collectors.groupingBy(Caracteristica::getTipo, Collectors.maxBy((c1, c2) -> c1.getData().compareTo(c2.getData()))))
                .values().stream()
                .map(opt -> opt.orElse(null))
                .collect(Collectors.toList());
    }

    public List<Caracteristica> findByData(LocalDate data) {
        return repository.findByData(data);
    }

    public List<Caracteristica> findByPacienteIdAndTipoAndDataBetween(Long pacienteId, String tipo, LocalDate startDate, LocalDate endDate) {
        return repository.findByPacienteIdAndTipoAndDataBetween(pacienteId, tipo, startDate, endDate);
    }

    public List<Caracteristica> findByPacienteIdAndTipoAndValorBetween(Long pacienteId, String tipo, String startValue, String endValue) {
        return repository.findByPacienteIdAndTipoAndValorBetween(pacienteId, tipo, startValue, endValue);
    }

    public Map<String, Caracteristica> findAllRecentesByPacienteId(Long pacienteId) {
        List<Caracteristica> caracteristicas = repository.findByPacienteId(pacienteId);
        return caracteristicas.stream()
                .collect(Collectors.toMap(
                        Caracteristica::getTipo,
                        c -> c,
                        (c1, c2) -> c1.getData().isAfter(c2.getData()) ? c1 : c2
                ));
    }

    public List<Caracteristica> findAll() {
        return repository.findAll();
    }
}