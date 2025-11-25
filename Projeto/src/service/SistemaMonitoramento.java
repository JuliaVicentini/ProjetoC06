package service;

import model.Paciente;
import model.alertas.Alerta;
import devices.DispositivoMedico;
import devices.ECG;
import devices.Termometro;
import devices.Oximetro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SistemaMonitoramento {
    private List<Paciente> pacientes;
    private List<DispositivoMedico> dispositivos;
    private List<Alerta> alertas;
    private List<Thread> threads;
    private GerenciadorArquivos gerenciadorArquivos;

    public SistemaMonitoramento() {
        this.pacientes = new ArrayList<>();
        this.dispositivos = new ArrayList<>();
        this.alertas = new CopyOnWriteArrayList<>();
        this.threads = new ArrayList<>();
        this.gerenciadorArquivos = new GerenciadorArquivos();
    }

    public void inicializarSistema() {
        // Tenta carregar do arquivo
        this.pacientes = gerenciadorArquivos.carregarPacientes();

        // Resiliência: Se não tiver dados, cria padrão para não abrir vazio
        if (pacientes.isEmpty()) {
            System.out.println("Base de dados vazia. Criando pacientes padrão...");
            pacientes.add(new Paciente(1, "João Silva", 45));
            pacientes.add(new Paciente(2, "Maria Santos", 68));
            pacientes.add(new Paciente(3, "Pedro Oliveira", 32));
            gerenciadorArquivos.salvarPacientes(pacientes);
        }

        // Instancia sensores para cada paciente (Integração com Parte 2)
        for (Paciente p : pacientes) {
            dispositivos.add(new ECG(p.getId()));
            dispositivos.add(new Termometro(p.getId()));
            dispositivos.add(new Oximetro(p.getId()));
        }

        for (DispositivoMedico d : dispositivos) {
            d.calibrar();
        }
        System.out.println("Sistema inicializado: " + pacientes.size() + " pacientes.");
    }

    public void iniciarMonitoramento() {
        gerenciadorArquivos.limparArquivoLeituras();
        threads.clear();

        for (Paciente p : pacientes) {
            List<DispositivoMedico> dispositivosPaciente = new ArrayList<>();

            // Filtra dispositivos do paciente (Compatibilidade)
            for(DispositivoMedico d : dispositivos) {
                // Verifica se o dispositivo é deste paciente (Lógica genérica compatível)
                if (d.toString().contains(String.valueOf(p.getId())) ||
                        (d instanceof ECG) || (d instanceof Termometro) || (d instanceof Oximetro)) {
                    dispositivosPaciente.add(d);
                }
            }

            // Criar Thread passando Runnable
            ColetorDadosThread coletor = new ColetorDadosThread(p, dispositivosPaciente, this);
            Thread thread = new Thread(coletor);
            threads.add(thread);
            thread.start(); // Inicia a thread
        }
        System.out.println("Monitoramento iniciado.");
    }

    public void pararMonitoramento() {
        // Parar cada coletor e interromper threads
        for (Thread thread : threads) {
            thread.interrupt(); // Interrompe a thread
        }

        try {
            Thread.sleep(1000); // Aguarda threads finalizarem
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threads.clear();
        System.out.println("Monitoramento finalizado.");
    }

    public void adicionarAlerta(Alerta alerta) {
        alertas.add(alerta);
        alerta.gerarAlerta();
    }

    public void exibirRelatorio() {
        try {
            List<String> logs = gerenciadorArquivos.lerHistorico();
            System.out.println("\n=== RELATÓRIO DE HISTÓRICO===");
            if (logs.isEmpty()) {
                System.out.println("(Sem dados registrados)");
            } else {
                logs.forEach(System.out::println);
            }
            System.out.println("Total de Alertas na Sessão: " + alertas.size());
        } catch (IOException e) {
            System.err.println("Erro ao ler relatório: " + e.getMessage());
        }
    }

    public GerenciadorArquivos getGerenciadorArquivos() {
        return gerenciadorArquivos;
    }
}