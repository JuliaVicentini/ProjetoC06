package service;

import model.Paciente;
import model.Leitura;
import model.alertas.Alerta;
import java.io.IOException;
import java.nio.file.*; // API NIO (Moderna)
import java.util.ArrayList;
import java.util.List;

public class GerenciadorArquivos {

    private static final String DIR_PATH = "logs";
    private static final String LEITURAS_FILE = "logs/leituras.txt";
    private static final String ALERTAS_FILE = "logs/alertas.txt";
    private static final String PACIENTES_FILE = "logs/pacientes.txt";

    public GerenciadorArquivos() {
        criarDiretorios();
    }

    private void criarDiretorios() {
        try {
            Path pathDir = Paths.get(DIR_PATH);
            if (Files.notExists(pathDir)) {
                Files.createDirectory(pathDir);
            }
        } catch (IOException e) {
            System.err.println("ERRO CRÍTICO: Falha ao criar diretório de logs: " + e.getMessage());
        }
    }

    private void escreverArquivo(String caminho, String conteudo, StandardOpenOption option) throws IOException {
        Path path = Paths.get(caminho);
        String linha = conteudo + System.lineSeparator();
        Files.write(path, linha.getBytes(), StandardOpenOption.CREATE, option);
    }

    // --- Métodos chamados pelo ColetorDadosThread (Membro 2) ---
    
    public void salvarLeituras(List<Leitura> leituras) {
        if (leituras == null || leituras.isEmpty()) return;
        try {
            for (Leitura l : leituras) {
                // APPEND: Adiciona ao final do arquivo (Histórico)
                escreverArquivo(LEITURAS_FILE, l.toString(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lote de leituras: " + e.getMessage());
        }
    }

    public void salvarAlertas(List<Alerta> alertas) {
        if (alertas == null || alertas.isEmpty()) return;
        try {
            for (Alerta a : alertas) {
                escreverArquivo(ALERTAS_FILE, a.toString(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lote de alertas: " + e.getMessage());
        }
    }

    // --- Métodos Individuais (Caso necessário) ---
    public void salvarLeitura(Leitura leitura) {
        try {
            escreverArquivo(LEITURAS_FILE, leitura.toString(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Falha IO: " + e.getMessage());
        }
    }

    public void salvarAlerta(Alerta alerta) {
        try {
            escreverArquivo(ALERTAS_FILE, alerta.toString(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Falha IO: " + e.getMessage());
        }
    }


    public void salvarPacientes(List<Paciente> pacientes) {
        try {
            Files.deleteIfExists(Paths.get(PACIENTES_FILE));
            for (Paciente p : pacientes) {
                // Serialização CSV: ID;Nome;Idade
                String linha = p.getId() + ";" + p.getNome() + ";" + p.getIdade();
                escreverArquivo(PACIENTES_FILE, linha, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    public List<Paciente> carregarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        Path path = Paths.get(PACIENTES_FILE);
        
        if (Files.notExists(path)) return lista;

        try {
            List<String> linhas = Files.readAllLines(path);
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    int idade = Integer.parseInt(partes[2]);
                    lista.add(new Paciente(id, nome, idade));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return lista;
    }

    public void limparArquivoLeituras() {
        try {
            Files.deleteIfExists(Paths.get(LEITURAS_FILE));
            Files.createFile(Paths.get(LEITURAS_FILE));
        } catch (IOException e) {
            System.err.println("Aviso: Não foi possível limpar logs.");
        }
    }

    public List<String> lerHistorico() throws IOException {
        if (Files.exists(Paths.get(LEITURAS_FILE))) {
            return Files.readAllLines(Paths.get(LEITURAS_FILE));
        }
        return new ArrayList<>();
    }
}
