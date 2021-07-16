   <%@page import="model.Usuario"%>
   <!-- Permissao -->
    <%
      Usuario u = (Usuario)session.getAttribute("ulogado");
      
      int nivel = u.getNivel();
      
      if(nivel==1){
          
          %>
     