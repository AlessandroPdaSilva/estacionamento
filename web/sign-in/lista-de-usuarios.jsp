

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>


    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    
        <%@include file="../page/cabeçalho.jsp" %>
        
            <h1 class="my-3">Lista de Usuarios</h1>
        
            <!-- tabela -->
        <table class="table table-dark table-striped" id="listarUsuario">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Usuario</th>
                    <th scope="col">Nivel </th>
                    <th scope="col">Opções </th>
                     

                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.UsuarioDAO" id="udao"/><!-- objeto -->
                <c:forEach var="u" items="${udao.all}">


                    <tr>
                        <th scope="row">${u.id}</th>
                        <td>${u.nome}</td>
                        <td>
                            <c:if test="${u.nivel==1}"><!-- Administrador-->
                                Administrador
                            </c:if>
                            
                            <c:if test="${u.nivel==2}"><!-- Gerente-->
                                Gerente
                            </c:if>
                            
                            <c:if test="${u.nivel==3}"><!-- Usuario-->
                                Usuario
                            </c:if>
                            
                            <c:if test="${u.nivel==4}"><!-- Funcionario-->
                                Funcionario &nbsp
                                <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                                </button>
                                <ul class="dropdown-menu">
                                    &nbsp Nome: ${u.funcionario.nome}<br>
                                    &nbsp Matricula: ${u.funcionario.matricula}
                                </ul>
                            </c:if>
                            
                        </td>
                        
                        <td>
                            <a class="btn btn-primary" onclick="confirmarEdicao(${u.id},'${u.nome}',${u.nivel})" data-bs-toggle="modal" data-bs-target="#ModalConfirmarEdicao">
                                <img src="../imagens/pencil.svg">
                            </a>
                            <a class="btn btn-danger" onclick="confirmarExclusao(${u.id},'${u.nome}' )" data-bs-toggle="modal" data-bs-target="#ModalConfirmarExclusao">
                                <img src="../imagens/trash-fill.svg">
                            </a>
                        </td>
                         
                    </tr>



                </c:forEach>


            </tbody>


        </table>

        
        <!-- MODAL CONFIRMAR EXCLUSAO-->
      <div id="ModalConfirmarExclusao" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_login.do" method="POST">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-exclusao"> </div> 
                          
                      </div>
                           
                          
                      
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <button type="submit" class="btn btn-danger">Confirmar</button>
                      </div>
                  </form>

              </div>
          </div>
      </div>
         
         <!-- MODAL CONFIRMAR EDICAO-->
      <div id="ModalConfirmarEdicao" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_login.do" method="POST">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-edicao"> </div> 
                          
                      </div>
                           
                          
                      
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                          <button type="submit" class="btn btn-danger">Confirmar</button>
                      </div>
                  </form>

              </div>
          </div>
      </div>       
                
        
        <script type="text/javascript">
            
            function confirmarExclusao(id, nome) {
                 
                var body = document.getElementById('body-exclusao');
                
                body.innerHTML = "\nDeseja <span style='color:crimson;font-weight:750'>EXCLUIR</span> o usuario <span style='font-weight:650'>"+nome+"</span>  ?"
                 +"<input name='acao' value='excluir-usuario' hidden>"
                 +"<input name='id' value='"+id+"' hidden>"
                  
                  
                  
            }

            
            function confirmarEdicao(id,login,nivel) {
                 
                var body = document.getElementById('body-edicao');
                
                body.innerHTML = "<input name='acao' value='editar-usuario' hidden>"
                +"<input name='id' value='"+id+"' hidden>"
                 
                +"<div class='form-floating'>"
                +"<input name='login' value='"+login+"' type='text' class='form-control' id='floatingInput' placeholder='name@example.com'>"
                +"<label for='floatingInput'>nome de usuario</label>"   
                +"</div><br>"   
                        
                +"<div class='form-floating'>"
                +"<input name='senha' type='password' class='form-control' id='floatingInput' placeholder='name@example.com'>"
                +"<label for='floatingInput'>Nova senha</label>"   
                +"</div>"
                                
                            
                  
            }
            
        </script>
        
        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#listarUsuario").dataTable({

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
    
    <%@include file="../page/sem_permissao.jsp" %>

</c:if>



<%@include file="../page/acesso_negado.jsp" %>



