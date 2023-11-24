package br.com.prime.novatask.dominio.tarefa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Table(name = "tarefas")
@Entity(name = "Tarefa")
@EqualsAndHashCode(of = "id")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Requer título")
    private String titulo;
    private boolean feito;
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;
    private String tempoDecorrido;

    public Tarefa(long id, String titulo, boolean feito, LocalDateTime dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.feito = feito;
        this.dataCadastro = dataCadastro;
    }

    public String marcarComoConcluida() {
        if (!this.feito) {
            this.feito = true;
            Duration tempoDecorrido = Duration.between(this.dataCadastro, LocalDateTime.now());
            long horas = tempoDecorrido.toHours();
            long minutos = tempoDecorrido.toMinutesPart();
            long segundos = tempoDecorrido.toSecondsPart();

            StringBuilder resultado = new StringBuilder();
            if (horas > 0) {
                resultado.append(horas).append(" hora(s) ");
            }
            if (minutos > 0) {
                resultado.append(minutos).append(" minuto(s) ");
            }
            if (segundos > 0) {
                resultado.append(segundos).append(" segundo(s)");
            }
            this.tempoDecorrido = resultado.toString();
            return this.tempoDecorrido;
        }
        return "Tarefa já concluída anteriormente";
    }

    @PrePersist // Faz com que a data seja definida quando uma nova tarefa é persistida no banco
    protected void aoCriar() {
        this.dataCadastro = LocalDateTime.now();
    }
}
