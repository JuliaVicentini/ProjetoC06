package model;

import java.time.LocalDateTime;

// Herda da classe Leitura e implementa o metodo de validação
public class TemperaturaCorporal extends Leitura {

    // Construtor
    public TemperaturaCorporal(int idPaciente, double valor, LocalDateTime dataHora) {
        super(idPaciente, "Temperatura Corporal", valor, dataHora);
    }

    // Valida se está dentro dos parâmetros normais
    @Override
    public boolean validarLeitura() {
        return valor >= 36.1 && valor <= 37.2;
    }

    // Classifica a temperatura corporal com base no valor
    public String getClassificacao() {
        if (valor < 36.1) return "Hipotermia";
        if (valor <= 37.2) return "Normal";
        if (valor <= 38.0) return "Febril";
        return "Febre Alta";
    }
}