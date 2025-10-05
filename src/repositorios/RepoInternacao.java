package repositorios;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entidades.*;

public class RepoInternacao {
    private final String CAMINHO_ARQUIVO = "internacoes.csv";
    private List<Internacao> internacoes;
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;
    private RepoQuartos quartoRepo;

    public RepoInternacao(RepoPaciente pRepo, RepoMedico mRepo, RepoQuartos qRepo) {
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.quartoRepo = qRepo;
        this.internacoes = new ArrayList<>();
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
                int quartoNumero = Integer.parseInt(valores[2]);
                LocalDateTime dataEntrada = LocalDateTime.parse(valores[3]);
                String dataSaidaStr = valores[4];
                double custoTotal = Double.parseDouble(valores[5]);
                Paciente paciente = pacienteRepo.buscarPorCpf(pacienteCpf);
                Medico medico = medicoRepo.buscarPorCrm(medicoCrm);
                Quarto quarto = quartoRepo.buscarPorNumero(quartoNumero);

                if (paciente != null && medico != null && quarto != null) {
                    Internacao internacao = new Internacao(paciente, medico, quarto, dataEntrada);
                    if (dataSaidaStr != null && !dataSaidaStr.isEmpty()) {
                        internacao.setDataSaida(LocalDateTime.parse(dataSaidaStr));
                    }
                    internacao.setCustoTotal(custoTotal);
                    this.internacoes.add(internacao);
                    paciente.addInternacao(internacao);
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo internacoes.csv nao encontrado. Sera criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("paciente_cpf,medico_crm,quarto_numero,data_entrada,data_saida,custo_total");
            bw.newLine();
            for (Internacao internacao : this.internacoes) {
                String dataSaidaStr = (internacao.getDataSaida() == null) ? "" : internacao.getDataSaida().toString();
                String linha = internacao.getPaciente().getCpf() + "," +
                               internacao.getMedicoResponsavel().getCrm() + "," +
                               internacao.getQuarto().getNumero() + "," +
                               internacao.getDataEntrada().toString() + "," +
                               dataSaidaStr + "," +
                               internacao.getCustoTotal();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar internacoes: " + e.getMessage());
        }
    }

    public void adicionar(Internacao internacao) {
        this.internacoes.add(internacao);
        internacao.getPaciente().addInternacao(internacao);
        salvarNoCsv();
    }

    public List<Internacao> listarTodos() {
        return this.internacoes;
    }
}