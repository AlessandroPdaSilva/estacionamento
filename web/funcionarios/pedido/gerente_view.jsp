
<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="../permissao.jsp" %>
    
    
        <%@include file="../../page/cabeçalho.jsp" %>
        <h1 class="my-3">Pedido de Funcionarios</h1>


        <a class="btn btn-primary mb-3" href="../index.jsp">
            Voltar
        </a>
 
        <!-- tabela -->
        <table class="table table-dark table-striped" id="listarPedido">
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
                                <button class="btn btn-secondary">
                                    <img src="../../imagens/chat-dots.svg">
                                </button>
                            </c:if>
                            
                            <c:if test="${p.status==2}">
                                <a onclick="confirmarAceito(${p.id},'${p.funcionario.nome}','${p.veiculo.placa}',0 )" class="btn btn-success" >
                                    <img src="../../imagens/hand-thumbs-up.svg">
                                </a>
                                <a onclick="confirmarRecusado(${p.id},'${p.funcionario.nome}','${p.veiculo.placa}',1 )" class="btn btn-danger" >
                                    <img src="../../imagens/hand-thumbs-down.svg">
                                </a>
                            </c:if>
                        </td>
                    </tr>



                </c:forEach>


            </tbody>


        </table>





      






        <!-- javaScript -->
        <script type="text/javascript">
            function confirmarRecusado(id, nome,placa,status) {
                if (confirm("\nDeseja RECUSAR o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?" )) {
                    location.href = "/estacionamento/gerenciar_pedido.do?acao=pedido_recusado&id=" + id +"&status=1";
                }
            }
            
            function confirmarAceito(id, nome,placa,status) {
                if (confirm("\nDeseja ACEITAR o pedido de "+nome+" para o uso \ndo veiculo de placa '"+placa+"' ?" )) {
                    location.href = "/estacionamento/gerenciar_pedido.do?acao=pedido_aceito&id=" + id +"&status=0";
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

    
