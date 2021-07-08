

<c:if test="${ulogado.nivel==1}"> 
    <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="navegacao nav-link px-4" > Frota de Veiculos</a></li>
    <li><a href="/estacionamento/funcionarios/index.jsp" class="navegacao nav-link px-4" > Funcionarios</a></li>
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>

</c:if>

<c:if test="${ulogado.nivel==2}"> 
    <li><a href="/estacionamento/frota_de_veiculos/index.jsp" class="navegacao nav-link px-4" > Frota de Veiculos</a></li>
    <li><a href="/estacionamento/funcionarios/index.jsp" class="navegacao nav-link px-4" > Funcionarios</a></li>
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>

</c:if>

<c:if test="${ulogado.nivel==3}"> 
    <li><a href="/estacionamento/relatorio/index.jsp" class="navegacao nav-link px-4" > Relatorio</a></li>

</c:if>
