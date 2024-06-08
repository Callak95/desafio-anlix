package com.br.desafioanlix.controller;

import com.br.desafioanlix.model.Paciente;
import com.br.desafioanlix.model.Caracteristica;
import com.br.desafioanlix.service.PacienteService;
import com.br.desafioanlix.service.CaracteristicaService;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private CaracteristicaService caracteristicaService;

    @GetMapping
    public List<Paciente> getAll() {
        return pacienteService.findAll();
    }

    @GetMapping("/{id}")
    public Paciente getById(@PathVariable Long id) {
        return pacienteService.findById(id);
    }

    @GetMapping("/{id}/caracteristicas")
    public List<Caracteristica> getCaracteristicas(@PathVariable Long id) {
        return caracteristicaService.findByPacienteId(id);
    }

    @GetMapping("/{id}/caracteristicas/recentes")
    public List<Caracteristica> getRecentesCaracteristicas(@PathVariable Long id) {
        return caracteristicaService.findRecentesByPacienteId(id);
    }

    @GetMapping("/data/{data}")
    public List<Caracteristica> getCaracteristicasByData(@PathVariable String data) {
        LocalDate localDate = parseDate(data);
        return caracteristicaService.findByData(localDate);
    }

    @GetMapping("/{id}/caracteristicas/{tipo}/data/{startDate}/{endDate}")
    public List<Caracteristica> getCaracteristicasByDateRange(
            @PathVariable Long id,
            @PathVariable String tipo,
            @PathVariable String startDate,
            @PathVariable String endDate) {
        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);
        return caracteristicaService.findByPacienteIdAndTipoAndDataBetween(id, tipo, start, end);
    }

    @GetMapping("/{id}/caracteristicas/{tipo}/valor/{startValue}/{endValue}")
    public List<Caracteristica> getCaracteristicasByValueRange(
            @PathVariable Long id,
            @PathVariable String tipo,
            @PathVariable String startValue,
            @PathVariable String endValue) {
        return caracteristicaService.findByPacienteIdAndTipoAndValorBetween(id, tipo, startValue, endValue);
    }

    @GetMapping("/buscar/{nome}")
    public List<Paciente> buscarPorNome(@PathVariable String nome) {
        return pacienteService.findByNomeContaining(nome);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportToCsv() throws IOException {
        String filename = "pacientes.csv";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(writer);

        // Cabeçalho do CSV
        String[] header = {"ID", "Nome", "Idade", "CPF", "RG", "DataNasc", "Sexo", "Signo", "Mae", "Pai", "Email", "Senha", "CEP", "Endereco", "Numero", "Bairro", "Cidade", "Estado", "TelefoneFixo", "Celular", "Altura", "Peso", "TipoSanguineo", "Cor", "Caracteristicas"};
        csvWriter.writeNext(header);

        List<Paciente> pacientes = pacienteService.findAll();
        for (Paciente paciente : pacientes) {
            StringBuilder caracteristicasStr = new StringBuilder();
            List<Caracteristica> caracteristicas = paciente.getCaracteristicas();
            for (Caracteristica caracteristica : caracteristicas) {
                caracteristicasStr.append(caracteristica.getTipo()).append("=").append(caracteristica.getValor()).append("; ");
            }

            // Linhas do CSV
            String[] data = {
                    String.valueOf(paciente.getId()), paciente.getNome(), String.valueOf(paciente.getIdade()), paciente.getCpf(), paciente.getRg(),
                    paciente.getDataNasc(), paciente.getSexo(), paciente.getSigno(), paciente.getMae(), paciente.getPai(),
                    paciente.getEmail(), paciente.getSenha(), paciente.getCep(), paciente.getEndereco(), String.valueOf(paciente.getNumero()),
                    paciente.getBairro(), paciente.getCidade(), paciente.getEstado(), paciente.getTelefoneFixo(), paciente.getCelular(),
                    paciente.getAltura(), String.valueOf(paciente.getPeso()), paciente.getTipoSanguineo(), paciente.getCor(), caracteristicasStr.toString().trim()
            };
            csvWriter.writeNext(data);
        }

        csvWriter.close();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(baos.toByteArray());
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yy"),
                DateTimeFormatter.ofPattern("dd-MM-yy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                // Ignorar a exceção
            }
        }
        throw new IllegalArgumentException("Formato da data inválido: " + dateStr);
    }
}