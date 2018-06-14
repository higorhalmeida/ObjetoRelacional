package br.com.grupointegrado.helper;

import br.com.grupointegrado.dao.Dao;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class DaoHelper extends Dao {
    
    public DaoHelper() throws ClassNotFoundException, SQLException{
        
    }
    
    public Integer getLastCode( String tableColumn, String tableName ) throws SQLException {
        
        String sql = "SELECT MAX( " + tableColumn + ") AS LASTCODE FROM " + tableName;
        
        super.pstmt = super.conexao.prepareStatement( sql );
        System.out.println(sql);
        try {
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                System.out.println(super.resultSet.getInt("LASTCODE"));
                return super.resultSet.getInt("LASTCODE");
            } else {
                JOptionPane.showMessageDialog( null, "Erro ao selecionar último código: nenhum registro encontrado");
            }

        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao selecionar último código: " + e.getMessage() );
        }
        
        return null;
    }
    
    public Integer getNextCode( String tableColumn, String tableName ) throws SQLException {
        
        String sql = "SELECT ( MAX( " +  tableColumn + " ) + 1 ) AS NEXTCODE FROM " + tableName;
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try {
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                return super.resultSet.getInt("NEXTCODE");               
            }

        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao selecionar próximo código: " + e.getMessage() );
        }
        
        return null;
    }
    
    /**
     * Faz a busca em uma tabela por meio de uma descrição.
     * 
     * @param table
     * @param column
     * @param descricao
     * @return
     * @throws SQLException 
     */
    public Boolean checkSqlBeforeTransaction( String table, String column, String descricao ) throws SQLException {
        
        String sql = "SELECT * FROM " + table + " WHERE " + column + " = ?";

        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setString( 1, descricao );

        super.executeSql();

        return super.resultSet.next();
        
    }
    
}
