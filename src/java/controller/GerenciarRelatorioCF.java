  
package controller;

import dao.RelatorioChaveFuncionarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
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
         
    }
 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
