 
<%@page import="dao.UsuarioDAO" %>
<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css">

        <title>Projeto Estacionamento</title>
    </head>
    <body>
        
        <div class="container">  
            <!-- form -->
            <form action="gerenciar_login.do" method="POST" >

                <h1 class="h3 mb-3 fw-normal my-5">Fazer Login</h1>

                <div class="form-floating">
                    <input name="login" type="text" class="form-control" id="floatingInput" placeholder="name@example.com">
                    <label for="floatingInput">Usuario</label>
                </div>
                <br>
                <div class="form-floating">
                    <input name="senha" type="password" class="form-control" id="floatingPassword" placeholder="Password">
                    <label for="floatingPassword">Senha</label>
                </div>
                
                <input hidden="" name="acao" value="logar">

                <div class="checkbox mb-3">

                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Entrar</button>

            </form>
            <br>
            <div  class="btn btn-success" onclick="confirmarExclusao()">
                 Criar Usuario
            </div>
            
            
            <div/>
            
            
            
            
            
            <!-- javaScript -->
            <script type="text/javascript">
                function confirmarExclusao(){
                    senha = prompt(" OBS: Somente o setor de informatica pode ter acesso a esse recurso. \n\n" +
                            "Digite a senha: ")
                    
                    if(!(senha === null)){
                        location.href = "gerenciar_login.do?acao=criar_com_senha&campo="+senha;
                    }

                    
                }
                
            </script>
            <!-- Option 1: Bootstrap Bundle with Popper -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>

            <!-- Option 2: Separate Popper and Bootstrap JS -->
            <!--
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js" integrity="sha384-SR1sx49pcuLnqZUnnPwx6FCym0wLsk5JZuNx2bPPENzswTNFaQU1RDvt3wT4gWFG" crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>
            -->
    </body>
</html>