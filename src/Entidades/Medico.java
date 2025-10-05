package entidades;

import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa {
    private String crm;
    private Especialidade especialidade;
    private double custoConsulta;
    private List<Consulta> agenda;

    public Medico(String nome, String cpf, String crm, Especialidade especialidade, double custoConsulta) {
        super(nome, cpf);
        this.crm = crm;
        this.especialidade = especialidade;
        this.custoConsulta = custoConsulta;
        this.agenda = new ArrayList<>();

    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public double getCustoConsulta() {
        return custoConsulta;
    }

    public void setCustoConsulta(double custoConsulta) {
        this.custoConsulta = custoConsulta;
    }

    public List<Consulta> getAgenda() {
        return agenda;
    }

    //métodos
    public void adicionarConsultaNaAgenda(Consulta consulta) {
        this.agenda.add(consulta);
    }

    public void removerConsultaDaAgenda(Consulta consulta) {
        this.agenda.remove(consulta);
    }

    @Override
    public String toString() {
        return String.format("Medico[Nome: %s, CRM: %s, Especialidade: %s, Custo da Consulta: R$%.2f]", this.getNome(), this.crm, this.especialidade.getTipoEspecialidade(), this.custoConsulta);
    }
}
