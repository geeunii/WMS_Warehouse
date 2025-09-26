DELIMITER &&

-- 문의 등록
CREATE PROCEDURE createRequest(IN p_uid INT, IN p_title VARCHAR(255), IN p_content TEXT,
                               IN p_type ENUM ('board','onetoone'))
BEGIN
INSERT INTO Request(uid, r_title, r_content, r_type, r_status, r_createdAt, r_updatedAt)
VALUES (p_uid, p_title, p_content, p_type, '대기', NOW(), NOW());
END &&

-- 답변 등록/수정
CREATE PROCEDURE updateResponse(IN p_requestID INT, IN p_response TEXT)
BEGIN
UPDATE Request
SET r_response  = p_response,
    r_status    = '답변완료',
    r_updatedAt = NOW()
WHERE requestID = p_requestID;
END &&

-- 사용자 글 수정
CREATE PROCEDURE updateRequest(IN p_requestID INT, IN p_uid INT, IN p_title VARCHAR(255), IN p_content TEXT)
BEGIN
UPDATE Request
SET r_title       = p_title,
    r_content     = p_content,
    r_updatedAt = NOW()
WHERE requestID = p_requestID
  AND uid = p_uid;
END &&

-- 글 삭제
CREATE PROCEDURE deleteRequest(IN p_requestID INT, IN p_uid INT)
BEGIN
DELETE
FROM Request
WHERE requestID = p_requestID
  AND uid = p_uid;
END &&

-- 타입별 전체 조회
CREATE PROCEDURE selectAllRequests(IN p_type ENUM ('board','onetoone'))
BEGIN
SELECT *
FROM Request
WHERE r_type = p_type
ORDER BY r_createdAt DESC;
END &&

-- 사용자 본인 글 조회
CREATE PROCEDURE selectMyRequest(IN p_uid INT)
BEGIN
SELECT *
FROM Request
WHERE uid = p_uid
ORDER BY r_createdAt DESC;
END &&

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE NoticeProcedure(
    IN p_action VARCHAR(20), -- 'CREATE', 'UPDATE', 'DELETE', 'SELECT', 'SELECT_ONE'
    IN p_noticeID INT, -- 수정, 삭제, 상세 조회 시 사용
    IN p_title VARCHAR(100),
    IN p_content VARCHAR(255),
    IN p_priority INT,
    IN p_mid INT
)
BEGIN
    IF p_action = 'CREATE' THEN
        INSERT INTO Notice(n_title, n_content, n_createAt, n_updateAt, n_priority, mid)
        VALUES (p_title, p_content, NOW(), NOW(), p_priority, p_mid);

    ELSEIF p_action = 'UPDATE' THEN
        UPDATE Notice
        SET n_title    = p_title,
            n_content  = p_content,
            n_updateAt = NOW(),
            n_priority = p_priority
        WHERE noticeID = p_noticeID;

    ELSEIF p_action = 'DELETE' THEN
        DELETE FROM Notice WHERE noticeID = p_noticeID;

    ELSEIF p_action = 'SELECT' THEN
        SELECT noticeID, n_title, n_content, n_createAt, n_updateAt, n_priority, mid
        FROM Notice
        ORDER BY n_priority DESC, n_createAt DESC;

    ELSEIF p_action = 'SELECT_ONE' THEN
        SELECT noticeID, n_title, n_content, n_createAt, n_updateAt, n_priority, mid
        FROM Notice
        WHERE noticeID = p_noticeID;

    ELSE
        SELECT '잘못된 action 값입니다. CREATE, UPDATE, DELETE, SELECT, SELECT_ONE 중 하나를 사용하세요.' AS message;
    END IF;
END $$

DELIMITER ;