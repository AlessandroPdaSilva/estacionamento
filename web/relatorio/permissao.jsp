
   <!-- Permissao -->
    <%
      Usuario u = (Usuario)session.getAttribute("ulogado");
      
      int nivel = u.getNivel();
      
      if(nivel==1 || nivel==2 || nivel==3){
          
          %>
     