
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
        <h1 class="my-3">Chaves Concedidas</h1>

         
         
 
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarPedido">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Veiculo</th>
                    <th scope="col">Data de Coleta</th>
                    <th scope="col">Data de Devolução</th>
                    
                    <th scope="col">Status</th>
                     


                </tr>
            </thead>


            <tbody>

                <!-- view -->
                <jsp:useBean class="dao.RelatorioChaveFuncionarioDAO" id="rfdao"/><!-- objeto -->
                <c:forEach var="r" items="${rfdao.getAllUsuario(ulogado.funcionario.id)}">


                    <tr>
                        <th scope="row">${r.pedido.id}</th>
                        <td>
                            ${r.pedido.funcionario.nome} &nbsp
                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                  &nbsp Matricula: ${r.pedido.funcionario.matricula} <br>
                                   
                              </ul>
                        </td>
                        <td>
                            ${r.veiculo.id}&nbsp
                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                  &nbsp Odometro: ${r.odometroColeta}Km / <c:if test="${r.odometroDevolucao!=-1}">${r.odometroDevolucao}Km</c:if> 
                                  <br>
                              </ul>
                        </td>
                        <td>
                            ${r.dataColeta}
                        </td>
                        <td>
                            ${r.dataDevolucao}
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

    
