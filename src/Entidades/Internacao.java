package entidades;

import java.time.LocalDateTime;

public class Internacao {
    private Paciente paciente;
    private Medico medicoResponsavel;
    private Quarto quarto;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private double custoTotal;


    public Internacao(Paciente paciente, Medico medico, Quarto quarto, LocalDateTime dataEntrada) {
        this.paciente = paciente;
        this.medicoResponsavel = medico;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = null;
        this.custoTotal = 0.0;
    }


    public Paciente getPaciente(){ 
        return paciente; 
    }
    
    public Medico getMedicoResponsavel(){ 
        return medicoResponsavel; 
    }
    
    public Quarto getQuarto(){ 
        return quarto; 
    }
    
    public LocalDateTime getDataEntrada(){ 
        return dataEntrada; 
    }
    
    public LocalDateTime getDataSaida(){ 
        return dataSaida; 
    }
    
    public double getCustoTotal(){ 
        return custoTotal; 
    }
    
    public void setDataSaida(LocalDateTime dataSaida){ 
        this.dataSaida = dataSaida;
    }
    
    public void setCustoTotal(double custoTotal){ 
        this.custoTotal = custoTotal; 
    }
    

    @Override
    public String toString() {
        String saidaStr = (dataSaida == null) ? "INTERNADO" : dataSaida.toString();
        return String.format("Internacao[Paciente: %s, Quarto: %d, Entrada: %s, Saida: %s]", this.paciente.getNome(), this.quarto.getNumero(), this.dataEntrada.toString(), saidaStr);
    }
}