
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
        <h1 class="my-3">Veiculo em uso</h1>

         
         
 
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarFrota">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">modelo</th>
                    <th scope="col">Placa </th>
                    <th scope="col">Status</th>
                     
                    

                     

                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.VeiculoDaFrotaDAO" id="vfdao"/><!-- objeto -->
                <jsp:useBean class="dao.RelatorioChaveFuncionarioDAO" id="rcdao"/><!-- objeto -->
                <c:forEach var="vf" items="${vfdao.all}">


                    <tr>
                        <th scope="row">${vf.id}</th>
                        <td>${vf.modelo}</td>
                        <td>${vf.placa}</td>
                        <td>
                            <c:if test="${rcdao.getCarroEmUso(vf.placa)}">
                                <div style="color:crimson;font-weight:750"> Em uso </div> 
                            </c:if>
                            <c:if test="${rcdao.getCarroEmUso(vf.placa)==false}">
                                <div style="color:#198754;font-weight:750"> Sem uso </div> 
                            </c:if>    
                        </td>
                         
                      
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



    <%@include file="../../page/rodape.jsp" %>

<!-- Permissao fim-->
    <%@include file="../../page/sem_permissao.jsp" %>
    
</c:if>



<%@include file="../../page/acesso_negado.jsp" %>

    
