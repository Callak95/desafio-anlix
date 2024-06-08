package com.br.desafioanlix.service;

import com.br.desafioanlix.model.Caracteristica;
import com.br.desafioanlix.model.Paciente;
import com.br.desafioanlix.repository.CaracteristicaRepository;
import com.br.desafioanlix.repository.PacienteRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataImportService {

    private final PacienteRepository pacienteRepository;
    private final CaracteristicaRepository caracteristicaRepository;
    private final Resource resourceFile;

    @Autowired
    public DataImportService(PacienteRepository pacienteRepository, CaracteristicaRepository caracteristicaRepository, @Value("${data.import.filepath}") Resource resourceFile) {
        this.pacienteRepository = pacienteRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.resourceFile = resourceFile;
    }

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Paciente>> typeReference = new TypeReference<List<Paciente>>() {};

        try (InputStream inputStream = resourceFile.getInputStream()) {
            JsonNode root = mapper.readTree(inputStream);
            List<Paciente> pacientes = new ArrayList<>();

            for (JsonNode node : root) {
                Paciente paciente = mapper.treeToValue(node, Paciente.class);
                Optional<Paciente> existingPaciente = pacienteRepository.findByCpf(paciente.getCpf());
                if (existingPaciente.isEmpty()) {
                    List<Caracteristica> caracteristicas = new ArrayList<>();

                    if (node.has("altura")) {
                        Caracteristica altura = new Caracteristica();
                        altura.setTipo("Altura");
                        altura.setValor(node.get("altura").asText());
                        altura.setData(LocalDate.now());
                        altura.setPaciente(paciente);
                        caracteristicas.add(altura);
                    }

                    if (node.has("peso")) {
                        Caracteristica peso = new Caracteristica();
                        peso.setTipo("Peso");
                        peso.setValor(node.get("peso").asText());
                        peso.setData(LocalDate.now());
                        peso.setPaciente(paciente);
                        caracteristicas.add(peso);
                    }

                    paciente.setCaracteristicas(caracteristicas);
                    pacientes.add(paciente);
                }
            }

            pacienteRepository.saveAll(pacientes);
            for (Paciente paciente : pacientes) {
                caracteristicaRepository.saveAll(paciente.getCaracteristicas());
            }
            System.out.println("Pacientes e características salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao importar dados: " + e.getMessage());
        }
    }
}