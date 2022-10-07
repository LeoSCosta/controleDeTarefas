package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class Ordernar implements Filtrar{
    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        switch (tarefaDto.getOrdem()){
            case "AlfabeticaCrescente":
                tarefas.sort(Comparator.comparing(Tarefa::getTitulo));
                break;

            case "AlfabeticaDecrescente":
                tarefas.sort((o1, o2) -> o2.getTitulo().compareTo(o1.getTitulo()));
                break;

            case "dataDecrescente":
                tarefas.sort((o1, o2) -> o2.getDataTarefa().compareTo(o1.getDataTarefa()));
                break;

            case "duracaoMenor":
                tarefas.sort(Comparator.comparingLong(Tarefa::getDuracao));
                break;

            case "duracaoMaior":
                tarefas.sort((o1, o2) -> Long.compare(o2.getDuracao(), o1.getDuracao()));
                break;

            case "prioridadeAlta":
                tarefas.sort(Comparator.comparing(Tarefa::getPrioridade));
                break;

            case "prioridadeBaixa":
                tarefas.sort((o1, o2) -> o2.getPrioridade().compareTo(o1.getPrioridade()));
                break;
            case "pendente":
                tarefas.sort(Comparator.comparing(Tarefa::getStatus));
                break;

            case "finalizada":
                tarefas.sort((o1, o2) -> o2.getStatus().compareTo(o1.getStatus()));
                break;
            default:
                tarefas.sort(Comparator.comparing(Tarefa::getDataTarefa));
        }
        return tarefas;
    }
}
