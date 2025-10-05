package servicos;

import entidades.*;
import repositorios.*;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServicoDeRelatorio {
    
    private RepoPaciente pacienteRepo;
    private RepoMedico medicoRepo;
    private RepoConsulta consultaRepo;
    private RepoInternacao internacaoRepo;
    private RepoPlano repoPlano;
    private ServicoDeFaturamento servicoDeFaturamento;

    public ServicoDeRelatorio(RepoPaciente pRepo, RepoMedico mRepo, RepoConsulta cRepo, RepoInternacao iRepo, RepoPlano planoRepo, ServicoDeFaturamento servicoDeFaturamento) {
        this.pacienteRepo = pRepo;
        this.medicoRepo = mRepo;
        this.consultaRepo = cRepo;
        this.internacaoRepo = iRepo;
        this.repoPlano = planoRepo;
        this.servicoDeFaturamento = servicoDeFaturamento;
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

    public void gerarRelatorioAgendaPorMedico(Medico medico) {
    System.out.println("\n--- Agenda do(a) Dr(a). " + medico.getNome() + " ---");
    List<Consulta> agenda = medico.getAgenda();
    if (agenda.isEmpty()) {
        System.out.println("Nenhuma consulta agendada.");
        return;
    }
    agenda.sort(Comparator.comparing(Consulta::getDataHora));
    for (Consulta c : agenda) {
        System.out.printf("  - Data: %s | Paciente: %s | Status: %s\n", c.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), 
        c.getPaciente().getNome(), c.getStatus());
    }
}

public void gerarRelatorioPacientesPorMedico(Medico medico) {
    System.out.println("\n--- Pacientes do(a) Dr(a). " + medico.getNome() + " ---");
    Set<Paciente> pacientesUnicos = new HashSet<>();
    for (Consulta c : medico.getAgenda()) {
        pacientesUnicos.add(c.getPaciente());
    }
    if (pacientesUnicos.isEmpty()) {
        System.out.println("Este medico ainda nao atendeu nenhum paciente.");
        return;
    }
    for (Paciente p : pacientesUnicos) {
        System.out.println("  - " + p.toString());
    }
}

 public String gerarConteudoRelatorioPacientes() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("CPF,Nome,Idade,Qtd. Consultas,Qtd. Internacoes\n");

        List<Paciente> pacientes = pacienteRepo.listarTodos();
        if (pacientes.isEmpty()) {
            return "Nenhum paciente cadastrado.";
        }

        for (Paciente p : pacientes) {
            relatorio.append(String.format("%s,%s,%d,%d,%d\n", p.getCpf(), p.getNome(), p.getIdade(), p.gethConsultas().size(), p.gethInternacao().size()));
        }
        return relatorio.toString();
    }

    public String gerarConteudoRelatorioMedicos() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("CRM,Nome,Especialidade,Custo Consulta,Consultas Agendadas\n");
        List<Medico> medicos = medicoRepo.listarTodos();
        if (medicos.isEmpty()) {
            return "Nenhum medico cadastrado.";
        }
        for (Medico m : medicos) {
            relatorio.append(String.format("%s,%s,%s,%.2f,%d\n", m.getCrm(), m.getNome(), m.getEspecialidade().getTipoEspecialidade(), 
            m.getCustoConsulta(), m.getAgenda().size()));
        }
        return relatorio.toString();
    }

    public String gerarConteudoRelatorioConsultas(String filtroCpf, String filtroCrm) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Data/Hora,Paciente,Medico,Especialidade,Status\n");
        List<Consulta> consultas = consultaRepo.listarTodos();
        boolean encontrou = false;
        for(Consulta c : consultas) {
            boolean match = true;
            if (filtroCpf != null && !filtroCpf.isEmpty() && !c.getPaciente().getCpf().equals(filtroCpf)) {
                match = false;
            }
            if (filtroCrm != null && !filtroCrm.isEmpty() && !c.getMedico().getCrm().equals(filtroCrm)) {
                match = false;
            }

            if(match) {
                relatorio.append(String.format("%s,%s,%s,%s,%s\n", c.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), 
                c.getPaciente().getNome(), c.getMedico().getNome(), c.getMedico().getEspecialidade().getTipoEspecialidade(), c.getStatus()));
                encontrou = true;
            }
        }
        if (!encontrou) return "Nenhuma consulta encontrada para os filtros fornecidos.";
        return relatorio.toString();
    }
    
    public String gerarConteudoRelatorioPacientesInternados() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Paciente,Quarto,Medico Responsavel,Data de Entrada\n");
        List<Internacao> todasAsInternacoes = internacaoRepo.listarTodos();
        boolean encontrouAlgum = false;
        for (Internacao internacao : todasAsInternacoes) {
            if (internacao.getDataSaida() == null) {
                relatorio.append(String.format("%s,%d,%s,%s\n", internacao.getPaciente().getNome(), internacao.getQuarto().getNumero(), 
                internacao.getMedicoResponsavel().getNome(), internacao.getDataEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
                encontrouAlgum = true;
            }
        }
        if (!encontrouAlgum) return "Nenhum paciente internado no momento.";
        return relatorio.toString();
    }

    public void imprimirEstatisticasGerais() {
        System.out.println("\n--- Estatisticas Gerais ---");
        gerarEstatisticaMedicoMaisAtivo();
        gerarEstatisticaEspecialidadeMaisProcurada();
    }
    
    public void gerarEstatisticaEspecialidadeMaisProcurada() {
        List<Consulta> consultas = consultaRepo.listarTodos();
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta realizada para calcular especialidade mais procurada.");
            return;
        }
        Map<String, Integer> contagem = new HashMap<>();
        for (Consulta c : consultas) {
            String especialidade = c.getMedico().getEspecialidade().getTipoEspecialidade();
            contagem.put(especialidade, contagem.getOrDefault(especialidade, 0) + 1);
        }
        String maisProcurada = "";
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maisProcurada = entry.getKey();
            }
        }
        System.out.println("Especialidade mais procurada: " + maisProcurada + " (" + maxCount + " consultas).");
    }

    public void imprimirEstatisticaPlanoSaude() {
        System.out.println("\n--- Estatisticas de Planos de Saude ---");
        List<PlanoSaude> planos = repoPlano.listarTodos();
        List<Paciente> pacientes = pacienteRepo.listarTodos();
        List<Consulta> consultas = consultaRepo.listarTodos();
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano de saude cadastrado.");
            return;
        }
        for (PlanoSaude plano : planos) {
            int contadorPacientes = 0;
            double economiaTotal = 0.0;
            for (Paciente p : pacientes) {
                if (p instanceof PacientePlano) {
                    PacientePlano pPlano = (PacientePlano) p;
                    if (pPlano.getPlano().getNomeDoPlano().equals(plano.getNomeDoPlano())) {
                        contadorPacientes++;
                    }
                }
            }
            for (Consulta c : consultas) {
                if (c.getPaciente() instanceof PacientePlano) {
                    PacientePlano pPlano = (PacientePlano) c.getPaciente();
                    if (pPlano.getPlano().getNomeDoPlano().equals(plano.getNomeDoPlano())) {
                        double custoBase = c.getMedico().getCustoConsulta();
                        double custoFinal = servicoDeFaturamento.calcularCustoConsulta(c);
                        economiaTotal += (custoBase - custoFinal);
                    }
                }
            }
            System.out.printf("Plano '%s': %d pacientes | Economia total gerada: R$ %.2f\n",
                              plano.getNomeDoPlano(), contadorPacientes, economiaTotal);
        }
    }
}