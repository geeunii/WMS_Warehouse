-- ===== Users 시드 (id 생략, AUTO_INCREMENT) =====
INSERT INTO `Users` (name, userID, userPW, phone, account, createAt, adminCheck) VALUES
('김민수',  'minsu01',  'pass1234!', '010-1111-2222', 'KB-123-456-789012', '2025-09-26', 'Waiting'),
('이수연',  'suyon99',  'pw@2024',  '010-3333-4444', 'SH-987-654-321000', '2025-09-26', 'Approval'),
('박지훈',  'jihoon7',  'abcDEF12', '010-5555-6666', 'NH-110-220-330440', '2025-09-26', 'Cancel'),
('최유리',  'yuri_c',   '!Qaz2wsx', '010-7777-8888', 'KB-222-333-444555', '2025-09-26', 'Approval'),
('정해인',  'hainn',    'p@ssW0rd', '010-9999-0000', 'HN-555-666-777888', '2025-09-26', 'Waiting'),
('오지현',  'jihyun_o', 'Zxcv!234', '010-1212-3434', 'SH-333-222-111000', '2025-09-26', 'Approval');

-- ===== Admin 시드 (id 생략, AUTO_INCREMENT) =====
INSERT INTO `Admins` (adminID, adminPW, adminName, adminPhone, role) VALUES
('master01', 'root!234', '총관리자_김팀장', '010-1000-2000', 'Master'),
('adminA',   'ad!n2025', '관리자_박주임',   '010-3000-4000', 'Admin'),
('adminB',   'ad#B7788', '관리자_이대리',   '010-5000-6000', 'Admin'),
('master02', 'Root#567', '총관리자_최부장', '010-7000-8000', 'Master');

select * from Users;
select * from Admins;
