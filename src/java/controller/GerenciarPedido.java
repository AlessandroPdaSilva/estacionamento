 
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(name = "GerenciarPedido", urlPatterns = {"/gerenciar_pedido.do"})
public class GerenciarPedido extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarPedido</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarPedido at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        
        
        // aceito
        if (acao.equals("pedido_aceito")) {
            out.println("deu certo: aceito");
        }
        
        // recusado
        if (acao.equals("pedido_recusado")) {
            out.println("deu certo: recusado");
        }
        
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
    }
 
    @Override
    public String getServletInfo() {
        return "Short description";
    } 

}
