package com.br.desafioanlix.controller;

import com.br.desafioanlix.model.Caracteristica;
import com.br.desafioanlix.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaService caracteristicaService;

    @GetMapping("/paciente/{pacienteId}")
    public List<Caracteristica> getCaracteristicasByPacienteId(@PathVariable Long pacienteId) {
        return caracteristicaService.findByPacienteId(pacienteId);
    }

    @GetMapping("/paciente/{pacienteId}/recentes")
    public List<Caracteristica> getRecentesCaracteristicasByPacienteId(@PathVariable Long pacienteId) {
        return caracteristicaService.findRecentesByPacienteId(pacienteId);
    }

    @GetMapping("/data/{data}")
    public List<Caracteristica> getCaracteristicasByData(@PathVariable String data) {
        LocalDate localDate = parseDate(data);
        return caracteristicaService.findByData(localDate);
    }

    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}/daterange")
    public List<Caracteristica> getCaracteristicasByDateRange(@PathVariable Long pacienteId, @RequestParam String tipo, @RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);
        return caracteristicaService.findByPacienteIdAndTipoAndDataBetween(pacienteId, tipo, start, end);
    }

    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}/valuerange")
    public List<Caracteristica> getCaracteristicasByValueRange(@PathVariable Long pacienteId, @RequestParam String tipo, @RequestParam String startValue, @RequestParam String endValue) {
        return caracteristicaService.findByPacienteIdAndTipoAndValorBetween(pacienteId, tipo, startValue, endValue);
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd/MM/yyyy][dd-MM-yyyy][dd/MM/yy][dd-MM-yy]");
        return LocalDate.parse(date, formatter);
    }
}