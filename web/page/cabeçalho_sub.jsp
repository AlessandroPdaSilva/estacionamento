 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->




<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css">
        <link rel="shortcut icon" href="../../imagens/logo-setur.ico" />
        
        

        <style>

            .navegacao{
                color:#ffffff;
            }

            .nav-nome{
                border:none;
                background: none;
                color:white;
                
            }
            
            .nav-nome:hover{
                text-decoration: underline;
            }

            .nav-nome2{
                border:none;
                background: none;
                color:black;
                cursor:pointer;
            }
            
            .nav-nome2:hover{
                text-decoration: underline;
            }
        </style>

        <title>Projeto Estacionamento</title>
    </head>
    <body>

        <!-- nav bar-->
        <header class="p-3 bg-dark text-white">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <!--
                    <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>
                    </a>
                    -->


                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        
                       
                        <%@include file="../page/permissao_menu.jsp" %> 
                            
                        
                    </ul>



                    <div class="text-end">
                        
                        <button class="nav-nome" data-bs-toggle="modal" data-bs-target="#ModalPerfil">${ulogado.nome}</button>

                        <a  class="btn btn-success mx-3" href="/estacionamento/gerenciar_login.do?acao=deslogar"> Deslogar </a>
                    </div>

                </div>

            </div>
        </header>

        <!-- body-->
        <div class="container">  
            
            
            
            
            <!-- MODAL PERFIL-->
            <div id="ModalPerfil" class="modal" tabindex="-1">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">

                        <!-- form-->
                        <form action="/estacionamento/gerenciar_agendamento.do" method="POST">
                            <div class="modal-header">
                                <h5 class="modal-title"><img src="../../imagens/perfil.png"> 
                                    &nbsp Usuario
                                </h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>

                            <!-- body form -->
                            <div class="modal-body">                                 
                                <p class="fs-5">
                                Nome: ${ulogado.nome}<br>
                                Perfil: <c:if test="${ulogado.nivel==1}"> Administrador</c:if>
                                        <c:if test="${ulogado.nivel==2}"> Gerente</c:if>
                                        <c:if test="${ulogado.nivel==3}"> Usuario</c:if>
                                        <c:if test="${ulogado.nivel==4}"> Funcionario</c:if>
                                 
                                        <c:if test="${ulogado.nivel==4}"> 
                                            <jsp:useBean class="dao.FuncionarioDAO" id="ffdao"/><!-- objeto -->
                                            
                                            <br>Nome do Funcionario: ${ffdao.getFuncionario(ulogado.funcionario.id).nome}
                                            
                                            
                                        </c:if>
                                        
                                        
                                        <c:if test="${ulogado.nivel==1}"> 
                                            <br><br>
                                            <a  class="btn btn-primary mb-3" href="../sign-in/criar_login.jsp">
                                                Criar Usuario
                                            </a>
                                        </c:if>
                                        <br><br>
                                        
                                        
                                   </p>     
                                   <div class="d-flex justify-content-center">Create by&nbsp<a class="nav-nome2 fw-bold" href="https://alessandropdasilva.github.io/" target="_blank">Alessandro</a></div>            
                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fechar</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
