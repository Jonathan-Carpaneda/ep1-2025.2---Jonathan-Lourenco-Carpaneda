package Entidades;

import java.time.LocalDateTime;

public class Consulta {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String local;
    private StatusConsulta status;
    private String diagnostico;
    private String prescricao;

    public Consulta(Paciente paciente, Medico medico, LocalDateTime dataHora, String local){
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.local = local;
        this.status = StatusConsulta.AGENDADA;
        this.diagnostico = ""; 
        this.prescricao = ""; 
    }
    
    
    public Paciente getPaciente(){ 
        return paciente; 
    }
    
    public Medico getMedico(){ 
        return medico; 
    }
    
    public LocalDateTime getDataHora(){ 
        return dataHora; 
    }
    
    public String getLocal(){ 
        return local; 
    }
    
    public StatusConsulta getStatus(){ 
        return status; 
    }
    
    public String getDiagnostico(){ 
        return diagnostico; 
    }
    
    public String getPrescricao(){ 
        return prescricao; 
    }

    public void setStatus(StatusConsulta status){ 
        this.status = status; 
    }
    
    public void setDiagnostico(String diagnostico){ 
        this.diagnostico = diagnostico; 
    }
    
    public void setPrescricao(String prescricao){ 
        this.prescricao = prescricao; 
    }

    @Override
    public String toString() {
        return String.format("Consulta[Data: %s, Paciente: %s, Médico: %s, Status: %s]", this.dataHora.toString(), this.paciente.getNome(), this.medico.getNome(), this.status);
    }
}
