package view.notice_view;

import vo.Requests.Notice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoticeAdminView {
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");



    public int noticeAdminMenu() {
        while (true) {
            System.out.println("\n--- 관리자 공지 메뉴 ---");
            System.out.println("1. 공지 작성");
            System.out.println("2. 공지 수정");
            System.out.println("3. 공지 삭제");
            System.out.println("4. 전체 공지 조회");
            System.out.println("5. 공지 상세 보기");
            System.out.println("6. 뒤로가기");
            System.out.print("메뉴 선택: ");
            try {
                int choice = Integer.parseInt(input.readLine().trim());
                if (choice >= 1 && choice <= 6) return choice;
            } catch (IOException | NumberFormatException ignored) {}
            System.out.println("잘못된 입력입니다. 1~6 사이의 숫자를 입력해주세요.");
        }
    }

    public Notice createNotice() throws IOException {
        Notice notice = new Notice();
        System.out.print("제목: ");
        notice.setN_title(input.readLine().trim());
        System.out.print("내용: ");
        notice.setN_content(input.readLine().trim());

        while (true) {
            System.out.print("우선순위(숫자, 기본 0): ");
            String line = input.readLine().trim();
            if (line.isEmpty()) {
                notice.setN_priority(0);
                break;
            }
            try {
                notice.setN_priority(Integer.parseInt(line));
                break;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력 가능합니다.");
            }
        }
        return notice;
    }

    public void selectAll(List<Notice> notices) {
        if (notices == null || notices.isEmpty()) {
            System.out.println("공지사항이 없습니다.");
            return;
        }
        System.out.printf("%-5s %-30s %-20s %-10s\n", "ID", "제목", "작성일", "우선순위");
        for (Notice n : notices) {
            String createAt = n.getN_createAt() != null ? n.getN_createAt().format(formatter) : "-";
            System.out.printf("%-5d %-30s %-20s %-10d\n",
                    n.getNoticeID(), n.getN_title(), createAt, n.getN_priority());
        }
    }

    public void selectNotice(Notice n) {
        if (n == null) {
            System.out.println("공지사항이 존재하지 않습니다.");
            return;
        }
        System.out.println("\n--- 공지 상세 ---");
        System.out.println("ID: " + n.getNoticeID());
        System.out.println("제목: " + n.getN_title());
        System.out.println("내용: " + n.getN_content());
        System.out.println("작성일: " + (n.getN_createAt() != null ? n.getN_createAt().format(formatter) : "-"));
        System.out.println("수정일: " + (n.getN_updateAt() != null ? n.getN_updateAt().format(formatter) : "-"));
        System.out.println("우선순위: " + n.getN_priority());
    }

    // 각 동작에서 직접 ID 입력
    public int inputNoticeID(String action) throws IOException {
        while (true) {
            System.out.print(action + "할 공지 ID를 입력해주세요: ");
            try {
                return Integer.parseInt(input.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ID는 숫자로 입력해주세요.");
            }
        }
    }
}
