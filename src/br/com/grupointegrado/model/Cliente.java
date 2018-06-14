package br.com.grupointegrado.model;

public class Cliente {
    
    private Integer codigo;
    private String nome;
    private String cpf;
    private Fidelidade fidelidade;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Fidelidade getFidelidade() {
        return fidelidade;
    }

    public void setFidelidade(Fidelidade fidelidade) {
        this.fidelidade = fidelidade;
    }
    
}
