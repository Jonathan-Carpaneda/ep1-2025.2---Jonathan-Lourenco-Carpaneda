package entidades;

public class Especialidade {
    private String tipoEspecialidade;

    public Especialidade(String tipo) {
        this.tipoEspecialidade = tipo;
    }

    public String getTipoEspecialidade() {
        return tipoEspecialidade;
    }

    public void setTipoEspecialidade(String tipoEspecialidade) {
        this.tipoEspecialidade = tipoEspecialidade;
    }

    @Override
    public String toString() {
        return this.tipoEspecialidade;
    }
    
}
