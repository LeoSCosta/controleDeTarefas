package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;

import java.util.List;

public interface Filtrar {
     List<Tarefa> filtrar(TarefaDto tarefaDto,
                                List<Tarefa> tarefas,
                                TarefaRepository tarefaRepository,
                                ProjetoRepository projetoRepository);
}
