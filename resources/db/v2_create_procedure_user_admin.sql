
-- admin login
create
    definer = root@`%` procedure adminlogin(IN admin_id varchar(50), IN admin_pw varchar(50))
begin
    select * from Admins where adminID = admin_id and adminPW = admin_pw;
end ;

-- 관리자 비번 변경 
create
    definer = root@`%` procedure changeAdminPassword(IN p_admin_id varchar(50), IN p_new_pw varchar(255),
                                                     OUT p_result int)
BEGIN
    DECLARE v_exists INT DEFAULT 0;

    -- 1) 존재여부 체크
    SELECT COUNT(*) INTO v_exists
    FROM Admins
    WHERE adminID = p_admin_id;

    IF v_exists = 0 THEN
        SET p_result = -1;  -- 해당 ID 없음
    ELSE
        -- 2) 동일 비번이면 업데이트해도 ROW_COUNT()가 0이므로, 미리 분기
        IF EXISTS (SELECT 1 FROM Admins WHERE adminID = p_admin_id AND adminPW = p_new_pw) THEN
            SET p_result = 0;  -- 변경 없음(같은 비번)
        ELSE
            UPDATE Admins
            SET adminPW   = p_new_pw
            WHERE adminID   = p_admin_id;
            SET p_result = 1;  -- 변경 성공
        END IF;
    END IF;
END;

-- 사용자 비번 변경 
create
    definer = root@`%` procedure changeUserPassword(IN p_user_id varchar(50), IN p_phone varchar(50),
                                                    IN p_new_pw varchar(255), OUT p_result int)
BEGIN
    DECLARE v_exists INT DEFAULT 0;

    -- 1) 존재 여부 (아이디+전화번호 매칭)
    SELECT COUNT(*) INTO v_exists
    FROM Users
    WHERE userID = p_user_id
      AND phone  = p_phone;

    IF v_exists = 0 THEN
        SET p_result = -1; -- 해당 사용자 없음
    ELSE
        -- 2) 기존 비번과 같은지 확인 (해시를 쓰는 경우 해시값 비교)
        IF EXISTS (
            SELECT 1 FROM Users
            WHERE userID = p_user_id
              AND phone  = p_phone
              AND userPW = p_new_pw
        ) THEN
            SET p_result = 0;  -- 변경 없음
        ELSE
            -- 3) 업데이트
            UPDATE Users
            SET userPW   = p_new_pw,
                createAt = NOW()   -- 컬럼명에 맞게 조정
            WHERE userID = p_user_id
              AND phone  = p_phone;

            SET p_result = 1;  -- 성공
        END IF;
    END IF;
END;

-- 사용자 정보 삭제
create
    definer = root@`%` procedure deleteUserInfo(IN user_id varchar(50))
BEGIN
    DELETE FROM Users WHERE userID = user_id;
end;


-- 어드민 아이디 찾기
create
    definer = root@`%` procedure findAdminID(IN admin_name varchar(50), IN admin_phone varchar(50))
begin
    select * from Admins where adminName = admin_name and adminPhone = admin_phone;
end;

-- 사용자 아이디 찾기
create
    definer = root@`%` procedure findUserID(IN uname varchar(50), IN uphone varchar(50))
begin
    select * from Users where name = uname and phone = uphone;
end;

-- 관리자 회원 가입
create
    definer = root@`%` procedure registerAdmin(IN arole varchar(50), IN admin_id varchar(50), IN admin_pw varchar(50),
                                               IN admin_name varchar(50), IN admin_phone varchar(50))
begin
    insert Admins(role, adminID, adminPW, adminName, adminPhone) values (arole, admin_id, admin_pw, admin_name, admin_phone);
end;

-- 사용자 회원 가입
create
    definer = root@`%` procedure registerUser(IN name varchar(50), IN userid varchar(50), IN userpw varchar(50),
                                              IN phone varchar(50), IN account varchar(50), IN adminCheck varchar(50))
begin
        insert Users(name, userID, userPW, phone, account, createAt, adminCheck) values(name, userid, userpw, phone, account, now(), adminCheck);
    end;

-- 사용자 정보 검색 
create
    definer = root@`%` procedure searchUserInfo(IN u_id varchar(50))
BEGIN
    select uID, name, userID, phone, account, createAt from Users where uID = u_id;
end;

-- 어드민 삭제
create
    definer = root@`%` procedure sp_admin_delete_by_id(IN p_admin_id varchar(50))
BEGIN
    DELETE FROM Admins WHERE adminID = p_admin_id;

END;

-- 어드민 정보 확인 
create
    definer = root@`%` procedure sp_admin_get_by_mid(IN p_mid varchar(50))
BEGIN
    SELECT adminID, adminPW, adminName, role, adminPhone
    FROM Admins
    WHERE mID = p_mid;
END;

-- 어드민 정보 수정
create
    definer = root@`%` procedure sp_admin_update_by_id(IN p_admin_id varchar(50), IN p_admin_pw varchar(255),
                                                       IN p_admin_nm varchar(100), IN p_role varchar(20),
                                                       IN p_phone varchar(50), IN o_admin_id varchar(50))
BEGIN
    UPDATE Admins
    SET adminID = p_admin_id,
        adminPW  = COALESCE(p_admin_pw, adminPW),
        adminName= COALESCE(p_admin_nm, adminName),
        role     = COALESCE(p_role, role),
        adminPhone = COALESCE(p_phone, adminPhone)
    WHERE adminID  = o_admin_id;
END;

-- 사용자 승인 변경 
create
    definer = root@`%` procedure sp_user_change_admin_check(IN p_user_id varchar(50), IN p_status varchar(20))
BEGIN
    UPDATE Users
    SET adminCheck = p_status
    WHERE userID = p_user_id;

END;

-- 사용자 정보 확인 (한 명) 
create
    definer = root@`%` procedure sp_user_get_by_id(IN p_user_id varchar(50))
BEGIN
    SELECT uid, name, userID, userPW, phone, account, createAt, adminCheck
    FROM Users
    WHERE userID = p_user_id;
END;

-- 사용자 전체 정보 확인 
create
    definer = root@`%` procedure sp_user_list_all()
BEGIN
    SELECT *
    FROM Users
    ORDER BY createAt DESC, uid DESC;
END;

-- 승인 대기 사용자 정보 확인 
create
    definer = root@`%` procedure sp_user_list_by_status(IN p_status varchar(20))
BEGIN
    SELECT uid, name, userID, phone, account, createAt, adminCheck
    FROM Users
    WHERE adminCheck = p_status
    ORDER BY createAt DESC, uid DESC;
END;


-- 사용자 정보 수정 
create
    definer = root@`%` procedure updateUserInfo(IN newname varchar(50), IN new_user_id varchar(50),
                                                IN newphone varchar(50), IN newaccount varchar(50),
                                                IN user_id varchar(50))
begin
        update Users set name = newname, userId = new_user_id, phone = newphone, account = newaccount where userID = user_id;
    end;

-- 사용자 로그인 
create
    definer = root@`%` procedure userlogin(IN user_id varchar(50), IN user_pw varchar(50))
begin
    select * from Users where userID = user_id and userPW = user_pw;
end;