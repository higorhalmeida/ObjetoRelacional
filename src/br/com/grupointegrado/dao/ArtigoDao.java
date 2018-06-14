package br.com.grupointegrado.dao;

import br.com.grupointegrado.helper.DaoHelper;
import br.com.grupointegrado.model.Artigo;
import br.com.grupointegrado.model.Tema;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ArtigoDao extends Dao {
    
    private DaoHelper helper;
    private Artigo artigo;
    private Tema tema;
    
    public ArtigoDao() throws ClassNotFoundException, SQLException {
        helper = new DaoHelper();
        artigo = new Artigo();
        tema = new Tema();
    }
    
    public void insert( Artigo artigo ) throws SQLException {
        
        String sql = "INSERT INTO ARTIGO VALUES ( ?, ?, ?, ?, TEMA_TYPE( ?,? ) )";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , helper.getLastCode( "COD_ARTIGO", "ARTIGO" ) );
        super.pstmt.setString( 2, artigo.getDescricao() );
        super.pstmt.setInt( 3, artigo.getQuantidade() );
        super.pstmt.setDouble( 4, artigo.getPreco() );
        super.pstmt.setInt( 5, artigo.getTema().getCodigo() );
        super.pstmt.setString( 6, artigo.getTema().getDescricao() );
        
        try { 
            
            super.insert();

        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao inserir Artigo. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void update( Artigo artigo ) throws SQLException {
        
        String sql = "UPDATE ARTIGO SET DESCRICAO = ?, QUANTIDADE = ?, PRECO = ?, TEMA_TY( ?, ? ) WHERE ID_ARTIGO = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setString( 1, artigo.getDescricao() );
        super.pstmt.setInt( 2, artigo.getQuantidade() );
        super.pstmt.setDouble( 3, artigo.getPreco() );
        super.pstmt.setInt( 4, artigo.getTema().getCodigo() );
        super.pstmt.setString( 5, artigo.getTema().getDescricao() );
        super.pstmt.setInt( 6, artigo.getCodigo() );
        
        try { 
            
            super.update();
            
            super.conexao.commit();
            super.conexao.close();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao atualizar artigo. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void delete( Artigo artigo ) throws SQLException {
        
        String sql = "DELETE FROM ARTIGO WHERE COD_ARTIGO = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1, artigo.getCodigo() );
        
        try { 
            
            super.delete();
            
            super.conexao.commit();
            super.conexao.close();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao apagar artigo. \nErro: " + e.getMessage() );
        }
        
    }
    
    public Artigo findOne( Integer codigo ) throws SQLException {
        
        String sql = "SELECT COD_ARTIGO, DESCRICAO, QUANTIDADE, PRECO, A.TEMA.COD_TEMA, A.TEMA.DESCRICAO FROM ARTIGO A WHERE COD_ARTIGO = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , codigo );
        
        try { 
            
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                tema = new Tema();
                
                artigo.setCodigo( super.resultSet.getInt( "COD_ARTIGO" ) );
                artigo.setDescricao( super.resultSet.getString( "DESCRICAO" ) );
                artigo.setQuantidade( super.resultSet.getInt( "QUANTIDADE" ));
                artigo.setPreco( super.resultSet.getDouble( "PRECO" ) );
                
                tema.setCodigo( super.resultSet.getInt( "TEMA.COD_TEMA" ) );
                tema.setDescricao( super.resultSet.getString( "TEMA.DESCRICAO" ) );
                
                artigo.setTema( tema );
            }
            
            super.conexao.close();
            
            return artigo;
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar artigo. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    
}
