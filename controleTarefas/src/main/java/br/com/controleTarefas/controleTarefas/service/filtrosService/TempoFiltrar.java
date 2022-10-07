package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TempoFiltrar implements Filtrar {
    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if(!tarefaDto.getDuracaoDaTarefa().isEmpty()){
            int duracao = Integer.parseInt(tarefaDto.getDuracaoDaTarefa());
            for(Tarefa tarefa : tarefas){
                if (tarefa.getDuracao()<duracao) tarefasAux.add(tarefa);
            }
            tarefas = tarefasAux;
        }
        return tarefas;
    }
}
