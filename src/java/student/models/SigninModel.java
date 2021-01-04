package student.models;

import java.util.Map;

public class SigninModel {

    Database db;

    public SigninModel(Database db) {
        this.db = db;
    }

    public SigninInfo setAltmodel(Map<String, Object> row) {

        if (row != null) {
            SigninInfo getRow = SigninInfo.builder()
                    .ID((String) row.get("ID"))
                    .USERNAME((String) row.get("USERNAME"))
                    .PASSWORD((String) row.get("PASSWORD"))
                    .STATUS((String) row.get("STATUS"))
                    .build();

            return getRow;
        } else {
            return null;
        }
    }

    public boolean SigninChecking(String username) {

        String sql = "SELECT CAST(ID AS VARCHAR(10))AS ID,USERNAME,PASSWORD,CAST(STATUS AS VARCHAR(1))AS STATUS FROM USERS WHERE USERNAME = '"+username+"'";
        Map<String, Object> row = db.querySingle(sql);

        SigninInfo chk = setAltmodel(row);

        try {
            if (chk != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }//end of check before insert
    
    public boolean RegisterUser(String username) {
        // int colorNo = getColorNo();
        String sql = "INSERT INTO USERS(ID,USERNAME,PASSWORD,STATUS)  VALUES((select max(ID)+1 FROM USERS),?,0,0)";

        String[] genCol = {"USERS"};
        int chk = db.insertRc(genCol, sql, username);

        try {
            if (chk > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }//end of insert

}
