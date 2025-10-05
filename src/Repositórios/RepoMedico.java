package Repositórios;

import Entidades.Especialidade;
import Entidades.Medico;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepoMedico {
    private final String CAMINHO_ARQUIVO = "medicos.csv";
    private List<Medico> medicos;
    private RepoEspecialidade especialidadeRepo;
    public RepoMedico(RepoEspecialidade espRepo) {
        this.especialidadeRepo = espRepo;
        this.medicos = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",");
                String cpf = valores[0];
                String nome = valores[1];
                String crm = valores[2];
                String nomeEspecialidade = valores[3];
                double custoConsulta = Double.parseDouble(valores[4]);
                Especialidade especialidade = especialidadeRepo.buscarPorNome(nomeEspecialidade);
                
                if (especialidade != null) {
                    this.medicos.add(new Medico(nome, cpf, crm, especialidade, custoConsulta));
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo medicos.csv não encontrado. Será criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("cpf,nome,crm,especialidade_nome,custo_consulta");
            bw.newLine();
            for (Medico medico : this.medicos) {
                String linha = medico.getCpf() + "," + medico.getNome() + "," + medico.getCrm() + "," + medico.getEspecialidade().getTipoEspecialidade() + "," + medico.getCustoConsulta();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    public void adicionar(Medico medico) {
        this.medicos.add(medico);
        salvarNoCsv();
    }

    public List<Medico> listarTodos() {
        return this.medicos;
    }
    
    public Medico buscarPorCrm(String crm) {
        for (Medico med : this.medicos) {
            if (med.getCrm().equalsIgnoreCase(crm)) {
                return med;
            }
        }
        return null;
    }
}