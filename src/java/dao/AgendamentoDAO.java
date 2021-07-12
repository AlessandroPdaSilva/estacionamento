 
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
        String sql = "INSERT INTO agendamento(id_veiculo,id_funcionario,vaga_estacionamento,saida_do_veiculo,entrada_do_veiculo,status) VALUES (?,?,?,?,?,?)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        int exec = 0;
        Boolean msg = false;
        try {
            //conexao
            conn = ConnectionFactory.createConnectionToMySql();
            //preparando
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setInt(1, a.getVeiculo().getId());//bind 1
            pstmt.setInt(2, a.getFuncionario().getId());//bind 2
            pstmt.setInt(3, a.getVagaEstacionamento());//bind 3
            pstmt.setString(4, a.getSaidaVeiculo());//bind 4
            pstmt.setString(5, a.getEntradaVeiculo());//bind 5
            pstmt.setInt(5, a.getStatus());//bind 6

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
    public ArrayList<Agendamento> getAll() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM agendamento r "
       + "INNER JOIN funcionario f ON r.id_funcionario = f.id "
       + "INNER JOIN veiculo_da_frota v ON r.id_veiculo = v.id ";

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

                Agendamento r = new Agendamento();// objeto
                
                
                r.setId(rset.getInt("r.id"));
                
                // funcionario
                Funcionario f = new Funcionario();
                f.setId(rset.getInt("f.id"));
                f.setMatricula(rset.getString("f.matricula"));
                f.setNome(rset.getString("f.nome"));
                
                r.setFuncionario(f);
                
                // veiculo
                VeiculoDaFrota v = new VeiculoDaFrota();
                v.setPlaca(rset.getString("v.placa"));
                
                r.setVeiculo(v);
                
                
                r.setVagaEstacionamento(rset.getInt("vaga_estacionamento"));// veiculo
                
                //data e hora
                // --saida
                String fsaida = rset.getString("saida_do_veiculo");
                String separa[] = fsaida.split(" ");
                String data[] = separa[0].split("-");
                String time[] = separa[1].split(":");
                String saida = ""+data[2]+"/"+data[1]+"/"+data[0]+" - "+time[0]+":"+time[1];

                // --entrada
                String fentrada = rset.getString("entrada_do_veiculo");
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String entrada = ""+data2[2]+"/"+data2[1]+"/"+data2[0]+" - "+time2[0]+":"+time2[1];
                
                
                
                r.setSaidaVeiculo(saida);// saida
                r.setEntradaVeiculo(entrada);//entrada
                r.setStatus(rset.getInt("r.status"));

                
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

    
    
    
    
    
}
