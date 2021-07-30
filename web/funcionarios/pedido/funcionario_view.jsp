
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="dao.RelatorioChaveFuncionarioDAO"%>
<%@page import="dao.PedidoDAO"%>
<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>
    
    
        <%@include file="../../page/cabeçalho_sub.jsp" %>
        <h1 class="my-3">Meus Pedidos</h1>

          
         
         <!-- botoes -->
         <%@include file="permissao_botoes.jsp" %>
 
        
        
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarPedido">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Veiculo </th>
                    <th scope="col">Data do Pedido</th>
                    <th scope="col">Percurso</th>
                    <th scope="col">Status</th>
                    <th scope="col">Opcoes</th>


                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.PedidoDAO" id="pdao"/><!-- objeto -->
                <jsp:useBean class="dao.RelatorioChaveFuncionarioDAO" id="rcdao"/><!-- objeto -->
                <c:forEach var="p" items="${pdao.getPedido(ulogado.funcionario.id)}">


                    <tr>
                        <th scope="row">${p.id}</th>
                        <td>${p.funcionario.nome}</td>
                        <td>${p.veiculo.placa}</td>
                        <td>${p.dataPedido}</td>
                        <td>${p.percurso}</td>
                        
                        
                        <td>
                            <c:if test="${p.status==0}"> 
                                <div style="color:#198754;font-weight:750"> Concedido </div>  
                            </c:if>

                            <c:if test="${p.status==1}"> 
                                <div style="color:crimson;font-weight:750"> Recusado </div>  
                            </c:if>

                            <c:if test="${p.status==2}"> 
                                <!--
                                <div style="color:black;background:#ffca2c;text-align: center;"> Em aberto </div>  
                                -->
                                <div style="color:#ffca2c;font-weight:750"> Em aberto </div>  
                            </c:if>
                        </td>
                        
                        <td>
                            <c:if test="${p.status==1}">
                                <button onclick="abrirMensagem(${p.id},'${p.mensagem}')" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#ModalMensage">
                                    <img src="../../imagens/chat-dots.svg">
                                </button>
                            </c:if>
                        </td>
                    </tr>



                </c:forEach>


            </tbody>


        </table>


    <jsp:useBean class="dao.FuncionarioDAO" id="fdao"/><!-- objeto -->
    <jsp:useBean class="dao.VeiculoDaFrotaDAO" id="vdao"/><!-- objeto -->
    
      <!-- MODAL CRIAR PEDIDO-->
      <div id="ModalCriarPedido" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_pedido.do" method="POST">
                      <div class="modal-header">
                          <h5 class="modal-title">Criar Novo Pedido de Veiculo</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <input name="acao" value="criar_pedido" hidden="">   
                          
                          <input class="form-control" type="text" value="${fdao.getFuncionario(ulogado.funcionario.id).nome}" aria-label="Disabled input example" disabled readonly>
                          <input name="id_funcionario" value="${ulogado.funcionario.id}" hidden="">

                          <br>
                          
                          <label style="font-weight: bold " for='exampleFormControlTextarea1' class='form-label'> Motivo da Solicitação</label>
                          <textarea name="solicitacao" placeholder="Ex: Vou organizar o evento 'Celebração do rock na Torre de TV'" class='form-control' id='exampleFormControlTextarea1' rows='2' ></textarea>
                          
                          <br>
                          
                          <%
                          //--data
                            LocalDateTime agora = LocalDateTime.now();//data atual


                            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("uuuu-MM-dd");// formatar a data
                            String dataFormatada = formatterData.format(agora);


                            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");// formatar a hora
                            String horaFormatada = formatterHora.format(agora);

                            String data = ""+dataFormatada + "T" + horaFormatada +"";

                            
                          %> 
                          
                          <div style="font-weight: bold "> Data para usar veiculo:</div>
                          <input name="dataParaUso" value="<%= data%>" type="datetime-local" class="form-control">
                          
                            

                          <div id="resp2">
                                 
                          </div>

                          
                          
                          <br>
                          <div style="font-weight: bold ">Percurso</div>
                          <div class="mb-3 row">
                            <label for="inputPassword" class="col-sm-2 col-form-label">Origem:</label>
                            <div class="col-sm-10">
                              <input class="form-control" type="text" value="SETUR" aria-label="Disabled input example" disabled readonly>
                          
                            </div>
                          </div>
                          <div class="mb-3 row">
                            <label for="inputPassword" class="col-sm-2 col-form-label">Destino:</label>
                            <div class="col-sm-10">
                              <input name="percurso" type="text" class="form-control" id="floatingPassword" placeholder="Ex: Torre de TV">
                            </div>
                          </div>
                           
                                               </div>
                      <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <button type="submit" class="btn btn-primary">Salvar</button>
                      </div>
                  </form>

              </div>
          </div>
      </div>
        
    <!-- MODAL DELETAR PEDIDO-->
      <div id="ModalDeletarPedido" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_pedido.do" method="POST">

                      <input name="acao" value="deletar_pedido" hidden="">  
                      <input name="id_funcionario" value="${ulogado.funcionario.id}" hidden="">

                      <div class="modal-header">
                          <h5 class="modal-title">Deletar Pedido</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          Deseja realmente DELETAR o ultimo pedido feito? <br><br>

                          Obs: So é possivel deletar pedidos com status 'em aberto'.<br>
                          &nbsp So e possivel criar pedidos se na tabela 'chave / funcionario' seu status estiver como 'devolvido'.<br>
                          &nbsp So e possivel criar pedidos caso seu pedido ja estiver sido avalidado (Ex: pedido recusado)<br>

                      <div class="modal-footer">
                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <button type="submit" class="btn btn-danger">Deletar</button>
                      </div>
                  </form>

              </div>
          </div>
      </div>
      </div>

        <!-- MODAL MENSAGEM-->
      <div id="ModalMensage" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_pedido.do" method="POST">
                      <div class="modal-header">
                          <h5 class="modal-title">Mensagem</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="resp-mensagem">  </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                         <div id="resp1">  </div>
                      </div>
                  </form>

              </div>
          </div>
      </div>
      






    <!-- javaScript -->
    <script type="text/javascript">
        

     

    
    function abrirMensagem(id,texto){
                var resp = document.getElementById('resp-mensagem');
                  
                  
                if(texto == " "){
                    resp.innerHTML = " Não há mensagens"
                }else{
                resp.innerHTML = "<div class='mb-3'>"
                                +"<label for='exampleFormControlTextarea1' class='form-label'>Pedido Nº "+id+"</label>"
                                +"<textarea class='form-control' id='exampleFormControlTextarea1' rows='5' disabled readonly>"+texto+"</textarea>"
                                +"</div>";
                        
                }        
                 
            }
                      


    </script>



    <script type="text/javascript" src="datatables/jquery.js"></script>
    <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#listarPedido").dataTable({

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



    <%@include file="../../page/rodape.jsp" %>

<!-- Permissao fim-->
    <%@include file="../../page/sem_permissao.jsp" %>
    
</c:if>



<%@include file="../../page/acesso_negado.jsp" %>

    
