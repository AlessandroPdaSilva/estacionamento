 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Pedido;
import model.RelatorioChaveFuncionario;

public class RelatorioChaveFuncionarioDAO {
    
    //CREATE
    public Boolean save(RelatorioChaveFuncionario r) {
        // query
        String sql = "INSERT INTO relatorio_chave_funcionario"
                + "(id_pedido,data_coleta,odometro_coleta,data_devolucao,odometro_devolucao,status)"
                + " VALUES (?,?,?,'0000-01-00 00:00:00',-1,2)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            
            pstmt.setInt(1, r.getPedido().getId());//bind 1
            pstmt.setString(2, r.getDataColeta());//bind 2
            pstmt.setInt(3, r.getOdometroColeta());//bind 3
             
            
           
             

            // execute
            exec = pstmt.executeUpdate();

            if (exec > 0) {
                msg = true;
            } else {
                msg = false;
            }

        } catch (Exception e) {
            e.printStackTrace();// erro
            return false;
        } finally {
            // close connect
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return msg;

    }
    
    
    //UPDATE
    public Boolean update(int idPedido,String dataDevolucao,int odometroDevolucao,int status) throws Exception {
        
        // query
        String sql = "UPDATE relatorio_chave_funcionario "
                + "SET data_devolucao = ?, odometro_devolucao = ?,status = ? "
                + "WHERE id_pedido = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            
             
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, dataDevolucao);// bind 1
            pstmt.setInt(2, odometroDevolucao);// bind 2
            pstmt.setInt(3, status);// bind 3
            pstmt.setInt(4, idPedido);// bind 4
            
             

            // execução (boolean)
            exec = pstmt.executeUpdate();

            if (exec > 0) {
                msg = true;
            } else {
                msg = false;
            }

        } catch (Exception e) {// erro
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

        }

        // mensagem de exclusao
        return msg;

    }
    
    
}
