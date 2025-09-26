package controller.notice_controller;

import model.request_service.NoticeDAO;
import view.notice_view.NoticeAdminView;
import view.notice_view.NoticeUserView;
import vo.Requests.Notice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoticeControllerImpl implements Notice_Controller {

    private final NoticeAdminView adminView;
    private final NoticeUserView userView;
    private final NoticeDAO dao;
    private final boolean isAdmin;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public NoticeControllerImpl(boolean isAdmin) {
        this.adminView = new NoticeAdminView();
        this.userView = new NoticeUserView();
        this.dao = new NoticeDAO();
        this.isAdmin = isAdmin;
    }

    public void run() throws IOException {
        if (isAdmin) adminMenuLoop();
        else userMenuLoop();
    }

    private void adminMenuLoop() throws IOException {
        while (true) {
            int choice = adminView.noticeAdminMenu();
            switch (choice) {
                case 1 -> createNotice();
                case 2 -> updateNotice();
                case 3 -> deleteNotice();
                case 4 -> selectAll();
                case 5 -> selectNotice();
                case 6 -> {
                    System.out.println("뒤로갑니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }

    private void userMenuLoop() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int choice = userView.noticeUserMenu();
            switch (choice) {
                case 1 -> selectAll();
                case 2 -> selectNotice();
                case 3 -> {
                    System.out.println("뒤로갑니다.");
                    return;
                }
                default -> System.out.println("잘못된 입력입니다.");
            }
        }
    }


    @Override
    public int createNotice() {
        try {
            Notice notice = adminView.createNotice();
            int result = dao.createNotice(notice);
            System.out.println(result > 0 ? "공지 작성 완료" : "공지 작성 실패");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateNotice() {
        try {
            int noticeID = adminView.inputNoticeID("수정");
            Notice existing = dao.selectNotice(noticeID);
            if (existing == null) {
                System.out.println("공지 존재하지 않음");
                return 0;
            }

            Notice updated = adminView.createNotice();
            updated.setNoticeID(noticeID);
            int result = dao.updateNotice(updated);
            System.out.println(result > 0 ? "공지 수정 완료!" : "공지 수정 실패!");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteNotice() {
        try {
            int noticeID = adminView.inputNoticeID("삭제");
            int result = dao.deleteNotice(noticeID);
            System.out.println(result > 0 ? "공지 삭제 완료" : "공지 삭제 실패");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void selectAll() {
        List<Notice> notices = dao.selectAll();
        if (isAdmin) adminView.selectAll(notices);
        else userView.selectAll(notices);
    }

    @Override
    public void selectNotice() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            int noticeID;
            if (isAdmin) {
                noticeID = adminView.inputNoticeID("조회");
            } else {
                while (true) {
                    System.out.print("조회할 공지 ID를 입력해주세요: ");
                    try {
                        noticeID = Integer.parseInt(input.readLine().trim());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("ID는 숫자로 입력해주세요.");
                    }
                }
            }

            Notice notice = dao.selectNotice(noticeID);
            if (isAdmin) adminView.selectNotice(notice);
            else userView.selectNotice(notice);

        } catch (IOException e) {
            System.out.println("잘못된 입력입니다.");
        }
    }
}
