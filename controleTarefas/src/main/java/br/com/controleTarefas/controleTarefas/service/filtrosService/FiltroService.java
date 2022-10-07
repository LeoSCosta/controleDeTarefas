package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiltroService {

    @Autowired
    private final List<Filtrar> filtros;

    public FiltroService(List<Filtrar> filtros) {
        this.filtros = filtros;
    }


    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas){

        for (Filtrar filtrar : filtros){
            tarefas = filtrar.filtrar(tarefaDto, tarefas);
        }

        return tarefas;
    }
}
