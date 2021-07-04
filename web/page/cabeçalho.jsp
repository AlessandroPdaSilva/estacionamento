 


<!doctype html>
<html lang="pt-BR">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
        <link rel="stylesheet" href="datatables/jquery.dataTables.min.css">
        
        <style>
            
            .navegacao{
                color:#ffffff;
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
                        
                        <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="navegacao nav-link px-4" > Frota de Veiculos</a></li>
                        <li><a href="/estacionamento/funcionarios/index.jsp" class="navegacao nav-link px-4" > Funcionarios</a></li>
                        
                    </ul>

                    
                    
                    <div class="text-end">
                        ${ulogado.nome}
                        
                        <a  class="btn btn-success mx-3" href="/estacionamento/gerenciar_login.do?acao=deslogar"> Deslogar </a>
                    </div>
                    
                </div>
                
            </div>
        </header>
        
        <!-- body-->
        <div class="container">  
