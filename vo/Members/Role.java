package vo.Members;

// vo/Members/Role.java
public enum Role { Master, Admin } // 현재 enum 상수에 맞춤(대/소문자 주의)

// 공백 입력이면 "유지"를 위해 null 반환하도록 설계
final class RoleMapper {
    private RoleMapper() {}
    public static Role fromInput(String s) {
        if (s == null) return null;
        String v = s.trim();
        if (v.isEmpty()) return null;             // 공백 → 기존값 유지
        switch (v.toLowerCase()) {
            case "총관리자":
            case "마스터":
            case "master": return Role.Master;
            case "관리자":
            case "admin":  return Role.Admin;
            default:       return null;           // 모르면 유지 (원하면 예외로 바꿔도 됨)
        }
    }
}

