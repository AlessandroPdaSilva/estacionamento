 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;
import model.Pedido;
import model.RelatorioChaveFuncionario;
import model.VeiculoDaFrota;

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
    
    //READ
    public ArrayList<RelatorioChaveFuncionario> getAll() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM relatorio_chave_funcionario rf "
                + "INNER JOIN pedido p ON p.id = rf.id_pedido";

        // var -veiculo_dados
        ArrayList<RelatorioChaveFuncionario> relatorio_dados = new ArrayList<>();
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

                RelatorioChaveFuncionario r = new RelatorioChaveFuncionario();// objeto
                
                PedidoDAO pd = new PedidoDAO();
                Pedido p = new Pedido();// objeto
                
                // PEDIDO
                p.setId(rset.getInt("p.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                FuncionarioDAO fd = new FuncionarioDAO();
                
                f = fd.getFuncionario(rset.getInt("p.id_funcionario"));
                
                
                p.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                        
                v = vd.getVeiculo(rset.getInt("p.id_veiculo"));
                
                p.setVeiculo(v);
                
                 
                // percurso
                String percurso = rset.getString("p.percurso");
                String separaPer[] = percurso.split("/");
                
                p.setPercurso(""+separaPer[0]+" - "+separaPer[1]);
                
                 
                
                // RELATORIO
                
                r.setPedido(p);
                
                // status
                r.setStatus(rset.getInt("rf.status"));
                
                
                //datas
                // --saida
                String fsaida = rset.getString("rf.data_coleta");// query
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataColeta = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                // --entrada
                String fentrada = rset.getString("rf.data_devolucao");// query
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String dataDevolucao = "" + data2[2] + "/" + data2[1] + "/" + data2[0] + " - " + time2[0] + ":" + time2[1];

                if(dataDevolucao.equals("00/01/0000 - 00:00")){
                    dataDevolucao = "Nao devolvido";
                }
                
                r.setDataColeta(dataColeta);
                r.setDataDevolucao(dataDevolucao);
                
                
                // odometro
                r.setOdometroColeta(rset.getInt("rf.odometro_coleta"));
                r.setOdometroDevolucao(rset.getInt("rf.odometro_devolucao"));

                 
                 
                
                relatorio_dados.add(r);// adiciona na variavel (array)
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
    public ArrayList<RelatorioChaveFuncionario> getAllDesc() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM relatorio_chave_funcionario rf "
                + "INNER JOIN pedido p ON p.id = rf.id_pedido "
                + "ORDER BY rf.id_pedido DESC";

        // var -veiculo_dados
        ArrayList<RelatorioChaveFuncionario> relatorio_dados = new ArrayList<>();
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

                RelatorioChaveFuncionario r = new RelatorioChaveFuncionario();// objeto
                
                PedidoDAO pd = new PedidoDAO();
                Pedido p = new Pedido();// objeto
                
                // PEDIDO
                p.setId(rset.getInt("p.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                FuncionarioDAO fd = new FuncionarioDAO();
                
                f = fd.getFuncionario(rset.getInt("p.id_funcionario"));
                
                
                p.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                        
                v = vd.getVeiculo(rset.getInt("p.id_veiculo"));
                
                p.setVeiculo(v);
                
                 
                // percurso
                String percurso = rset.getString("p.percurso");
                String separaPer[] = percurso.split("/");
                
                p.setPercurso(""+separaPer[0]+" - "+separaPer[1]);
                
                 
                
                // RELATORIO
                
                r.setPedido(p);
                
                // status
                r.setStatus(rset.getInt("rf.status"));
                
                
                //datas
                // --saida
                String fsaida = rset.getString("rf.data_coleta");// query
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataColeta = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                // --entrada
                String fentrada = rset.getString("rf.data_devolucao");// query
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String dataDevolucao = "" + data2[2] + "/" + data2[1] + "/" + data2[0] + " - " + time2[0] + ":" + time2[1];

                if(dataDevolucao.equals("00/01/0000 - 00:00")){
                    dataDevolucao = "Nao devolvido";
                }
                
                r.setDataColeta(dataColeta);
                r.setDataDevolucao(dataDevolucao);
                
                
                // odometro
                r.setOdometroColeta(rset.getInt("rf.odometro_coleta"));
                r.setOdometroDevolucao(rset.getInt("rf.odometro_devolucao"));

                 
                 
                
                relatorio_dados.add(r);// adiciona na variavel (array)
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
    
    
    //READ USUARIO
    public ArrayList<RelatorioChaveFuncionario> getAllUsuario(int idFuncionario) throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM relatorio_chave_funcionario rf "
                + "INNER JOIN pedido p ON p.id = rf.id_pedido "
                + "WHERE p.id_funcionario = ?";

        // var -veiculo_dados
        ArrayList<RelatorioChaveFuncionario> relatorio_dados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idFuncionario);//bind 
            // array do resultado
            rset = pstmt.executeQuery();
            
            

            while (rset.next()) {// passando valores para var(veiculo_dados)

                RelatorioChaveFuncionario r = new RelatorioChaveFuncionario();// objeto
                
                PedidoDAO pd = new PedidoDAO();
                Pedido p = new Pedido();// objeto
                
                // PEDIDO
                p.setId(rset.getInt("p.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                FuncionarioDAO fd = new FuncionarioDAO();
                
                f = fd.getFuncionario(rset.getInt("p.id_funcionario"));
                
                
                p.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                        
                v = vd.getVeiculo(rset.getInt("p.id_veiculo"));
                
                p.setVeiculo(v);
                
                 
                // percurso
                String percurso = rset.getString("p.percurso");
                String separaPer[] = percurso.split("/");
                
                p.setPercurso(""+separaPer[0]+" - "+separaPer[1]);
                
                 
                
                // RELATORIO
                
                r.setPedido(p);
                
                // status
                r.setStatus(rset.getInt("rf.status"));
                
                
                //datas
                // --saida
                String fsaida = rset.getString("rf.data_coleta");// query
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String dataColeta = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                // --entrada
                String fentrada = rset.getString("rf.data_devolucao");// query
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String dataDevolucao = "" + data2[2] + "/" + data2[1] + "/" + data2[0] + " - " + time2[0] + ":" + time2[1];

                if(dataDevolucao.equals("00/01/0000 - 00:00")){
                    dataDevolucao = "Nao devolvido";
                }
                
                r.setDataColeta(dataColeta);
                r.setDataDevolucao(dataDevolucao);
                
                
                // odometro
                r.setOdometroColeta(rset.getInt("rf.odometro_coleta"));
                r.setOdometroDevolucao(rset.getInt("rf.odometro_devolucao"));

                 
                 
                
                relatorio_dados.add(r);// adiciona na variavel (array)
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
    
    // carro em uso
    public Boolean getCarroEmUso(String placa) throws SQLException{
        
        Pedido p = new Pedido();
        
        VeiculoDaFrotaDAO vf = new VeiculoDaFrotaDAO();
        VeiculoDaFrota vv = new VeiculoDaFrota();
        
        vv = vf.getVeiculo(placa);
        
        // query
        String sql = "SELECT * FROM relatorio_chave_funcionario rf "
                + "INNER JOIN pedido p ON p.id = rf.id_pedido "
                + "WHERE (p.id_veiculo = ? AND (rf.status = 2 OR rf.status = 1))";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, vv.getId());// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                 
                rset.getInt("id");
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
    
    // funcionario em uso
    public Boolean getRelatorioChaveEmUso(int idFuncionario) throws SQLException{
         
        
        // query
        String sql = "SELECT * FROM relatorio_chave_funcionario rf "
                + "INNER JOIN pedido p ON p.id = rf.id_pedido "
                + "WHERE (p.id_funcionario = ? AND (rf.status = 2 OR rf.status = 1))";
                 
                

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
    
    
    //UPDATE DEVOLDIDO
    public Boolean updateDevolvido(int idPedido,String dataDevolucao,int odometroDevolucao,int status) throws Exception {
        
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
    
    //UPDATE COLETADO
    public Boolean updateColetado(int idPedido,int status) throws Exception {
        
        // query
        String sql = "UPDATE relatorio_chave_funcionario "
                + "SET status = ? "
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
             
            pstmt.setInt(1, status);// bind 1
            pstmt.setInt(2, idPedido);// bind 2
            
             

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
