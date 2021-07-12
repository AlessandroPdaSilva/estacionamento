 
package model;
 
public class Agendamento {
    
    // var
    private int id;
    private VeiculoDaFrota veiculo;
    private Funcionario funcionario;
    private int vagaEstacionamento;
    private String saidaVeiculo;
    private String entradaVeiculo;
    private int status;
    
    
    // get e set

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    

    public VeiculoDaFrota getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoDaFrota veiculo) {
        this.veiculo = veiculo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVagaEstacionamento() {
        return vagaEstacionamento;
    }

    public void setVagaEstacionamento(int vagaEstacionamento) {
        this.vagaEstacionamento = vagaEstacionamento;
    }

    public String getSaidaVeiculo() {
        return saidaVeiculo;
    }

    public void setSaidaVeiculo(String saidaVeiculo) {
        this.saidaVeiculo = saidaVeiculo;
    }

    public String getEntradaVeiculo() {
        return entradaVeiculo;
    }

    public void setEntradaVeiculo(String entradaVeiculo) {
        this.entradaVeiculo = entradaVeiculo;
    }
    
    
    
    
}
