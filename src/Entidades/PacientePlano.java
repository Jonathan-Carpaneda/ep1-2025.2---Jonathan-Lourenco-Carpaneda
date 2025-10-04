package Entidades;

public class PacientePlano extends Paciente {
    private PlanoSaude plano;

    //construtor
    public PacientePlano(String nome, String cpf, int idade, PlanoSaude plano){
        super(nome, cpf, idade);
        this.plano = plano;
    }

    //getters e setters
    public void setNomePlano(PlanoSaude nomePlano) {
        this.plano = nomePlano;
    }

    public PlanoSaude getNomePlano() {
        return plano;
    }

}
