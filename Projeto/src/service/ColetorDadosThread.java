package service;

import model.Paciente;
import model.Leitura;
import model.FrequenciaCardiaca;
import model.TemperaturaCorporal;
import model.OxigenacaoSanguinea;
import model.alertas.Alerta;
import model.alertas.AlertaCritico;
import model.alertas.AlertaModerado;
import devices.DispositivoMedico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ColetorDadosThread implements Runnable {
    private Paciente paciente;
    private List<DispositivoMedico> dispositivos;
    private SistemaMonitoramento sistema;
    private volatile boolean executando;

    public ColetorDadosThread(Paciente paciente, List<DispositivoMedico> dispositivos, SistemaMonitoramento sistema) {
        this.paciente = paciente;
        this.dispositivos = dispositivos;
        this.sistema = sistema;
        this.executando = true;
    }

    // metodo: run (implementação da interface Runnable
    @Override
    public void run() {
        System.out.println("Iniciando monitoramento para paciente: " + paciente.getNome());

        while (executando) {
            try {
                Thread.sleep(60000); // 1 minuto

                List<Leitura> leiturasColetadas = new ArrayList<>();
                List<Alerta> alertasGerados = new ArrayList<>();

                for (DispositivoMedico dispositivo : dispositivos) {
                    if (dispositivo.isCalibrado()) {
                        try {
                            double valor = dispositivo.coletarDados();
                            Leitura leitura = criarLeitura(dispositivo, valor);

                            if (leitura != null) {
                                leiturasColetadas.add(leitura);
                                paciente.adicionarLeitura(leitura);

                                Alerta alerta = verificarAlerta(leitura);
                                if (alerta != null) {
                                    alertasGerados.add(alerta);
                                    sistema.adicionarAlerta(alerta);
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao coletar dados do dispositivo: " + e.getMessage());
                        }
                    }
                }

                if (!leiturasColetadas.isEmpty()) {
                    sistema.getGerenciadorArquivos().salvarLeituras(leiturasColetadas);
                }
                if (!alertasGerados.isEmpty()) {
                    sistema.getGerenciadorArquivos().salvarAlertas(alertasGerados);
                }

            } catch (InterruptedException e) {
                System.out.println("Thread interrompida para paciente: " + paciente.getNome());
                break;
            }
        }
    }

    private Leitura criarLeitura(DispositivoMedico dispositivo, double valor) {
        String tipo = dispositivo.getTipoDispositivo();
        LocalDateTime agora = LocalDateTime.now();

        switch (tipo) {
            case "ECG":
                return new FrequenciaCardiaca(paciente.getId(), valor, agora);
            case "Termômetro":
                return new TemperaturaCorporal(paciente.getId(), valor, agora);
            case "Oxímetro":
                return new OxigenacaoSanguinea(paciente.getId(), valor, agora);
            default:
                return null;
        }
    }

    private Alerta verificarAlerta(Leitura leitura) {
        if (!leitura.validarLeitura()) {
            String mensagem = String.format("Leitura anormal: %s - Valor: %.2f",
                    leitura.getTipoLeitura(), leitura.getValor());

            if (leitura instanceof FrequenciaCardiaca) {
                FrequenciaCardiaca fc = (FrequenciaCardiaca) leitura;
                if (fc.getValor() < 40 || fc.getValor() > 160) {
                    return new AlertaCritico(paciente.getId(), mensagem);
                } else {
                    return new AlertaModerado(paciente.getId(), mensagem);
                }
            } else if (leitura instanceof TemperaturaCorporal) {
                TemperaturaCorporal temp = (TemperaturaCorporal) leitura;
                if (temp.getValor() < 34.0 || temp.getValor() > 40.0) {
                    return new AlertaCritico(paciente.getId(), mensagem);
                } else {
                    return new AlertaModerado(paciente.getId(), mensagem);
                }
            } else if (leitura instanceof OxigenacaoSanguinea) {
                OxigenacaoSanguinea oxi = (OxigenacaoSanguinea) leitura;
                if (oxi.getValor() < 85) {
                    return new AlertaCritico(paciente.getId(), mensagem);
                } else {
                    return new AlertaModerado(paciente.getId(), mensagem);
                }
            }
        }
        return null;
    }

    public void pararMonitoramento() {
        this.executando = false;
    }
}