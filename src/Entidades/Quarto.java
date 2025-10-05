package entidades;

public class Quarto {
    private int numero;
    private boolean estaOcupado;

    public Quarto(int numero){
        this.numero = numero;
        this.estaOcupado = false;
    }

    public int getNumero(){ 
        return numero; 
    }
    
    
    public boolean isEstaOcupado(){ 
        return estaOcupado; 
    }
    
    public void setEstaOcupado(boolean estaOcupado){ 
        this.estaOcupado = estaOcupado; 
    }

    @Override
    public String toString() {
        return "Quarto " + numero;
    }
}