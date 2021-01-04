package student.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportProvincialModel {

    DatabaseProvincail db;

    public ReportProvincialModel(DatabaseProvincail db) {
        this.db = db;
    }

    public ReportProvincialInfo setAltmodel(Map<String, Object> row) {

        if (row != null) {
            ReportProvincialInfo getRow = ReportProvincialInfo.builder()
                    .ENROLL_YEAR((String) row.get("ENROLL_YEAR"))
                    .ENROLL_SEMESTER((String) row.get("ENROLL_SEMESTER"))
                    .REGINAL_NO((String) row.get("REGINAL_NO"))
                    .REGINAL_NAME_THAI((String) row.get("REGIONAL_NAME_THAI"))
                    .FACULTY_NO((String) row.get("FACULTY_NO"))
                    .FACULTY_NAME_THAI((String) row.get("FACULTY_NAME_THAI"))
                    .MAJOR_NO((String) row.get("MAJOR_NO"))
                    .MAJOR_NAME_THAI((String) row.get("MAJOR_NAME_THAI"))
                    .CNT_ALL((BigDecimal) row.get("CNT_ALL"))
                    .CNT_RECEIPT((BigDecimal) row.get("CNT_RECEIPT"))
                    .CNT_STD_CODE((BigDecimal) row.get("CNT_STD_CODE"))
                    .CNT_CENT_ALL((BigDecimal) row.get("CNT_CENT_ALL"))
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
    
    public List<ReportProvincialInfo> findEnrollYearAndSemester() {
        List<ReportProvincialInfo> list = new ArrayList<ReportProvincialInfo>();
        String sql = "SELECT DISTINCT A.ENROLL_YEAR,A.ENROLL_SEMESTER FROM bcampus01.ass_stdregis_ru2 A ORDER BY A.ENROLL_YEAR";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }

    public List<ReportProvincialInfo> findAll(String year, String semester) {
        List<ReportProvincialInfo> list = new ArrayList<ReportProvincialInfo>();
        String sql = "SELECT a.enroll_year,a.enroll_semester,ru25.reginal_no,r.regional_name_thai,a.faculty_no,y.faculty_name_thai,a.major_no,x.major_name_thai,count(distinct a.application_id) cnt_all, sum(decode(b.receipt_pay_status,'2',1,0)) cnt_receipt,sum(decode(a.std_code,null,0,1)) cnt_std_code FROM bcampus01.ass_stdregis_ru2 a,bcampus01.ugb_major x,bcampus01.ugb_faculty y,bcampus01.ugb_regional_center r,(select distinct application_id,receipt_pay_status from bcampus01.ass_receipt) b,bcampus01.ass_certificate_end d ,bcampus01.ass_stdhis_ru25_new ru25 WHERE A.ENROLL_YEAR = '" + year + "' AND A.ENROLL_SEMESTER = '" + semester + "' AND a.application_id=b.application_id AND a.application_id=d.application_id AND a.faculty_no=y.faculty_no AND a.faculty_no=x.faculty_no AND a.major_no=x.major_no AND ru25.application_id=b.application_id AND ru25.reginal_no=r.regional_no GROUP BY a.enroll_year,a.enroll_semester,ru25.reginal_no,r.regional_name_thai,a.faculty_no,a.major_no,y.faculty_name_thai,x.major_name_thai ORDER BY ru25.reginal_no,a.faculty_no,a.major_no";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }
    //end find 

    public List<ReportProvincialInfo> findAllFaculty(String year, String semester) {
        List<ReportProvincialInfo> list = new ArrayList<ReportProvincialInfo>();
        String sql = "SELECT DISTINCT r.regional_name_thai,a.enroll_year,a.enroll_semester,ru25.reginal_no FROM bcampus01.ass_stdregis_ru2 a,bcampus01.ugb_major x,bcampus01.ugb_faculty y,bcampus01.ugb_regional_center r,(SELECT DISTINCT application_id,receipt_pay_status FROM bcampus01.ass_receipt) b,bcampus01.ass_certificate_end d,bcampus01.ass_stdhis_ru25_new ru25 WHERE A.ENROLL_YEAR = '" + year + "' AND A.ENROLL_SEMESTER = '" + semester + "' AND a.application_id=b.application_id AND a.application_id=d.application_id AND a.faculty_no=y.faculty_no AND a.faculty_no=x.faculty_no AND a.major_no=x.major_no AND ru25.application_id=b.application_id AND ru25.reginal_no=r.regional_no GROUP BY r.regional_name_thai,a.enroll_year,a.enroll_semester,ru25.reginal_no,a.faculty_no,a.major_no,y.faculty_name_thai,x.major_name_thai ORDER BY ru25.reginal_no";
        List<Map<String, Object>> result = db.queryList(sql);

        for (Map<String, Object> row : result) {

            list.add(setAltmodel(row));
        }
        return list;
    }
    //end find 
}
