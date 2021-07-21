 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- taglib -->

<!-- logado -->
<c:if test="${ulogado!=null}">

<!-- Permissao -->
<%@include file="permissao.jsp" %>
    
<%@include file="../page/cabeçalho.jsp" %>

<h1>Editar Veiculo</h1>

<form action="gerenciar_veiculo.do" method="POST">

    <div class="col-md-6">
        <label for="modelo" class="form-label">Modelo</label>
        <input type="text" class="form-control" id="modelo" name="modelo" value="${vf.modelo}">
    </div>

    <div class="col-md-2">
        <label for="placa" class="form-label">Placa</label>
        <input type="text" class="form-control" id="placa" name="placa" value="${vf.placa}">
    </div>

    <div class="col-md-2 mb-3">
        <label for="vaga" class="form-label">Vaga</label>
        <input type="number" class="form-control" id="vaga" name="vaga" value="${vf.vaga}">
    </div>
    <br>
    <div class="col-md-2 mb-3">
        <label for="odometro" class="form-label">Odometro</label>
        <input type="number" class="form-control" id="vaga" name="odometro" value="${vf.odometro}">
    </div>
    
    <input name="id" value="${vf.id}" hidden="">
    <input name="acao" value="editar" hidden="">

    <a href="frota_de_veiculos/index.jsp" class="btn btn-primary "> Voltar</a>
    <button class="btn btn-success" type="submit">Editar</button>
</form>


<%@include file="../page/rodape.jsp" %>

<!-- Permissao fim-->
    <%@include file="../page/sem_permissao.jsp" %>

</c:if>



    <%@include file="../page/acesso_negado.jsp" %>