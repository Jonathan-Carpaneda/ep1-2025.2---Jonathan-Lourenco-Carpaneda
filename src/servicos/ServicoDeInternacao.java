package servicos;

import repositorios.RepoInternacao;
import repositorios.RepoMedico;
import repositorios.RepoPaciente;
import repositorios.RepoQuartos;
import entidades.Internacao;
import entidades.Medico;
import entidades.Paciente;
import entidades.Quarto;

import java.time.LocalDateTime;

public class ServicoDeInternacao {
    private RepoInternacao internacaoRepo;
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;
    private RepoQuartos quartoRepo;

    public ServicoDeInternacao(RepoInternacao iRepo, RepoPaciente pRepo, RepoMedico mRepo, RepoQuartos qRepo) {
        this.internacaoRepo = iRepo;
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.quartoRepo = qRepo;
    }

    public String iniciarInternacao(String pacienteCpf, String medicoCrm, int numeroQuarto, LocalDateTime dataEntrada) {
        Paciente paciente = pacienteRepo.buscarPorCpf(pacienteCpf);
        if (paciente == null) return "ERRO: Paciente não encontrado.";
        Medico medico = medicoRepo.buscarPorCrm(medicoCrm);
        if (medico == null) return "ERRO: Médico não encontrado."; 
        Quarto quarto = quartoRepo.buscarPorNumero(numeroQuarto);
        if (quarto == null) return "ERRO: Quarto não encontrado.";
        if (quarto.isEstaOcupado()) {
            return "ERRO: O Quarto " + numeroQuarto + " já está ocupado.";
        }
        quarto.setEstaOcupado(true);
        Internacao novaInternacao = new Internacao(paciente, medico, quarto, dataEntrada);
        internacaoRepo.adicionar(novaInternacao);
        return "SUCESSO: Internação iniciada para " + paciente.getNome() + " no quarto " + numeroQuarto + ".";
    }

    public String finalizarInternacao(String pacienteCpf) {
        Internacao internacaoAtiva = internacaoRepo.buscarInternacaoAtivaPorCpf(pacienteCpf);
        if (internacaoAtiva == null) {
            return "ERRO: Paciente com CPF " + pacienteCpf + " não possui uma internação ativa.";
        }

        internacaoAtiva.setDataSaida(LocalDateTime.now());
        internacaoAtiva.getQuarto().setEstaOcupado(false);
        internacaoRepo.salvar();

        return "SUCESSO: Alta registrada para o paciente " + internacaoAtiva.getPaciente().getNome() + ".";
    }

    public String cancelarInternacao(String pacienteCpf) {
        Internacao internacaoAtiva = internacaoRepo.buscarInternacaoAtivaPorCpf(pacienteCpf);
        if (internacaoAtiva == null) {
            return "ERRO: Paciente com CPF " + pacienteCpf + " não possui uma internação ativa para cancelar.";
        }
        // Libera o quarto
        internacaoAtiva.getQuarto().setEstaOcupado(false);
        // Remove o registro da internação
        internacaoRepo.remover(internacaoAtiva);

        return "SUCESSO: Internação do paciente " + internacaoAtiva.getPaciente().getNome() + " foi cancelada.";
    }
}

