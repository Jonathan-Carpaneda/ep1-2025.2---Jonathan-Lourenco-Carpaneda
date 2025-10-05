package repositorios;

import entidades.PlanoSaude;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepoPlano {
    private final String CAMINHO_ARQUIVO = "planos_saude.csv";
    private List<PlanoSaude> planosDeSaude;

    public RepoPlano() {
        this.planosDeSaude = new ArrayList<>();
        carregarDoCsv();
    }

    private void carregarDoCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            br.readLine();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] valores = linha.split(",", -1);
                String nome = valores[0];
                String descontosStr = valores.length > 1 ? valores[1] : "";
                PlanoSaude plano = new PlanoSaude(nome);
                if (!descontosStr.isEmpty()) {
                    String[] paresDeDesconto = descontosStr.split(";");
                    for (String par : paresDeDesconto) {
                        String[] chaveValor = par.split(":");
                        String especialidade = chaveValor[0];
                        double desconto = Double.parseDouble(chaveValor[1]);
                        plano.definirDesconto(especialidade, desconto);
                    }
                }
                this.planosDeSaude.add(plano);
            }
        } catch (IOException e) {
            System.out.println("Arquivo planos_saude.csv nao encontrado. Sera criado um novo.");
        }
    }

    private void salvarNoCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("nome_do_plano,descontos_formatados");
            bw.newLine();
            for (PlanoSaude plano : this.planosDeSaude) {
                StringBuilder descontosStr = new StringBuilder();
                for (Map.Entry<String, Double> entry : plano.getDescontos().entrySet()) {
                    descontosStr.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
                }
                String linha = plano.getNomeDoPlano() + "," + descontosStr.toString();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar planos de saude: " + e.getMessage());
        }
    }

    public void adicionar(PlanoSaude plano) {
        this.planosDeSaude.add(plano);
        salvarNoCsv();
    }

    public List<PlanoSaude> listarTodos() {
        return this.planosDeSaude;
    }

    public PlanoSaude buscarPorNome(String nome) {
        for (PlanoSaude p : this.planosDeSaude) {
            if (p.getNomeDoPlano().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }
    

}