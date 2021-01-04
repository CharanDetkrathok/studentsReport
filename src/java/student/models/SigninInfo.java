package student.models;

import lombok.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninInfo {
    private String ID;
    private String USERNAME;
    private String PASSWORD;
    private String STATUS;
}
