package br.com.controleTarefas.controleTarefas.service.filtrosService;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class DataFiltrar implements Filtrar {
    @Override
    public List<Tarefa> filtrar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (!(tarefaDto.getDataInicial() == null) & !tarefaDto.getDataInicial().isEmpty()){
            if (!(tarefaDto.getDataFinal() == null) & !(tarefaDto.getDataFinal().isEmpty())){
                for(Tarefa tarefa: tarefas){
                    if (tarefa.getDataTarefa().isAfter(LocalDate.parse(tarefaDto.getDataInicial()).minusDays(1))){
                        if (tarefa.getDataTarefa().isBefore(LocalDate.parse(tarefaDto.getDataFinal()).plusDays(1))){
                            tarefasAux.add(tarefa);
                        }

                    }
                }
            }else{
                for(Tarefa tarefa: tarefas){
                    if(tarefa.getDataTarefa().isEqual(LocalDate.parse(tarefaDto.getDataInicial()))){
                        tarefasAux.add(tarefa);
                    }
                }
            }
            tarefasAux.forEach(System.out::println);
            tarefas = tarefasAux;
        }
        return tarefas;
    }
}
