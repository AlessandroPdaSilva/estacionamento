 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Pedido;
import model.Funcionario;
import model.VeiculoDaFrota;

public class PedidoDAO {
    
    //CREATE
    public Boolean save(Pedido p) {
        // query
        String sql = "INSERT INTO pedido(id_funcionario,data_pedido,percurso,solicitacao,data_para_uso,mensagem,status)"
                + " VALUES (?,?,?,?,?,' ',2)";

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
             
            
            
            pstmt.setInt(1, f.getId());//bind 2
            pstmt.setString(2, p.getDataPedido());//bind 3
            pstmt.setString(3, p.getPercurso());//bind 4
            pstmt.setString(4, p.getSolicitacao());//bind 5
            pstmt.setString(5, p.getDataParaUso());//bind 6
            
           
             

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
        
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id ";

        
        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<Pedido>();
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
                
                 
                
                 
                // percurso
                p.setPercurso(rset.getString("p.percurso"));
                
                
                p.setStatus(rset.getInt("p.status"));

                //data e hora
                 
                String fsaida = rset.getString("p.data_pedido");
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataPedido = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                p.setDataPedido(dataPedido);
                p.setMensagem(rset.getString("p.mensagem"));
                
                //solicitacao
                p.setSolicitacao(rset.getString("p.solicitacao"));

                // data para uso
                p.setDataParaUso(rset.getString("p.data_para_uso")); 
                
                
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

    //READ DESC
    public ArrayList<Pedido> getAllDesc() throws SQLException {
        
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id "
       + "ORDER BY p.id DESC "
       + "LIMIT 125";

        
        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<Pedido>();
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
                
                 
                
                 
                // percurso
                p.setPercurso(rset.getString("p.percurso"));
                
                
                p.setStatus(rset.getInt("p.status"));

                //data e hora
                 
                String fsaida = rset.getString("p.data_pedido");
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataPedido = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                p.setDataPedido(dataPedido);
                p.setMensagem(rset.getString("p.mensagem"));
                
                //solicitacao
                p.setSolicitacao(rset.getString("p.solicitacao"));

                // data para uso
                p.setDataParaUso(rset.getString("p.data_para_uso")); 
                
                
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
    public Boolean updateStatus(int id,int status) throws Exception {
        
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
    
    //UPDATE MENSAGEM
    public Boolean updateMensagem(int id,String mensagem) throws Exception {
        
        // query
        String sql = "UPDATE pedido SET mensagem = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            
             
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, mensagem);// bind 1
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
    public Boolean delete(int idFuncionario) throws Exception {
        // query
        String sql = "DELETE FROM pedido WHERE id_funcionario = ? AND status = 2";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);// bind 1

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
    
    //get pedido
    public ArrayList<Pedido> getPedido(int idFuncionario) throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id "
       + "WHERE p.id_funcionario = ? "
       + "ORDER BY p.id DESC "
       + "LIMIT 125 ";

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
            pstmt.setInt(1, idFuncionario);//bind 1
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
                 
                
                p.setDataPedido(rset.getString("p.data_pedido"));
                
                
                // percurso
                p.setPercurso(rset.getString("p.percurso"));
                
                
                p.setStatus(rset.getInt("p.status"));
                
                //data e hora
                // --saida
                String fsaida = rset.getString("p.data_pedido");
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataPedido = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                p.setDataPedido(dataPedido);
                
                p.setMensagem(rset.getString("p.mensagem"));
                
                //solicitacao
                p.setSolicitacao(rset.getString("p.solicitacao"));

                // data para uso
                p.setDataParaUso(rset.getString("p.data_para_uso")); 
                
                
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
     
    //get pedido
    public Pedido getPedidoId(int idPedido) throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM pedido p "
       + "INNER JOIN funcionario f ON p.id_funcionario = f.id "
        
       + "WHERE p.id = ?";

        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        Pedido p = new Pedido();// objeto

        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idPedido);//bind 1
            // array do resultado
            rset = pstmt.executeQuery();
            
            

            while (rset.next()) {// passando valores para var(veiculo_dados)

                
                
                
                p.setId(rset.getInt("p.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                p.setFuncionario(f);
                
                 
                
                p.setDataPedido(rset.getString("p.data_pedido"));
                
                
                // percurso
                p.setPercurso(rset.getString("p.percurso"));
                
                
                p.setStatus(rset.getInt("p.status"));
                
                //data e hora
                // --saida
                String fsaida = rset.getString("p.data_pedido");
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataPedido = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                p.setDataPedido(dataPedido);
                
                p.setMensagem(rset.getString("p.mensagem"));
                
                //solicitacao
                p.setSolicitacao(rset.getString("p.solicitacao"));

                // data para uso
                p.setDataParaUso(rset.getString("p.data_para_uso")); 
                 
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

        return p;//  

    }
    
    
    // funcionario em uso
    public Boolean getPedidoEmUso(int idFuncionario) throws SQLException{
         
        
        // query
        String sql = "SELECT * FROM pedido WHERE (id_funcionario = ? AND status = 2)";
                 
                

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                
                msg = true;
            }

        } catch (Exception e) {// erro
            e.printStackTrace();
            msg = false;
        } finally {

            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

        }

        return msg;
        
    }
    
    
    
    
    //get mensagem
    public String getPedidoMensagem(int idPedido) throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM pedido WHERE id = ?";

        // var -veiculo_dados
        ArrayList<Pedido> relatorio_dados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        
        Pedido p = new Pedido();// objeto
        
        p.setMensagem(" ");

        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idPedido);//bind 1
            // array do resultado
            rset = pstmt.executeQuery();
            
            

            while (rset.next()) {// passando valores para var(veiculo_dados)

                
                
                
                p.setId(rset.getInt("id"));
                
                p.setMensagem(rset.getString("mensagem"));
                
                 
                
                 
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

        return p.getMensagem();

    }
     
        
    // teste 
    public static void main(String [] args){
        
        PedidoDAO pd = new PedidoDAO();
        Pedido p = new Pedido();
        Funcionario f = new Funcionario();
        
        
    }
     
}