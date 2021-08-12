 
package controller;

import dao.AgendamentoDAO;
import dao.FuncionarioDAO;
import dao.RelatorioVeiculoFuncionarioDAO;
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
        
        // Relatorio Garagem
        if (acao.equals("gerar-pdf-relatorio")) {
            //data input
            String mesano = request.getParameter("mesEano");
            String auxMesAno[] = mesano.split("-");
            int mes = Integer.parseInt(auxMesAno[1]);
            int ano = Integer.parseInt(auxMesAno[0]);

             
            
            
            try {
                
                RelatorioVeiculoFuncionarioDAO rd = new RelatorioVeiculoFuncionarioDAO();
                
                ArrayList<RelatorioVeiculoFuncionario> r = new ArrayList<>();
                
                
                
                r = rd.getAllDescPdf(mes, ano);
                
                String texto = ""; 
                
                // setando tabela
                for(RelatorioVeiculoFuncionario aux:r){ 
                    
                    aux.getId();
                    texto += ""+
    "                            janela.document.write(''\n" +
    "                                    \n" +
    "                                    +''\n" +
    "                                    +'<tr>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\" scope=\"row\"> "+aux.getId()+" </td>'\n" +
 
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getVeiculo().getPlaca()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getFuncionario().getNome()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getFuncionario().getMatricula()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getVagaEstacionamento()+" </td>'\n" +
    "                                    +'<td style=\"border: 1px solid black;\"> "+aux.getSaidaVeiculo()+" </td>'\n" +
     "                                   +'<td style=\"border: 1px solid black;\"> "+aux.getEntradaVeiculo()+" </td>'\n" +
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
                    out.println("<a href='/estacionamento/relatorio/index.jsp'> Voltar</a>");
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
    "                            janela.document.write('<h1 style=\"display: flex;flex-direction: row;justify-content: center;\">Relatorio da Garagem</h1>');\n" +
    "                            janela.document.write('<div style=\"display: flex;flex-direction: row;justify-content: center;\">')\n" +
    "                            janela.document.write('<table style=\"border-collapse: collapse;\" cellspacing=\"0\" cellpadding=\"6\" border-spacing=\"0\">')\n" +
    "                            janela.document.write(''+'<thead>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">ID</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Placa do carro</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Nome</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Matricula</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Vaga</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Saida do carro</th>'\n" +
    "                                    +'<th style=\"border: 1px solid black;\" scope=\"col\">Entrada do carro</th>'\n" +
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
    } 

}
