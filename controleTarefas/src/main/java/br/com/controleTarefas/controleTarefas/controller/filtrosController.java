package br.com.controleTarefas.controleTarefas.controller;


import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import br.com.controleTarefas.controleTarefas.service.Status;
import br.com.controleTarefas.controleTarefas.service.filtrosService.FiltroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/filtros")
public class  filtrosController {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    ProjetoRepository projetoRepository;

    @Autowired
    FiltroService filtroService;

    @GetMapping("/home")
    public String indexProjeto(Model model){
        List<Tarefa> tarefas = tarefaRepository.findAll();
        tarefas.sort(Comparator.comparing(Tarefa::getDataTarefa));
        List<Projeto> projetos = projetoRepository.findAll();
        model.addAttribute("tarefas",tarefas);
        model.addAttribute("projetos",projetos);

        return "filtros/indexFiltros";
    }


    @PostMapping("/filtrar")
    public String filtrar(Model model, TarefaDto tarefaDto){

        List<Tarefa> tarefas = tarefaRepository.findAll();
        tarefas = filtroService.filtrar( tarefaDto, tarefas);

        List<Projeto> projetos = projetoRepository.findAll();
        model.addAttribute("projetos",projetos);
        model.addAttribute("tarefas",tarefas);
        return "filtros/indexFiltros";
    }


    @GetMapping("/especiais")
    public String especiais(Model model){
        return "filtros/especiaisFiltros";
    }

    @PostMapping("/limiteDiario")
    public String limiteDiario(Model model, TarefaDto tarefaDto){
        List<Tarefa> tarefas = tarefaRepository.findByStatusOrderByPrioridade(Status.PENDENTE);
        List<Tarefa> tarefaAux = new ArrayList<>();

        int limite  = Integer.parseInt(tarefaDto.getDuracaoDaTarefa());
        int duracao =0;

        tarefas.sort(Comparator.comparing(Tarefa::getDataTarefa));
        for(Tarefa tarefa : tarefas){
            if (duracao + tarefa.getDuracao() >= limite){
                break;
            }
            else{
                tarefaAux.add(tarefa);
                duracao += tarefa.getDuracao();
            }
        }
        model.addAttribute("tarefas", tarefaAux);

        return "filtros/especiaisFiltros";
    }

}
