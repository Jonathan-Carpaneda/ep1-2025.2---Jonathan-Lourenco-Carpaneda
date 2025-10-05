package app;

import entidades.*;
import repositorios.*;
import servicos.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    private final RepoPaciente repoPaciente;
    private final RepoMedico repoMedico;
    private final RepoEspecialidade repoEspecialidade;
    private final RepoQuartos repoQuartos;
    private final RepoConsulta repoConsulta;
    private final RepoInternacao repoInternacao;
    private final RepoPlano repoPlano;
    private final ServicoDeAgendamento servicoDeAgendamento;
    private final ServicoDeInternacao servicoDeInternacao;
    private final ServicoDeFaturamento servicoDeFaturamento;
    private final ServicoDeRelatorio servicoDeRelatorio;

    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Main() {
        this.repoEspecialidade = new RepoEspecialidade();
        this.repoQuartos = new RepoQuartos();
        this.repoPaciente = new RepoPaciente();
        this.repoPlano = new RepoPlano();
        this.repoMedico = new RepoMedico(this.repoEspecialidade);
        this.repoConsulta = new RepoConsulta(this.repoPaciente, this.repoMedico);
        this.repoInternacao = new RepoInternacao(this.repoPaciente, this.repoMedico, this.repoQuartos);
        this.servicoDeAgendamento = new ServicoDeAgendamento(this.repoConsulta, this.repoPaciente, this.repoMedico);
        this.servicoDeInternacao = new ServicoDeInternacao(this.repoInternacao, this.repoPaciente, this.repoMedico, this.repoQuartos);
        this.servicoDeFaturamento = new ServicoDeFaturamento();
        this.servicoDeRelatorio = new ServicoDeRelatorio(this.repoPaciente, this.repoMedico, this.repoConsulta, this.repoInternacao);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Erro de codificação: " + e.getMessage());
        }
        Main app = new Main();
        app.exibirMenuPrincipal();
    }

    public void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n===== Menu Principal =====" + ANSI_RESET);
            System.out.println("1. Cadastros");
            System.out.println("2. Operacoes (Consulta/Internacao)");
            System.out.println("3. Relatorios");
            System.out.println(ANSI_YELLOW + "0. Sair" + ANSI_RESET);
            System.out.print("Escolha uma opcao: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1: exibirMenuCadastros(); break;
                case 2: exibirMenuOperacoes(); break;
                case 3: exibirMenuRelatorios(); break;
                case 0: System.out.println("Encerrando o sistema..."); break;
                default: System.out.println(ANSI_RED + "Opcao invalida. Tente novamente." + ANSI_RESET); break;
            }
        }
        scanner.close();
    }

    private void exibirMenuCadastros() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n--- Menu de Cadastros ---" + ANSI_RESET);
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Medico");
            System.out.println("3. Cadastrar Plano de Saude");
            System.out.println("4. Cadastrar Especialidade");
            System.out.println("5. Cadastrar Quarto");
            System.out.println(ANSI_YELLOW + "0. Voltar ao Menu Principal" + ANSI_RESET);
            System.out.print("Escolha uma opcao: ");
            
            opcao = lerOpcao();
            switch (opcao) {
                case 1: cadastrarPaciente(); break;
                case 2: cadastrarMedico(); break;
                case 3: cadastrarPlanoDeSaude(); break;
                case 4: cadastrarEspecialidade(); break;
                case 5: cadastrarQuarto(); break;
                case 0: break;
                default: System.out.println(ANSI_RED + "Opcao invalida." + ANSI_RESET); break;
            }
        }
    }

    private void exibirMenuOperacoes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n--- Menu de Operacoes ---" + ANSI_RESET);
            System.out.println("1. Agendar Consulta");
            System.out.println("2. Concluir Consulta (Registrar Diagnostico)");
            System.out.println("3. Iniciar Internacao");
            System.out.println("4. Finalizar Internacao (Dar Alta)");
            System.out.println(ANSI_YELLOW + "0. Voltar ao Menu Principal" + ANSI_RESET);
            System.out.print("Escolha uma opcao: ");
            
            opcao = lerOpcao();

            switch (opcao) {
                case 1: agendarConsulta(); break;
                case 2: concluirConsulta(); break;
                case 3: iniciarInternacao(); break;
                case 4: finalizarInternacao(); break;
                case 0: break;
                default: System.out.println(ANSI_RED + "Opcao invalida." + ANSI_RESET); break;
            }
        }
    }

    private void exibirMenuRelatorios() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n--- Menu de Relatorios ---" + ANSI_RESET);
            System.out.println("1. Listar todos os Pacientes");
            System.out.println("2. Listar todos os Medicos");
            System.out.println("3. Buscar consultas de um Paciente");
            System.out.println("4. Medico com mais atendimentos");
            System.out.println(ANSI_YELLOW + "0. Voltar ao Menu Principal" + ANSI_RESET);
            System.out.print("Escolha uma opcao: ");

            opcao = lerOpcao();
            
            switch(opcao) {
                case 1: servicoDeRelatorio.gerarRelatorioPacientes(); break;
                case 2: servicoDeRelatorio.gerarRelatorioMedicos(); break;
                case 3: relatorioConsultasPorPaciente(); break;
                case 4: servicoDeRelatorio.gerarEstatisticaMedicoMaisAtivo(); break;
                case 0: break;
                default: System.out.println(ANSI_RED + "Opcao invalida." + ANSI_RESET); break;
            }
             if (opcao != 0) {
                System.out.println("\nPressione Enter para voltar ao menu de relatorios...");
                scanner.nextLine();
            }
        }
    }

    private void cadastrarPaciente() {
        try {
            System.out.println("\n--- Cadastro de Paciente ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Paciente tem plano de saude? (s/n): ");
            String temPlano = scanner.nextLine();
            
            if (temPlano.equalsIgnoreCase("s")) {
                System.out.print("Nome do Plano de Saude: ");
                String nomePlano = scanner.nextLine();
                PlanoSaude plano = repoPlano.buscarPorNome(nomePlano);
                if (plano == null) {
                    System.out.println(ANSI_RED + "Erro: Plano de saude nao encontrado. Cadastre-o primeiro." + ANSI_RESET);
                    return;
                }
                PacientePlano novoPaciente = new PacientePlano(nome, cpf, idade, plano);
                repoPaciente.adicionar(novoPaciente);
            } else {
                Paciente novoPaciente = new Paciente(nome, cpf, idade);
                repoPaciente.adicionar(novoPaciente);
            }
            
            System.out.println(ANSI_GREEN + "Paciente cadastrado com sucesso!" + ANSI_RESET);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Erro: Idade deve ser um numero." + ANSI_RESET);
        }
    }

    private void cadastrarMedico() {
        try {
            System.out.println("\n--- Cadastro de Medico ---");
            List<Especialidade> especialidades = repoEspecialidade.listarTodos();
            if (especialidades.isEmpty()) {
                System.out.println(ANSI_RED + "Erro: Nenhuma especialidade cadastrada. Cadastre uma primeiro." + ANSI_RESET);
                return;
            }
            System.out.println("Especialidades disponiveis:");
            for (int i = 0; i < especialidades.size(); i++) {
                System.out.println((i + 1) + ". " + especialidades.get(i).getTipoEspecialidade());
            }
            System.out.print("Escolha o numero da especialidade: ");
            int opcaoEspecialidade = lerOpcao();
            if (opcaoEspecialidade <= 0 || opcaoEspecialidade > especialidades.size()) {
                System.out.println(ANSI_RED + "Erro: Opcao de especialidade invalida." + ANSI_RESET);
                return;
            }
            Especialidade especialidadeEscolhida = especialidades.get(opcaoEspecialidade - 1);
            
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("CRM: ");
            String crm = scanner.nextLine();
            System.out.print("Custo da Consulta: ");
            double custo = Double.parseDouble(scanner.nextLine());
            
            Medico novoMedico = new Medico(nome, cpf, crm, especialidadeEscolhida, custo);
            repoMedico.adicionar(novoMedico);
            
            System.out.println(ANSI_GREEN + "Medico cadastrado com sucesso!" + ANSI_RESET);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Erro: Custo da consulta ou opcao deve ser um numero." + ANSI_RESET);
        }
    }

    private void cadastrarPlanoDeSaude() {
        System.out.println("\n--- Cadastro de Plano de Saude ---");
        System.out.print("Nome do Plano: ");
        String nome = scanner.nextLine();
        PlanoSaude novoPlano = new PlanoSaude(nome);
        String adicionarMais = "s";
        while(adicionarMais.equalsIgnoreCase("s")) {
            System.out.print("Deseja adicionar um desconto por especialidade? (s/n): ");
            adicionarMais = scanner.nextLine();
            if (adicionarMais.equalsIgnoreCase("s")) {
                System.out.print("  Nome da Especialidade: ");
                String nomeEsp = scanner.nextLine();
                System.out.print("  Valor do Desconto (ex: 0.25 para 25%): ");
                double valorDesc = Double.parseDouble(scanner.nextLine());
                novoPlano.definirDesconto(nomeEsp, valorDesc);
            }
        }
        
        repoPlano.adicionar(novoPlano);
        System.out.println(ANSI_GREEN + "Plano de Saude cadastrado com sucesso!" + ANSI_RESET);
    }

    private void cadastrarEspecialidade() {
        System.out.println("\n--- Cadastro de Especialidade ---");
        System.out.print("Nome da nova especialidade: ");
        String nome = scanner.nextLine();
        repoEspecialidade.adicionar(new Especialidade(nome));
        System.out.println(ANSI_GREEN + "Especialidade cadastrada com sucesso!" + ANSI_RESET);
    }

    private void cadastrarQuarto() {
        try {
            System.out.println("\n--- Cadastro de Quarto ---");
            System.out.print("Digite o numero do novo quarto: ");
            int numero = lerOpcao();
            if (numero <= 0) {
                 System.out.println(ANSI_RED + "Erro: Numero do quarto deve ser um numero positivo." + ANSI_RESET);
                 return;
            }
            repoQuartos.adicionar(new Quarto(numero));
            System.out.println(ANSI_GREEN + "Quarto cadastrado com sucesso!" + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Erro inesperado ao cadastrar quarto." + ANSI_RESET);
        }
    }

    private void agendarConsulta() {
        try {
            System.out.println("\n--- Agendamento de Consulta ---");
            System.out.print("CPF do Paciente: ");
            String pacienteCpf = scanner.nextLine();
            System.out.print("CRM do Medico: ");
            String medicoCrm = scanner.nextLine();
            System.out.print("Data e Hora (dd/MM/yyyy HH:mm): ");
            LocalDateTime dataHora = LocalDateTime.parse(scanner.nextLine(), formatter);
            System.out.print("Local: ");
            String local = scanner.nextLine();
            String resultado = servicoDeAgendamento.agendarConsulta(pacienteCpf, medicoCrm, dataHora, local);
            if (resultado.startsWith("SUCESSO")) {
                System.out.println(ANSI_GREEN + resultado + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + resultado + ANSI_RESET);
            }
        } catch (DateTimeParseException e) {
            System.out.println(ANSI_RED + "Erro: Formato de data invalido! Use dd/MM/yyyy HH:mm." + ANSI_RESET);
        }
    }
    
    private void concluirConsulta() {
        try {
            System.out.println("\n--- Concluir Consulta e Registrar Diagnostico ---");
            System.out.print("CRM do Medico: ");
            String crm = scanner.nextLine();
            System.out.print("CPF do Paciente: ");
            String cpf = scanner.nextLine();
            Consulta consulta = servicoDeAgendamento.buscarConsultaAgendada(cpf, crm);

            if(consulta == null) {
                System.out.println(ANSI_RED + "Nenhuma consulta agendada encontrada para este medico e paciente." + ANSI_RESET);
                return;
            }
            
            System.out.println("Consulta encontrada: " + consulta.toString());
            System.out.print("Diagnostico: ");
            String diagnostico = scanner.nextLine();
            System.out.print("Prescricao: ");
            String prescricao = scanner.nextLine();

            servicoDeAgendamento.concluirConsulta(consulta, diagnostico, prescricao);
            System.out.println(ANSI_GREEN + "Consulta concluida e diagnostico registrado!" + ANSI_RESET);

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Ocorreu um erro: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void iniciarInternacao() {
        try {
            System.out.println("\n--- Iniciar Internacao ---");
            System.out.print("CPF do Paciente: ");
            String pacienteCpf = scanner.nextLine();
            System.out.print("CRM do Medico Responsavel: ");
            String medicoCrm = scanner.nextLine();
            System.out.print("Numero do Quarto: ");
            int numQuarto = Integer.parseInt(scanner.nextLine());
            String resultado = servicoDeInternacao.iniciarInternacao(pacienteCpf, medicoCrm, numQuarto, LocalDateTime.now());
            if (resultado.startsWith("SUCESSO")) {
                System.out.println(ANSI_GREEN + resultado + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + resultado + ANSI_RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Erro: Numero do quarto deve ser um numero." + ANSI_RESET);
        }
    }
    
    private void finalizarInternacao() {
        try {
            System.out.println("\n--- Finalizar Internacao (Dar Alta) ---");
            System.out.print("CPF do Paciente a receber alta: ");
            String pacienteCpf = scanner.nextLine();
            String resultado = servicoDeInternacao.finalizarInternacao(pacienteCpf);
            if (resultado.startsWith("SUCESSO")) {
                Internacao internacao = repoInternacao.buscarInternacaoAtivaPorCpf(pacienteCpf);
                double custoFinal = servicoDeFaturamento.calcularCustoInternacao(internacao);
                System.out.println(ANSI_GREEN + resultado + ANSI_RESET);
                System.out.printf("Custo final da internacao: R$ %.2f\n", custoFinal);
            } else {
                System.out.println(ANSI_RED + resultado + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Ocorreu um erro: " + e.getMessage() + ANSI_RESET);
        }
    }

    private void relatorioConsultasPorPaciente() {
        System.out.print("Digite o CPF do paciente para o relatorio: ");
        String cpf = scanner.nextLine();
        servicoDeRelatorio.gerarRelatorioConsultasPorPaciente(cpf);
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}