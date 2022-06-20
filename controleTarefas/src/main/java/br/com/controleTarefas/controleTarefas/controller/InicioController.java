package br.com.controleTarefas.controleTarefas.controller;

import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import br.com.controleTarefas.controleTarefas.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class InicioController {

    @Autowired
    private TarefaRepository repository;

    @GetMapping("/api/home")
    public String index(Model model) {
        List<Tarefa> tarefas = repository.findByStatusOrderByDataTarefa(Status.PENDENTE);
        model.addAttribute("tarefas", tarefas);

        return "index";

    }
}
