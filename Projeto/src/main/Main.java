package main;

import service.SistemaMonitoramento;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Injeção de Dependência: Main cria o Sistema
        SistemaMonitoramento sistema = new SistemaMonitoramento();
        
        // Inicializa a infraestrutura
        sistema.inicializarSistema();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== SIMULADOR HOSPITALAR RPM (C06) ===");

        while (running) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Iniciar Monitoramento");
            System.out.println("2. Parar Monitoramento");
            System.out.println("3. Ver Histórico (Arquivo)");
            System.out.println("4. Sair");
            System.out.print("Opção: ");

            String op = scanner.nextLine();

            // Tratamento de Erro na Interface (Critério 6)
            try {
                switch (op) {
                    case "1":
                        sistema.iniciarMonitoramento();
                        break;
                    case "2":
                        sistema.pararMonitoramento();
                        break;
                    case "3":
                        sistema.exibirRelatorio();
                        break;
                    case "4":
                        sistema.pararMonitoramento();
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.err.println("Erro inesperado no sistema: " + e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Sistema encerrado.");
    }
}