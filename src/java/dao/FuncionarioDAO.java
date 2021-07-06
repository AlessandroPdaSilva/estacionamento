 
package dao;
 
import connect.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;

public class FuncionarioDAO {
    
    //CREATE
    public Boolean save(Funcionario f) {
        // query
        String sql = "INSERT INTO funcionario(matricula,nome,telefone) VALUES (?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, f.getMatricula());//bind 1
            pstmt.setString(2, f.getNome());//bind 2
            pstmt.setString(3, f.getTelefone());//bind 3
            

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
    public ArrayList<Funcionario> getAll() throws SQLException {

        // query
        String sql = "SELECT * FROM funcionario";

        // var -veiculo_dados
        ArrayList<Funcionario> funcionario_dados = new ArrayList<>();
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

                Funcionario f = new Funcionario();// objeto
                
                f.setId(rset.getInt("id"));
                f.setMatricula(rset.getString("matricula"));// placa
                f.setNome(rset.getString("nome"));// vaga
                f.setTelefone(rset.getString("telefone"));

                
                funcionario_dados.add(f);// adiciona na variavel (array)
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

        return funcionario_dados;// retorna array 

    }

    //UPDATE
    public Boolean update(int id, String matricula, String nome, String telefone) throws Exception {
        // query
        String sql = "UPDATE funcionario SET matricula = ?, nome = ?, telefone = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, matricula);// bind 1
            pstmt.setString(2, nome);// bind 2
            pstmt.setString(3,telefone);// bind 3
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
        String sql = "DELETE FROM funcionario WHERE id = ?";

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

    // get Veiculo
    public Funcionario getFuncionario(int id) throws SQLException{
        
        Funcionario f = new Funcionario();
        // query
        String sql = "SELECT * FROM funcionario WHERE id = ?";

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
                f.setId(rset.getInt("id"));
                f.setMatricula(rset.getString("matricula"));
                f.setNome(rset.getString("nome"));
                f.setTelefone(rset.getString("telefone"));

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

        return f;
        
    }
    
    
    
    
}
