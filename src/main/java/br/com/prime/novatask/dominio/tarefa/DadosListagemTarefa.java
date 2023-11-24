package br.com.prime.novatask.dominio.tarefa;

import java.time.LocalDateTime;

public record DadosListagemTarefa(long id, String titulo, boolean feito, LocalDateTime dataCadastro) {

    public DadosListagemTarefa(Tarefa tarefa) {
        this(tarefa.getId(), tarefa.getTitulo(), tarefa.isFeito(), tarefa.getDataCadastro());
    }
}