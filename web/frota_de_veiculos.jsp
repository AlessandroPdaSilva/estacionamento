
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">


    <%@include file="page/cabeçalho.jsp" %>
    <h1 class="my-3">Frota de Veiculos</h1>


    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriar">
        Criar Veiculo
    </button>



    <!-- tabela -->
    <table class="table table-dark table-striped" id="listarFrota">
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">modelo</th>
                <th scope="col">Placa </th>
                <th scope="col">Vaga</th>

                <th scope="col">Opções</th>

            </tr>
        </thead>


        <tbody>
                
            <!-- view -->
            <jsp:useBean class="dao.VeiculoDaFrotaDAO" id="vfdao"/><!-- objeto -->
            <c:forEach var="vf" items="${vfdao.all}">


                <tr>
                    <th scope="row">${vf.id}</th>
                    <td>${vf.modelo}</td>
                    <td>${vf.placa}</td>
                    <td>${vf.vaga}</td>


                    <td>
                        <a href="gerenciar_veiculo.do?acao=editar_page&id=${vf.id}" class="btn btn-primary" >
                            <img src="imagens/pencil.svg">
                        </a>
                        <a class="btn btn-danger" onclick="confirmarExclusao(${vf.id},'${vf.modelo}','${vf.placa}' )">
                            <img src="imagens/trash-fill.svg">
                        </a>
                    </td>
                </tr>



            </c:forEach>
                
                
        </tbody>


    </table>





    <!-- MODAL CRIAR-->
    <div id="ModalCriar" class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- form-->
                <form action="gerenciar_veiculo.do" method="GET">
                    <div class="modal-header">
                        <h5 class="modal-title">Criar Veiculo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <div class="modal-body">

                        <div class="form-floating">
                            <input name="modelo" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                            <label for="floatingInput">modelo</label>
                        </div>
                        <br>
                        <div class="form-floating">
                            <input name="placa" type="text" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">placa</label>
                        </div>
                        <br>
                        <div class="form-floating">
                            <input name="vaga" type="number" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">vaga</label>
                        </div>

                        <input name="acao" value="criar_veiculo" hidden="">
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-primary">Salvar</button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    
    
    
    



    <!-- javaScript -->
    <script type="text/javascript">
        function confirmarExclusao(id, modelo ,placa) {
            if (confirm("\nDeseja realmente EXCLUIR o " + modelo + "? \n\n - Placa: " + placa +"\n " )) {
                location.href = "gerenciar_veiculo.do?acao=deletar&id=" + id;
            }
        }
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



    <%@include file="page/rodape.jsp" %>

</c:if>




<!-- nao logado -->
<c:if test="${ulogado==null}">

    <h1>Acesso negado</h1>

</c:if>

