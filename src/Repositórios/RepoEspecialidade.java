package Repositórios;

import Entidades.Especialidade;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepoEspecialidade {
    private final String CAMINHO_ARQUIVO = "especialidades.csv";
    private List<Especialidade> especialidades;

    public RepoEspecialidade() {
        this.especialidades = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                this.especialidades.add(new Especialidade(linha.trim()));
            }
        } catch (IOException e) {
            System.out.println("Arquivo especialidades.csv não encontrado. Será criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("nome");
            bw.newLine();
            for (Especialidade esp : this.especialidades) {
                bw.write(esp.getTipoEspecialidade());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar especialidades: " + e.getMessage());
        }
    }

    public void adicionar(Especialidade especialidade) {
        this.especialidades.add(especialidade);
        salvarNoCsv();
    }

    public List<Especialidade> listarTodos() {
        return this.especialidades;
    }
    
    public Especialidade buscarPorNome(String nome) {
        for (Especialidade esp : this.especialidades) {
            if (esp.getTipoEspecialidade().equalsIgnoreCase(nome)) {
                return esp;
            }
        }
        return null;
    }
}