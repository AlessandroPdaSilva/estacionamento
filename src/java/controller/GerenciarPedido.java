package controller;

import dao.FuncionarioDAO;
import dao.PedidoDAO;
import dao.RelatorioChaveFuncionarioDAO;
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
import model.Funcionario;
import model.Pedido;
import model.RelatorioChaveFuncionario;
import model.VeiculoDaFrota;

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

            int id = Integer.parseInt((String) request.getParameter("id"));
            int status = Integer.parseInt((String) request.getParameter("status"));
            String placa = request.getParameter("placa");
            
            PedidoDAO pd = new PedidoDAO();
            Pedido p = new Pedido();

            try {
                
                
                p = pd.getPedidoId(id);

                if (pd.updateStatus(id, status)) {
                   
                    // jogando para relatorio
                    RelatorioChaveFuncionarioDAO rd = new RelatorioChaveFuncionarioDAO();
                    RelatorioChaveFuncionario r = new RelatorioChaveFuncionario();
                    
                    //--pedido
                    
                    
                    r.setPedido(p);
                    
                     
                    
                    //--odometro e Veiculo
                    
                    VeiculoDaFrotaDAO vd = new VeiculoDaFrotaDAO();
                    VeiculoDaFrota v = new VeiculoDaFrota();
                    v = vd.getVeiculo(placa);
                    
                    
                    r.setOdometroColeta(v.getOdometro());
                    r.setVeiculo(v);
                    
                     
                    // salvar
                    rd.save(r);
                     
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Aceito com sucesso')");
                    out.println("location.href='funcionarios/pedido/gerente_view.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao aceitar')");
                    out.println("location.href='funcionarios/pedido/gerente_view.jsp'");
                    out.println("</script>");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // recusado
        if (acao.equals("pedido_recusado")) {
            int id = Integer.parseInt((String) request.getParameter("id"));
            int status = Integer.parseInt((String) request.getParameter("status"));
            String mensagem = request.getParameter("mensagem");

            try {
                PedidoDAO pd = new PedidoDAO();

                if (mensagem.indexOf("\n") > -1) {// se pressionar enter
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro. Nao quebre linha no campo de mensagem')");
                    out.println("location.href='funcionarios/pedido/gerente_view.jsp'");
                    out.println("</script>");

                } else {

                    if (pd.updateStatus(id, status)) {

                        if (mensagem != "") {//se nao estiver vazio
                            pd.updateMensagem(id, mensagem);
                        }

                        out.println("<script type='text/javascript'>");
                        out.println("alert('Recusado com sucesso')");
                        out.println("location.href='funcionarios/pedido/gerente_view.jsp'");
                        out.println("</script>");
                    } else {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Erro ao recusar')");
                        out.println("location.href='funcionarios/pedido/gerente_view.jsp'");
                        out.println("</script>");
                    }

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

        // criar_pedido
        if (acao.equals("criar_pedido")) {
            //var
            String percurso = request.getParameter("percurso");

            int id_funcionario = Integer.parseInt((String) request.getParameter("id_funcionario"));
            String solicitacao = request.getParameter("solicitacao");
            String dataParaUso = request.getParameter("data_para_uso");

            String aux[] = dataParaUso.split("T");
            dataParaUso = aux[0] + " " + aux[1];

            PedidoDAO pd = new PedidoDAO();

            if (solicitacao.indexOf("\n") > -1 || percurso.indexOf("\n") > -1) {// se pressionar enter
                out.println("<script type='text/javascript'>");
                out.println("alert(' Erro. Nao quebre linha no campo de solicitacao')");
                out.println("location.href='funcionarios/pedido/funcionario_view.jsp'");
                out.println("</script>");

            } else {

                try {

                    //-funcionario
                    Funcionario f = new Funcionario();
                    FuncionarioDAO fd = new FuncionarioDAO();

                    f.setId(id_funcionario);

                    //-data pedido
                    LocalDateTime agora = LocalDateTime.now();//data atual

                    DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                    String dataFormatada = formatterData.format(agora);

                    DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");// formatar a hora
                    String horaFormatada = formatterHora.format(agora);

                    String dataPedido = "" + dataFormatada + " " + horaFormatada + "";

                    //p
                    Pedido p = new Pedido();

                    p.setFuncionario(f);
                    p.setPercurso("setur/" + percurso);
                    p.setDataPedido(dataPedido);

                    //solicitacao
                    p.setSolicitacao(solicitacao);

                    // data para uso
                    p.setDataParaUso(dataParaUso);

                    if (pd.save(p)) {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Pedido feito com sucesso')");
                        out.println("location.href='funcionarios/pedido/funcionario_view.jsp'");
                        out.println("</script>");
                    } else {
                        out.println("<script type='text/javascript'>");
                        out.println("alert('Erro ao criar pedido')");
                        out.println("location.href='funcionarios/pedido/funcionario_view.jsp'");
                        out.println("</script>");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        // deletar_pedido
        if (acao.equals("deletar_pedido")) {
            int id_funcionario = Integer.parseInt((String) request.getParameter("id_funcionario"));

            try {

                PedidoDAO pd = new PedidoDAO();

                if (pd.delete(id_funcionario)) {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Pedido deletado com sucesso')");
                    out.println("location.href='funcionarios/pedido/funcionario_view.jsp'");
                    out.println("</script>");
                } else {
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Erro ao deletar pedido')");
                    out.println("location.href='funcionarios/pedido/funcionario_view.jsp'");
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
