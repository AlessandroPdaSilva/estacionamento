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

        // deslogar
        if (acao.equals("deslogar")) {
            request.getSession().removeAttribute("ulogado");
            response.sendRedirect("index.jsp");
        }

        // bloqueio -criar usuario
        if (acao.equals("criar_com_senha")) {

            String senha = request.getParameter("campo");

            if (senha.equals("123")) {
                boolean l = true;
                
                HttpSession sessao = request.getSession();
                sessao.setAttribute("logado", l);
                response.sendRedirect("criar_login.jsp");
            } else {
                
                out.println("<script type='text/javascript'>");
                out.println("alert('Senha invalida')");
                out.println("location.href='index.jsp'");
                out.println("</script>");
            }
            
        }
            //bloqueio -voltar
            if (acao.equals("voltar")) {
                request.getSession().removeAttribute("logado");
                response.sendRedirect("index.jsp");
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
                    response.sendRedirect("frota_de_veiculos/index.jsp");

                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Login ou Senha invalida')");
                    out.println("location.href='index.jsp'");
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

            UsuarioDAO ud = new UsuarioDAO();
            Usuario u = new Usuario();

            senha = ud.criptografar(senha);

            u.setNome(login);
            u.setSenha(senha);
            
            if(ud.save(u)){
                out.println("<script type='text/javascript'>");
                out.println("alert('Criado com sucesso')");
                out.println("location.href='index.jsp'");
                out.println("</script>");
            }else{
                out.println("<script type='text/javascript'>");
                out.println("alert('Erro ao criar')");
                out.println("location.href='index.jsp'");
                out.println("</script>");
            }
            
            
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
