package controller.notice_controller;

public interface Notice_Controller {
    int createNotice();       // 공지 작성
    int updateNotice();       // 공지 수정
    int deleteNotice();       // 공지 삭제
    void selectAll();         // 전체 공지 조회
    void selectNotice();      // 공지 상세 보기
}
