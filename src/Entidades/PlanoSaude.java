package Entidades;

import java.util.HashMap;
import java.util.Map;

public class PlanoSaude {
    private String nomeDoPlano;
    private Map<String, Double> descontos;

    public PlanoSaude(String nome){
        this.nomeDoPlano = nome;
        this.descontos = new HashMap<>();
    }

    public void setNomeDoPlano(String nomeDoPlano){
        this.nomeDoPlano = nomeDoPlano;
    }

    public String getNomeDoPlano(){
        return nomeDoPlano;
    }

    //métodos

    public void definirDesconto(String especialidade, double desconto){
        this.descontos.put(especialidade, desconto);
    }

    public double getDesconto(String especialidade){
        return this.descontos.getOrDefault(especialidade, 0.0);
    }

    @Override
    public String toString() {
        return this.nomeDoPlano;
    }
}
