package br.com.controleTarefas.controleTarefas.repository;

import br.com.controleTarefas.controleTarefas.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto,Long> {
}
