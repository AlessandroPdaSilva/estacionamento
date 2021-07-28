
<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="../permissao.jsp" %>
    
    
        <%@include file="../../page/cabe�alho_sub.jsp" %>
        <h1 class="my-3">Chave / Funcionario</h1>

         
         
 
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarPedido">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Data de Coleta</th>
                    <th scope="col">Data de Devolu��o</th>
                    <th scope="col">Odometro</th>
                    <th scope="col">Status</th>
                    <th scope="col">Opcoes</th>


                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.RelatorioChaveFuncionarioDAO" id="rfdao"/><!-- objeto -->
                <c:forEach var="r" items="${rfdao.all}">


                    <tr>
                        <th scope="row">${r.pedido.id}</th>
                        <td>
                            ${r.pedido.funcionario.nome} &nbsp
                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                  &nbsp Matricula: ${r.pedido.funcionario.matricula} <br>
                                  &nbsp Placa: ${r.pedido.veiculo.placa} 
                              </ul>
                        </td>
                         
                        <td>
                            ${r.dataColeta}
                        </td>
                        <td>${r.dataDevolucao}</td>
                        
                        <td>
                            ${r.odometroColeta}Km / <c:if test="${r.odometroDevolucao!=-1}">${r.odometroDevolucao}Km</c:if> 
                        </td>
                         
                         
                        <td>
                        <c:if test="${r.status==0}"> 
                                <div style="color:#198754;font-weight:750"> Devolvido </div>  
                            </c:if>

                            <c:if test="${r.status==1}"> 
                                <div style="color:crimson;font-weight:750"> Em uso </div>  
                            </c:if>

                            <c:if test="${r.status==2}"> 
                                <!--
                                <div style="color:black;background:#ffca2c;text-align: center;"> Em aberto </div>  
                                -->
                                <div style="color:#ffca2c;font-weight:750"> Nao coletado </div>  
                            </c:if>
                            
                        </td>
                        <td>
                        <c:if test="${r.status==2}">
                            <a onclick="confirmarColetado(${r.pedido.id},'${r.pedido.funcionario.nome}','${r.pedido.veiculo.placa}' )" data-bs-toggle="modal" data-bs-target="#ModalConfirmarColetado" class="btn btn-primary mb-3">
                                Coletado
                            </a>
                        </c:if>
                        <c:if test="${r.status==1}">
                            <a onclick="confirmarDevolvido(${r.pedido.id},'${r.pedido.funcionario.nome}','${r.pedido.veiculo.placa}' )" data-bs-toggle="modal" data-bs-target="#ModalConfirmarDevolvido" class="btn btn-success mb-3">
                                Devolvido
                            </a>
                        </c:if>
                        </td>
                        
                    </tr>



                </c:forEach>


            </tbody>


        </table>


 
                
       <!-- MODAL CONFIRMAR COLETADO-->
      <div id="ModalConfirmarColetado" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_relatorio_cf.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirma��o</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-coletado">  </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-danger">Confirmar</button>   
                      </div>
                  </form>

              </div>
          </div>
      </div>
      
       <!-- MODAL CONFIRMAR DEVOLVIDO-->
      <div id="ModalConfirmarDevolvido" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_relatorio_cf.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirma��o</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-devolvido">  </div>
                          <br>
                          Antes de finalizar, coloque o valor do odometro:
                          <div class="form-floating">
                              <input name="odometro" type="number" class="form-control" id="floatingPassword" placeholder="Password" required="">
                                <label for="floatingPassword">od�metro</label>
                            </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-success">Confirmar</button>   
                      </div>
                  </form>

              </div>
          </div>
      </div>
      



        <!-- javaScript -->
        <script type="text/javascript">
             function confirmarColetado(idPedido,nome,placa){
                 
                var body = document.getElementById('body-coletado');
                
                body.innerHTML = "\nTem certeza que "+nome+" ja <span style='color:crimson;font-weight:750'>COLETOU</span> a chave\n do veiculo de placa '"+placa+"' ?"
                +" <br>(ID: "+idPedido+")" 
                +"<input name='acao' value='chave_coletada' hidden>"
                 +"<input name='id_pedido' value='"+idPedido+"' hidden>"
                 
                  
                    
             }
             
             function confirmarDevolvido(idPedido,nome,placa){
                 
                var body = document.getElementById('body-devolvido');
                
                body.innerHTML = "\nTem certeza que "+nome+" ja <span style='color:#198754;font-weight:750'>DEVOLVEU</span> a chave\n do veiculo de placa '"+placa+"' ?"
                +" <br>(ID: "+idPedido+")<br>" 
                +" " 
                +"<input name='acao' value='chave_devolvido' hidden>"
                 +"<input name='id_pedido' value='"+idPedido+"' hidden>"
                 
                  
                    
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
                        "sZeroRecords": "N�o foram encontrados resultados",
                        "sInfo": "Mostrar _START_ at� _END_ de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando de 0 at� 0 de 0 requisitos",
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

    
