package br.com.grupointegrado.model;

public class Fidelidade {
    
    private Integer codigo;
    private String descricao;
    private Integer bonus;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
    
}
