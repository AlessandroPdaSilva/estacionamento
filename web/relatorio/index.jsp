
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
        
        
        <h1 class="my-3">Relatorio</h1>



        <!-- tabela -->
        <table class="table table-dark table-striped" id="listarFrota">
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
                <jsp:useBean class="dao.FuncionarioDAO" id="fdao"/><!-- objeto -->
                <jsp:useBean class="dao.RelatorioVeiculoFuncionarioDAO" id="rdao"/><!-- objeto -->
                <c:forEach var="r" items="${rdao.all}">

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

    
