package vo.Requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data


public class Notice {

    private int noticeID;
    private String n_title;
    private String n_content;
    private LocalDateTime n_createAt;
    private LocalDateTime n_updateAt;
    private int n_priority;
    private int mid;
}
