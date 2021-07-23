 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Agendamento;
import model.Pedido;
import model.Funcionario;
import model.VeiculoDaFrota;

public class PedidoDAO {
    
    //CREATE
    public Boolean save(Pedido p) {
        // query
        String sql = "INSERT INTO pedido(id_veiculo,id_funcionario,data_pedido,percurso,status)"
                + " VALUES (?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            Funcionario f = new Funcionario();
            f = p.getFuncionario();
            
            VeiculoDaFrota v = new VeiculoDaFrota();
            v = p.getVeiculo();
            
            pstmt.setInt(1, v.getId());//bind 1
            pstmt.setInt(2, f.getId());//bind 2
            pstmt.setString(3, p.getDataPedido());//bind 3
            pstmt.setString(4, p.getPercurso());//bind 4
            pstmt.setInt(5, p.getStatus());//bind 5
             

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
    
    //READ
    public ArrayList<Pedido> getAll() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id "
       + "INNER JOIN veiculo_da_frota v ON p.id_veiculo = v.id ";

        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            // array do resultado
            rset = pstmt.executeQuery();

            while (rset.next()) {// passando valores para var(veiculo_dados)

                Pedido p = new Pedido();// objeto
                
                
                p.setId(rset.getInt("p.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                p.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setPlaca(rset.getString("v.placa"));
                
                p.setVeiculo(v);
                
                p.setDataPedido(rset.getString("p.data_pedido"));
                p.setPercurso(rset.getString("p.percurso"));
                
                p.setStatus(rset.getInt("p.status"));

                
                relatorio_dados.add(p);// adiciona na variavel (array)
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
            if (rset != null) {
                rset.close();
            }
        }

        return relatorio_dados;// retorna array 

    }

    //UPDATE STATUS
    public Boolean update(int id,int status) throws Exception {
        
        // query
        String sql = "UPDATE pedido SET status = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            
             
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, status);// bind 1
            pstmt.setInt(2, id);// bind 2
            
            
             

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
    
    
    //DELETE
    public Boolean delete(int id) throws Exception {
        // query
        String sql = "DELETE FROM pedido WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id);// bind 1

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
    
    //READ
    public ArrayList<Pedido> getPedido(int id) throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id "
       + "INNER JOIN veiculo_da_frota v ON p.id_veiculo = v.id "
       + "WHERE p.id = ?";

        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id);//bind 1
            // array do resultado
            rset = pstmt.executeQuery();

            while (rset.next()) {// passando valores para var(veiculo_dados)

                Pedido p = new Pedido();// objeto
                
                
                p.setId(rset.getInt("a.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                p.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setPlaca(rset.getString("v.placa"));
                
                p.setVeiculo(v);
                
                p.setDataPedido(rset.getString("a.data_pedido"));
                p.setPercurso(rset.getString("a.percurso"));
                
                p.setStatus(rset.getInt("a.status"));

                
                relatorio_dados.add(p);// adiciona na variavel (array)
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
            if (rset != null) {
                rset.close();
            }
        }

        return relatorio_dados;// retorna array 

    }

    
}
