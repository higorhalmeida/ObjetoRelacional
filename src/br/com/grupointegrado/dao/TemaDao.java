package br.com.grupointegrado.dao;

import br.com.grupointegrado.helper.DaoHelper;
import br.com.grupointegrado.model.Tema;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class TemaDao extends Dao {
    
    private DaoHelper helper;
    
    public TemaDao () throws ClassNotFoundException, SQLException {
        helper = new DaoHelper();
    }
    
    public void insert( Tema tema ) throws SQLException {
        
        String sql = "INSERT INTO TEMA VALUES ( ? , ? )";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , helper.getNextCode( "COD_TEMA", "TEMA" ) );
        super.pstmt.setString( 2, tema.getDescricao() );
        
        try { 
            
            super.insert();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao inserir tema. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void update( Tema tema ) throws SQLException {
        
        String sql = "UPDATE TEMA SET DESCRICAO = ? WHERE ID_TEMA = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setString( 1, tema.getDescricao() );
        super.pstmt.setInt( 2 , tema.getCodigo() );
        
        try { 
            
            super.update();

            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao atualizar tema. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void delete( Tema tema ) throws SQLException {
        
        String sql = "DELETE FROM TEMA WHERE COD_TEMA = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , tema.getCodigo() );
        
        try { 
            
            super.delete();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao apagar tema. \nErro: " + e.getMessage() );
        }
        
    }
    
    public Tema findOne( Integer codigo ) throws SQLException {
        
        String sql = "SELECT * FROM TEMA WHERE COD_TEMA = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , codigo );
        
        try { 
            
            Tema tema = new Tema();
            
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                tema.setCodigo( super.resultSet.getInt( "COD_TEMA" ) );
                tema.setDescricao( super.resultSet.getString( "DESCRICAO" ));
            }
            
            return tema;
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar tema. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
}
