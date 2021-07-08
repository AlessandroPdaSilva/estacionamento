 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->

<!-- logado -->
<c:if test="${ulogado!=null}">

    <!-- Permissao -->
    <%@include file="permissao.jsp" %>

    <%@include file="../page/cabeçalho.jsp" %>

    <h1>Editar Veiculo</h1>

    <form action="gerenciar_funcionario.do" method="POST">

        <div class="col-md-6">
            <label for="matricula" class="form-label">Matricula</label>
            <input type="text" class="form-control" id="matricula" name="matricula" value="${f.matricula}">
        </div>

        <div class="col-md-2">
            <label for="nome" class="form-label">Nome</label>
            <input type="text" class="form-control" id="nome" name="nome" value="${f.nome}">
        </div>
        
        <div class="col-md-2">
            <label for="telefone" class="form-label">Telefone</label>
            <input type="text" class="form-control" id="telefone" name="telefone" placeholder="Ex.: 61 9999-9999"  value="${f.telefone}">
        </div>
        
        



        <input name="id" value="${f.id}" hidden="">
        <input name="acao" value="editar" hidden="">

        <a href="funcionarios/index.jsp" class="btn btn-primary my-3"> Voltar</a>
        <button class="btn btn-success" type="submit">Editar</button>
    </form>


    <%@include file="../page/rodape.jsp" %>
    
    <!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>

</c:if>



    <%@include file="../page/acesso_negado.jsp" %>