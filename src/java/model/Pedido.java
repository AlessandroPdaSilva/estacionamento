 
package model;
 
public class Pedido {
    
    // var
    private int id;
    private Funcionario funcionario;
    private VeiculoDaFrota veiculo;
    private String dataPedido;
    private String percurso;
    private int status;
    private String mensagem;
    
    // gets e sets

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

    public VeiculoDaFrota getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoDaFrota veiculo) {
        this.veiculo = veiculo;
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
