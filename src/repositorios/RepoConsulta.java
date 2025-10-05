package repositorios;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entidades.*;

public class RepoConsulta {
    private final String CAMINHO_ARQUIVO = "consultas.csv";
    private List<Consulta> consultas;
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;

    public RepoConsulta(RepoPaciente pRepo, RepoMedico mRepo) {
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.consultas = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",", -1);
                String pacienteCpf = valores[0];
                String medicoCrm = valores[1];
                LocalDateTime dataHora = LocalDateTime.parse(valores[2]);
                String local = valores[3];
                StatusConsulta status = StatusConsulta.valueOf(valores[4]);
                String diagnostico = valores[5];
                String prescricao = valores[6];
                Paciente paciente = pacienteRepo.buscarPorCpf(pacienteCpf);
                Medico medico = medicoRepo.buscarPorCrm(medicoCrm);

                if (paciente != null && medico != null) {
                    Consulta consulta = new Consulta(paciente, medico, dataHora, local);
                    consulta.setStatus(status);
                    consulta.setDiagnostico(diagnostico);
                    consulta.setPrescricao(prescricao);
                    this.consultas.add(consulta);
                    paciente.addConsulta(consulta);
                    medico.adicionarConsultaNaAgenda(consulta);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo consultas.csv nao encontrado. Sera criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("paciente_cpf,medico_crm,data_hora,local,status,diagnostico,prescricao");
            bw.newLine();
            for (Consulta consulta : this.consultas) {
                String linha = consulta.getPaciente().getCpf() + "," + consulta.getMedico().getCrm() + "," + consulta.getDataHora().toString() + "," + 
                consulta.getLocal() + "," + consulta.getStatus() + "," + consulta.getDiagnostico() + "," + consulta.getPrescricao(); bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public void adicionar(Consulta consulta) {
        this.consultas.add(consulta);
        consulta.getPaciente().addConsulta(consulta);
        consulta.getMedico().adicionarConsultaNaAgenda(consulta);
        salvarNoCsv();
    }

    public List<Consulta> listarTodos() {
        return this.consultas;
    }
}