 
package model;
 
public class RelatorioChaveFuncionario {
    
    private Pedido pedido;
    private String dataColeta;
    private int odometroColeta;
    private String dataDevolucao;
    private int odometroDevolucao;
    private int status;
    
    // get e set 

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }

    public int getOdometroColeta() {
        return odometroColeta;
    }

    public void setOdometroColeta(int odometroColeta) {
        this.odometroColeta = odometroColeta;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getOdometroDevolucao() {
        return odometroDevolucao;
    }

    public void setOdometroDevolucao(int odometroDevolucao) {
        this.odometroDevolucao = odometroDevolucao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
