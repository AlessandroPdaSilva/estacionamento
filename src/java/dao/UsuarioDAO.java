 
package dao;
 
import connect.ConnectionFactory;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Funcionario;
import model.Usuario;

public class UsuarioDAO {
    
    //CREATE
    public Boolean save(Usuario u) {
        // query
        String sql = "INSERT INTO usuario(nome,senha,nivel,id_funcionario) VALUES (?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, u.getNome());//bind 1
            pstmt.setString(2, u.getSenha());//bind 2
            pstmt.setInt(3, u.getNivel());//bind 3
            pstmt.setInt(4, u.getFuncionario().getId());//bind 4
            

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
    public ArrayList<Usuario> getAll() throws SQLException {

        // query
        String sql = "SELECT * FROM usuario";

        // var -usuario_dados
        ArrayList<Usuario> usuario_dados = new ArrayList<Usuario>();
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

            while (rset.next()) {// passando valores para var(usuario_dados)

                Usuario usuario = new Usuario();// objeto
                
                usuario.setId(rset.getInt("id"));
                usuario.setNome(rset.getString("nome"));// nome
                usuario.setSenha(rset.getString("senha"));// senha
                
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                usuario.setFuncionario(f);// funcionario

                
                usuario_dados.add(usuario);// adiciona na variavel (array)
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

        return usuario_dados;// retorna array 

    }

    //UPDATE
    public Boolean update(int id, String nome, String senha) throws Exception {
        // query
        String sql = "UPDATE usuario SET nome = ?, senha = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, nome);// bind 1
            pstmt.setString(2, senha);// bind 2
            pstmt.setInt(3,id);// bind 3
             

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
        String sql = "DELETE FROM usuario WHERE id = ?";

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

    // getID
    public Usuario getCarregaPorID(int idUsuario) throws Exception {
        Usuario u = new Usuario();
        // query
        String sql = "SELECT * FROM usuario WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                u.setId(rset.getInt("id"));
                u.setNome(rset.getString("nome"));// nome
                u.setSenha(rset.getString("senha"));// senha
                
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                u.setFuncionario(f);// id funcionario
                 

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

        return u;

    }

    // get usuario
    public Usuario getUsuario(String login) throws SQLException{
        
        Usuario u = new Usuario();
        // query
        String sql = "SELECT * FROM usuario WHERE nome = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, login);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                u.setId(rset.getInt("id"));
                u.setNome(rset.getString("nome"));
                u.setSenha(rset.getString("senha"));
                u.setNivel(rset.getInt("nivel"));
                
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("id_funcionario"));
                
                u.setFuncionario(f);
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

        return u;
        
    }
    
    // criptografia
    public String criptografar(String senha){
        
        String senhaHex = "";
        
        try {
            
            MessageDigest md = MessageDigest.getInstance("SHA-1");// MD5,SHA-1,SHA-256
            byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));// criptografando
            
            StringBuilder sb = new StringBuilder();
            
            for(byte b : messageDigest){
                sb.append(String.format("%02x", 0xFF & b));//passando para String
                
            }
            
            senhaHex = sb.toString();// senha criptografada
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return senhaHex;
    }
    
    // funcionario em uso
    public Boolean getFuncionarioEmUso(int id_funcionario) throws SQLException{
         
        
        // query
        String sql = "SELECT * FROM usuario WHERE id_funcionario = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, id_funcionario);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                
                
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
    
    
}
