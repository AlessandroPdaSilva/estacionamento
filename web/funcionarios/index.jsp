
<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>
    
    
        <%@include file="../page/cabeçalho.jsp" %>
        <h1 class="my-3">Lista de Funcionarios</h1>


        <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriar">
            Criar Funcionario
        </button>

        
        <!-- tabela -->
        <table class="table table-dark table-striped" id="listarFrota">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Matricula</th>
                    <th scope="col">Nome </th>
                    <th scope="col">Telefone</th>

                    <th scope="col">Opções</th>

                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.FuncionarioDAO" id="fdao"/><!-- objeto -->
                <c:forEach var="f" items="${fdao.all}">


                    <tr>
                        <th scope="row">${f.id}</th>
                        <td>${f.matricula}</td>
                        <td>${f.nome}</td>
                        <td>${f.telefone}</td>


                        <td>
                            <a href="/estacionamento/gerenciar_funcionario.do?acao=editar_page&id=${f.id}" class="btn btn-primary" >
                                <img src="../imagens/pencil.svg">
                            </a>
                            <a class="btn btn-danger" onclick="confirmarExclusao(${f.id},'${f.matricula}','${f.nome}' )">
                                <img src="../imagens/trash-fill.svg">
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
                    <form action="/estacionamento/gerenciar_funcionario.do" method="GET">
                        <div class="modal-header">
                            <h5 class="modal-title">Criar Funcionario</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>

                        <div class="modal-body">

                            <div class="form-floating">
                                <input name="matricula" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                                <label for="floatingInput">matricula</label>
                            </div>
                            <br>
                            <div class="form-floating">
                                <input name="nome" type="text" class="form-control" id="floatingPassword" placeholder="Password">
                                <label for="floatingPassword">nome</label>
                            </div>
                            <br>

                                telefone (opcional)
                                <input name="telefone" type="text" placeholder="Ex.: 61 9999-9999">




                            <input name="acao" value="criar_funcionario" hidden="">
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
            function confirmarExclusao(id, matricula ,nome) {
                if (confirm("\nDeseja realmente EXCLUIR o " + nome + "? \n\n - Matricula: " + matricula +"\n " )) {
                    location.href = "/estacionamento/gerenciar_funcionario.do?acao=deletar&id=" + id;
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



        <%@include file="../page/rodape.jsp" %>
    
    <!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>
    
</c:if>



<%@include file="../page/acesso_negado.jsp" %>

    
