package student.models;

import java.math.BigDecimal;
import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportProvincialInfo {

    public String ENROLL_YEAR;
    public String ENROLL_SEMESTER;
    public String REGINAL_NO;
    public String REGINAL_NAME_THAI;
    public String FACULTY_NO;
    public String FACULTY_NAME_THAI;
    public String MAJOR_NO;
    public String MAJOR_NAME_THAI;
    public BigDecimal CNT_ALL;
    public BigDecimal CNT_RECEIPT;
    public BigDecimal CNT_STD_CODE;
    public BigDecimal CNT_CENT_ALL;
    public String SUM1;
    public String SUM2;
    public String SUM3;
    public String SUM4;
    public String SUM5;
    public String TOTAL;
    public String TOTAL_COLUMN;
}
