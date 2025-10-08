package entidades;

public class PacientePlano extends Paciente {
    private PlanoSaude plano;

    //construtor
    public PacientePlano(String nome, String cpf, int idade, PlanoSaude plano){
        super(nome, cpf, idade);
        this.plano = plano;
    }

    //getters e setters
    public void setPlano(PlanoSaude plano) {
        this.plano = plano;
    }

    public PlanoSaude getPlano() {
        return plano;
    }

}
