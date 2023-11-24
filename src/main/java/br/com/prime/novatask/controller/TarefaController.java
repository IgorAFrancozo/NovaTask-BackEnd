package br.com.prime.novatask.controller;

import br.com.prime.novatask.dominio.tarefa.Tarefa;
import br.com.prime.novatask.dominio.tarefa.TarefaRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/tarefas")
@SecurityRequirement(name = "bearer-key")
public class TarefaController {

    @Autowired
    TarefaRepository tarefaRepository;

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    @PostMapping("adicionar")
    public ResponseEntity<String> adicionaTarefa(@Valid @RequestBody Tarefa tarefa) {
        tarefaRepository.save(tarefa);
        String mensagem = "Tarefa adicionada com sucesso.";
        return new ResponseEntity<>(mensagem, HttpStatus.CREATED);
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity atualizarTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);
        if (tarefaOptional.isPresent()) {
            Tarefa tarefa = tarefaOptional.get();
            String mensagem = tarefa.marcarComoConcluida();

            if (!mensagem.equals("Tarefa já concluída anteriormente")) {
                tarefaRepository.save(tarefa);
                return new ResponseEntity<>("Tarefa Concluída. Tempo decorrido: " + mensagem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Tarefa não existe", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity deletarTarefa(@PathVariable Long id) {
        boolean existe = tarefaRepository.existsById(id);
        if (existe) {
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>("Tarefa deletada", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Tarefa não existe", HttpStatus.BAD_REQUEST);
    }
}
