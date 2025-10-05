package app;

import entidades.*;
import repositorios.RepoConsulta;
import repositorios.RepoEspecialidade;
import repositorios.RepoInternacao;
import repositorios.RepoMedico;
import repositorios.RepoPaciente;
import repositorios.RepoPlano;
import repositorios.RepoQuartos;
import servicos.ServicoDeAgendamento;
import servicos.ServicoDeFaturamento;
import servicos.ServicoDeInternacao;
import servicos.ServicoDeRelatorio;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    //cores para o ANSI
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

    //interface
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
        this.servicoDeRelatorio = new ServicoDeRelatorio(this.repoPaciente, this.repoMedico, this.repoConsulta);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.exibirMenuPrincipal();
    }

    public void exibirMenuPrincipal() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n===== Menu Principal =====" + ANSI_RESET);
            System.out.println("1. Cadastros");
            System.out.println("2. Operações (Consulta/Internação)");
            System.out.println("3. Relatórios");
            System.out.println(ANSI_YELLOW + "0. Sair" + ANSI_RESET);
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    exibirMenuCadastros();
                    break;
                case 2:
                    System.out.println(ANSI_RED + "Menu de Operações ainda não implementado." + ANSI_RESET);
                    break;
                case 3:
                    servicoDeRelatorio.gerarRelatorioPacientes();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println(ANSI_RED + "Opção inválida. Tente novamente." + ANSI_RESET);
                    break;
            }
        }
        scanner.close();
    }

    private void exibirMenuCadastros() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println(ANSI_CYAN + "\n--- Menu de Cadastros ---" + ANSI_RESET);
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Médico");
            System.out.println("3. Cadastrar Plano de Saúde");
            System.out.println(ANSI_YELLOW + "0. Voltar ao Menu Principal" + ANSI_RESET);
            System.out.print("Escolha uma opção: ");
            
            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    cadastrarPaciente();
                    break;
                case 2:
                    System.out.println(ANSI_RED + "Funcionalidade ainda não implementada." + ANSI_RESET);
                    break;
                case 0:
                    break;
                default:
                    System.out.println(ANSI_RED + "Opção inválida." + ANSI_RESET);
                    break;
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
            Paciente novoPaciente = new Paciente(nome, cpf, idade);
            repoPaciente.adicionar(novoPaciente);
            System.out.println(ANSI_GREEN + "Paciente cadastrado com sucesso!" + ANSI_RESET);
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "Erro: Idade deve ser um número." + ANSI_RESET);
        }
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}