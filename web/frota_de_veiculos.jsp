

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- logado -->
<c:if test="${ulogado!=null}">
    
    
    <%@include file="page/cabeçalho.jsp" %>
        <h1>Frota de veiculos</h1>
        
         <a  class="btn btn-success" href="gerenciar_login.do?acao=deslogar">
                deslogar
         </a>
 
    <%@include file="page/rodape.jsp" %>
    
</c:if>

        
        
        
<!-- nao logado -->
<c:if test="${ulogado==null}">
    
    <h1>Acesso negado</h1>
    
</c:if>

