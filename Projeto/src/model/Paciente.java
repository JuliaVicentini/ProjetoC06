package model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int id; // Identificador único do paciente
    private String nome; // Nome do paciente
    private int idade; // Idade do paciente
    private List<Leitura> leituras; // Lista de leituras do paciente

    // construtor
    public Paciente(int id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.leituras = new ArrayList<>(); // Inicializa a lista de leituras
    }

    // Métodos getters e setters para acessar e modificar os atributos privados
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<Leitura> getLeituras() {
        return leituras;
    }

    // Adiciona uma leitura à lista de leituras do paciente
    public void adicionarLeitura(Leitura leitura) {
        this.leituras.add(leitura);
    }

    @Override
    public String toString() {
        return "Paciente{id=" + id + ", nome='" + nome + "', idade=" + idade + ", leituras=" + leituras.size() + "}";
    }
}