 
package controller;

import dao.FuncionarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Funcionario;


 
@WebServlet(name = "GerenciarFuncionario", urlPatterns = {"/gerenciar_funcionario.do"})
public class GerenciarFuncionario extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarFuncionario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarFuncionario at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");   
        
        // criar veiculo
        if (acao.equals("criar_funcionario")) {
            
            String matricula = request.getParameter("matricula");
            String nome = request.getParameter("nome");
            String telefone = request.getParameter("telefone");
            
            Funcionario f = new Funcionario();
            FuncionarioDAO fd = new FuncionarioDAO();
            
            f.setMatricula(matricula);
            f.setNome(nome);
            f.setTelefone(telefone);
            
            if(fd.save(f)){
                out.println("<script type='text/javascript'>");
                out.println("alert('Criado com sucesso')");
                out.println("location.href='funcionarios/index.jsp'");
                out.println("</script>");
            }else{
                out.println("<script type='text/javascript'>");
                out.println("alert('Erro ao criar')");
                out.println("location.href='funcionarios/index.jsp'");
                out.println("</script>");
            }
            
        }
        
        
        // Deletar
        if (acao.equals("deletar")) {
            
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            FuncionarioDAO vfd = new FuncionarioDAO();
            
            try {
                
                if(vfd.delete(id)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Deletado com sucesso')");
                    out.println("location.href='funcionarios/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao deletar')");
                    out.println("location.href='funcionarios/index.jsp'");
                    out.println("</script>");
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
        
        
        // Editar page
        if (acao.equals("editar_page")) {
            
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            
            
            try {
                FuncionarioDAO fd = new FuncionarioDAO();
                Funcionario f = new Funcionario();
                
                f = fd.getFuncionario(id);
                
                RequestDispatcher disp = getServletContext().getRequestDispatcher("/funcionarios/editar_funcionario.jsp");// jogar para form
                request.setAttribute("f", f);// content
                disp.forward(request, response);// dispachar
                
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
        
        // criar veiculo
        if (acao.equals("editar")) {
            
            String matricula = request.getParameter("matricula");
            String nome = request.getParameter("nome");
            String telefone = request.getParameter("telefone");
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            FuncionarioDAO fd = new FuncionarioDAO();
            
            try {
                
                if(fd.update(id, matricula, nome, telefone)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Editado com sucesso')");
                    out.println("location.href='funcionarios/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao editar')");
                    out.println("location.href='funcionarios/index.jsp'");
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
    } 

}
