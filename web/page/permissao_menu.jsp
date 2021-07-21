
<!-- Administrador-->
<c:if test="${ulogado.nivel==1}"> 
    <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="navegacao nav-link px-4" > Frota de Veiculos</a></li>
    <li><a href="/estacionamento/funcionarios/index.jsp" class="navegacao nav-link px-4" > Funcionarios</a></li>
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>
    <li><a href="/estacionamento/agendamento/index.jsp" class="navegacao nav-link px-4" > Agendamento</a></li>
    
</c:if>
<!-- Gerente-->
<c:if test="${ulogado.nivel==2}"> 
    <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="navegacao nav-link px-4" > Frota de Veiculos</a></li>
    <li><a href="/estacionamento/funcionarios/index.jsp" class="navegacao nav-link px-4" > Funcionarios</a></li>
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>
    
    
</c:if>
<!-- Usuario-->
<c:if test="${ulogado.nivel==3}"> 
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>
    <li><a href="/estacionamento/agendamento/index.jsp" class="navegacao nav-link px-4" > Agendamento</a></li>

</c:if>

<!-- Funcionario-->
<c:if test="${ulogado.nivel==4}"> 
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>
    
</c:if>