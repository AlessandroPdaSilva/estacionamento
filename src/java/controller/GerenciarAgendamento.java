 
package controller;

import dao.AgendamentoDAO;
import dao.FuncionarioDAO;
import dao.RelatorioVeiculoFuncionarioDAO;
import dao.VeiculoDaFrotaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Agendamento;
import model.Funcionario;
import model.RelatorioVeiculoFuncionario;
import model.VeiculoDaFrota;
 
@WebServlet(name = "GerenciarAgendamento", urlPatterns = {"/gerenciar_agendamento.do"})
public class GerenciarAgendamento extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarAgendamento</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GerenciarAgendamento at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        
        
        // criar agendamento
        if (acao.equals("criar_agendamento")) {
            
            String matricula = request.getParameter("matricula");
            String placa = request.getParameter("placa");
            int vaga = Integer.parseInt((String) request.getParameter("vaga"));
            int status = 1;
            
            
            try {
                
                //-funcionario
                Funcionario f = new Funcionario();
                FuncionarioDAO fd = new FuncionarioDAO();

                f = fd.getFuncionario(matricula);
                
                //-veiculo
                VeiculoDaFrota vf = new VeiculoDaFrota();
                VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
                
                vf = vfd.getVeiculo(placa);
                
                //-vaga
                vf.setVaga(vaga);
                
                //-saida do carro
                LocalDateTime agora = LocalDateTime.now();//data atual

                
                DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                String dataFormatada = formatterData.format(agora);

                
                DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");// formatar a hora
                String horaFormatada = formatterHora.format(agora);
                
                String saida = ""+dataFormatada + " " + horaFormatada +"";
                
                // agendando
                Agendamento a = new Agendamento();
                a.setFuncionario(f);
                a.setVeiculo(vf);
                a.setSaidaVeiculo(saida);
                a.setEntradaVeiculo("0000-01-01 00:00:00");
                a.setVagaEstacionamento(vaga);
                a.setStatus(status);
                
                
                AgendamentoDAO ad = new AgendamentoDAO();
                if(ad.save(a)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Agendado com sucesso')");
                    out.println("location.href='agendamento/index.jsp'");
                    out.println("</script>");
                }else{
                     out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao agendar')");
                    out.println("location.href='agendamento/index.jsp'");
                    out.println("</script>");
                }
            
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            
            
        }
        
        // finalizar Agendamento
        if (acao.equals("finalizar")) {
            
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            try {
                
                //-entrada do carro
                LocalDateTime agora = LocalDateTime.now();//data atual

                
                DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                String dataFormatada = formatterData.format(agora);

                
                DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");// formatar a hora
                String horaFormatada = formatterHora.format(agora);
                
                String entrada = ""+dataFormatada + " " + horaFormatada +"";
                
                
                AgendamentoDAO ad = new AgendamentoDAO();
                if(ad.finalizarAgendamento(id, entrada)){
                    
                    //-salvando em relatorio
                    Agendamento a = new Agendamento();
                    a = ad.getAgendamento(id);
                    
                    RelatorioVeiculoFuncionario r = new RelatorioVeiculoFuncionario();
                    RelatorioVeiculoFuncionarioDAO rd = new RelatorioVeiculoFuncionarioDAO();
                    
                    r.setFuncionario(a.getFuncionario());
                    r.setVeiculo(a.getVeiculo());
                    r.setVagaEstacionamento(a.getVagaEstacionamento());
                    r.setSaidaVeiculo(a.getSaidaVeiculo());
                    r.setEntradaVeiculo(a.getEntradaVeiculo());
                    
                    rd.save(r);
                    
//                    if(rd.save(r)){
//                        out.println("<script type='text/javascript'>");
//                        out.println("alert('relatorio salvo')");
//                        
//                        out.println("</script>");
//                    }
                    
                    
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Finalizado com sucesso')");
                    out.println("location.href='agendamento/index.jsp'");
                    out.println("</script>");
                    
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao finalizar')");
                    out.println("location.href='agendamento/index.jsp'");
                    out.println("</script>");
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        // Deletar
        if (acao.equals("deletar")) {
            
            int id = Integer.parseInt((String) request.getParameter("id"));
            
            AgendamentoDAO a = new AgendamentoDAO();
            
            try {
                
                if(a.delete(id)){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Deletado com sucesso')");
                    out.println("location.href='agendamento/editar.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao deletar')");
                    out.println("location.href='agendamento/editar.jsp'");
                    out.println("</script>");
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
        }
        
        
        // deleta limpar
        if (acao.equals("limpar")) {
            AgendamentoDAO a = new AgendamentoDAO();
            
            try {
                
                if(a.deleteLimpar()){
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Limpado com sucesso')");
                    out.println("location.href='agendamento/index.jsp'");
                    out.println("</script>");
                }else{
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao Limpar')");
                    out.println("location.href='agendamento/index.jsp'");
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
        
        // editar
        if (acao.equals("editar")) {
            
            String matricula = request.getParameter("matricula");
            String placa = request.getParameter("placa");
            int vaga = Integer.parseInt((String) request.getParameter("vaga"));
            int id = Integer.parseInt((String) request.getParameter("id"));
            int status = 1;
            
            
            try {
                
                //-funcionario
                Funcionario f = new Funcionario();
                FuncionarioDAO fd = new FuncionarioDAO();

                f = fd.getFuncionario(matricula);
                int id_funcionario = f.getId();
                
                //-veiculo
                VeiculoDaFrota vf = new VeiculoDaFrota();
                VeiculoDaFrotaDAO vfd = new VeiculoDaFrotaDAO();
                
                vf = vfd.getVeiculo(placa);
                int id_veiculo = vf.getId();
                
                 
                
                
                AgendamentoDAO ad = new AgendamentoDAO();
                if(ad.update(id,id_veiculo,id_funcionario,vaga)){// editando
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Editado com sucesso')");
                    out.println("location.href='agendamento/editar.jsp'");
                    out.println("</script>");
                }else{
                     out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao editar')");
                    out.println("location.href='agendamento/editar.jsp'");
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
