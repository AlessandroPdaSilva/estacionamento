
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>

    <%@include file="../page/cabe�alho.jsp" %>
    <h1 class="my-3">Agendamento</h1>


    <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriar">
        Criar Agendamento
    </button>



    <!-- tabela -->
    <table class="table table-dark table-striped" id="listarFrota">
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Placa</th>
                <th scope="col">Nome </th>
                <th scope="col">Matricula</th>
                <th scope="col">Vaga</th>
                <th scope="col">Status</th>
                <th scope="col">Op��es</th>

            </tr>
        </thead>


        <tbody>

            <!-- view -->
            <jsp:useBean class="dao.VeiculoDaFrotaDAO" id="pp"/><!-- objeto -->
            <c:forEach var="p" items="${pp.all}">


                <tr>
                    <th scope="row">${p.id}</th>
                    <td>${p.modelo}</td>
                    <td>${p.placa}</td>
                    <td>${p.vaga}</td>
                    <td>${p.vaga}</td>
                    <td>${p.vaga}</td>
                    <td>
                        <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriar">
                            botao
                        </button>
                    </td>
                </tr>



            </c:forEach>


        </tbody>


    </table>





    <!-- MODAL CRIAR-->
    <div id="ModalCriar" class="modal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- form-->
                <form action="/estacionamento/gerenciar_veiculo.do" method="GET">
                    <div class="modal-header">
                        <h5 class="modal-title">Criar Agendamento</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <!-- body form -->
                    <div class="modal-body">


                        <select id="funcionario" class="form-select" aria-label="Default select example">
                            <option selected disabled=""> Funcionario</option>
                            
                            <jsp:useBean class="dao.FuncionarioDAO" id="fdao"/><!-- objeto -->
                            <c:forEach var="f" items="${fdao.all}">
                                <option value="${f.matricula}">${f.nome}</option>
                            </c:forEach>
                            
                        </select>
 
                        <div id="resp1"></div>

                        <br>
                        
                        <select id="placa" class="form-select " aria-label="Default select example">
                            <option selected disabled=""> placa</option>
                            <jsp:useBean class="dao.VeiculoDaFrotaDAO" id="vdao"/><!-- objeto -->
                            <c:forEach var="v" items="${vdao.all}">
                                <option value="${v.modelo}">${v.placa}</option>
                            </c:forEach>

                        </select>

                        
                        <div id="resp2"></div>
                        
                        <br>
                        <div class="form-floating">
                            <input name="vaga" type="number" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">vaga</label>
                        </div>
                        <input name="acao" value="criar_veiculo" hidden="">                        </div>
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
        function confirmarExclusao(id, modelo, placa) {
            if (confirm("\nDeseja realmente EXCLUIR o " + modelo + "? \n\n - Placa: " + placa + "\n ")) {
                location.href = "/estacionamento/gerenciar_veiculo.do?acao=deletar&id=" + id;
            }
        }


        var select = document.querySelector('select#funcionario');
        select.addEventListener('change', function () {
            var option = this.selectedOptions[0];
            var texto = option.value;
            var resp = document.getElementById('resp1');

            //resp.innerHTML = '<br><input class="form-control" id="disabledInput" type="text" placeholder="'+texto+'" disabled>'
            resp.innerHTML = '<br><div class="alert alert-success" role="alert"> Matricula = '+texto+'</div>'
            console.log(texto);
        });

        
        var select = document.querySelector('select#placa');
        select.addEventListener('change', function () {
            var option = this.selectedOptions[0];
            var texto = option.value;
            var resp = document.getElementById('resp2');

            //resp.innerHTML = '<br><input class="form-control" id="disabledInput" type="text" placeholder="'+texto+'" disabled>'
            resp.innerHTML = '<br><div class="alert alert-success" role="alert">'+texto+'</div>'
            console.log(texto);
        });

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



    <%@include file="../page/rodape.jsp" %>

    <!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>

</c:if>




<%@include file="../page/acesso_negado.jsp" %>

