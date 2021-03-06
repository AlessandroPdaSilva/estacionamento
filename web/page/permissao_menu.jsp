
<!-- Administrador-->
<c:if test="${ulogado.nivel==1}"> 
    
    <li class="nav-item dropdown">
        <a style="color: white" class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Funcionario
        </a>
          <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><a href="/estacionamento/funcionarios/index.jsp" class="dropdown-item">Lista de Funcionarios</a></li>
            <li><a href="/estacionamento/funcionarios/pedido/gerente_view.jsp" class="dropdown-item">Pedidos</a></li>
            <li><a href="/estacionamento/funcionarios/chave-funcionario/gerente_view.jsp" class="dropdown-item">Chaves Concedidas</a></li>

          </ul>
    </li>
    
    <li class="nav-item dropdown">
        <a style="color: white" class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Frota de Veiculos
        </a>
          <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="dropdown-item" >Lista de Veiculos </a></li>
            <li><a href="/estacionamento/frota_de_veiculos/veiculos-em-uso/index.jsp" class="dropdown-item" >Veiculos em uso </a></li>
          
          </ul>
    </li>
    
    
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio da Garagem</a></li>
    <li><a href="/estacionamento/agendamento/index.jsp" class="navegacao nav-link px-4" > Agendamento</a></li>
    
</c:if>
<!-- Gerente-->
<c:if test="${ulogado.nivel==2}"> 
    <li class="nav-item dropdown">
        <a style="color: white" class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Funcionario
        </a>
          <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><a class="dropdown-item" href="/estacionamento/funcionarios/index.jsp">Lista de Funcionarios</a></li>
            <li><a class="dropdown-item" href="/estacionamento/funcionarios/pedido/gerente_view.jsp">Pedidos</a></li>
            <li><a class="dropdown-item" href="/estacionamento/funcionarios/chave-funcionario/gerente_view.jsp">Chaves Concedidas</a></li>

          </ul>
    </li>
    
    <li class="nav-item dropdown">
        <a style="color: white" class="nav-link dropdown-toggle" href="#" id="navbarDarkDropdownMenuLink" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Frota de Veiculos
        </a>
          <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDarkDropdownMenuLink">
            <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="dropdown-item" >Lista de Veiculos </a></li>
            <li><a href="/estacionamento/frota_de_veiculos/veiculos-em-uso/index.jsp" class="dropdown-item" >Veiculos em uso </a></li>
          
          </ul>
    </li>
    
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio da Garagem</a></li>
    
    
</c:if>
    
<!-- Usuario-->
<c:if test="${ulogado.nivel==3}"> 
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio da Garagem</a></li>
    <li><a href="/estacionamento/agendamento/index.jsp" class="navegacao nav-link px-4" > Agendamento</a></li>

</c:if>

<!-- Funcionario-->
<c:if test="${ulogado.nivel==4}"> 
    <li><a href="/estacionamento/funcionarios/pedido/funcionario_view.jsp" class="navegacao nav-link px-4" > Pedido</a></li>
    <li><a href="/estacionamento/funcionarios/chave-funcionario/funcionario_view.jsp" class="navegacao nav-link px-4" > Chaves Concedidas</a></li>
    <li><a href="/estacionamento/frota_de_veiculos/veiculos-em-uso/index.jsp" class="navegacao nav-link px-4" >Veiculos em uso </a></li>
</c:if>