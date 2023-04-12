-- -- 스포츠 종류
-- INSERT INTO category_tb(id, sport, created_at) 
-- VALUES (1, '테니스', now()), (5, '농구', now()), 
--        (2, '축구', now()),  (6, '볼링', now()),
--        (3, '풋살', now()),  (7, '탁구', now()),
--        (4, '야구', now()),  (8, '당구', now());
       
-- -- 결제방식
-- INSERT INTO payment_type_tb(id, payment_type, created_at) 
-- VALUES (1, 'Card', now()),   (4, 'Bank', now()),
--        (2, 'Mobile', now()), (5, 'Point', now()),
--        (3, 'Cash', now());

-- -- 개인정보
-- INSERT INTO company_tb(id, username, password, email, role, status, created_at) 
-- VALUES (1, 'com1', 'com1@nate.com', '1234', 'COMPANY', 0, now()),
--        (2, 'com2', 'com2@nate.com', '1234', 'COMPANY', 0, now()),
--        (3, 'com3', 'com3@nate.com', '1234', 'COMPANY', 0, now()),
--        (4, 'com4', 'com4@nate.com', '1234', 'COMPANY', 0, now());

-- INSERT INTO company_info_tb(id, company_id, business_number, business_address, tel, ceo, created_at) 
-- VALUES (1, 1, '111-11-22211', '부산시 연제구', '010-1001-0001', '조성호', now()),
--        (2, 2, '211-11-22211', '안산시 상록구', '010-2001-0001', '박혜정', now()),
--        (3, 3, '311-11-22211', '대구시 수성구', '010-3001-0001', '이동수', now()),
--        (4, 4, '411-11-22211', '서울시 강남구', '010-4001-0001', '김정훈', now());

-- INSERT INTO user_tb(id, nickname, password, email, role, status, created_at) 
-- VALUES (1, 'user1', 'user1@nate.com', '1234', 'USER', 0, now()),
--        (2, 'user2', 'user2@nate.com', '1234', 'USER', 0, now()),
--        (3, 'user3', 'user3@nate.com', '1234', 'USER', 0, now()),
--        (4, 'user4', 'user4@nate.com', '1234', 'USER', 0, now()),
--        (5, 'user5', 'user5@nate.com', '1234', 'USER', 0, now()),
--        (6, 'user6', 'user6@nate.com', '1234', 'USER', 0, now()),
--        (7, 'user7', 'user7@nate.com', '1234', 'USER', 0, now()),
--        (8, 'user8', 'user8@nate.com', '1234', 'USER', 0, now());
       
-- INSERT INTO user_info_tb(id, user_id, gender, age, address, tel, thumnail, created_at) 
-- VALUES (1, 1, '남', 32, '강남','010-1002-0001', '', now()),
--        (2, 2, '여', 29, '부산','010-2002-0001', '', now()),
--        (3, 3, '남', 23, '안산','010-3002-0001', '', now()),
--        (4, 4, '남', 41, '강남','010-4002-0001', '', now()),
--        (5, 5, '남', 21, '안산','010-5002-0001', '', now()),
--        (6, 6, '여', 36, '안산','010-6002-0001', '', now()),
--        (7, 7, '남', 72, '강남','010-7002-0001', '', now()),
--        (8, 8, '여', 22, '제주','010-8002-0001', '', now());

-- -- 경기장 & 코트
-- INSERT INTO stadium_tb(id, company_info_id, tel, address, lat, lon, thumbnail, start_time, end_time, rental_status, created_at)
-- VALUES (1, 1, '010-1234-5678', '서울시 강남구 역삼1동 123-4', 37.5013, 127.0273, '', '09:00', '22:00', 1, NOW()),
--        (2, 1, '010-2234-5678', '서울시 강남구 논현1동 223-4', 37.5163, 126.0293, '', '09:00', '22:00', 1, NOW()),
--        (3, 2, '010-3234-5678', '안산시 상록구 본오동 323-4', 37.2936, 126.8679, '', '10:00', '18:00', 1, NOW()),
--        (4, 2, '010-4234-5678', '안산시 상록구 부곡동 423-4', 37.3226, 126.8593, '', '10:00', '18:00', 1, NOW());

-- INSERT INTO stadium_court_tb(id, stadium_id, category_id, thumbnail, price, capacity, content, status, created_at) 
-- VALUES (1, 1, 1, '', 40000, 4, '최신 시설 코트', 0, now()), -- 강남구 역삼1동 123-4 (테니스장)
--        (2, 1, 1, '', 60000, 4, '최신 시설 코트', 0, now()),
--        (3, 2, 1, '', 30000, 4, '경치 좋은 코트', 0, now()), -- 강남구 논현1동 223-4 (테니스장)
--        (4, 2, 1, '', 50000, 4, '경치 좋은 코트', 0, now()),
--        (5, 3, 3, '', 50000, 22, '잔디 상태 최상', 0, now()), -- 상록구 본오동 323-4 (풋살장)
--        (6, 3, 3, '', 50000, 22, '교통 편리 위치', 0, now()); -- 상록구 부곡동 423-4 (풋살장)

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

-- -- 결제
-- INSERT INTO stadium_payment_tb(id, payment_type_id, payment_amount, user_id, company_id, status, created_at)
-- VALUES (1, 1, 40000, 1, 1, 0, now()),
--        (2, 3, 60000, 4, 1, 0, now()),
--        (3, 2, 50000, 3, 3, 0, now());

-- -- 예약
-- INSERT INTO stadium_reservation_tb (id, user_id, board_id, start_time, end_time, status, created_at)
-- VALUES (1, 1, 1, '2023-06-11 14:00:00', '2023-06-11 16:00:00', 0, NOW()),
--        (2, 4, 2, '2023-06-11 10:00:00', '2023-06-11 11:00:00', 0, NOW()),
--        (3, 3, 3, '2023-06-03 09:00:00', '2023-06-03 10:00:00', 0, NOW());

-- -- 선호 스포츠
-- INSERT INTO user_favorite_sport_tb(id, user_info, category_id)
-- VALUES (1, 1, 1), (5, 4, 1),
--        (2, 1, 4), (6, 5, 2),
--        (3, 1, 5), (7, 6, 1),
--        (4, 2, 1), (8, 7, 1);

