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
import model.Funcionario;
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

        // deslogar
        if (acao.equals("deslogar")) {
            request.getSession().removeAttribute("ulogado");
            response.sendRedirect("sign-in/index.jsp");
        }

        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");

        // logar
        if (acao.equals("logar")) {
            GerenciarLogin.response = response;
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");

            UsuarioDAO ud = new UsuarioDAO();
            Usuario u = new Usuario();
            
            senha = ud.criptografar(senha);

            try {
                u = ud.getUsuario(login);

                if (u.getId() > 0 && u.getSenha().equals(senha)) {
                    
                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("ulogado", u);
                    
                    // se funcionario
                    if(u.getNivel()==4){
                        response.sendRedirect("funcionarios/pedido/funcionario_view.jsp");
                    }else{
                        response.sendRedirect("relatorio/index.jsp");
                    }
                    
                    
                    
                    
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Login ou Senha invalida')");
                    out.println("location.href='sign-in/index.jsp'");
                    out.println("</script>");
                }

            } catch (Exception e) {
                out.print(e);
            }

        }
        
        
        // criar
        if (acao.equals("criar")) {

            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            int nivel = Integer.parseInt((String) request.getParameter("nivel"));
            int idFuncionario = Integer.parseInt((String) request.getParameter("id-funcionario"));
             
            
            UsuarioDAO ud = new UsuarioDAO();
            Usuario u = new Usuario();

            senha = ud.criptografar(senha);

            u.setNome(login);
            u.setSenha(senha);
            u.setNivel(nivel);
            
            Funcionario f = new Funcionario();//funcionario
            f.setId(idFuncionario);
            
            u.setFuncionario(f);
            
            if(ud.save(u)){
                out.println("<script type='text/javascript'>");
                out.println("alert('Criado com sucesso')");
                out.println("location.href='relatorio/index.jsp'");
                out.println("</script>");
            }else{
                out.println("<script type='text/javascript'>");
                out.println("alert('Erro ao criar')");
                out.println("location.href='relatorio/index.jsp'");
                out.println("</script>");
            }
            
            
        }
        
        if (acao.equals("excluir-usuario")) {
            int idUsuario = Integer.parseInt((String) request.getParameter("id"));
            
            
            try {
                
                
                UsuarioDAO ud = new UsuarioDAO();
            
                if(ud.delete(idUsuario)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Excluido com sucesso')");
                    out.println("location.href='relatorio/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao excluir')");
                    out.println("location.href='relatorio/index.jsp'");
                    out.println("</script>");
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            
        }
        
        
        if (acao.equals("editar-usuario")) {
            int idUsuario = Integer.parseInt((String) request.getParameter("id"));
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            
            if(senha != ""){
                
                
                try {
                    
                UsuarioDAO ud = new UsuarioDAO();
            
                    if(ud.update(idUsuario,login,senha)){
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Editado com sucesso')");
                        out.println("location.href='sign-in/lista-de-usuarios.jsp'");
                        out.println("</script>");
                    }else{
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Erro ao editar')");
                        out.println("location.href='sign-in/lista-de-usuarios.jsp'");
                        out.println("</script>");
                    }

                
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                
                try {
                    
                UsuarioDAO ud = new UsuarioDAO();
            
                    if(ud.update(idUsuario,login)){
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Editado com sucesso')");
                        out.println("location.href='sign-in/lista-de-usuarios.jsp'");
                        out.println("</script>");
                    }else{
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Erro ao editar')");
                        out.println("location.href='sign-in/lista-de-usuarios.jsp'");
                        out.println("</script>");
                    }

                
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                
            }
            
            
            
            
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
