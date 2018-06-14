package br.com.grupointegrado.dao;

import br.com.grupointegrado.helper.DaoHelper;
import br.com.grupointegrado.model.Artigo;
import br.com.grupointegrado.model.Cliente;
import br.com.grupointegrado.model.Compra;
import br.com.grupointegrado.model.Fidelidade;
import br.com.grupointegrado.model.Tema;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CompraDao extends Dao {
    
    private Compra compra;
    private Cliente cliente;
    private Fidelidade fidelidade;
    private Artigo artigo;
    private Tema tema;
    private ArrayList list;
    private DaoHelper helper;
       
    public CompraDao() throws ClassNotFoundException, SQLException {

        list = new ArrayList<>();
        compra = new Compra();
        helper = new DaoHelper();
        
    }
    
    public void insert( Compra compra ) throws SQLException {
        
        String sql = "INSERT INTO COMPRA VALUES ( "
                + "?, ( SELECT SYSDATE FROM DUAL ), ?, ?, "
                + "CLIENTE_TYPE( ?, ?, ?, FIDELIDADE_TYPE( ?, ?, ? ) ), "
                + "ARTIGO_TYPE( ?, ?, ?, ?, TEMA_TYPE( ?, ? ) ) )";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt( 1, helper.getNextCode( "COD_COMPRA", "COMPRA" ) );
        super.pstmt.setInt( 2, compra.getQuantidade() );
        super.pstmt.setDouble( 3, compra.getValor() );
        super.pstmt.setInt( 4, compra.getCliente().getCodigo() );
        super.pstmt.setString( 5, compra.getCliente().getNome() );
        super.pstmt.setString( 6, compra.getCliente().getCpf() );
        super.pstmt.setInt( 7, compra.getCliente().getFidelidade().getCodigo() );
        super.pstmt.setString( 8, compra.getCliente().getFidelidade().getDescricao() );
        super.pstmt.setInt( 9, compra.getCliente().getFidelidade().getBonus() );
        super.pstmt.setInt( 10, compra.getArtigo().getCodigo() );
        super.pstmt.setString( 11, compra.getArtigo().getDescricao() );
        super.pstmt.setInt( 12, compra.getArtigo().getQuantidade() );
        super.pstmt.setDouble( 13, compra.getArtigo().getPreco() );
        super.pstmt.setInt( 14, compra.getArtigo().getTema().getCodigo() );
        super.pstmt.setString( 15, compra.getArtigo().getTema().getDescricao() );

        
        try {
            super.insert();
        } catch ( SQLException e ) {
            System.out.println( e.toString() );
            JOptionPane.showMessageDialog( null, "Erro ao cadastrar a compra: " + e.getMessage() );
        }    
    }
    
    
    public Compra findOne( Integer code ) throws SQLException {
        
        String sql = "SELECT COD_COMPRA, DATA_VENDA, QUANTIDADE, VALOR_COMPRA, "
                + "C.CLIENTE.COD_CLIENTE AS CLIENTE_CODIGO, "
                + "C.CLIENTE.NOME AS CLIENTE_NOME, "
                + "C.CLIENTE.CPF AS CLIENTE_CPF, "
                + "C.CLIENTE.FIDELIDADE.COD_FIDELIDADE AS FIDELIDADE_CODIGO, "
                + "C.CLIENTE.FIDELIDADE.DESCRICAO AS FIDELIDADE_DESCRICAO, "
                + "C.CLIENTE.FIDELIDADE.BONUS AS FIDELIDADE_BONUS, "
                + "C.ARTIGO.COD_ARTIGO AS ARTIGO_CODIGO, "
                + "C.ARTIGO.DESCRICAO AS ARTIGO_DESCRICAO, "
                + "C.ARTIGO.QUANTIDADE AS ARTIGO_QUANTIDADE, "
                + "C.ARTIGO.PRECO AS ARTIGO_PRECO, "
                + "C.ARTIGO.TEMA.COD_TEMA AS TEMA_CODIGO, "
                + "C.ARTIGO.TEMA.DESCRICAO AS TEMA_DESCRICAO "
                + "FROM COMPRA C WHERE COD_COMPRA = ?";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        super.pstmt.setInt( 1, code );
        
        try {
            super.executeSql();
            
            if ( super.resultSet.next() ) {
                
                compra.setCodigo( super.resultSet.getInt("COD_COMPRA") );
                compra.setData( super.resultSet.getString("DATA_VENDA") );
                compra.setQuantidade( super.resultSet.getInt("QUANTIDADE") );
                compra.setValor( super.resultSet.getDouble("VALOR_COMPRA")  );

                cliente = new Cliente();

                cliente.setCodigo( super.resultSet.getInt("CLIENTE_CODIGO") );
                cliente.setNome( super.resultSet.getString("CLIENTE_NOME") );
                cliente.setCpf( super.resultSet.getString("CLIENTE_CPF") );

                fidelidade = new Fidelidade();

                fidelidade.setCodigo( super.resultSet.getInt("FIDELIDADE_CODIGO") );
                fidelidade.setDescricao( super.resultSet.getString("FIDELIDADE_DESCRICAO") );
                fidelidade.setBonus( super.resultSet.getInt("FIDELIDADE_BONUS") );

                cliente.setFidelidade( fidelidade );
                compra.setCliente( cliente );

                artigo = new Artigo();

                artigo.setCodigo( super.resultSet.getInt("ARTIGO_CODIGO") );
                artigo.setDescricao( super.resultSet.getString("ARTIGO_DESCRICAO") );
                artigo.setQuantidade( super.resultSet.getInt("ARTIGO_QUANTIDADE") );
                artigo.setPreco( super.resultSet.getDouble("ARTIGO_PRECO") );

                tema = new Tema();

                tema.setCodigo( super.resultSet.getInt("TEMA_CODIGO") );
                tema.setDescricao( super.resultSet.getString("TEMA_DESCRICAO") );

                artigo.setTema( tema );

                compra.setArtigo( artigo );
                
                return compra;
                
            } else {
                JOptionPane.showMessageDialog( null, "Não há compras cadastradas com este código." );
            }
            
            
        } catch ( SQLException e ) {
            JOptionPane.showMessageDialog( null, "Não foi possível realizar a busca pelo código informado. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList findAll() throws SQLException {
        
        String sql = "SELECT COD_COMPRA, DATA_VENDA, QUANTIDADE, VALOR_COMPRA, "
                + "C.CLIENTE.COD_CLIENTE AS CLIENTE_CODIGO, "
                + "C.CLIENTE.NOME AS CLIENTE_NOME, "
                + "C.CLIENTE.CPF AS CLIENTE_CPF, "
                + "C.CLIENTE.FIDELIDADE.COD_FIDELIDADE AS FIDELIDADE_CODIGO, "
                + "C.CLIENTE.FIDELIDADE.DESCRICAO AS FIDELIDADE_DESCRICAO, "
                + "C.CLIENTE.FIDELIDADE.BONUS AS FIDELIDADE_BONUS, "
                + "C.ARTIGO.COD_ARTIGO AS ARTIGO_CODIGO, "
                + "C.ARTIGO.DESCRICAO AS ARTIGO_DESCRICAO, "
                + "C.ARTIGO.QUANTIDADE AS ARTIGO_QUANTIDADE, "
                + "C.ARTIGO.PRECO AS ARTIGO_PRECO, "
                + "C.ARTIGO.TEMA.COD_TEMA AS TEMA_CODIGO, "
                + "C.ARTIGO.TEMA.DESCRICAO AS TEMA_DESCRICAO "
                + "FROM COMPRA C";
        
        super.pstmt = super.conexao.prepareStatement( sql );
        
        try {
            super.executeSql();
            
            return getObject( super.resultSet );
            
        } catch ( SQLException e ) {
            System.out.println(e.getErrorCode());
            JOptionPane.showMessageDialog( null, "Não foi possível realizar a busca pelas compras. \nErro: " + e.getMessage() );
        }
        
        return null;
    }
    
    public ArrayList getObject( ResultSet resultset ) throws SQLException {
        
        list = new ArrayList<>();
        
        while ( resultset.next() ) {
            
            compra.setCodigo( resultset.getInt("COD_COMPRA") );
            compra.setData( resultset.getString("DATA_VENDA") );
            compra.setQuantidade( resultset.getInt("QUANTIDADE") );
            compra.setValor( resultset.getDouble("VALOR_COMPRA")  );

            cliente = new Cliente();

            cliente.setCodigo( resultset.getInt("CLIENTE_CODIGO") );
            cliente.setNome( resultset.getString("CLIENTE_NOME") );
            cliente.setCpf( resultset.getString("CLIENTE_CPF") );

            fidelidade = new Fidelidade();

            fidelidade.setCodigo( resultset.getInt("FIDELIDADE_CODIGO") );
            fidelidade.setDescricao( resultset.getString("FIDELIDADE_DESCRICAO") );
            fidelidade.setBonus( resultset.getInt("FIDELIDADE_BONUS") );

            cliente.setFidelidade( fidelidade );
            compra.setCliente( cliente );

            artigo = new Artigo();

            artigo.setCodigo( resultset.getInt("ARTIGO_CODIGO") );
            artigo.setDescricao( resultset.getString("ARTIGO_DESCRICAO") );
            artigo.setQuantidade( resultset.getInt("ARTIGO_QUANTIDADE") );
            artigo.setPreco( resultset.getDouble("ARTIGO_PRECO") );

            tema = new Tema();

            tema.setCodigo( resultset.getInt("TEMA_CODIGO") );
            tema.setDescricao( resultset.getString("TEMA_DESCRICAO") );

            artigo.setTema( tema );

            compra.setArtigo( artigo );

            list.add( compra );
        }
            
        return list;
        
    }
            
    
    
}
