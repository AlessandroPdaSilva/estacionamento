
<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="../permissao.jsp" %>
    
    
        <%@include file="../../page/cabeçalho_sub.jsp" %>
        <h1 class="my-3">Pedido de Funcionarios</h1>


         
 
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
                    

                    <th scope="col">Opções</th>

                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.PedidoDAO" id="pdao"/><!-- objeto -->
                <c:forEach var="p" items="${pdao.all}">


                    <tr>
                        <th scope="row">${p.id}</th>
                        <td>
                            ${p.funcionario.nome} &nbsp
                              <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                &nbsp Matricula: ${p.funcionario.matricula}
                              </ul>
                        </td>
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
                            
                            <c:if test="${p.status==2}">
                                <a onclick="confirmarAceito(${p.id},'${p.funcionario.nome}','${p.veiculo.placa}',0 )" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#ModalConfirmarAceito">
                                    <img src="../../imagens/hand-thumbs-up.svg">
                                </a>
                                <a onclick="confirmarRecusado(${p.id},'${p.funcionario.nome}','${p.veiculo.placa}',1 )" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#ModalConfirmarRecusado">
                                    <img src="../../imagens/hand-thumbs-down.svg">
                                </a>
                            </c:if>
                        </td>
                    </tr>



                </c:forEach>


            </tbody>


        </table>





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

                          <div id="resp">  </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                         <div id="resp1">  </div>
                      </div>
                  </form>

              </div>
          </div>
      </div>
      
      <!-- MODAL CONFIRMAR ACEITO-->
      <div id="ModalConfirmarAceito" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="" method="">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-aceito">  </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <div id="resp-aceito">  </div>
                      </div>
                  </form>

              </div>
          </div>
      </div>
      
    <!-- MODAL CONFIRMAR RECUSADO-->
      <div id="ModalConfirmarRecusado" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_pedido.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">
                          

                          <div id="body-recusado">  </div>
                          <!-- colapse -->
                          <br>
                          <button class="btn btn-outline-dark" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                            colocar uma mensagem
                          </button>
                          <div class="collapse" id="collapseExample">
                            <div class="card card-body">
                              
                             <div class='mb-3'> 
                                 <label for='exampleFormControlTextarea1' class='form-label'> Digite aqui</label> 
                                 <textarea name="mensagem" class='form-control' id='exampleFormControlTextarea1' rows='5' > </textarea> 
                             </div> 
                            
                            </div>
                          </div>
                          
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <button type="submit" class="btn btn-danger">Confirmar</button>
                      </div>
                  </form>

              </div>
          </div>
      </div>
      




        <!-- javaScript -->
        <script type="text/javascript">
            function confirmarRecusado(id, nome,placa,status) {
                var resp = document.getElementById('resp-recusado');
                var body = document.getElementById('body-recusado');
                
                body.innerHTML = "\nDeseja <span style='color:crimson;font-weight:750'>RECUSAR</span> o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?"
                 +"<input name='acao' value='pedido_recusado' hidden>"
                 +"<input name='id' value='"+id+"' hidden>"
                 +"<input name='status' value='1' hidden>"
                 +"<input name='placa' value='"+placa+"' hidden>"
                    
                /*
                 * resp.innerHTML = '<a href="/estacionamento/gerenciar_pedido.do?acao=pedido_recusado&id=' + id +'&status=1" class="btn btn-danger" >Confirmar</a>'
                                +''
                 * 
                 * 
                if (confirm("\nDeseja RECUSAR o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?" )) {
                    location.href = "/estacionamento/gerenciar_pedido.do?acao=pedido_recusado&id=" + id +"&status=1";
                }
                */
            }
            
            function confirmarAceito(id, nome,placa,status) {
                
                var resp = document.getElementById('resp-aceito');
                var body = document.getElementById('body-aceito');
                
                body.innerHTML = "\nDeseja <span style='color:#198754;font-weight:750'>ACEITAR</span> o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?"
                resp.innerHTML = '<a href="/estacionamento/gerenciar_pedido.do?acao=pedido_aceito&id=' + id +'&status=0" class="btn btn-success" >Confirmar</a>'
               
                /*
                if (confirm("\nDeseja ACEITAR o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?" )) {
                    location.href = "/estacionamento/gerenciar_pedido.do?acao=pedido_aceito&id=" + id +"&status=0";
                }
                */
                
            }
            
            function abrirMensagem(id,texto){
                var resp = document.getElementById('resp');
                 
                 
                if(texto == " "){
                    resp.innerHTML = " Não há mensagens"
                }else{
                resp.innerHTML = "<div class='mb-3'>"
                                +"<label for='exampleFormControlTextarea1' class='form-label'>Pedido Nº "+id+"</label>"
                                +"<textarea class='form-control' id='exampleFormControlTextarea1' rows='5' disabled readonly >"+texto+"</textarea>"
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

    
