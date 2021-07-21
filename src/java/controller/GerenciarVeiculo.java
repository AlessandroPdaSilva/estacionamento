
package controller;

import dao.VeiculoDaFrotaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.VeiculoDaFrota;

@WebServlet(name = "GerenciarVeiculo", urlPatterns = {"/gerenciar_veiculo.do"})
public class GerenciarVeiculo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarVeiculo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarVeiculo at " + request.getContextPath() + "</h1>");
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
        if (acao.equals("criar_veiculo")) {
            
            String modelo = request.getParameter("modelo");
            String placa = request.getParameter("placa");
            int vaga = Integer.parseInt((String) request.getParameter("vaga"));
            int odometro = Integer.parseInt((String) request.getParameter("odometro"));
            
            VeiculoDaFrota vf = new VeiculoDaFrota();
            VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
            
            vf.setModelo(modelo);
            vf.setPlaca(placa);
            vf.setVaga(vaga);
            vf.setOdometro(odometro);
            
            if(vfd.save(vf)){
                out.println("<script type='text/javascript'>");
                out.println("alert('Criado com sucesso')");
                out.println("location.href='frota_de_veiculos/index.jsp'");
                out.println("</script>");
            }else{
                out.println("<script type='text/javascript'>");
                out.println("alert('Erro ao criar')");
                out.println("location.href='frota_de_veiculos/index.jsp'");
                out.println("</script>");
            }
            
        }
        
        
        // Deletar
        if (acao.equals("deletar")) {
            
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
            
            try {
                
                if(vfd.delete(id)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Deletado com sucesso')");
                    out.println("location.href='frota_de_veiculos/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao deletar')");
                    out.println("location.href='frota_de_veiculos/index.jsp'");
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
                VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
                VeiculoDaFrota vf = new VeiculoDaFrota();
                
                vf = vfd.getVeiculo(id);
                
                RequestDispatcher disp = getServletContext().getRequestDispatcher("/frota_de_veiculos/editar_veiculo.jsp");// jogar para form
                request.setAttribute("vf", vf);// content
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
        
        // editar veiculo
        if (acao.equals("editar")) {
            
            String modelo = request.getParameter("modelo");
            String placa = request.getParameter("placa");
            int vaga = Integer.parseInt((String) request.getParameter("vaga"));
            int id = Integer.parseInt((String) request.getParameter("id"));
            int odometro = Integer.parseInt((String) request.getParameter("odometro"));
            
            VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
            
            try {
                
                if(vfd.update(id, placa, modelo, vaga,odometro)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Editado com sucesso')");
                    out.println("location.href='frota_de_veiculos/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao editar')");
                    out.println("location.href='frota_de_veiculos/index.jsp'");
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
