 
package connect;
import java.sql.Connection;
import java.sql.DriverManager;

// classe
public class ConnectionFactory {
    
    //  -- VAR
    
    private static final String username = "adm";
    private static final String password = "1234";
    private static final String database_url = "jdbc:mysql://127.0.0.1:3306/db_estacionamento?useTimezone=true&serverTimezone=UTC";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    
    
    
    // -- metodos
    
    
    // CONEXAO
    public static Connection createConnectionToMySql() throws Exception{
        
        // fazendo conexao
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(database_url,username,password);
         
        return conn;
    }
    
    
    
    
    // MAIN
    public static void main(String[] args) throws Exception{
        
        // pegando CONEXAO para msg
        Connection conn = createConnectionToMySql();
        
        if(conn != null){
            System.out.println("Conexão feita com sucesso");
            conn.close();
        }
    }
    
}



