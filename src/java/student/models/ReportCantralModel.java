package student.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportCantralModel {

    Database db;

    public ReportCantralModel(Database db) {
        this.db = db;
    }

    public ReportCentralInfo setAltmodel(Map<String, Object> row) {

        if (row != null) {
            ReportCentralInfo getRow = ReportCentralInfo.builder()
                    .ENROLL_YEAR((String) row.get("ENROLL_YEAR"))
                    .ENROLL_SEMESTER((String) row.get("ENROLL_SEMESTER"))
                    .FACULTY_NO((String) row.get("FACULTY_NO"))
                    .FACULTY_NAME_THAI((String) row.get("FACULTY_NAME_THAI"))
                    .MAJOR_NO((String) row.get("MAJOR_NO"))
                    .MAJOR_NAME_THAI((String) row.get("MAJOR_NAME_THAI"))
                    .CNT_NET_ALL((BigDecimal) row.get("CNT_NET_ALL"))
                    .CNT_NET_RECEIPT((BigDecimal) row.get("CNT_NET_RECEIPT"))
                    .CNT_NET_STD((BigDecimal) row.get("CNT_NET_STD"))
                    .CNT_CENT_ALL((BigDecimal) row.get("CNT_CENT_ALL"))
                    .CNT_POST_ALL((BigDecimal) row.get("CNT_POST_ALL"))
                    .SUM1((String) row.get("SUM1"))
                    .SUM2((String) row.get("SUM2"))
                    .SUM3((String) row.get("SUM3"))
                    .SUM4((String) row.get("SUM4"))
                    .SUM5((String) row.get("SUM5"))
                    .TOTAL((String) row.get("TOTAL"))
                    .TOTAL_COLUMN((String) row.get("TOTAL_COLUMN"))
                    .build();

            return getRow;
        } else {
            return null;
        }
    }

    public List<ReportCentralInfo> findEnrollYearAndSemester() {
        List<ReportCentralInfo> list = new ArrayList<ReportCentralInfo>();
        String sql = "SELECT DISTINCT A.ENROLL_YEAR,A.ENROLL_SEMESTER FROM bdbass01.ass_stdregis_ru2 A ORDER BY A.ENROLL_YEAR";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }

    public List<ReportCentralInfo> findAll(String year, String semester) {
        List<ReportCentralInfo> list = new ArrayList<ReportCentralInfo>();
        String sql = "SELECT A.ENROLL_YEAR,A.ENROLL_SEMESTER,A.FACULTY_NO,A.FACULTY_NAME_THAI,A.MAJOR_NO,A.MAJOR_NAME_THAI,A.CNT_NET_ALL,A.CNT_NET_RECEIPT,A.CNT_NET_STD,A.CNT_CENT_ALL,A.CNT_POST_ALL FROM BDBASS01.REP_STAT_STD_NEW A WHERE A.ENROLL_YEAR = '" + year + "' AND A.ENROLL_SEMESTER = '" + semester + "' ORDER BY A.FACULTY_NO";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }
    //end find 

    public List<ReportCentralInfo> findAllFaculty(String year, String semester) {
        List<ReportCentralInfo> list = new ArrayList<ReportCentralInfo>();
        String sql = "SELECT DISTINCT A.FACULTY_NO,A.ENROLL_YEAR,A.ENROLL_SEMESTER,A.FACULTY_NAME_THAI FROM BDBASS01.REP_STAT_STD_NEW A WHERE A.ENROLL_YEAR = '" + year + "' AND A.ENROLL_SEMESTER = '" + semester + "' ORDER BY A.FACULTY_NO";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }
    //end find 

}
