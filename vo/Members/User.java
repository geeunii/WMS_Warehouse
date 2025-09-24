package vo.Members;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {

    private int uid;
    private String name;
    private String userID;
    private String userPW;
    private String phone;
    private String account;
    private Date createAt;
    private AdminCheck adminCheck;

}
