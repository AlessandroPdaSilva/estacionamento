 
package model;
 
public class Pedido {
    
    // var
    private int id;
    private Funcionario funcionario;
     
    private String dataPedido;
    private String percurso;
    private int status;
    private String mensagem;
    private String solicitacao;
    private String dataParaUso;
    
    // gets e sets

    public String getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(String solicitacao) {
        this.solicitacao = solicitacao;
    }

    public String getDataParaUso() {
        return dataParaUso;
    }

    public void setDataParaUso(String dataParaUso) {
        this.dataParaUso = dataParaUso;
    }
    
    

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
 

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getPercurso() {
        return percurso;
    }

    public void setPercurso(String percurso) {
        this.percurso = percurso;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
