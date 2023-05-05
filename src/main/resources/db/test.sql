-- ▶ FileInfo
INSERT INTO file_info_tb(id, type) 
VALUES (1, '플레이어프로필'), (2, '기업프로필'),
       (3, '경기장사진'), (4, '코트사진');


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
       (31, '코트사진'), (32, '코트사진'),

       -- Admin 경기장사진
       (33, '경기장사진'), (34, '경기장사진'),
       (35, '경기장사진'), (36, '경기장사진'),
       (37, '경기장사진'), (38, '경기장사진');


INSERT INTO file_tb(id, file_info_id, file_name, file_url)
VALUES (1, 1, 'PlayerProfile/player.jpg', 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/PlayerProfile/player.jpg'),
       (3, 3, 'CompanyProfile/company.jpg', 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/CompanyProfile/company.jpg'), 
       (4, 4, 'Stadium/야구_Stadium.png', 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Stadium/%EC%95%BC%EA%B5%AC_Stadium.png'),
       (5, 5, 'Court/야구_Court.png', 'https://3-sporting.s3.ap-northeast-2.amazonaws.com/Court/%EC%95%BC%EA%B5%AC_Court.png');


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
       (3, 'baseball451', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'cos@nate.com', 'COMPANY', '기업회원', now(), now()),
       (4, 'user512', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'love@nate.com', 'COMPANY', '기업회원', now(), now()),
       (0, 'admin999', '$2a$10$AnO40455ZBKSalBx0YJ26eo4/a0J6UZPtYgRmdirjkn1GbgNeB/JW', 'admin@nate.com', 'ADMIN', '관리자', now(), now());

INSERT INTO player_info_tb(id, user_id, gender, age, address, tel, file_info_id, updated_at) 
VALUES (1, 1, '남자', 'AGE_20', '부산시','010-1001-1111', 1, now()),  
       (2, 2, '남자', 'AGE_20', '부산시','100-555-5555', 2, now()); 

INSERT INTO company_info_tb(id, user_id, business_number, business_address, tel, ceo, file_info_id, updated_at) 
VALUES (1, 3, '111-11-11111', '부산시 연제구', '010-1001-2222', '', 3, now()), -- view에 ceo이름 없는상태
       (2, 4, '', '', '', '', 4, now());
       

-- ▶ 경기장 & 코트
INSERT INTO stadium_tb(id, company_info_id, name, tel, category_id, address, lat, lon, file_info_id, start_time, end_time, status, created_at, updated_at)
VALUES (1, 1, 'a 야구장', '010-0234-5678', 1, '부산시', 35.1846, 128.9863, 5, '09:00', '18:00', '운영중', Now(), NOW()),
       (2, 1, 'b 야구장', '010-1234-5678', 1, '부산시', 35.2098, 129.0811, 6, '09:00', '18:00', '운영중', Now(), NOW()),
       (3, 2, 'a 농구장', '010-2234-5678', 4, '울산시', 37.2936, 126.8679, 7, '09:00', '18:00', '승인대기', Now(), NOW()),
       (4, 2, 'b 농구장', '010-3234-5678', 4, '울산시', 37.3226, 126.8593, 8, '09:00', '18:00', '운영중', Now(), NOW()),

       (5, 2, 'a 축구장', '010-4234-5672', 2, '울산시', 37.3226, 126.8593, 15, '09:00', '18:00', '운영중', Now(), NOW()),

       (6, 2, 'a 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 16, '09:00', '18:00', '운영중', Now(), NOW()),
       (7, 2, 'b 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 17, '09:00', '18:00', '운영중', Now(), NOW()),
       (8, 2, 'c 배구장', '010-4234-5673', 3, '울산시', 37.3226, 126.8593, 18, '09:00', '18:00', '운영중', Now(), NOW()),

       (9, 2, 'a 풋살장', '010-4234-5675', 5, '울산시', 37.3226, 126.8593, 19, '09:00', '18:00', '운영중', Now(), NOW()),

       (10, 2, 'a 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 20, '09:00', '18:00', '운영중', Now(), NOW()),
       (11, 2, 'b 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 21, '09:00', '18:00', '운영중', Now(), NOW()),
       (12, 2, 'c 볼링장', '010-4234-5676', 6, '울산시', 37.3226, 126.8593, 22, '09:00', '18:00', '운영중', Now(), NOW()),

       (13, 2, 'a 탁구장', '010-4234-5677', 7, '울산시', 37.3226, 126.8593, 23, '09:00', '18:00', '휴업', Now(), NOW()),
       
       (14, 2, 'a 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 24, '09:00', '18:00', '폐업', Now(), NOW()),
       (15, 2, 'b 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 25, '09:00', '18:00', '비활성', Now(), NOW()),
       (16, 2, 'c 테니스장', '010-4234-5678', 8, '울산시', 37.3226, 126.8593, 26, '09:00', '18:00', '비활성', Now(), NOW()),

       (17, 2, '부천 볼링장', '010-1123-1111', 6, '대전시', null, null, 33, '09:00', '18:00', '승인대기', Now(), NOW()),
       (18, 2, '삼익 야구장', '010-2223-1111', 1, '부산시', null, null, 34, '09:00', '18:00', '승인대기', Now(), NOW()),
       (19, 2, '리베라 야구장', '010-3323-1111', 1, '대구시', null, null, 35, '09:00', '18:00', '승인대기', Now(), NOW()),
       (20, 2, '아레나 풋살장', '010-4423-1111', 5, '인천시', null, null, 36, '09:00', '18:00', '승인대기', Now(), NOW()),
       (21, 2, '연제 풋살장', '010-5523-1111', 5, '서울시', null, null, 37, '09:00', '18:00', '승인대기', Now(), NOW()),
       (22, 2, '장호 테니스장', '010-6623-1111', 8, '대전시', null, null, 38, '09:00', '18:00', '승인대기', Now(), NOW());



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
       (11, 12, 31, 30000, 10, 'c 볼링장(코트2)', '스핀 맛집 볼링장', '비활성', now(), now()),
       (12, 12, 32, 30000, 10, 'c 볼링장(코트3)', '600점 이상 시 상품지급', '등록대기', now(), now());

        

-- ▶ 결제
INSERT INTO court_payment_tb(id, payment_type, payment_amount, player_info_id, company_info_id, stadium_court_id, origin_data, receipt_id, status, requested_at, purchased_at, created_at)
VALUES (1,  'Card', 40000, 1, 1,  1, 'data', 'RC16123', '결제완료', now(), now(), now()),
       (2,  'Card', 20000, 1, 2,  8, 'data', 'RC51628', '결제완료', now(), now(), now()),
       (3,  'Card', 40000, 1, 1,  1, 'data', 'RC61957', '결제완료', now(), now(), now()),
       (4,  'Card', 50000, 1, 2,  6, 'data', 'RC32166', '결제완료', now(), now(), now()),
       (5,  'Card', 30000, 1, 2, 11, 'data', 'RC51429', '결제완료', now(), now(), now()),
 
       (6,  'Card', 20000, 2, 2,  7, 'data', 'RC661927', '결제완료', now(), now(), now()),
       (7,  'Card', 20000, 2, 2,  7, 'data', 'RC992120', '결제완료', now(), now(), now()),
       (8,  'Card', 20000, 2, 2,  8, 'data', 'RC169275', '결제완료', now(), now(), now()),
       (9,  'Card', 20000, 2, 2,  8, 'data', 'RC136619', '결제완료', now(), now(), now()),
       (10, 'Card', 20000, 2, 2,  9, 'data', 'RC991624', '결제완료', now(), now(), now());


-- ▶ 예약
INSERT INTO court_reservation_tb (id, user_id, court_payment_id, stadium_court_id, reservation_date, reservation_time, status, created_at)
VALUES (1,  1,  1,  1, '2023-05-04', '16', '승낙', NOW()),
       (2,  1,  2,  8, '2023-05-20', '12', '승낙', NOW()),
       (3,  1,  3,  1, '2023-05-21', '15', '승낙', NOW()),
       (4,  1,  4,  6, '2023-05-27', '15', '승낙', NOW()),
       (5,  1,  5, 11, '2023-05-28', '16', '승낙', NOW()),
 
       (6,  2,  6, 7, '2023-05-04',  '9', '승낙', NOW()),
       (7,  2,  7, 7, '2023-05-04', '11', '승낙', NOW()),
       (8,  2,  8, 8, '2023-05-04', '12', '승낙', NOW()),
       (9,  2,  9, 8, '2023-05-04', '15', '승낙', NOW()),
       (10, 2, 10, 9, '2023-05-04', '16', '승낙', NOW());

-- ▶ 선호 스포츠
INSERT INTO user_favorite_sport_tb(id, player_info_id, category_id)
VALUES (1, 1, 2), (2, 1, 4),
       (3, 1, 5), (4, 1, 6),
       (5, 2, 1), (6, 2, 2);


