-- 모든 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;
truncate table user_tb;
truncate table file_info_tb;
truncate table file_tb;
truncate table company_info_tb;
SET REFERENTIAL_INTEGRITY TRUE;
-- 모든 제약 조건 활성화