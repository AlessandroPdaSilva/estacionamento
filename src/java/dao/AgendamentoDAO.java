 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Agendamento;
import model.Funcionario;
import model.RelatorioVeiculoFuncionario;
import model.VeiculoDaFrota;

public class AgendamentoDAO {
 
     
      
    //CREATE
    public Boolean save(Agendamento a) {
        // query
        String sql = "INSERT INTO agendamento(id_veiculo,id_funcionario,vaga_estacionamento,saida_do_veiculo,status,entrada_do_veiculo) VALUES (?,?,?,?,?,?)";

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
            f = a.getFuncionario();
            
            VeiculoDaFrota v = new VeiculoDaFrota();
            v = a.getVeiculo();
            
            pstmt.setInt(1, v.getId());//bind 1
            pstmt.setInt(2, f.getId());//bind 2
            pstmt.setInt(3, a.getVagaEstacionamento());//bind 3
            pstmt.setString(4, a.getSaidaVeiculo());//bind 3
            pstmt.setInt(5, a.getStatus());//bind 5
            pstmt.setString(6, a.getEntradaVeiculo());//bind 6

            // execute
            exec = pstmt.executeUpdate();

            if (exec > 0) {
                msg = true;
                VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                vd.modificarVaga(v.getId(), a.getVagaEstacionamento());
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
    public ArrayList<Agendamento> getAll() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM agendamento a "
       + "INNER JOIN funcionario f ON a.id_funcionario = f.id "
       + "INNER JOIN veiculo_da_frota v ON a.id_veiculo = v.id ";

        // var -veiculo_dados
        ArrayList<Agendamento> relatorio_dados = new ArrayList<>();
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

                Agendamento a = new Agendamento();// objeto
                
                
                a.setId(rset.getInt("a.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                a.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setPlaca(rset.getString("v.placa"));
                
                a.setVeiculo(v);
                
                
                a.setVagaEstacionamento(rset.getInt("a.vaga_estacionamento"));// veiculo

                a.setStatus(rset.getInt("a.status"));

                
                relatorio_dados.add(a);// adiciona na variavel (array)
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
    public ArrayList<Agendamento> getAllDesc() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM agendamento a "
       + "INNER JOIN funcionario f ON a.id_funcionario = f.id "
       + "INNER JOIN veiculo_da_frota v ON a.id_veiculo = v.id "
       + "ORDER BY a.id DESC";

        // var -veiculo_dados
        ArrayList<Agendamento> relatorio_dados = new ArrayList<>();
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

                Agendamento a = new Agendamento();// objeto
                
                
                a.setId(rset.getInt("a.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                a.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setPlaca(rset.getString("v.placa"));
                
                a.setVeiculo(v);
                
                
                a.setVagaEstacionamento(rset.getInt("a.vaga_estacionamento"));// veiculo

                a.setStatus(rset.getInt("a.status"));

                
                relatorio_dados.add(a);// adiciona na variavel (array)
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

    
    //UPDATE
    public Boolean update(int id, int id_veiculo, int id_funcionario, int vaga) throws Exception {
        
        // query
        String sql = "UPDATE agendamento SET id_veiculo = ?, id_funcionario = ?, vaga_estacionamento = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            
             
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id_veiculo);// bind 1
            pstmt.setInt(2, id_funcionario);// bind 2
            pstmt.setInt(3,vaga);// bind 3
            pstmt.setInt(4,id);// bind 4
            
             

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
        String sql = "DELETE FROM agendamento WHERE id = ?";

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
    
    //DELETE LIMPAR
    public Boolean deleteLimpar() throws Exception {
        // query
        String sql = "DELETE FROM agendamento WHERE status = 0";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);

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
    
    
    // get agendamento
    public Agendamento getAgendamento(int id) throws SQLException{
        
        Agendamento a = new Agendamento();
        // query
        String sql = "SELECT * FROM agendamento WHERE id = ? ";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                
                a.setId(rset.getInt("id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                
                a.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setId(rset.getInt("id_veiculo"));
                
                a.setVeiculo(v);
                
                
                a.setVagaEstacionamento(rset.getInt("vaga_estacionamento"));// veiculo
                a.setStatus(rset.getInt("status"));
                a.setSaidaVeiculo(rset.getString("saida_do_veiculo"));
                a.setEntradaVeiculo(rset.getString("entrada_do_veiculo"));
                
                
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

        return a;
        
    }
    
    
    // carro em uso
    public Boolean getCarroEmUso(String placa) throws SQLException{
        
        Agendamento a = new Agendamento();
        
        VeiculoDaFrotaDAO vf = new VeiculoDaFrotaDAO();
        VeiculoDaFrota vv = new VeiculoDaFrota();
        
        vv = vf.getVeiculo(placa);
        
        // query
        String sql = "SELECT * FROM agendamento WHERE (id_veiculo = ? AND status = 1)";

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
                
                a.setId(rset.getInt("id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                
                a.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setId(rset.getInt("id_veiculo"));
                
                a.setVeiculo(v);
                
                
                a.setVagaEstacionamento(rset.getInt("vaga_estacionamento"));// veiculo
                a.setStatus(rset.getInt("status"));
                a.setSaidaVeiculo(rset.getString("saida_do_veiculo"));
                a.setEntradaVeiculo(rset.getString("entrada_do_veiculo"));
                
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
    public Boolean getFuncionarioEmUso(String matricula) throws SQLException{
        
        Agendamento a = new Agendamento();
        
        FuncionarioDAO fd = new FuncionarioDAO();
        Funcionario ff = new Funcionario();
        ff = fd.getFuncionario(matricula);
        
        // query
        String sql = "SELECT * FROM agendamento WHERE (id_funcionario = ? AND status = 1)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, ff.getId());// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                
                a.setId(rset.getInt("id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                
                a.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setId(rset.getInt("id_veiculo"));
                
                a.setVeiculo(v);
                
                
                a.setVagaEstacionamento(rset.getInt("vaga_estacionamento"));// veiculo
                a.setStatus(rset.getInt("status"));
                a.setSaidaVeiculo(rset.getString("saida_do_veiculo"));
                a.setEntradaVeiculo(rset.getString("entrada_do_veiculo"));
                
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
    
    
    // finalizar
    public Boolean finalizarAgendamento(int id, String entrada) throws SQLException{
        // query
        String sql = "UPDATE agendamento SET entrada_do_veiculo = ?,status = 0 WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, entrada);// bind 1
            pstmt.setInt(2,id);// bind 2
            
             

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
    
    // CREATE RELATORIO
    public void saveRelatorio(Agendamento a) {
        // query
        String sql = "INSERT INTO relatorio_veiculo_funcionario(id_veiculo,id_funcionario,vaga_estacionamento,saida_do_veiculo,entrada_do_veiculo) VALUES (?,?,?,?,?)";

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
            f = a.getFuncionario();
            
            VeiculoDaFrota v = new VeiculoDaFrota();
            v = a.getVeiculo();
            
            pstmt.setInt(1, v.getId());//bind 1
            pstmt.setInt(2, f.getId());//bind 2
            pstmt.setInt(3, a.getVagaEstacionamento());//bind 3
            pstmt.setString(4, a.getSaidaVeiculo());//bind 3
            
            pstmt.setString(5, a.getEntradaVeiculo());//bind 6

            // execute
            exec = pstmt.executeUpdate();

            if (exec > 0) {
                msg = true;
            } else {
                msg = false;
            }

        } catch (Exception e) {
            e.printStackTrace();// erro
             
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

         

    }
    
    
    
}
