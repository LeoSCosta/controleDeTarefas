package br.com.controleTarefas.controleTarefas.controller;


import br.com.controleTarefas.controleTarefas.dto.TarefaDto;
import br.com.controleTarefas.controleTarefas.model.Projeto;
import br.com.controleTarefas.controleTarefas.model.Tarefa;
import br.com.controleTarefas.controleTarefas.repository.ProjetoRepository;
import br.com.controleTarefas.controleTarefas.repository.TarefaRepository;
import br.com.controleTarefas.controleTarefas.util.Status;
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
public class filtrosController {

    @Autowired
    TarefaRepository tarefaRepository;

    @Autowired
    ProjetoRepository projetoRepository;

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

        tarefas = filtarPorProjeto(tarefaDto,tarefas);
        tarefas = filtrarPorData(tarefaDto, tarefas);
        tarefas = filtraPorFrequencia(tarefaDto, tarefas);
        tarefas = filtrarPorPrioridade(tarefaDto,tarefas);
        tarefas = filtarPorTempo(tarefaDto,tarefas);
        tarefas = filtarPorStatus(tarefaDto,tarefas);


        ordernar(tarefaDto, tarefas);
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




    private List<Tarefa> filtarPorStatus(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (tarefaDto.getStatus().equalsIgnoreCase("PENDENTE")|
                tarefaDto.getStatus().equalsIgnoreCase("FINALIZADA")){
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getStatus().name().equalsIgnoreCase(tarefaDto.getStatus())) {
                    tarefasAux.add(tarefa);
                }
            }
            tarefas = tarefasAux;
        }

        return tarefas;
    }

    private List<Tarefa> filtarPorTempo(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if(!tarefaDto.getDuracaoDaTarefa().isEmpty()){
            int duracao = Integer.parseInt(tarefaDto.getDuracaoDaTarefa());
            for(Tarefa tarefa : tarefas){
                if (tarefa.getDuracao()<duracao) tarefasAux.add(tarefa);
            }
            tarefas = tarefasAux;
        }
        return tarefas;
    }

    private List<Tarefa> filtrarPorPrioridade(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("ALTA")|
                tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("MEDIA")|
                tarefaDto.getPrioridadeDaTarefa().equalsIgnoreCase("BAIXA")){
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getPrioridade().name().equalsIgnoreCase(tarefaDto.getPrioridadeDaTarefa())) {
                    tarefasAux.add(tarefa);
                }
            }
            tarefas = tarefasAux;
        }

        return tarefas;
    }

    private void ordernar(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        switch (tarefaDto.getOrdem()){
            case "AlfabeticaCrescente":
                tarefas.sort(Comparator.comparing(Tarefa::getTitulo));
                break;

            case "AlfabeticaDecrescente":
                tarefas.sort((o1, o2) -> o2.getTitulo().compareTo(o1.getTitulo()));
                break;

            case "dataDecrescente":
                tarefas.sort((o1, o2) -> o2.getDataTarefa().compareTo(o1.getDataTarefa()));
                break;

            case "duracaoMenor":
                tarefas.sort(Comparator.comparingLong(Tarefa::getDuracao));
                break;

            case "duracaoMaior":
                tarefas.sort((o1, o2) -> Long.compare(o2.getDuracao(), o1.getDuracao()));
                break;

            case "prioridadeAlta":
                tarefas.sort(Comparator.comparing(Tarefa::getPrioridade));
                break;

            case "prioridadeBaixa":
                tarefas.sort((o1, o2) -> o2.getPrioridade().compareTo(o1.getPrioridade()));
                break;
            case "pendente":
                tarefas.sort(Comparator.comparing(Tarefa::getStatus));
                break;

            case "finalizada":
                tarefas.sort((o1, o2) -> o2.getStatus().compareTo(o1.getStatus()));
                break;
            default:
                tarefas.sort(Comparator.comparing(Tarefa::getDataTarefa));
        }

    }


    private List<Tarefa> filtraPorFrequencia(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        List<Tarefa> tarefasAux = new ArrayList<>();
        if (tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("DIARIAMENTE")|
                tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("SEMANALMENTE")|
                tarefaDto.getFrequenciaDaTarefa().equalsIgnoreCase("MENSALMENTE")){
            for (Tarefa tarefa : tarefas) {
                if (tarefa.getFrequencia().name().equalsIgnoreCase(tarefaDto.getFrequenciaDaTarefa())) {
                    tarefasAux.add(tarefa);
                }
            }
            tarefas = tarefasAux;
        }

        return tarefas;
    }

    private List<Tarefa> filtarPorProjeto(TarefaDto tarefaDto, List<Tarefa> tarefas) {
        if (!tarefaDto.getProjetoDaTarefa().equalsIgnoreCase("Selecione um projeto")){
            Optional<Projeto> projetoOptional = projetoRepository.findById(Long.valueOf(tarefaDto.getProjetoDaTarefa()));
            if (projetoOptional.isPresent()){
                tarefas = tarefaRepository.findByProjeto(projetoOptional.get());
            }
        }
        return tarefas;
    }


    private List<Tarefa> filtrarPorData(TarefaDto tarefaDto, List<Tarefa> tarefas) {

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
