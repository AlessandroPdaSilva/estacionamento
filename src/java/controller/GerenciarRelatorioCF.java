  
package controller;

import dao.RelatorioChaveFuncionarioDAO;
 
import dao.VeiculoDaFrotaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 
import model.RelatorioChaveFuncionario;
@WebServlet(name = "GerenciarRelatorioCF", urlPatterns = {"/gerenciar_relatorio_cf.do"})
public class GerenciarRelatorioCF extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarRelatorioCF</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarRelatorioCF at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");

        // chave coletada
        if (acao.equals("chave_coletada")) {
            
            int idPedido = Integer.parseInt((String) request.getParameter("id_pedido"));
            int status = 1;
            
            RelatorioChaveFuncionarioDAO rd = new RelatorioChaveFuncionarioDAO();
            
            try {
                
                if(rd.updateColetado(idPedido, status)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Coletado com sucesso')");
                    out.println("location.href='funcionarios/chave-funcionario/gerente_view.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao coletar')");
                    out.println("location.href='funcionarios/chave-funcionario/gerente_view.jsp'");
                    out.println("</script>");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        // chave devolvida
        if (acao.equals("chave_devolvido")) {
            
            int idPedido = Integer.parseInt((String) request.getParameter("id_pedido"));
            int idVeiculo = Integer.parseInt((String) request.getParameter("id_veiculo"));
            int odometroDevolvido = Integer.parseInt((String) request.getParameter("odometro"));
            int odometroColeta = Integer.parseInt((String) request.getParameter("odometro_coleta"));
            int status = 0;
            
            RelatorioChaveFuncionarioDAO rd = new RelatorioChaveFuncionarioDAO();
            
            try {
                
                if(odometroDevolvido>odometroColeta){
                        //--data
                        LocalDateTime agora = LocalDateTime.now();//data atual


                        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                        String dataFormatada = formatterData.format(agora);


                        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");// formatar a hora
                        String horaFormatada = formatterHora.format(agora);

                        String dataDevolucao = ""+dataFormatada + " " + horaFormatada +"";



                        if(rd.updateDevolvido(idPedido, dataDevolucao, odometroDevolvido, status)){
                            
                            // precisa atualizar
                            VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                            vd.modificarOdometro(idVeiculo, odometroDevolvido);
                            
                            out.println("<script type='text/javascript'>");
                            out.println("alert('Devolvido com sucesso')");
                            out.println("location.href='funcionarios/chave-funcionario/gerente_view.jsp'");
                            out.println("</script>");
                        }else{
                            out.println("<script type='text/javascript'>");
                            out.println("alert('Erro ao devolver')");
                            out.println("location.href='funcionarios/chave-funcionario/gerente_view.jsp'");
                            out.println("</script>");
                        }
                        
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro. O valor do odometro digitado e menor do que o valor de odometro original')");
                    out.println("location.href='funcionarios/chave-funcionario/gerente_view.jsp'");
                    out.println("</script>");
                }
                
                
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");

        
        // Relatorio Garagem
        if (acao.equals("gerar-pdf-relatorio")) {
            //data input
            String mesano = request.getParameter("mesEano");
            String auxMesAno[] = mesano.split("-");
            int mes = Integer.parseInt(auxMesAno[1]);
            int ano = Integer.parseInt(auxMesAno[0]);

             
            
            
            try {
                
                RelatorioChaveFuncionarioDAO rd = new RelatorioChaveFuncionarioDAO();
                
                ArrayList<RelatorioChaveFuncionario> r = new ArrayList<>();
                
                
                
                r = rd.getAllDescPdf(mes, ano);
                
                String texto = ""; 
                
                // setando tabela
                for(RelatorioChaveFuncionario aux:r){ 
                    
                    int coleta = aux.getOdometroColeta();
                    int devolucao = aux.getOdometroDevolucao();
                    int statusAux = aux.getStatus();
                     
                    String odometroColeta = "";
                    String odometroDevolucao = "";
                    String status = "";
                    
                    if(coleta==-1){// coleta
                        odometroColeta = "não coletado";
                    }else{
                        odometroColeta = Integer.toString(coleta);
                    }
                    
                    if(devolucao==-1){// devolucao
                        odometroDevolucao = "não devolvido";
                    }else{
                        odometroDevolucao = Integer.toString(devolucao);
                    }
                    
                    if(statusAux==0){// status
                        status = "Devovido";
                    }else if(statusAux==1){
                        status = "Em uso";
                    }else if(statusAux==2){
                        status = "Nao coletado";
                    }
                    
                    
                    texto += ""+
    "                            janela.document.write(''\n" +
    "                                    \n" +
    "                                    +''\n" +
    "                                    +'<tr>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\" scope=\"row\"> "+aux.getPedido().getId()+" </td>'\n" +
 

    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getPedido().getFuncionario().getNome()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getPedido().getFuncionario().getMatricula()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getVeiculo().getPlaca()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getDataColeta()+" </td>'\n" +
    "                                   +'<td style=\"border: 1px solid black;\"> "+aux.getDataDevolucao()+" </td>'\n" +
    "                                   +'<td style=\"border: 1px solid black;\"> "+odometroColeta+"Km /  "+odometroDevolucao+"  </td>'\n" + 
    "                                   +'<td style=\"border: 1px solid black;\">"+status+"</td>'\n" +
    "                                    +'</tr>'\n" +
    "                                    +''\n" +
    "                                    \n" +
    "                                    +'')\n" +
    "                                   \n" +
    "                            " ;
                    
                }
                
                
                //verificando se ha registros
                if(texto==""){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Nao ha registros para esse mês')");
                    out.println("location.href='relatorio/index.jsp'");
                    out.println("</script>");
                }else{
                    
                    // gerando html
                    out.println("<br><h1 style=\"display: flex;flex-direction: row;justify-content: center;\">PDF gerado com sucesso!!</h1>");
                    
                    out.println("<div style=\"display: flex;flex-direction: row;justify-content: center;\">");
                    out.println("<p> Obs: Caso o PDF não tenha sido gerado, verifique se os pop ups estão bloqueados</p>");
                    out.println("</div>");
                     
                    out.println("<div style=\"display: flex;flex-direction: row;justify-content: center;\">");
                    out.println("<a href='/estacionamento/funcionarios/chave-funcionario/gerente_view.jsp'> Voltar</a>");
                    out.println("</div>");
                    
                    
                    
                    
                    // gerando pop up
                    out.println("<script type='text/javascript'>");

                    out.println("" +

    "                            onload = function(){"+                             
    "                            var janela = open( '  ' ,  '  ' ,'width=800,heigth=600')\n" +
    "                            janela.document.write('<input type=\"text\" hidden=\"\" >')\n" +
    "                            janela.document.write('</head>')\n" +
    "                            janela.document.write('<body>')\n" +
    "                            \n" +
    "                        \n" +
    "                            \n" +
    "                            janela.document.write('<h1 style=\"display: flex;flex-direction: row;justify-content: center;\">Relatorio de Chaves Concedidas</h1>');\n" +
    "                            janela.document.write('<div style=\"display: flex;flex-direction: row;justify-content: center;\">')\n" +
    "                            janela.document.write('<table style=\"border-collapse: collapse;\" cellspacing=\"0\" cellpadding=\"6\" border-spacing=\"0\">')\n" +
    "                            janela.document.write(''+'<thead>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">ID</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Funcionario</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Matricula</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Placa</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Data de coleta</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Data de devolucao</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Odometro</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Status</th>'\n" +
    "                                    +'</thead>');\n" +
    "                            janela.document.write('<tbody>')\n" );

                         
                        out.println(texto);
                        

                        out.println(
        "                                \n" +
        "                                janela.document.write('</tbody>')\n" +
        "                                janela.document.write('</table>')\n" +
        "                                janela.document.write('</div>')\n" +
        "                            janela.document.write('</body></html>')\n" +
        "                            janela.document.close()\n" +
        "                            janela.print()" +
                        "};");
                        
                        
                         
                        
                        out.println("</script>");
                    
                    }
                
                   
                
            } catch (Exception e) {
                e.printStackTrace();
            }
           
            
        }
    }
 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
