package model;

import java.time.LocalDateTime;

// Herda da classe Leitura
public class OxigenacaoSanguinea extends Leitura {

    public OxigenacaoSanguinea(int idPaciente, double valor, LocalDateTime dataHora) {
        super(idPaciente, "Oxigenação Sanguínea", valor, dataHora);
    }

    // Valida se está dentro dos parâmetros normais (>= 95)
    @Override
    public boolean validarLeitura() {
        return valor >= 95;
    }

    // Classificação da oxigenação sanguinea com base no valor
    public String getClassificacao() {
        if (valor >= 95) return "Normal";
        if (valor >= 90) return "Hipoxemia Leve";
        if (valor >= 80) return "Hipoxemia Moderada";
        return "Hipoxemia Severa";
    }
}