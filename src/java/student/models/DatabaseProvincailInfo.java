package student.models;

import lombok.Data;

@Data
public class DatabaseProvincailInfo {
    
    public static final String URL = "jdbc:oracle:thin:@10.2.1.98:1521:rubram";
    public static final String USER = "BCAMPUS02";
    public static final String PASSWORD = "BCAMPUS00";
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

    private DatabaseProvincailInfo() {

    }
}
