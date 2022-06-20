package br.com.controleTarefas.controleTarefas.controller;

import br.com.controleTarefas.controleTarefas.dto.ProjetoDto;
import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import br.com.controleTarefas.controleTarefas.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/projetos")
public class ProjetoController {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private ProjetoRepository projetoRepository;


    @GetMapping("/home")
    public String indexProjeto(Model model){
        return projetoInicio(model);
    }

    @GetMapping("/novoProjeto")
    public String novoProjeto(ProjetoDto projetoDto){
        return "projeto/novoProjeto";
    }

    @PostMapping("novo")
    public String nova(Model model, @Valid ProjetoDto projetoDto, BindingResult result) {
        if (result.hasErrors()){
            return "projeto/novoProjeto";
        }
        Projeto projeto = projetoDto.toProjeto();
        projetoRepository.save(projeto);
        return projetoInicio(model);
    }

    @GetMapping(value = "concluir/{id}")
    public String finalizarProjeto (@PathVariable("id")Long id, RedirectAttributes redirAttrs){
        Optional<Projeto> byId = projetoRepository.findById(id);
        Projeto projeto = byId.get();
        List<Tarefa> tarefas = tarefaRepository.findByProjeto(projeto);
        tarefas.forEach(this::finalizarTarefa);
        projeto.setStatus(Status.FINALIZADA);
        projetoRepository.save(projeto);

        return "redirect:/api/projetos/home";
    }

    @GetMapping(value = "excluir/{id}")
    public String excluirProjeto (@PathVariable("id")Long id, RedirectAttributes redirAttrs){
        Optional<Projeto> byId = projetoRepository.findById(id);
        Projeto projeto = byId.get();
        List<Tarefa> tarefas = tarefaRepository.findByProjeto(projeto);
        tarefas.forEach(tarefa -> tarefaRepository.deleteById(tarefa.getId()));
        projetoRepository.deleteById(id);

        return "redirect:/api/projetos/home";
    }



    private void finalizarTarefa(Tarefa tarefa) {
        tarefa.setStatus(Status.FINALIZADA);
        tarefaRepository.save(tarefa);
    }


    public String projetoInicio(Model model){
        List<Projeto> projetos = projetoRepository.findAll();
        model.addAttribute("projetos", projetos);
        return "projeto/indexProjeto";

    }
}

