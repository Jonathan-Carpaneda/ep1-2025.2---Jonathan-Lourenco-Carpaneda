package servicos;

import entidades.*;

public class ServicoDeFaturamento {
    
    public ServicoDeFaturamento() {}

    public double calcularCustoConsulta(Consulta consulta) {
        double custoBase = consulta.getMedico().getCustoConsulta();
        double custoFinal = custoBase;
        Paciente paciente = consulta.getPaciente();

        if (paciente instanceof PacientePlano) {
            PacientePlano pacienteComPlano = (PacientePlano) paciente;
            PlanoSaude plano = pacienteComPlano.getPlano();
            String especialidade = consulta.getMedico().getEspecialidade().getTipoEspecialidade();            
            double descontoPlano = plano.getDesconto(especialidade);
            custoFinal -= custoBase * descontoPlano;
        }

        if (paciente.getIdade() >= 60) {
            custoFinal -= custoBase * 0.10;
        }

        return custoFinal < 0 ? 0 : custoFinal;
    }

    public double calcularCustoInternacao(Internacao internacao) {
        Paciente paciente = internacao.getPaciente();
        double custoCalculado = 1000.0; // Custo base simbólico
        if (paciente.getIdade() >= 60) {
             custoCalculado -= custoCalculado * 0.15;
        }
        return custoCalculado;
    }
}