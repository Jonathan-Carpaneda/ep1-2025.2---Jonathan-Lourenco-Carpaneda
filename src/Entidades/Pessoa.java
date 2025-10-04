package Entidades;

public class Pessoa {
    private String nome;
    private String cpf;

    // construtores
    public Pessoa(){

    }
    public Pessoa(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    //Getters e Setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }


    //Override método toString para formatação
    @Override
    public String toString(){
        return String.format("Pessoa-[Nome: %s, CPF: %s]", this.getNome(), this.getCpf());
    }
}

