-- ▶ FileInfo
INSERT INTO file_info_tb(id, type) 
VALUES (1, '플레이어프로필'), (2, '플레이어프로필'),
       (3, '기업프로필'), (4, '기업프로필'), 
       (5, '경기장사진'), (6, '경기장사진'),
       (7, '경기장사진'), (8, '경기장사진'),
       (9, '코트사진'), (10, '코트사진'),
       (11, '코트사진'), (12, '코트사진'), 
       (13, '코트사진'), (14, '코트사진'),

       -- 추가 경기장사진
       (15, '경기장사진'), (16, '경기장사진'),
       (17, '경기장사진'), (18, '경기장사진'),
       (19, '경기장사진'), (20, '경기장사진'),
       (21, '경기장사진'), (22, '경기장사진'),
       (23, '경기장사진'), (24, '경기장사진'),
       (25, '경기장사진'), (26, '경기장사진'),

       -- 추가 코트사진
       (27, '코트사진'), (28, '코트사진'),
       (29, '코트사진'), (30, '코트사진'),
       (31, '코트사진'), (32, '코트사진');

INSERT INTO file_tb(id, file_info_id, file_url)
VALUES (1, 1, ''), (2, 2, ''),
       (3, 3, ''), (4, 4, ''),
       (5, 5, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%95%BC%EA%B5%AC_Stadium.png'), (6, 6, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%95%BC%EA%B5%AC_Stadium.png'),
       (7, 7, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%86%8D%EA%B5%AC_Stadium.png'), (8, 8, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%86%8D%EA%B5%AC_Stadium.png'),
       (9, 9, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EC%95%BC%EA%B5%AC_Court.png'), (10, 10, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EC%95%BC%EA%B5%AC_Court.png'), 
       (11, 11, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%86%8D%EA%B5%AC_Court.png'), (12, 12, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%86%8D%EA%B5%AC_Court.png'), 
       (13, 13, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%86%8D%EA%B5%AC_Court.png'), (14, 14, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%86%8D%EA%B5%AC_Court.png'), 
       (15, 15, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%B6%95%EA%B5%AC_Stadium.png'), (16, 16, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B0%B0%EA%B5%AC_Stadium.png'), 
       (17, 17, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B0%B0%EA%B5%AC_Stadium.png'), (18, 18, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B0%B0%EA%B5%AC_Stadium.png'), 
       (19, 19, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%92%8B%EC%82%B4%EC%9E%A5_Stadium.png'), (20, 20, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B3%BC%EB%A7%81_Stadium.png'), 
       (21, 21, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B3%BC%EB%A7%81_Stadium.png'), (22, 22, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EB%B3%BC%EB%A7%81_Stadium.png'), 
       (23, 23, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%83%81%EA%B5%AC_Stadium.png'), (24, 24, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%85%8C%EB%8B%88%EC%8A%A4_Stadium.png'), 
       (25, 25, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%85%8C%EB%8B%88%EC%8A%A4_Stadium.png'), (26, 26, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%ED%85%8C%EB%8B%88%EC%8A%A4_Stadium.png'), 
       (27, 27, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png'), (28, 28, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png'), 
       (29, 29, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png'), (30, 30, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png'), 
       (31, 31, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png'), (32, 32, 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EB%B3%BC%EB%A7%81_Court.png');

-- ▶ 스포츠 종류 
INSERT INTO sport_category_tb(id, sport, created_at) 
VALUES (1, '야구', now()), (5, '풋살', now()), 
       (2, '축구', now()),  (6, '볼링', now()),
       (3, '배구', now()),  (7, '탁구', now()),
       (4, '농구', now()),  (8, '테니스', now());
       

