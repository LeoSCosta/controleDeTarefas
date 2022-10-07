package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrioridadeFiltrar implements Filtrar {
    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("ALTA")|
                tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("MEDIA")|
                tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("BAIXA")){
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getPrioridade().name().equalsIgnoreCase(tarefaDto.getPrioridadeDaTarefa())) {
                    tarefasAux.add(tarefa);
                }
            }
            tarefas = tarefasAux;
        }
        return tarefas;
    }
}
