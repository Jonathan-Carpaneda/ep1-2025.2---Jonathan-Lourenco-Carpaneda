package entidades;

import java.util.HashMap;
import java.util.Map;

public class PlanoSaude {
    private String nomeDoPlano;
    private Map<String, Double> descontos;
    private boolean especialInternacaoGratuita;

    public PlanoSaude(String nome){
        this.nomeDoPlano = nome;
        this.descontos = new HashMap<>();
        this.especialInternacaoGratuita = false; // Valor padrão
    }

    public void setNomeDoPlano(String nomeDoPlano){
        this.nomeDoPlano = nomeDoPlano;
    }

    public String getNomeDoPlano(){
        return nomeDoPlano;
    }

    public Map<String, Double> getDescontos() {
        return this.descontos;
    }

    //métodos

    public void definirDesconto(String especialidade, double desconto){
        this.descontos.put(especialidade.toLowerCase(), desconto); // Convertendo para minúsculo para consistência
    }

    public double getDesconto(String especialidade){
        return this.descontos.getOrDefault(especialidade.toLowerCase(), 0.0);
    }

    public boolean isEspecialInternacaoGratuita() {
        return especialInternacaoGratuita;
    }

    public void setEspecialInternacaoGratuita(boolean especialInternacaoGratuita) {
        this.especialInternacaoGratuita = especialInternacaoGratuita;
    }


    @Override
    public String toString() {
        return this.nomeDoPlano;
    }
}

