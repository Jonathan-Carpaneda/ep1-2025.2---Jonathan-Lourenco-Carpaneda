package servicos;

import entidades.*;
import repositorios.RepoConsulta;
import repositorios.RepoMedico;
import repositorios.RepoPaciente;
import java.time.LocalDateTime;

public class ServicoDeAgendamento {
    private RepoConsulta consultaRepo;
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;

    public ServicoDeAgendamento(RepoConsulta cRepo, RepoPaciente pRepo, RepoMedico mRepo) {
        this.consultaRepo = cRepo;
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
    }

    public String agendarConsulta(String pacienteCpf, String medicoCrm, LocalDateTime dataHora, String local) {
        Paciente paciente = pacienteRepo.buscarPorCpf(pacienteCpf);
        if (paciente == null) {
            return "ERRO: Paciente com CPF " + pacienteCpf + " não encontrado.";
        }
        
        Medico medico = medicoRepo.buscarPorCrm(medicoCrm);
        if (medico == null) {
            return "ERRO: Médico com CRM " + medicoCrm + " não encontrado.";
        }

        for (Consulta consultaExistente : medico.getAgenda()) {
            if (consultaExistente.getDataHora().isEqual(dataHora) && consultaExistente.getStatus() != StatusConsulta.CANCELADA) {
                return "ERRO: O médico " + medico.getNome() + " já possui uma consulta agendada para este horário.";
            }
        }
        
        Consulta novaConsulta = new Consulta(paciente, medico, dataHora, local);
        consultaRepo.adicionar(novaConsulta);

        return "SUCESSO: Consulta agendada para " + paciente.getNome() + " com Dr(a). " + medico.getNome() + ".";
    }
    
}