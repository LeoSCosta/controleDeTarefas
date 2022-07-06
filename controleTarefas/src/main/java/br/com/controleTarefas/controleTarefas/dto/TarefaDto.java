package br.com.controleTarefas.controleTarefas.dto;

import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.service.Frequencia;
import br.com.controleTarefas.controleTarefas.service.Prioridade;
import br.com.controleTarefas.controleTarefas.service.Status;

import javax.validation.constraints.NotBlank;


public class TarefaDto {

    @NotBlank
    private String tituloDaTarefa;
    @NotBlank
    private String projetoDaTarefa;
    @NotBlank
    private String duracaoDaTarefa;
    @NotBlank
    private String frequenciaDaTarefa;

    private String dataInicial;
    private String dataFinal;
    private String ordem;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getTituloDaTarefa() {
        return tituloDaTarefa;
    }

    public void setTituloDaTarefa(String tituloDaTarefa) {
        this.tituloDaTarefa = tituloDaTarefa;
    }

    public String getProjetoDaTarefa() {
        return projetoDaTarefa;
    }

    public void setProjetoDaTarefa(String projetoDaTarefa) {
        this.projetoDaTarefa = projetoDaTarefa;
    }

    public String getDuracaoDaTarefa() {
        return duracaoDaTarefa;
    }

    public void setDuracaoDaTarefa(String duracaoDaTarefa) {
        this.duracaoDaTarefa = duracaoDaTarefa;
    }

    public String getFrequenciaDaTarefa() {
        return frequenciaDaTarefa;
    }

    public void setFrequenciaDaTarefa(String frequenciaDaTarefa) {
        this.frequenciaDaTarefa = frequenciaDaTarefa;
    }

    public String getPrioridadeDaTarefa() {
        return prioridadeDaTarefa;
    }

    public void setPrioridadeDaTarefa(String prioridadeDaTarefa) {
        this.prioridadeDaTarefa = prioridadeDaTarefa;
    }

    private String prioridadeDaTarefa;

    public Tarefa toPedido() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(tituloDaTarefa);
        tarefa.setDuracao(Integer.parseInt(duracaoDaTarefa));
        tarefa.setFrequencia(Frequencia.valueOf(frequenciaDaTarefa.toUpperCase()));
        tarefa.setPrioridade(Prioridade.valueOf(prioridadeDaTarefa.toUpperCase()));
        tarefa.setStatus(Status.PENDENTE);
        return tarefa;
    }


}
