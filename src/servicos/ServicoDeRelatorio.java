package servicos;

import entidades.*;
import repositorios.*;

import java.util.List;

public class ServicoDeRelatorio {
    
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;
    private RepoConsulta consultaRepo;
    private RepoInternacao internacaoRepo;

    public ServicoDeRelatorio(RepoPaciente pRepo, RepoMedico mRepo, RepoConsulta cRepo, RepoInternacao iRepo) {
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.consultaRepo = cRepo;
        this.internacaoRepo = iRepo;
    }

    public void gerarRelatorioPacientes() {
        System.out.println("\n--- Relatorio de Pacientes ---");
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : pacientes) {
            System.out.println(p.toString());
            System.out.println("  Consultas: " + p.gethConsultas().size());
            System.out.println("  Internacoes: " + p.gethInternacao().size());
        }
    }

    public void gerarEstatisticaMedicoMaisAtivo() {
        System.out.println("\n--- Estatisticas ---");
        List<Medico> medicos = medicoRepo.listarTodos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum medico cadastrado para gerar estatisticas.");
            return;
        }

        Medico medicoMaisAtivo = null;
        int maxConsultas = -1;

        for (Medico medico : medicos) {
            if (medico.getAgenda().size() > maxConsultas) {
                maxConsultas = medico.getAgenda().size();
                medicoMaisAtivo = medico;
            }
        }
        
        if (medicoMaisAtivo != null) {
            System.out.println("Medico com mais atendimentos: Dr(a). " + medicoMaisAtivo.getNome() + 
                               " (" + maxConsultas + " consultas agendadas).");
        }
    }

    public void gerarRelatorioMedicos() {
        System.out.println("\n--- Relatorio de Medicos ---");
        List<Medico> medicos = medicoRepo.listarTodos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum medico cadastrado.");
            return;
        }
        for (Medico m : medicos) {
            System.out.println(m.toString());
        }
    }

    public void gerarRelatorioConsultasPorPaciente(String pacienteCpf) {
        System.out.println("\n--- Relatorio de Consultas para o CPF: " + pacienteCpf + " ---");
        
        Paciente paciente = pacienteRepo.buscarPorCpf(pacienteCpf);
        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
            return;
        }

        System.out.println("Paciente: " + paciente.getNome());
        List<Consulta> historico = paciente.gethConsultas();

        if (historico.isEmpty()) {
            System.out.println("Este paciente nao possui consultas no historico.");
        } else {
            for (Consulta consulta : historico) {
                System.out.println("  - " + consulta.toString());
            }
        }
    }

    public void gerarRelatorioPacientesInternados() {
        System.out.println("\n--- Relatorio de Pacientes Internados Atualmente ---");
        List<Internacao> todasAsInternacoes = internacaoRepo.listarTodos();
        boolean encontrouAlgum = false;

        for (Internacao internacao : todasAsInternacoes) {
            if (internacao.getDataSaida() == null) {
                System.out.println(internacao.toString());
                encontrouAlgum = true;
            }
        }

        if (!encontrouAlgum) {
            System.out.println("Nenhum paciente internado no momento.");
        }
    }
}