-- ▶ 개인정보 
INSERT INTO user_tb(id, nickname, password, email, role, status, created_at, updated_at) 
-- 1)정보수정 진행한 상태, 2) 회원가입 직전으로 나뉨
-- 공백이나 default 지정시 nickname unique 제약조건에 걸림
VALUES (1, 'ssar123', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'ssar@nate.com', 'PLAYER', '일반회원', now(), now()), 
       (2, 'user316', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'dope@nate.com', 'PLAYER', '일반회원', now(), now()), 
       (3, 'baseball451', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'cos@nate.com', 'COMPANY', '인증완료', now(), now()),
       (4, 'user512', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'love@nate.com', 'COMPANY', '인증완료', now(), now());

INSERT INTO player_info_tb(id, user_id, gender, age, address, tel, file_info_id, updated_at) 
VALUES (1, 1, '남자', 'AGE_20', '부산시','010-1001-1111', 1, now()),  
       (2, 2, '', '', '','', 2, now()); 

INSERT INTO company_info_tb(id, user_id, business_number, business_address, tel, ceo, file_info_id, updated_at) 
VALUES (1, 3, '111-11-11111', '부산시 연제구', '010-1001-2222', '', 3, now()), -- view에 ceo이름 없는상태
       (2, 4, '', '', '', '', 4, now());
       

