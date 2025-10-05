package repositorios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entidades.Quarto;

public class RepoQuartos {
    private final String CAMINHO_ARQUIVO = "quartos.csv";
    private List<Quarto> quartos;

    public RepoQuartos() {
        this.quartos = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                int numero = Integer.parseInt(linha.trim());
                this.quartos.add(new Quarto(numero));
            }
        } catch (IOException e) {
            System.out.println("Arquivo quartos.csv nao encontrado. Sera criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("numero");
            bw.newLine();
            for (Quarto quarto : this.quartos) {
                bw.write(String.valueOf(quarto.getNumero()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar quartos: " + e.getMessage());
        }
    }

    public void adicionar(Quarto quarto) {
        this.quartos.add(quarto);
        salvarNoCsv();
    }

    public List<Quarto> listarTodos() {
        return this.quartos;
    }

    public Quarto buscarPorNumero(int numero) {
        for (Quarto q : this.quartos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }
}