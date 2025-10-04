package Entidades;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa{
    private int idade;
    private List<Consulta> hConsultas;
    private List<Internacao> hInternacao;

    //construtor

    public Paciente(String nome, String cpf, int idade){
        super(nome, cpf);
        this.idade = idade;
        this.hConsultas = new ArrayList<>();
        this.hInternacao = new ArrayList<>();
    }
    //get e setter idade
    public void setIdade(int idade) {
        if (idade > 0){
            this.idade = idade;
        }
    }

    public int getIdade() {
        return idade;
    }

    //métodos para manipulação das listas dos históricos
    public void addConsulta(Consulta novaConsulta){
        this.hConsultas.add(novaConsulta);
    }

    public void addInternacao(Internacao novaInternacao){
        this.hInternacao.add(novaInternacao);
    }

    public List<Consulta> gethConsultas() {
        return this.hConsultas;
    }

    public List<Internacao> gethInternacao() {
        return this.hInternacao;
    }

    @Override
    public String toString(){
        return String.format("Paciente-[Nome: %s, CPF: %s, Idade: %d, Qtd. Consultas: %d]", this.getNome(), this.getCpf(), this.idade, this.hConsultas.size());
    }
}