-- ▶ 경기장 & 코트
INSERT INTO stadium_tb(id, company_info_id, name, tel, category_id, address, lat, lon, file_info_id, start_time, end_time, status, created_at, updated_at)
VALUES (1, 1, 'a 야구장', '010-0234-5678', 1, '부산시', 35.1846, 128.9863, 5, '09:00', '18:00', '운영중', Now(), NOW()),
       (2, 1, 'b 야구장', '010-1234-5678', 1, '부산시', 35.2098, 129.0811, 6, '09:00', '18:00', '운영중', Now(), NOW()),
       (3, 2, 'a 농구장', '010-2234-5678', 4, '울산시', 37.2936, 126.8679, 7, '09:00', '18:00', '운영중', Now(), NOW()),
       (4, 2, 'b 농구장', '010-3234-5678', 4, '울산시', 37.3226, 126.8593, 8, '09:00', '18:00', '운영중', Now(), NOW()),

       (5, 2, 'a 축구장', '010-4234-5672', 2, '울산시', 37.3226, 126.8593, 15, '09:00', '18:00', '운영중', Now(), NOW()),

       (6, 2, 'a 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 16, '09:00', '18:00', '운영중', Now(), NOW()),
       (7, 2, 'b 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 17, '09:00', '18:00', '운영중', Now(), NOW()),
       (8, 2, 'c 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 18, '09:00', '18:00', '운영중', Now(), NOW()),

       (9, 2, 'a 풋살장', '010-4234-5675', 5, '울산시', 37.3226, 126.8593, 19, '09:00', '18:00', '운영중', Now(), NOW()),

       (10, 2, 'a 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 20, '09:00', '18:00', '운영중', Now(), NOW()),
       (11, 2, 'b 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 21, '09:00', '18:00', '운영중', Now(), NOW()),
       (12, 2, 'c 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 22, '09:00', '18:00', '운영중', Now(), NOW()),

       (13, 2, 'a 탁구장', '010-4234-5677', 7, '울산시', 37.3226, 126.8593, 23, '09:00', '18:00', '운영중', Now(), NOW()),
       
       (14, 2, 'a 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 24, '09:00', '18:00', '운영중', Now(), NOW()),
       (15, 2, 'b 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 25, '09:00', '18:00', '운영중', Now(), NOW()),
       (16, 2, 'c 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 26, '09:00', '18:00', '운영중', Now(), NOW());

INSERT INTO stadium_court_tb(id, stadium_id, file_info_id, court_price, capacity, title, content, status, created_at, updated_at) 
VALUES (1, 1, 9, 40000, 22, 'a 야구장(코트1)','최신 시설 야구장', '등록완료', now(), now()), 

       (2, 2, 10, 60000, 22, 'b 야구장(코트1)','선수 운영 야구장', '등록완료', now(), now()), 

       (3, 3, 11, 30000, 10, 'a 농구장(코트1)','그물상태 양호 농구장', '등록완료', now(), now()),
       (4, 3, 12, 30000, 10, 'a 농구장(코트2)','코트상태 양호 농구장', '등록완료', now(), now()), 

       (5, 4, 13, 50000, 10, 'b 농구장(코트1)','교통편리 위치 농구장', '등록완료', now(), now()), 
       (6, 4, 14, 50000, 10, 'b 농구장(코트2)','생수 무료제공 농구장', '등록완료', now(), now()),

       (7, 10, 27, 20000, 10, 'a 볼링장(코트1)', '잘 나가는 볼링장', '등록완료', now(), now()),
       (8, 10, 28, 20000, 10, 'a 볼링장(코트2)', '스트라이크 맛집', '등록완료', now(), now()),
       (9, 10, 29, 20000, 10, 'a 볼링장(코트3)', '분위기 좋아요', '등록완료', now(), now()),

       (10, 12, 30, 30000, 10, 'c 볼링장(코트1)', '에어컨 빵빵한 볼링장', '등록완료', now(), now()),
       (11, 12, 31, 30000, 10, 'c 볼링장(코트2)', '스핀 맛집 볼링장', '등록완료', now(), now()),
       (12, 12, 32, 30000, 10, 'c 볼링장(코트3)', '600점 이상 시 상품지급', '등록완료', now(), now());

        

-- ▶ 결제
INSERT INTO court_payment_tb(id, payment_type, payment_amount, player_info_id, company_info_id, origin_data, status, created_at)
VALUES (1, '카드결제', 40000, 1, 1, '', '결제완료', now()),
       (2, '계좌이체', 50000, 2, 2, '', '결제완료', now());


-- ▶ 예약
INSERT INTO court_reservation_tb (id, user_id, court_payment_id, reservation_date, reservation_time, status, created_at)
VALUES (1, 1, 1, '2023-06-11', '4', '승낙', NOW()),
       (2, 2, 2, '2023-05-11', '5', '승낙', NOW());

-- ▶ 선호 스포츠
INSERT INTO user_favorite_sport_tb(id, player_info_id, category_id)
VALUES (1, 1, 2), (2, 1, 4),
       (3, 1, 5), (4, 1, 6),
       (5, 2, 1), (6, 2, 2);

------------------------ 2단계 이후 예정
-- -- 매칭게시글
-- INSERT INTO board_tb(id, user_id, stadium_court_id, category_id, title, content, start_time, end_time, status, attendance_count, attendance_capacity, man_player_count, woman_player_count, average_age, price, created_at) 
-- VALUES (1, 1, 1, 1, '테니스 복식 한 세트 모집', '재밌게 치실분 구해요~', '2023-06-11 14:00:00', '2023-06-11 16:00:00', 0, 2, 4, 1, 1, 32, 10000, NOW()),
--        (2, 4, 2, 1, '테니스 한 세트 초보분 구해요', '초보 환영!!', '2023-06-11 10:00:00', '2023-06-11 11:00:00', 0, 1, 2, 0, 1, 24, 15000, NOW()),
--        (3, 3, 5, 3, '풋살 한 게임 하실분 모집 합니다!!', '진지하신분은 불가능이용..', '2023-06-03 09:00:00', '2023-06-03 10:00:00', 0, 3, 10, 3, 0, 23, 5000, NOW());

-- -- 매칭 신청
-- INSERT INTO board_apply_tb(id, user_id, stadium_court_id, board_id, personnel, man_player_count, woman_player_count, average_age, status, created_at)
-- VALUES (1, 4, 1, 1, 1, 1, 0, 41, 0, NOW()),
--        (2, 4, 2, 2, 1, 1, 0, 41, 0, NOW()),
--        (3, 7, 2, 1, 1, 1, 0, 72, 0, NOW()),
--        (4, 5, 5, 3, 5, 5, 0, 23, 0, NOW()),
--        (5, 6, 2, 2, 1, 0, 1, 36, 0, NOW());

