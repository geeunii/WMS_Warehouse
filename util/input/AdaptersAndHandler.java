package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class AdaptersAndHandler {

    // -------- Scanner / BufferedReader 어댑터 --------
    public static class ScannerAdapter implements ReaderAdapter {
        private final Scanner sc;
        public ScannerAdapter(Scanner sc) { this.sc = Objects.requireNonNull(sc); }
        @Override public String readLine() {
            try { return sc.nextLine(); }
            catch (NoSuchElementException e) { throw new InputException("입력 스트림 종료 감지", e); }
            catch (IllegalStateException e) { throw new InputException("Scanner 사용 불가 상태", e); }
        }
    }

    public static class BufferedReaderAdapter implements ReaderAdapter {
        private final BufferedReader br;
        public BufferedReaderAdapter(BufferedReader br) { this.br = Objects.requireNonNull(br); }
        @Override public String readLine() throws IOException { return br.readLine(); }
    }

    // ---------------- 공용 입력 핸들러 ----------------
    public static class InputHandler {
        private final ReaderAdapter in;
        public InputHandler(ReaderAdapter in) { this.in = in; }

        public String readString(String prompt, boolean allowEmpty) {
            while (true) {
                if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
                final String line;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    System.out.println("[입력 오류] 라인을 읽는 중 문제가 발생. 다시 입력하세요.");
                    continue;
                } catch (InputException e) {
                    throw e; // 스트림 종료 등 복구 불가
                }
                if (line == null) throw new InputException("입력 스트림이 종료됨(EOL/EOF)");
                if (!allowEmpty && line.trim().isEmpty()) {
                    System.out.println("[안내] 빈 값은 허용되지 않습니다. 다시 입력하세요.");
                    continue;
                }
                return line;
            }
        }

        public int readInt(String prompt, Integer minInclusive, Integer maxInclusive) {
            while (true) {
                String s = readString(prompt, false).trim();
                try {
                    int v = Integer.parseInt(s);
                    if (minInclusive != null && v < minInclusive) {
                        System.out.println("[검증 실패] 최소 " + minInclusive + " 이상이어야 합니다.");
                        continue;
                    }
                    if (maxInclusive != null && v > maxInclusive) {
                        System.out.println("[검증 실패] 최대 " + maxInclusive + " 이하여야 합니다.");
                        continue;
                    }
                    return v;
                } catch (NumberFormatException e) {
                    System.out.println("[형식 오류] 정수 형식이 아닙니다. 다시 입력하세요.");
                }
            }
        }

        public boolean readYesNo(String prompt) {
            while (true) {
                String s = readString(prompt + " (Y/N): ", false).trim().toLowerCase();
                if (s.equals("y") || s.equals("yes")) return true;
                if (s.equals("n") || s.equals("no")) return false;
                System.out.println("[안내] Y 또는 N 으로 입력하세요.");
            }
        }
    }
}
