 
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

 
@WebServlet(name = "GerenciarLogin", urlPatterns = {"/gerenciar_login.do"})
public class GerenciarLogin extends HttpServlet {

    private static HttpServletResponse response;
 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarLogin</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarLogin at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
         String acao = request.getParameter("acao");
         
         if(acao.equals("deslogar")){
             request.getSession().removeAttribute("ulogado");
             response.sendRedirect("form_login.jsp");
         }
        
    }
 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        PrintWriter out = response.getWriter();
        
        //out.print("<h1>Estamos aqui</h1>");
         
        GerenciarLogin.response = response;
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        UsuarioDAO usuarioDao = new UsuarioDAO();
        Usuario usuario = new Usuario();
        
        try {
            usuario = usuarioDao.getUsuario(login);
            
            if(usuario.getId()>0 && usuario.getSenha().equals(senha)){
                HttpSession sessao = request.getSession();
                sessao.setAttribute("ulogado", usuario);
                response.sendRedirect("frota_de_veiculos.jsp");
                
            }else{
                out.println("<script type='text/javascript'>");
                out.println("alert('Login ou Senha invalida')");
                out.println("location.href='index.jsp'");
                out.println("</script>");
            }
            
        } catch (Exception e) {
            out.print(e);
        }
        
    }
 
    
    @Override
    public String getServletInfo() {
        return "Short description";
    } 

}
