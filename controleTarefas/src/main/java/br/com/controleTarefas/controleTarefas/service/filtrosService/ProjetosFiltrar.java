package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProjetosFiltrar implements Filtrar {

    private ProjetoRepository projetoRepository;

    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefaAux = new ArrayList<>();
        if (!tarefaDto.getProjetoDaTarefa().equalsIgnoreCase("Selecione um projeto")){
            Optional<Projeto> projetoOptional = projetoRepository.findById(Long.valueOf(tarefaDto.getProjetoDaTarefa()));
            if (projetoOptional.isPresent()){
                for (Tarefa tarefa : tarefas){
                    if (tarefa.getProjeto().equals(projetoOptional.get())){
                        tarefaAux.add(tarefa);
                    }
                }
                tarefas = tarefaAux;
            }
        }
        return tarefas;

    }
}
