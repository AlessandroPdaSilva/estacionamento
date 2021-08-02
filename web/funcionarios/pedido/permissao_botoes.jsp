 <!-- botoes-->
        
        
         
        <%
            PedidoDAO pd = new PedidoDAO();
            RelatorioChaveFuncionarioDAO rd = new RelatorioChaveFuncionarioDAO();

            Usuario uu = (Usuario)session.getAttribute("ulogado");
            int idFuncionario = uu.getFuncionario().getId();
            
            if(pd.getPedidoEmUso(idFuncionario)==false){
                if(rd.getRelatorioChaveEmUso(idFuncionario)==false){
                    
        %>
            
            <button type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriarPedido">
                Criar Novo Pedido
            </button>
            
            <button disabled="" class="btn btn-danger mb-3" data-bs-toggle="modal" data-bs-target="#ModalDeletarPedido" >
                Deletar Pedido
            </button>
        
        <%        
            
                }else{
        %>
            <button  disabled="" type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriarPedido">
                Criar Novo Pedido
            </button>
            
            <button disabled="" class="btn btn-danger mb-3" data-bs-toggle="modal" data-bs-target="#ModalDeletarPedido" >
                Deletar Pedido
            </button>
        
        <%
                }
            }else{
        %>
            <button disabled="" type="button" class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#ModalCriarPedido">
                Criar Novo Pedido
            </button>
            
            <button  class="btn btn-danger mb-3" data-bs-toggle="modal" data-bs-target="#ModalDeletarPedido" >
                Deletar Pedido
            </button>
        
        <%
            }
        %>
            
             
      
