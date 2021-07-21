 
package dao;
 
import connect.ConnectionFactory;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.VeiculoDaFrota;


public class VeiculoDaFrotaDAO {
    //CREATE
    public Boolean save(VeiculoDaFrota vf) {
        // query
        String sql = "INSERT INTO veiculo_da_frota(placa,vaga,modelo,odometro) VALUES (?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, vf.getPlaca());//bind 1
            pstmt.setInt(2, vf.getVaga());//bind 2
            pstmt.setString(3, vf.getModelo());//bind 3
            pstmt.setInt(4, vf.getOdometro());//bind 4

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
    public ArrayList<VeiculoDaFrota> getAll() throws SQLException {

        // query
        String sql = "SELECT * FROM veiculo_da_frota";

        // var -veiculo_dados
        ArrayList<VeiculoDaFrota> veiculo_dados = new ArrayList<>();
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

                VeiculoDaFrota vf = new VeiculoDaFrota();// objeto
                
                vf.setId(rset.getInt("id"));
                vf.setPlaca(rset.getString("placa"));// placa
                vf.setVaga(rset.getInt("vaga"));// vaga
                vf.setModelo(rset.getString("modelo"));
                vf.setOdometro(rset.getInt("odometro"));
                
                veiculo_dados.add(vf);// adiciona na variavel (array)
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

        return veiculo_dados;// retorna array 

    }

    //UPDATE
    public Boolean update(int id, String placa, String modelo, int vaga, int odometro) throws Exception {
        // query
        String sql = "UPDATE veiculo_da_frota SET placa = ?, modelo = ?, vaga = ?, odometro = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, placa);// bind 1
            pstmt.setString(2, modelo);// bind 2
            pstmt.setInt(3,vaga);// bind 3
            pstmt.setInt(4,odometro);// bind 4
            pstmt.setInt(5,id);// bind 5
            
             

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
        String sql = "DELETE FROM veiculo_da_frota WHERE id = ?";

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
    public VeiculoDaFrota getVeiculo(int id) throws SQLException{
        
        VeiculoDaFrota vf = new VeiculoDaFrota();
        // query
        String sql = "SELECT * FROM veiculo_da_frota WHERE id = ?";

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
                vf.setId(rset.getInt("id"));
                vf.setModelo(rset.getString("modelo"));
                vf.setPlaca(rset.getString("placa"));
                vf.setVaga(rset.getInt("vaga"));
                vf.setOdometro(rset.getInt("odometro"));

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

        return vf;
        
    }
    
    // get Veiculo por placa
    public VeiculoDaFrota getVeiculo(String placa) throws SQLException{
        
        VeiculoDaFrota vf = new VeiculoDaFrota();
        // query
        String sql = "SELECT * FROM veiculo_da_frota WHERE placa = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        ResultSet rset;
        Boolean msg = false;
        try {
            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, placa);// bind 1

            // execução (boolean)
            rset = pstmt.executeQuery();

            if (rset.next()) {
                vf.setId(rset.getInt("id"));
                vf.setModelo(rset.getString("modelo"));
                vf.setPlaca(rset.getString("placa"));
                vf.setVaga(rset.getInt("vaga"));
                vf.setOdometro(rset.getInt("odometro"));

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

        return vf;
        
    }
    
    // modificar vaga
    public void modificarVaga(int id,int vaga) throws Exception {
        // query
        String sql = "UPDATE veiculo_da_frota SET vaga = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {

            // conexao
            conn = connect.ConnectionFactory.createConnectionToMySql();
            // preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            pstmt.setInt(1,vaga);// bind 1
            pstmt.setInt(2,id);// bind 2
            
             

            // execução (boolean)
            exec = pstmt.executeUpdate();

             

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
 

    }

    
}
