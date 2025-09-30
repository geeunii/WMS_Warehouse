    package vo.Requests;

    import lombok.Data;
    import java.time.LocalDateTime;

    @Data
    public class Request {
        private int requestID;
        private int uid;
        private String r_title;
        private String r_content;
        private String r_response;
        private LocalDateTime r_createAt;
        private LocalDateTime r_updateAt;
        private String r_status;
        private RequestType r_type;

        public enum RequestType {
            board, onetoone
        }
    }