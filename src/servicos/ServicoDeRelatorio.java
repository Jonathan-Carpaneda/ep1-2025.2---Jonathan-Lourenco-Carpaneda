package servicos;

import entidades.*;
import repositorios.RepoConsulta;
import repositorios.RepoMedico;
import repositorios.RepoPaciente;

import java.util.List;

public class ServicoDeRelatorio {
    
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;
    private RepoConsulta consultaRepo;

    public ServicoDeRelatorio(RepoPaciente pRepo, RepoMedico mRepo, RepoConsulta cRepo) {
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.consultaRepo = cRepo;
    }

    public void gerarRelatorioPacientes() {
        System.out.println("\n--- Relatório de Pacientes ---");
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente p : pacientes) {
            System.out.println(p.toString());
            System.out.println("  Consultas: " + p.gethConsultas().size());
            System.out.println("  Internações: " + p.gethInternacao().size());
        }
    }

    public void gerarEstatisticaMedicoMaisAtivo() {
        System.out.println("\n--- Estatísticas ---");
        List<Medico> medicos = medicoRepo.listarTodos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado para gerar estatísticas.");
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
            System.out.println("Médico com mais atendimentos: Dr(a). " + medicoMaisAtivo.getNome() + " (" + maxConsultas + " consultas agendadas).");
        }
    }
}