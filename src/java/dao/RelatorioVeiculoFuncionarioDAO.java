package dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import connect.ConnectionFactory;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import model.Funcionario;
import model.RelatorioVeiculoFuncionario;
import model.VeiculoDaFrota;

public class RelatorioVeiculoFuncionarioDAO {

    //CREATE
    public Boolean save(RelatorioVeiculoFuncionario r) {
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
            pstmt.setInt(1, r.getVeiculo().getId());//bind 1
            pstmt.setInt(2, r.getFuncionario().getId());//bind 2
            pstmt.setInt(3, r.getVagaEstacionamento());//bind 3
            pstmt.setString(4, r.getSaidaVeiculo());//bind 4
            pstmt.setString(5, r.getEntradaVeiculo());//bind 5

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
    public ArrayList<RelatorioVeiculoFuncionario> getAll() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM relatorio_veiculo_funcionario r "
                + "INNER JOIN funcionario f ON r.id_funcionario = f.id "
                + "INNER JOIN veiculo_da_frota v ON r.id_veiculo = v.id ";

        // var -veiculo_dados
        ArrayList<RelatorioVeiculoFuncionario> relatorio_dados = new ArrayList<>();
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

                RelatorioVeiculoFuncionario r = new RelatorioVeiculoFuncionario();// objeto

                r.setId(rset.getInt("id"));

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
                String saida = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                // --entrada
                String fentrada = rset.getString("entrada_do_veiculo");
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String entrada = "" + data2[2] + "/" + data2[1] + "/" + data2[0] + " - " + time2[0] + ":" + time2[1];

                r.setSaidaVeiculo(saida);// saida
                r.setEntradaVeiculo(entrada);//entrada

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
    public ArrayList<RelatorioVeiculoFuncionario> getAllDesc() throws SQLException {

        // query --id,id_veiculo,id_funcionario,vaga_estacionamento,DATE_FORMAT( saida_do_veiculo, '%d/%m/%Y  %H:%i' ),DATE_FORMAT( entrada_do_veiculo, '%d/%m/%Y  %H:%i' )
        //SELECT DATE_FORMAT(saida_do_veiculo,'%d/%m/%Y %H:%i'), DATE_FORMAT(entrada_do_veiculo,'%d/%m/%Y %H:%i'),id, id_veiculo, id_funcionario, vaga_estacionamento FROM relatorio_veiculo_funcionario
        String sql = "SELECT * FROM relatorio_veiculo_funcionario r "
                + "INNER JOIN funcionario f ON r.id_funcionario = f.id "
                + "INNER JOIN veiculo_da_frota v ON r.id_veiculo = v.id "
                + "ORDER BY r.id DESC";

        // var -veiculo_dados
        ArrayList<RelatorioVeiculoFuncionario> relatorio_dados = new ArrayList<>();
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

                RelatorioVeiculoFuncionario r = new RelatorioVeiculoFuncionario();// objeto

                r.setId(rset.getInt("id"));

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
                String saida = "" + data[2] + "/" + data[1] + "/" + data[0] + " - " + time[0] + ":" + time[1];

                // --entrada
                String fentrada = rset.getString("entrada_do_veiculo");
                String separa2[] = fentrada.split(" ");
                String data2[] = separa2[0].split("-");
                String time2[] = separa2[1].split(":");
                String entrada = "" + data2[2] + "/" + data2[1] + "/" + data2[0] + " - " + time2[0] + ":" + time2[1];

                r.setSaidaVeiculo(saida);// saida
                r.setEntradaVeiculo(entrada);//entrada

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

    // Criar PDF
    public String gerarPDF() {

        Document doc = new Document();
        doc.setPageSize(PageSize.A4.rotate());
        ArrayList<RelatorioVeiculoFuncionario> rList = new ArrayList<>();
        String resp = "";
        
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        System.out.println();

        String arquivoPdf = myRandom.substring(0,20)+".pdf";

        Boolean msg = false;

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(arquivoPdf));
            
            
            
            doc.open();

            Paragraph p = new Paragraph("Relatorio de Garagem");// titulo
            p.setAlignment(1);// centralizar titulo

            doc.add(p);

            p = new Paragraph(" ");// quebra linha

            doc.add(p);

            // tabela head
            PdfPTable table = new PdfPTable(7);
            PdfPCell cel1 = new PdfPCell(new Paragraph("ID"));
            PdfPCell cel2 = new PdfPCell(new Paragraph("Placa do Carro"));
            PdfPCell cel3 = new PdfPCell(new Paragraph("Nome"));
            PdfPCell cel4 = new PdfPCell(new Paragraph("Matricula"));
            PdfPCell cel5 = new PdfPCell(new Paragraph("Vaga"));
            PdfPCell cel6 = new PdfPCell(new Paragraph("Saida do Carro"));
            PdfPCell cel7 = new PdfPCell(new Paragraph("Entrada do Carro"));

            table.addCell(cel1);
            table.addCell(cel2);
            table.addCell(cel3);
            table.addCell(cel4);
            table.addCell(cel5);
            table.addCell(cel6);
            table.addCell(cel7);

            // tabela body
            rList = getAllDesc();

            if (rList != null) {
                msg = true;

                for (RelatorioVeiculoFuncionario r : rList) {
                    cel1 = new PdfPCell(new Paragraph(r.getId() + ""));
                    cel2 = new PdfPCell(new Paragraph(r.getVeiculo().getPlaca() + ""));
                    cel3 = new PdfPCell(new Paragraph(r.getFuncionario().getNome() + ""));
                    cel4 = new PdfPCell(new Paragraph(r.getFuncionario().getMatricula() + ""));
                    cel5 = new PdfPCell(new Paragraph(r.getVagaEstacionamento() + ""));
                    cel6 = new PdfPCell(new Paragraph(r.getSaidaVeiculo() + ""));
                    cel7 = new PdfPCell(new Paragraph(r.getEntradaVeiculo() + ""));

                    table.addCell(cel1);
                    table.addCell(cel2);
                    table.addCell(cel3);
                    table.addCell(cel4);
                    table.addCell(cel5);
                    table.addCell(cel6);
                    table.addCell(cel7);
                }

                doc.add(table);

                doc.close();
                
                if(moverPDF(arquivoPdf)==false){
                    msg = false;
                }
                
                
                
                Desktop.getDesktop().open(new File("C:/apache-tomcat-9.0.44/webapps/estacionamento/web/pdf/"+arquivoPdf));
                
                resp ="pdf/"+arquivoPdf+"";
                
            }
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

    // mover Arquivo
    public Boolean moverPDF(String relatorio){
        // Arquivo a ser movido
        File arquivo = new File(relatorio);
        Boolean msg = false;
        
        
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado");
            
        } else {
 
            // Diretorio de destino
            File diretorioDestino = new File("C:/apache-tomcat-9.0.44/webapps/estacionamento/web/pdf");

            if (!diretorioDestino.exists()) {
                diretorioDestino.mkdirs();
            }
 
            // Move o arquivo para o novo diretorio
            boolean sucesso = arquivo.renameTo(new File(diretorioDestino, arquivo.getName()));
            if (sucesso) {
                System.out.println("Arquivo movido para '" + diretorioDestino.getAbsolutePath() + "'");
                msg=true;
            } else {
                System.out.println("Erro ao mover arquivo '" + arquivo.getAbsolutePath() + "' para '"
                        + diretorioDestino.getAbsolutePath() + "'");
            }
        }
        
        return msg;
        
    }
    
  
    
    
}
