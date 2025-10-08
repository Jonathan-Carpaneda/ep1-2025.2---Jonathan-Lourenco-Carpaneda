# 🏥 Trabalho Prático – Sistema de Gerenciamento Hospitalar  

### 🎯 Objetivo  
Implementar um *Sistema de Gerenciamento Hospitalar* em *Java, aplicando conceitos avançados de **Programação Orientada a Objetos (POO), com foco em **herança, polimorfismo, encapsulamento, persistência de dados* e *regras de negócio mais complexas*.  

---
## Descrição do Projeto

Desenvolvimento de um sistema de gerenciamento hospitalar utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

## Dados do Aluno

- **Nome completo:** Jonathan Lourenço Carpaneda
- **Matrícula:** 242005365
- **Curso:** Engenharia de Software
- **Turma:** 02

---

## Instruções para Compilação e Execução

1. **Compilação:**  
   Na pasta "src" abra o terminal e escreva o seguinte comando  `javac -d ../bin app/Main.java`.

2. **Execução:**  
   Após a execução na pasta raiz do projeto será criada uma pasta "bin", dentro dela abra o terminal e exevute o seguinte comando `java app.Main`.

3. **Estrutura de Pastas:**  
```
   /ep1-2025.2---Jonathan-Lourenco-Carpaneda
   ├── src/
   │   ├── app/
   │   │   ├── Main.java
   │   ├── entidades/
   │   │   ├── Consulta.java
   │   │   ├── Especialidade.java
   │   │   ├── Internacao.java
   │   │   ├── Medico.java
   │   │   ├── Paciente.java
   │   │   ├── PacientePlano.java
   │   │   ├── Pessoa.java
   │   │   ├── PlanoSaude.java
   │   │   ├── Quarto.java
   │   │   └── StatusConsulta.java
   │   ├── repositorios/
   │   │   ├── RepoConsulta.java
   │   │   ├── RepoEspecialidade.java
   │   │   ├── RepoInternacao.java
   │   │   ├── RepoMedico.java
   │   │   ├── RepoPaciente.java
   │   │   ├── RepoPlano.java
   │   │   └── RepoQuartos.java
   │   └── servicos/
   │       ├── ServicoDeAgendamento.java
   │       ├── ServicoDeFaturamento.java
   │       ├── ServicoDeInternacao.java
   │       └── ServicoDeRelatorio.java
   └── bin/                (Criada após a compilação)
       └── *.csv           (Arquivos de dados gerados aqui)
```

3. **Versão do JAVA utilizada:**  
   openjdk version "17.0.16" 2025-07-15

---

## Vídeo de Demonstração

- [Inserir o link para o vídeo no YouTube/Drive aqui]

---

## Prints da Execução

1. Menu Principal:  
   ![Menu Principal do Sistema](./prints/Menu_Principal.png)

2. Cadastro de Médico:  
   ![Cadastro de Médico](./prints/Cadastro_De_Medico.png)

3. Relatório de Pacientes:  
   ![Relatório de Pacientes](./prints/Relatorio_De_Pacientes.png)

4. Relatório de médicos:  
   ![Relatório de Médicos](./prints/Relatorio_De_Medicos.png)

5. Relatório dos Planos de Saúde:  
   ![Relatório dos Planos de Saúde](./prints/Relatorio_De_Planos.png)

5. Menu de Operações:  
   ![Menu de Operações](./prints/Menu_De_Operacoes.png)

5. Agendamento de Consulta:  
   ![Agendamento de Consulta](./prints/Agendamento.png)

---

---

## Observações (Extras ou Dificuldades)

- Neste projeto tive dois problemas, o primeiro foi com a formatação e não consegui implementar o UTF-8, já tive esse mesmo problema quando fiz meu projeto em C na matéria de APC e o segundo problema foi que não consegui implementar a função para exportar os dados dos relatórios. Tirando esses problemas, no decorrer do projeto consegui entender muito mais como um software funciona, a POO é realmente facinante.

---

## Contato

- jonathancarpanedaj@gmail.com

---

### 🖥️ Descrição do Sistema  

O sistema deve simular o funcionamento de um hospital com cadastro de *pacientes, médicos, especialidades, consultas e internações*.  

1. *Cadastro de Pacientes*  
   - Pacientes comuns e pacientes especiais (ex: com plano de saúde).  
   - Cada paciente deve ter: nome, CPF, idade, histórico de consultas e internações.  

