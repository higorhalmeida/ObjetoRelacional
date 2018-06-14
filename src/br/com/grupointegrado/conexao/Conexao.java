package br.com.grupointegrado.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Conexao {

    private static Conexao instancy;
    private Connection conexao = null;
    private String url;
    private String driver;
    private String usuario;
    private String senha;

    private Conexao() throws ClassNotFoundException, SQLException {

        this.driver = "oracle.jdbc.driver.OracleDriver";

        this.url = "jdbc:oracle:thin:@10.211.55.4:1521:XE";
        this.usuario = "objrel";
        this.senha = "objrel";
        
        try {
            Class.forName(this.driver);
            System.out.println("Driver carregado.");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Driver do banco de dados,"
                    + "não localizado! ");
        }

        try {

            this.conexao = DriverManager.getConnection(
                    this.url,
                    this.usuario,
                    this.senha);
            System.out.println("CONECTADO COM SUCESSO!");
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage() );
        }

    }

    public static Conexao getInstancy() throws ClassNotFoundException, SQLException {

        instancy = new Conexao();

        return instancy;
    }

    public void desconeta() throws SQLException {

        try {
            this.conexao.close();
        } catch (SQLException e) {
            throw new SQLException( "Não foi possivel fechar a conexao" + e.getMessage() );
        }

    }

    public Connection getConexao() {
        return this.conexao;
    }

    public static void main(String[] args) {

        try {
            Conexao c = new Conexao();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro: " + e.toString());
        }
    }

}
