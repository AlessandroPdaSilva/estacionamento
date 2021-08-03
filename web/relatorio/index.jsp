
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
        
        
        <h1 class="my-3">Relatorio da Garagem</h1>

         
            
        <div class="p-2 bd-highlight">
            <button class="btn btn-danger mb-3" onclick="gerarPDF()" >
                <img src="../imagens/file-earmark-pdf.svg"> &nbsp gerar PDF
            </button>
        </div>
         

        <!-- tabela -->
        <div id="dados">
        <table data-order='[[ 0, "desc" ]]' class="table table-dark table-striped" id="listarFrota">
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
                
                <jsp:useBean class="dao.RelatorioVeiculoFuncionarioDAO" id="rdao"/><!-- objeto -->
                <c:forEach var="r" items="${rdao.allDesc}">

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
        </div>


                <script>
                    function gerarPDF(){
                        if (confirm("\nDeseja gerar um PDF ? " )) {
                            
                            var dados=document.getElementById('dados').innerHTML
                            var janela = window.open( '  ' ,  '  ' ,'width=800,heigth=600')
                            janela.document.write('<html><head>')
                            janela.document.write('</head>')
                            janela.document.write('<body>')
                            
                        
                            
                            janela.document.write('<h1 style="display: flex;flex-direction: row;justify-content: center;">Relatorio da Garagem</h1>');
                            janela.document.write('<div style="display: flex;flex-direction: row;justify-content: center;">')
                            janela.document.write('<table style="border-collapse: collapse;" cellspacing="0" cellpadding="6" border-spacing="0">')
                            janela.document.write(''+'<thead>'
                                    +'<th style="border: 1px solid black;" scope="col">ID</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Placa do carro</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Nome</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Matricula</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Vaga</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Saida do carro</th>'
                                    +'<th style="border: 1px solid black;" scope="col">Entrada do carro</th>'
                                    +'</thead>');
                            janela.document.write('<tbody>')
                            <c:forEach var="r" items="${rdao.allDesc}">
                            janela.document.write(''
                                    
                                    +''
                                    +'<tr>'
                                    +'<td style="border: 1px solid black;" scope="row">${r.id}</td>'
                                    +'<td style="border: 1px solid black;">${r.veiculo.placa}</td>'
                                    +'<td style="border: 1px solid black;">${r.funcionario.nome}</td>'
                                    +'<td style="border: 1px solid black;">${r.funcionario.matricula}</td>'
                                    +'<td style="border: 1px solid black;">${r.vagaEstacionamento}</td>'
                                    +'<td style="border: 1px solid black;">${r.saidaVeiculo}</td>'
                                    +'<td style="border: 1px solid black;">${r.entradaVeiculo}</td>'
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

    
