package br.com.controleTarefas.controleTarefas.dto;

import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.service.Status;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ProjetoDto {

    @NotBlank
    private String tituloDoProjeto;
    @NotBlank
    private String dataConclusao;

    public String getTituloDoProjeto() {
        return tituloDoProjeto;
    }

    public void setTituloDoProjeto(String tituloDoProjeto) {
        this.tituloDoProjeto = tituloDoProjeto;
    }

    public String getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(String dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Projeto toProjeto(){
        Projeto projeto = new Projeto();
        projeto.setNome(tituloDoProjeto);
        projeto.setDataConclusao(LocalDate.parse(dataConclusao));
        projeto.setStatus(Status.PENDENTE);
        return projeto;
    }
}
