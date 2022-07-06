package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FrequenciaFiltrar implements Filtrar {

    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas, TarefaRepository tarefaRepository, ProjetoRepository projetoRepository) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("DIARIAMENTE")|
                tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("SEMANALMENTE")|
                tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("MENSALMENTE")){
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getFrequencia().name().equalsIgnoreCase(tarefaDto.getFrequenciaDaTarefa())) {
                    tarefasAux.add(tarefa);
                }
            }
            tarefas = tarefasAux;
        }

        return tarefas;
    }
}
