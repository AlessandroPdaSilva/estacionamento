

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>


    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- Bootstrap CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
            <link rel="stylesheet" href="datatables/jquery.dataTables.min.css">
            <title>Criar Login</title>
        </head>
        <body>
            <div class="container">  


                <!-- form -->
                <form action="/estacionamento/gerenciar_login.do" method="POST" >

                    <h1 class="h3 mb-3 fw-normal my-5">Criar Login</h1>

                    <div class="form-floating">
                        <input name="login" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                        <label for="floatingInput">Novo Usuario</label>
                    </div>
                    <br>
                    <div class="form-floating">
                        <input name="senha" type="password" class="form-control" id="floatingPassword" placeholder="Password">
                        <label for="floatingPassword">Senha</label>
                    </div>
                    <br>
                    <select id="nivel" class="form-select" aria-label="Default select example" name="nivel">
                            <option selected disabled=""> Nivel</option>
                            <option value="1">Administrador</option>
                            <option value="2">Gerente</option>
                            <option value="3">Usuario</option>
                            <option value="4">Funcionario</option>
                            
                    </select>
                    
                    <div id="resp1"></div>
                    
                    
                    <input name="acao" value="criar" hidden="">
                    <div class="checkbox mb-3">

                    </div>
                    <button class="w-100 btn btn-lg btn-primary" type="submit">Entrar</button>

                </form>

                <br>
                <a  class="btn btn-success" href="../relatorio/index.jsp">
                    Voltar 
                </a>

                <div/>


                <!-- Option 1: Bootstrap Bundle with Popper -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

                <!-- Option 2: Separate Popper and Bootstrap JS -->
                <!--
                <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>
                -->



        </body>
    </html>
    
    <script type="text/javascript">
        
        // matricula
        var select = document.querySelector('select#nivel');
        select.addEventListener('change', function () {
            var option = this.selectedOptions[0];
            var texto = option.value;
            var resp = document.getElementById('resp1');
            
            if(texto == 4){
                //resp.innerHTML = '<br><input class="form-control" id="disabledInput" type="text" placeholder="'+texto+'" disabled>'
                resp.innerHTML = '<br><select id="id-funcionario" class="form-select" aria-label="Default select example" name="id-funcionario">'
                +'<option selected disabled=""> Selecione um Funcionario</option>'
                
                <jsp:useBean class="dao.FuncionarioDAO" id="fdao"/><!-- objeto -->
                <jsp:useBean class="dao.UsuarioDAO" id="ddao"/><!-- objeto -->
                <c:forEach var="f" items="${fdao.all}">
                    
                    <c:if test="${ddao.getFuncionarioEmUso(f.id)==false}">
                        +'<option value="${f.id}">${f.nome}</option>'
                    </c:if>
                </c:forEach>
        
                +'</select>'
                console.log(texto);
            }else{
                resp.innerHTML = ''
            }
            

            
        });
        
    </script>


    <%@include file="../page/rodape.jsp" %>

    <!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>

</c:if>



<%@include file="../page/acesso_negado.jsp" %>



