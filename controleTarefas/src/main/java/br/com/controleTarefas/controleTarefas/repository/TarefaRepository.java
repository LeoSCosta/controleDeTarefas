package br.com.controleTarefas.controleTarefas.repository;

import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

    List<Tarefa> findByProjeto(Projeto projeto);

    List<Tarefa> findByStatusOrderByPrioridade(Status status);

    List<Tarefa> findByStatusOrderByDataTarefa(Status pendente);
}