2. *Cadastro de Médicos*  
   - Médicos podem ter especialidades (ex: cardiologia, pediatria, ortopedia).  
   - Cada médico deve ter: nome, CRM, especialidade, custo da consulta e agenda de horários.  

3. *Agendamento de Consultas*  
   - Um paciente pode agendar uma consulta com um médico disponível.  
   - Consultas devem registrar: paciente, médico, data/hora, local, status (agendada, concluída, cancelada).  
   - Pacientes especiais (plano de saúde) podem ter *vantagens*, como desconto.  
   - Duas consultas não podem estar agendadas com o mesmo médico na mesma hora, ou no mesmo local e hora

4. *Consultas e Diagnósticos*  
   - Ao concluir uma consulta, o médico pode registrar *diagnóstico* e/ou *prescrição de medicamentos*.  
   - Cada consulta deve ser registrada no *histórico do paciente*.  

5. *Internações*  
   - Pacientes podem ser internados.  
   - Registrar: paciente, médico responsável, data de entrada, data de saída (se já liberado), quarto e custo da internação.  
   - Deve existir controle de *ocupação dos quartos* (não permitir duas internações no mesmo quarto simultaneamente).  
   - Internações devem poder ser canceladas, quando isso ocorrer, o sistema deve ser atualizado automaticamente.

6. *Planos de saúde*    
   -  Planos de saude podem ser cadastrados.
   -  Cada plano pode oferecer *descontos* para *especializações* diferentes, com possibilidade de descontos variados.
   -  Um paciente que tenha o plano de saúde deve ter o desconto aplicado.
   -  Deve existir a possibilidade de um plano *especial* que torna internação de menos de uma semana de duração gratuita.
   -  Pacientes com 60+ anos de idade devem ter descontos diferentes.

7. *Relatórios*  
   - Pacientes cadastrados (com histórico de consultas e internações).  
   - Médicos cadastrados (com agenda e número de consultas realizadas).  
   - Consultas futuras e passadas (com filtros por paciente, médico ou especialidade).  
   - Pacientes internados no momento (com tempo de internação).  
   - Estatísticas gerais (ex: médico que mais atendeu, especialidade mais procurada).  
   - Quantidade de pessoas em um determinado plano de saúde e quanto aquele plano *economizou* das pessoas que o usam.  


---

### ⚙️ Requisitos Técnicos  
- O sistema deve ser implementado em *Java*.  
- Interface via *terminal (linha de comando)*.  
- Os dados devem ser persistidos em *arquivos* (.txt ou .csv).  
- Deve existir *menu interativo*, permitindo navegar entre as opções principais.  

---

### 📊 Critérios de Avaliação  

1. *Modos da Aplicação (1,5)* → Cadastro de pacientes, médicos, planos de saúde, consultas e internações.  
2. *Armazenamento em arquivo (1,0)* → Dados persistidos corretamente, leitura e escrita funcional.  
3. *Herança (1,0)* → Ex.: Paciente e PacienteEspecial, Consulta e ConsultaEspecial, Médico e subclasses por especialidade.  
4. *Polimorfismo (1,0)* → Ex.: regras diferentes para agendamento, preços de consultas.
5. *Encapsulamento (1,0)* → Atributos privados, getters e setters adequados.  
6. *Modelagem (1,0)* → Estrutura de classes clara, bem planejada e com relacionamentos consistentes.  
7. *Execução (0,5)* → Sistema compila, roda sem erros e possui menus funcionais.  
8. *Qualidade do Código (1,0)* → Código limpo, organizado, nomes adequados e boas práticas.  
9. *Repositório (1,0)* → Uso adequado de versionamento, commits frequentes com mensagens claras.  
10. *README (1,0)* → Vídeo curto (máx. 5 min) demonstrando as funcionalidades + prints de execução + explicação da modelagem.  

🔹 *Total = 10 pontos*  
🔹 *Pontuação extra (até 1,5)* → Melhorias relevantes, como:  
- Sistema de triagem automática com fila de prioridade.  
- Estatísticas avançadas (tempo médio de internação, taxa de ocupação por especialidade).  
- Exportação de relatórios em formato .csv ou .pdf.  
- Implementação de testes unitários para classes principais.  
- Menu visual.
