package br.com.controleTarefas.controleTarefas.controller;

import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import br.com.controleTarefas.controleTarefas.util.Frequencia;
import br.com.controleTarefas.controleTarefas.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @GetMapping("/home")
    public String indexTarefa(Model model){
        return tarefaInicio(model);
    }

    @GetMapping("/novaTarefa")
    public String novaTarefa(TarefaDto tarefaDto, Model model){
        List<Projeto> projetos = projetoRepository.findAll();
        model.addAttribute("projetos",projetos);
        return "tarefa/novaTarefa";
    }

    @PostMapping("nova")
    public String nova(Model model,@Valid TarefaDto tarefaDto,  BindingResult result) {
        if (result.hasErrors()){
            return "tarefa/novaTarefa";
        }
        Optional<Projeto> projetoOptional = projetoRepository.findById(Long.valueOf(tarefaDto.getProjetoDaTarefa()));
        Projeto projeto = projetoOptional.get();

        Tarefa tarefa = tarefaDto.toPedido();
        tarefa.setProjeto(projeto);
        projeto.getTarefa().add(tarefa);
        dataTarefa(tarefa,projeto.getDataConclusao(),projeto);
        return tarefaInicio(model);
    }

    @GetMapping(value = "editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model){
        System.out.println("Atualizar Tarefa ||||||||||||||||||||||||||||||||||||||||||||||||||||");
        Optional<Tarefa> optionalTarefa = tarefaRepository.findById(id);
        Tarefa tarefa = optionalTarefa.get();
        model.addAttribute("tarefaid", tarefa);
        return "tarefa/editarTarefa";
    }


    @GetMapping(value = "concluir/{id}")
    public String concluir(Model model, @PathVariable("id") Long id){
        Optional<Tarefa> byId = tarefaRepository.findById(id);
        Tarefa tarefa = byId.get();
        tarefa.setStatus(Status.FINALIZADA);
        tarefaRepository.save(tarefa);
        return "redirect:/api/tarefas/home";
    }

    @GetMapping(value = "excluir/{id}")
    public String excluir(@PathVariable("id") Long id){
        tarefaRepository.deleteById(id);
        return "redirect:/api/tarefas/home";
    }



    public String tarefaInicio(Model model){
        List<Tarefa> tarefas = tarefaRepository.findAll();
        model.addAttribute("tarefas", tarefas);
        return "tarefa/indexTarefa";
    }


    public void dataTarefa(Tarefa tarefaControle, LocalDate dataConclusao, Projeto projeto){
        List<Tarefa> tarefas = new ArrayList<>();
        LocalDate dataInicio = LocalDate.now();
        while (dataConclusao.isAfter(dataInicio)){
            Tarefa novaTarefa = new Tarefa();
            novaTarefa.setTitulo(tarefaControle.getTitulo());
            novaTarefa.setPrioridade(tarefaControle.getPrioridade());
            novaTarefa.setFrequencia(tarefaControle.getFrequencia());
            novaTarefa.setDuracao(tarefaControle.getDuracao());
            novaTarefa.setProjeto(tarefaControle.getProjeto());
            novaTarefa.setStatus(Status.PENDENTE);
            if(novaTarefa.getFrequencia() == Frequencia.DIARIAMENTE) dataInicio = dataInicio.plusDays(1);
            if(novaTarefa.getFrequencia() == Frequencia.SEMANALMENTE) dataInicio = dataInicio.plusWeeks(1);
            if(novaTarefa.getFrequencia() == Frequencia.MENSALMENTE) dataInicio = dataInicio.plusMonths(1);
            projeto.getTarefa().add(novaTarefa);
            novaTarefa.setDataTarefa(dataInicio);
            tarefaRepository.save(novaTarefa);

        }
    }


}
