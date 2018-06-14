package br.com.grupointegrado.dao;

import br.com.grupointegrado.conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;



public class Dao {

    protected Connection conexao;
    private final Conexao c;
    protected PreparedStatement pstmt;
    protected ResultSet resultSet;
    private ResultSetMetaData metaData;

    public Dao() throws ClassNotFoundException, SQLException {

        c = Conexao.getInstancy();
        this.conexao = c.getConexao();
    }

    protected void insert() throws SQLException {
        this.pstmt.executeUpdate();
    }

    protected void delete() throws SQLException {
        this.pstmt.executeUpdate();

    }

    protected void update() throws SQLException {
        this.pstmt.executeUpdate();

    }

    protected void executeSql() throws SQLException {

        try {
            resultSet = this.pstmt.executeQuery();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        try {
            metaData = this.pstmt.getMetaData();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }

}
