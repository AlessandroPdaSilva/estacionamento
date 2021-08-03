
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>

    <%@include file="../page/cabeçalho.jsp" %>
    <h1 class="my-3">Editar Agendamento</h1>


    
    <a class="btn btn-primary mb-3" href="/estacionamento/agendamento/index.jsp">
        Voltar
    </a>


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
                <th scope="col">Opções</th>

            </tr>
        </thead>


        <tbody>

            <!-- view -->
            <jsp:useBean class="dao.AgendamentoDAO" id="adao"/><!-- objeto -->
            <c:forEach var="a" items="${adao.all}">


                <tr>
                    <th scope="row">${a.id}</th>
                    <td>${a.veiculo.placa}</td>
                    <td>${a.funcionario.nome}</td>
                    <td>${a.funcionario.matricula}</td>
                    <td>${a.vagaEstacionamento}</td>
                    <td>
                        
                        <c:if test="${a.status==1}"> 
                            <div style="color:crimson"> Em uso </div>  
                        </c:if>
                        
                        <c:if test="${a.status==0}"> 
                            <div style="color:#198754"> Finalizado </div>  
                        </c:if>
                    </td>
                    <td>
                        <a  onclick="enviarId(${a.id})" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#ModalEditar">
                                <img src="../imagens/pencil.svg">
                        </a>
                        <a class="btn btn-danger" onclick="confirmarExclusao(${a.id})" data-bs-toggle="modal" data-bs-target="#ModalConfirmarExclusao">
                                <img src="../imagens/trash-fill.svg">
                        </a>
                        
                    </td>
                </tr>



            </c:forEach>


        </tbody>


    </table>





    <!-- MODAL EDITAR-->
    <div id="ModalEditar" class="modal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- form-->
                <form action="/estacionamento/gerenciar_agendamento.do" method="POST">
                    <div class="modal-header">
                        <h5 class="modal-title">Editar Agendamento</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>

                    <!-- body form -->
                    <div class="modal-body">


                        <select id="funcionario" class="form-select" aria-label="Default select example" name="matricula">
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
                        <div id="resp3"></div>
                        
                        <br>
                        <div class="form-floating">
                            <input name="vaga" type="number" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">vaga</label>
                        </div>
                        
                        <input name="acao" value="editar" hidden="">  
                    
                    </div>
                            
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-primary">Editar</button>
                    </div>
                </form>

            </div>
        </div>
    </div>


<!-- MODAL CONFIRMAR FINALIZADO-->
      <div id="ModalConfirmarExclusao" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_agendamento.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">
                          

                          <div id="body-exclusao">  </div>
                           
                          
                          
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
        //finalizacao
        function confirmarFinalizacao(id,nome,placa) {
            if (confirm("\n Tem certeza que o " + nome + " ja chegou \n com carro de placa "+placa+" ? \n\n ")) {
                location.href = "/estacionamento/gerenciar_agendamento.do?acao=finalizar&id=" + id;
            }
        }

        // funcionario
        var select = document.querySelector('select#funcionario');
        select.addEventListener('change', function () {
            var option = this.selectedOptions[0];
            var texto = option.value;
            var resp = document.getElementById('resp1');

            //resp.innerHTML = '<br><input class="form-control" id="disabledInput" type="text" placeholder="'+texto+'" disabled>'
            resp.innerHTML = '<br><div class="alert alert-success" role="alert"> Matricula = '+texto+'</div>'
            console.log(texto);
        });

        // veiculo
        var select2 = document.querySelector('select#placa');
        select2.addEventListener('change', function () {
            var option = this.selectedOptions[0];
            var texto = option.value;
            var texto2 = option.text;
            var resp = document.getElementById('resp2');

            //resp.innerHTML = '<br><input class="form-control" id="disabledInput" type="text" placeholder="'+texto+'" disabled>'
            resp.innerHTML = '<br><div class="alert alert-success" role="alert">'+texto+'</div>'+
                    '<input name="placa" type="text" value="'+texto2+'" hidden></input>'
            
        
            console.log(texto);
        });
        
        // confirmar exclusao
        function confirmarExclusao(id) {
              
                var body = document.getElementById('body-exclusao');
                
                body.innerHTML = " Deseja realmente <span style='font-weight:650;color:crimson'> EXCLUIR</span> o agendamento de ID = "+id+" ? "
                 +"<input name='acao' value='deletar' hidden>"
                 +"<input name='id' value='"+id+"' hidden>"
                 
        }
        
        function enviarId(id){
            var resp = document.getElementById('resp3');
            
            resp.innerHTML += '<input name="id" type="text" value="'+id+'" hidden></input>'
            
            
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

