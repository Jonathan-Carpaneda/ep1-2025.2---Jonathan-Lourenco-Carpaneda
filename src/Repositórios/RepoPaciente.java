package Repositórios;

import Entidades.Paciente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoPaciente {
    private final String CAMINHO_ARQUIVO = "pacientes.csv";
    private List<Paciente> pacientes;
    public RepoPaciente() {
        this.pacientes = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                if (valores.length >= 3) {
                    String cpf = valores[0];
                    String nome = valores[1];
                    int idade = Integer.parseInt(valores[2].trim());
                    this.pacientes.add(new Paciente(nome, cpf, idade));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Arquivo pacientes.csv não encontrado ou corrompido. Um novo será criado.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("cpf,nome,idade");
            bw.newLine();
            for (Paciente paciente : this.pacientes) {
                String linha = paciente.getCpf() + "," + paciente.getNome() + "," + paciente.getIdade();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    public void adicionar(Paciente paciente) {
        this.pacientes.add(paciente);
        salvarNoCsv();
    }

    public List<Paciente> listarTodos() {
        return this.pacientes;
    }

    public Paciente buscarPorCpf(String cpf) {
        for (Paciente p : this.pacientes) {
            if (p.getCpf().equals(cpf)) {
                return p;
            }
        }
        return null;
    }
}