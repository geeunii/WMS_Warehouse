package view.request_view;

import vo.Requests.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class RequestAdminView {
    private final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // --- 메인 메뉴 ---
    public int mainMenu() throws IOException {
        while (true) {
            System.out.println("\n--- 게시판 ---");
            System.out.println("1. 문의관리 | 2. 공지사항 관리 | 3. 뒤로가기");
            System.out.print("[메뉴 선택]: ");
            try {
                int choice = Integer.parseInt(input.readLine().trim());
                if (choice >= 1 && choice <= 3) return choice;
            } catch (NumberFormatException e) { System.out.println("숫자를 입력해주세요."); }
            System.out.println("1~3 사이의 숫자를 입력해주세요.");
        }
    }

    // --- 문의 관리 메뉴 ---
    public int requestMenu() throws IOException {
        while (true) {
            System.out.println("\n--- 문의 관리 ---");
            System.out.println("1. 1:1 문의 관리");
            System.out.println("2. 문의 게시판 관리");
            System.out.println("3. 뒤로가기");
            System.out.print("[메뉴 선택]: ");
            try {
                int choice = Integer.parseInt(input.readLine().trim());
                if (choice >= 1 && choice <= 3) return choice;
            } catch (NumberFormatException e) { System.out.println("숫자를 입력해주세요."); }
            System.out.println("1~3 사이의 숫자를 입력해주세요.");
        }
    }

    // --- 1:1 문의 관리 메뉴 ---
    public int oneToOneMenu() throws IOException {
        while (true) {
            System.out.println("\n--- 1:1 문의 관리 ---");
            System.out.println("1. 1:1 문의 조회");
            System.out.println("2. 답변/답변 수정 등록");
            System.out.println("3. 뒤로가기");
            System.out.print("[메뉴 선택]: ");
            try {
                int choice = Integer.parseInt(input.readLine().trim());
                if (choice >= 1 && choice <= 3) return choice;
            } catch (NumberFormatException e) { System.out.println("숫자를 입력해주세요."); }
            System.out.println("1~3 사이의 숫자를 입력해주세요.");
        }
    }

    // --- 문의 게시판 메뉴 ---
    public int requestBoardMenu() throws IOException {
        while (true) {
            System.out.println("\n--- 문의 게시판 관리 ---");
            System.out.println("1. 모든 문의 조회");
            System.out.println("2. 답변/답변 수정 등록");
            System.out.println("3. 특정 문의 삭제");
            System.out.println("4. 뒤로가기");
            System.out.print("[메뉴 선택]: ");
            try {
                int choice = Integer.parseInt(input.readLine().trim());
                if (choice >= 1 && choice <= 4) return choice;
            } catch (NumberFormatException e) { System.out.println("숫자를 입력해주세요."); }
            System.out.println("1~4 사이의 숫자를 입력해주세요.");
        }
    }

    // --- 전체 문의 조회 ---
    public void selectAllRequests(List<Request> requests) {
        if (requests == null || requests.isEmpty()) {
            System.out.println("등록된 문의가 없습니다.");
            return;
        }
        System.out.println("\n--- 문의 목록 ---");
        System.out.printf("%-5s %-30s %-10s %-16s %-10s %-10s\n", "ID", "제목", "유형", "작성일", "상태", "답변");
        for (Request req : requests) {
            String type = req.getR_type() != null ? req.getR_type().name() : "없음";
            String date = req.getR_createAt() != null ? req.getR_createAt().format(fmt) : "없음";
            String response = req.getR_response() != null ? req.getR_response() : "-";
            System.out.printf("%-5d %-30s %-10s %-16s %-10s %-10s\n",
                    req.getRequestID(), req.getR_title(), type, date, req.getR_status(), response);
        }
    }

    // --- 답변/수정 입력 ---
    public Request updateResponse() throws IOException {
        System.out.print("답변/수정할 문의 ID 입력: ");
        int id;
        try { id = Integer.parseInt(input.readLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID는 숫자로 입력해주세요."); return null; }

        System.out.print("답변 내용을 입력해주세요: ");
        String content = input.readLine().trim();
        if (content.isEmpty()) { System.out.println("답변은 비워둘 수 없습니다."); return null; }

        System.out.print("정말 답변/수정하시겠습니까? (Y/N): ");
        if (!input.readLine().equalsIgnoreCase("Y")) { System.out.println("수정이 취소되었습니다."); return null; }


        Request req = new Request();
        req.setRequestID(id);
        req.setR_response(content);
        return req;
    }

    // --- 문의 삭제 ---
    public int deleteRequest() throws IOException {
        System.out.print("삭제할 문의 ID 입력: ");
        int id;
        try { id = Integer.parseInt(input.readLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID는 숫자로 입력해주세요."); return -1; }

        System.out.print("정말 삭제하시겠습니까? (Y/N): ");
        if (!input.readLine().equalsIgnoreCase("Y")) { System.out.println("삭제가 취소되었습니다."); return -1; }
        return id;
    }
}
