<%@page import="model.Usuario"%>
<%@page import="model.VeiculoDaFrota"%>
<%@page import="dao.VeiculoDaFrotaDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->



<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="../permissao.jsp" %>
    
    
        <%@include file="../../page/cabeçalho_sub.jsp" %>
        <h1 class="my-3">Chaves Concedidas</h1>

         <div class="p-2 bd-highlight">
            <button class="btn btn-danger mb-3" onclick="gerarPDF()" >
                <img src="../../imagens/file-earmark-pdf.svg"> &nbsp gerar PDF
            </button>
        </div>
         
        <div id="dados">
            
        <!-- tabela -->
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarChave">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Funcionario</th>
                    <th scope="col">Veiculo</th>
                    <th scope="col">Data de Coleta</th>
                    <th scope="col">Data de Devolução</th>
                    
                    <th scope="col">Status</th>
                    <th scope="col">Opcoes</th>


                </tr>
            </thead>


            <tbody>
                <jsp:useBean class="dao.RelatorioChaveFuncionarioDAO" id="rfdao"/><!-- objeto -->
                <c:forEach var="r" items="${rfdao.allDesc}">
                    <tr>
                        <th scope="row">${r.pedido.id}</th>
                        <td>
                            ${r.pedido.funcionario.nome} &nbsp
                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                  &nbsp Matricula: ${r.pedido.funcionario.matricula} 
                                   
                              </ul>
                        </td>
                         <td>
                            ${r.veiculo.placa}&nbsp
                            <button type="button" class="btn btn-sm btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false">
                                <span class="visually-hidden">Toggle Dropdown</span>
                              </button>
                              <ul class="dropdown-menu">
                                  &nbsp Odometro: ${r.odometroColeta}Km / 
                                  <c:if test="${r.odometroDevolucao!=-1}">${r.odometroDevolucao}Km &nbsp</c:if>
                                  <c:if test="${r.odometroDevolucao==-1}"> Nao devolvido &nbsp</c:if> 
                                  <br>
                                  &nbsp Modelo: ${r.veiculo.modelo}&nbsp
                              </ul>
                        </td>
                        <td>
                            ${r.dataColeta}
                        </td>
                        <td>${r.dataDevolucao}</td>
                         
                         
                         
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
                        <td>
                        <c:if test="${r.status==2}">
                            <a onclick="confirmarColetado(${r.pedido.id},'${r.pedido.funcionario.nome}' )" data-bs-toggle="modal" data-bs-target="#ModalConfirmarColetado" class="btn btn-primary mb-3">
                                Coletado
                            </a>
                        </c:if>
                        <c:if test="${r.status==1}">
                            <a onclick="confirmarDevolvido(${r.pedido.id},'${r.pedido.funcionario.nome}',${r.odometroColeta},${r.veiculo.id} )" data-bs-toggle="modal" data-bs-target="#ModalConfirmarDevolvido" class="btn btn-success mb-3">
                                Devolvido
                            </a>
                        </c:if>
                        </td>
                        
                    </tr>
                </c:forEach>
            </tbody>

        </table>

        </div>

 
                
       <!-- MODAL CONFIRMAR COLETADO-->
      <div id="ModalConfirmarColetado" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_relatorio_cf.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-coletado">  </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-danger">Confirmar</button>   
                      </div>
                  </form>

              </div>
          </div>
      </div>
      
       <!-- MODAL CONFIRMAR DEVOLVIDO-->
      <div id="ModalConfirmarDevolvido" class="modal" tabindex="-1">
          <div class="modal-dialog modal-lg">
              <div class="modal-content">

                  <!-- form-->
                  <form action="/estacionamento/gerenciar_relatorio_cf.do" method="GET">
                      <div class="modal-header">
                          <h5 class="modal-title">Confirmação</h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>

                      <!-- body form -->
                      <div class="modal-body">

                          <div id="body-devolvido">  </div>
                          <br>
                          Antes de finalizar, coloque o valor do odometro:
                          <div class="form-floating">
                              <input name="odometro" type="number" class="form-control" id="floatingPassword" placeholder="Password" required="">
                                <label for="floatingPassword">odômetro</label>
                            </div>
                          
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                        <button type="submit" class="btn btn-success">Confirmar</button>   
                      </div>
                  </form>

              </div>
          </div>
      </div>
      



        <!-- javaScript -->
        <script type="text/javascript">
             function confirmarColetado(idPedido,nome){
                 
                var body = document.getElementById('body-coletado');
                
                body.innerHTML = "\nTem certeza que <span style='font-weight:650'>"+nome+"</span> ja <span style='color:crimson;font-weight:750'>COLETOU</span> a chave ?"
                +" <br>(ID: "+idPedido+")" 
                +"<input name='acao' value='chave_coletada' hidden>"
                 +"<input name='id_pedido' value='"+idPedido+"' hidden>"
                 
                  
                    
             }
             
             function confirmarDevolvido(idPedido,nome,odometroColeta,idVeiculo){
                 
                var body = document.getElementById('body-devolvido');
                
                body.innerHTML = "\nTem certeza que <span style='font-weight:650'>"+nome+"</span> ja <span style='color:#198754;font-weight:750'>DEVOLVEU</span> a chave?"
                +" <br>(ID: "+idPedido+")<br>" 
                +" " 
                +"<input name='acao' value='chave_devolvido' hidden>"
                +"<input name='id_pedido' value='"+idPedido+"' hidden>"
                +"<input name='odometro_coleta' value='"+odometroColeta+"' hidden>"
                +"<input name='id_veiculo' value='"+idVeiculo+"' hidden>"
                 
                  
                    
             }
             
             
             function gerarPDF(){
                        if (confirm("\nDeseja gerar um PDF ? " )) {
                            
                            var dados=document.getElementById('dados').innerHTML
                            var janela = window.open( '  ' ,  '  ' ,'width=800,heigth=600')
                            janela.document.write('<html><head>')
                            janela.document.write('</head>')
                            janela.document.write('<body>')
                            
                        
                            
                            janela.document.write('<h1 style="display: flex;flex-direction: row;justify-content: center;">Relatorio Chave / Funcionario</h1>');
                            janela.document.write('<div style="display: flex;flex-direction: row;justify-content: center;">')
                            janela.document.write('<table style="border-collapse: collapse;" cellspacing="0" cellpadding="6" border-spacing="0">')
                            janela.document.write(''+'<thead>'
                                    +'<th style="border: 1px solid black;" scope="col">ID</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Funcionario</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Matricula</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Placa</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Data de Coleta</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Data de Devolução</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Odometro</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Status</th>'
                                    +'</thead>');
                            janela.document.write('<tbody>')
                            <c:forEach var="r" items="${rfdao.allDesc}">
                            janela.document.write(''
                                    
                                    +''
                                    +'<tr>'
                                    +'<td style="border: 1px solid black;" scope="row">${r.pedido.id}</td>'
                                    +'<td style="border: 1px solid black;">${r.pedido.funcionario.nome}</td>'
                                    +'<td style="border: 1px solid black;">${r.pedido.funcionario.matricula}</td>'
                                    +'<td style="border: 1px solid black;">${r.veiculo.placa}</td>'
                                    +'<td style="border: 1px solid black;">${r.dataColeta}</td>'
                                    +'<td style="border: 1px solid black;">${r.dataDevolucao}</td>'
                                    +'<td style="border: 1px solid black;">${r.odometroColeta}Km / '
                                    +'<c:if test="${r.odometroDevolucao!=-1}">${r.odometroDevolucao}Km &nbsp</c:if>'
                                    +'<c:if test="${r.odometroDevolucao==-1}"> Nao devolvido &nbsp</c:if> '
                                    +'</td>'
                                    
                                    +'<td style="border: 1px solid black;">'
                                    <c:if test="${r.status==0}"> 
                                        +' Devolvido '
                                    </c:if>

                                    <c:if test="${r.status==1}"> 
                                        +' Em uso  ' 
                                    </c:if>

                                    <c:if test="${r.status==2}"> 

                                        +' Nao coletado  ' 
                                    </c:if>
                                    +'</td>'
                                    +'</tr>'
                                    +''
                                    
                                    +'')
                                   
                            </c:forEach>
                                
                                janela.document.write('</tbody>')
                                janela.document.write('</table>')
                                janela.document.write('</div>')
                            janela.document.write('</body></html>')
                            janela.document.close()
                            janela.print()
        
                        }
                    }
                
             
        </script>
 


        <script type="text/javascript" src="datatables/jquery.js"></script>
        <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#listarChave").dataTable({

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

    
