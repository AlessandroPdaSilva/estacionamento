
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="model.Usuario"%>
<%@page import="model.RelatorioVeiculoFuncionario"%>
<%@page import="dao.RelatorioVeiculoFuncionarioDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>
    
    
        <%@include file="../page/cabeçalho.jsp" %>
        
        
        <h1 class="my-3">Relatorio da Garagem</h1>

         
            
        <div class="p-2 bd-highlight"><!-- onclick="gerarPDF()"-->
            <button class="btn btn-danger mb-3" data-bs-toggle="modal" data-bs-target="#ModalGerarPdf" >
                <img src="../imagens/file-earmark-pdf.svg"> &nbsp gerar PDF
            </button>
        </div>
         

        <!-- tabela -->
        <div id="dados">
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarFrota">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Placa do carro</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Matricula</th>
                    <th scope="col">Vaga</th>
                    <th scope="col">Saida do carro</th>
                    <th scope="col">Entrada do carro</th>

                </tr>
            </thead>

            
            <tbody>

                <!-- view -->
                
                <jsp:useBean class="dao.RelatorioVeiculoFuncionarioDAO" id="rdao"/><!-- objeto -->
                <c:forEach var="r" items="${rdao.allDesc}">

                    <tr>
                        <th scope="row">${r.id}</th>
                        <td>${r.veiculo.placa}</td>
                        <td>${r.funcionario.nome}</td>
                        <td>${r.funcionario.matricula}</td>
                        <td>${r.vagaEstacionamento}</td>
                        <td>${r.saidaVeiculo}</td>
                        <td>${r.entradaVeiculo}</td>
                        
                    </tr>



                </c:forEach>


            </tbody>
            

        </table>
        </div>


        <!-- MODAL GERAR PDF-->
      <div id="ModalGerarPdf" class="modal" tabindex="-1" >
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_agendamento.do" method="POST">
                      <div class="modal-header">
                          <h5 class="modal-title">Gerar PDF</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      
                      <input name="acao" value="gerar-pdf-relatorio" hidden="">
                      <%
                          //--data
                            LocalDateTime agora = LocalDateTime.now();//data atual


                            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                            String dataFormatada = formatterData.format(agora);

                            String auxString[] = dataFormatada.split("-");

                            String data= auxString[0]+ "-" +auxString[1];
                          %> 
                       
                          
                      <!-- body form -->
                      <div class="modal-body">
                          Clique no icone abaixo, e escolha o mês e ano:<br><br>
                          <input type="month" id="mesEano" name="mesEano" min="2021-07" max="<%=data%>" value="<%=data%>">
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-primary" >Gerar</button>
                        
                      </div>
                  </form>

              </div>
          </div>
      </div>
              
                
             
        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#listarFrota").dataTable({

                    "bJQueryUI": true,
                    "oLanguage": {
                        "sProcessing": "Processandro...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "Não foram encontrados resultados",
                        "sInfo": "Mostrar _START_ até _END_ de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando de 0 até 0 de 0 requisitos",
                        "sInfoFiltered": "",
                        "sInfoPostFix": "",
                        "sSearch": "Pesquisar",
                        "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext": "Proximo",
                            "sLast": "Ultimo"
                        }
                    }
                })
            })
        </script>



        <%@include file="../page/rodape.jsp" %>
    
    <!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>
    
</c:if>



<%@include file="../page/acesso_negado.jsp" %>

    
