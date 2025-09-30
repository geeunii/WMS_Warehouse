package vo.Members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin {
    private int mid;
    private Role role;
    private String adminID;
    private String adminPW;
    private String adminName;
    private String adminPhone;
}
