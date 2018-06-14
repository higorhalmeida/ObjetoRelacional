package br.com.grupointegrado.dao;

import br.com.grupointegrado.helper.DaoHelper;
import br.com.grupointegrado.model.Cliente;
import br.com.grupointegrado.model.Fidelidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClienteDao extends Dao {
    
    private Fidelidade fidelidade;
    private Cliente cliente;
    private DaoHelper helper;
    private ArrayList list;
    
    
    public ClienteDao() throws ClassNotFoundException, SQLException {

        list = new ArrayList<>();
        cliente = new Cliente();
        helper = new DaoHelper();
        
    }
    
    public void insert( Cliente cliente ) throws SQLException {
        
        String sql = "INSERT INTO CLIENTE VALUES ( ?, ?, ?, FIDELIDADE_TYPE( ?, ?, ? ) )";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt(1, helper.getNextCode( "COD_CLIENTE", "CLIENTE" ) );
        super.pstmt.setString( 2, cliente.getNome() );
        super.pstmt.setString( 3, cliente.getCpf() );
        super.pstmt.setInt( 4, cliente.getFidelidade().getCodigo() );
        super.pstmt.setString( 5, cliente.getFidelidade().getDescricao() );
        super.pstmt.setInt( 6, cliente.getFidelidade().getBonus() );

        try {
            super.insert();
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao cadastrar o cliente: " + e.getMessage() );
        }    
    }
    
    
    public void update( Cliente cliente ) throws SQLException {
        
        String sql = "UPDATE CLIENTE SET NOME = ?, CPF = ?, FIDELIDADE = FIDELIDADE_TYPE( ?, ?, ? ) WHERE COD_CLIENTE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setString( 1, cliente.getNome() );
        super.pstmt.setString( 2, cliente.getCpf() );
        super.pstmt.setInt( 3, cliente.getFidelidade().getCodigo() );
        super.pstmt.setString( 4, cliente.getFidelidade().getDescricao() );
        super.pstmt.setInt( 5, cliente.getFidelidade().getBonus() );
        super.pstmt.setInt( 6, cliente.getCodigo() );

        try {
            super.update();
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao atualizar o cliente: " + e.getMessage() );
        }    
    }

    public void delete( Cliente cliente ) throws SQLException {
        
        String sql = "DELETE FROM CLIENTE WHERE COD_CLIENTE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt( 1, cliente.getCodigo() );
        
        try {
            super.delete();
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Erro ao excluir cliente: " + e.getMessage() );            
        }
    }
    
    public Cliente findOne( Integer code ) throws SQLException {
        
        String sql = "SELECT COD_CLIENTE, NOME, CPF, C.FIDELIDADE.COD_FIDELIDADE, C.FIDELIDADE.DESCRICAO, C.FIDELIDADE.BONUS FROM CLIENTE C WHERE COD_CLIENTE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt( 1, code );
        
        System.out.println(super.pstmt);
        
        try {
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                fidelidade = new Fidelidade();
            
                cliente.setCodigo(  super.resultSet.getInt( "COD_CLIENTE" ) );
                cliente.setNome( super.resultSet.getString( "NOME" ) );
                cliente.setCpf( super.resultSet.getString( "CPF" ) );
                fidelidade.setCodigo( super.resultSet.getInt( "FIDELIDADE.COD_FIDELIDADE" ) );
                fidelidade.setDescricao( super.resultSet.getString( "FIDELIDADE.DESCRICAO" ) );
                fidelidade.setBonus( super.resultSet.getInt( "FIDELIDADE.BONUS" ) );

                cliente.setFidelidade( fidelidade );
                
                return cliente;
            } else {
                JOptionPane.showMessageDialog( null, "Não há clientes cadastrados com este código." );
            }
            
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Não foi possível realizar a busca pelo código informado. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList findByCode( Integer code ) throws SQLException {
        
        String sql = "SELECT COD_CLIENTE, NOME, CPF, C.FIDELIDADE.COD_FIDELIDADE, C.FIDELIDADE.DESCRICAO, C.FIDELIDADE.BONUS FROM CLIENTE C WHERE COD_CLIENTE = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt( 1, code );
        
        try {
            super.executeSql();
            
            return this.getObject( super.resultSet );
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Não foi possível realizar a busca pelo código informado. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    
    public ArrayList findAll() throws SQLException {
        String sql = "SELECT * FROM CLIENTE";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try {
            super.executeSql();
            
            return this.getObject( super.resultSet );
        
        } catch ( SQLException e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog( null, "Não foi possivel realizar a busca pelos assuntos. \n Erro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList getObject( ResultSet resultset ) throws SQLException {
        
        list = new ArrayList<>();
        
        while ( resultset.next() ) {
            
            fidelidade = new Fidelidade();
            
            cliente.setCodigo( Integer.parseInt( resultset.getString( "COD_CLIENTE" ) ) );
            cliente.setNome( resultset.getString( "NOME" ) );
            cliente.setCpf( resultset.getString( "CPF" ) );
            fidelidade.setCodigo( resultset.getInt( "FIDELIDADE.COD_FIDELIDADE" ) );
            fidelidade.setDescricao( resultset.getString( "FIDELIDADE.DESCRICAO" ) );
            fidelidade.setBonus( resultset.getInt( "FIDELIDADE.BONUS" ) );
            
            cliente.setFidelidade( fidelidade );
            
            list.add( cliente );
            
            cliente = null;
            fidelidade = null;
        }
        
        return list;
    }
    
}
