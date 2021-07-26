
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
        <h1 class="my-3">Chave / Funcionario</h1>

         
         
 
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarPedido">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Data de Coleta</th>
                    <th scope="col">Data de Devolução</th>
                    <th scope="col">Odometro</th>
                    <th scope="col">Status</th>
                    <th scope="col">Opcoes</th>


                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.PedidoDAO" id="pdao"/><!-- objeto -->
                <c:forEach var="p" items="${pdao.getPedido(ulogado.funcionario.id)}">


                    <tr>
                        <th scope="row">${p.id}</th>
                        <td>${p.funcionario.nome}</td>
                         
                        <td>${p.dataPedido}</td>
                        <td>${p.dataPedido}</td>
                        
                        <td>200000 / 200010</td>
                         
                         
                        <td>2</td>
                        <td>coletado</td>
                        
                    </tr>



                </c:forEach>


            </tbody>


        </table>

 




    <!-- javaScript -->
    <script type="text/javascript">
        
 

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

    
