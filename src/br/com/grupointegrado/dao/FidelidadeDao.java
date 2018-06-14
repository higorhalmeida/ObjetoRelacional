package br.com.grupointegrado.dao;

import br.com.grupointegrado.helper.DaoHelper;
import br.com.grupointegrado.model.Fidelidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FidelidadeDao extends Dao {
    
    private DaoHelper helper;
    private ArrayList list;
    
    public FidelidadeDao() throws ClassNotFoundException, SQLException {
        helper = new DaoHelper();
    }  
    
    public void insert( Fidelidade fidelidade ) throws SQLException {
        
        String sql = "INSERT INTO FIDELIDADE VALUES ( ?, ?, ? )";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , helper.getLastCode( "COD_FIDELIDADE", "FIDELIDADE" ) );
        super.pstmt.setString( 2, fidelidade.getDescricao() );
        super.pstmt.setInt( 3, fidelidade.getBonus() );
        
        try { 
            
            super.insert();
            
            super.conexao.commit();
            super.conexao.close();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao inserir fidelidade. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void update( Fidelidade fidelidade ) throws SQLException {
        
        String sql = "UPDATE FIDELIDADE SET BONUS = ?, DESCRICAO = ? WHERE COD_FIDELIDADE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1, fidelidade.getBonus() );
        super.pstmt.setString( 2, fidelidade.getDescricao() );
        super.pstmt.setInt( 3, fidelidade.getCodigo() );
        
        try { 
            
            super.update();
            
            super.conexao.commit();
            super.conexao.close();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao atualizar fidelidade. \nErro: " + e.getMessage() );
        }
        
    }
    
    public void delete( Fidelidade fidelidade ) throws SQLException {
        
        String sql = "DELETE FROM FIDELIDADE WHERE COD_FIDELIDADE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1, fidelidade.getCodigo());
        
        try { 
            
            super.delete();
            
            super.conexao.commit();
            super.conexao.close();
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao apagar fidelidade. \nErro: " + e.getMessage() );
        }
        
    }
    
    public Fidelidade findOne( Integer codigo ) throws SQLException {
        
        String sql = "SELECT * FROM FIDELIDADE WHERE COD_FIDELIDADE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        super.pstmt.setInt( 1 , codigo );
        
        try { 
            
            super.executeSql();
            
            Fidelidade fidelidade = new Fidelidade();
            
            if ( super.resultSet.next() ) {
                fidelidade.setCodigo( super.resultSet.getInt( "COD_FIDELIDADE" ) );
                fidelidade.setDescricao( super.resultSet.getString( "DESCRICAO" ));
                fidelidade.setBonus( super.resultSet.getInt( "BONUS" ) );
            }
            
            super.conexao.close();
            
            return fidelidade;
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar tema. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public Fidelidade getClienteNormal() throws SQLException {
        
        String sql = "SELECT * FROM FIDELIDADE WHERE COD_FIDELIDADE = 1";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try { 
            
            Fidelidade fidelidade = new Fidelidade();
            
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                fidelidade.setCodigo( super.resultSet.getInt( "COD_FIDELIDADE" ) );
                fidelidade.setDescricao( super.resultSet.getString( "DESCRICAO" ));
                fidelidade.setBonus( super.resultSet.getInt( "BONUS" ) );
            }
            
            super.conexao.close();
            
            return fidelidade;
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar fidelidade do cliente normal. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public Fidelidade getClienteFiel() throws SQLException {
        
        String sql = "SELECT * FROM FIDELIDADE WHERE COD_FIDELIDADE = 2";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try { 
            
            Fidelidade fidelidade = new Fidelidade();
            
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                fidelidade.setCodigo( super.resultSet.getInt( "COD_FIDELIDADE" ) );
                fidelidade.setDescricao( super.resultSet.getString( "DESCRICAO" ));
                fidelidade.setBonus( super.resultSet.getInt( "BONUS" ) );
            }
            
            super.conexao.close();
            
            return fidelidade;
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar fidelidade do cliente fiel. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList findAll() throws SQLException {
        
        String sql = "SELECT * FROM FIDELIDADE";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try { 
            
            super.executeSql();
            
            return getObject( super.resultSet );
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao buscar planos de fidelidade. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList getObject( ResultSet resultset ) throws SQLException {
        
        list = new ArrayList<>();
        
        while ( resultset.next() ) {
            Fidelidade fidelidade = new Fidelidade();
            
            fidelidade.setCodigo( resultset.getInt( "COD_FIDELIDADE" ) );
            fidelidade.setDescricao( resultset.getString( "DESCRICAO" ) );
            fidelidade.setBonus( resultset.getInt( "BONUS" ) );
            
            list.add( fidelidade );
        }
        
        return list;
        
    }

    
    
}
