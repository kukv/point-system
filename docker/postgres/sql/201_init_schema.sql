\connect wagokoro
set role bradmin;

--#########################################
--# drop
--#########################################
DROP SCHEMA IF EXISTS point CASCADE;

--#########################################
--# create
--#########################################
CREATE SCHEMA point;

COMMENT ON SCHEMA point is 'ポイント情報を管理するスキーマ';

GRANT USAGE ON SCHEMA point TO luster;

ALTER DEFAULT PRIVILEGES
    GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE
    ON TABLES
    TO luster;

ALTER DEFAULT PRIVILEGES
    GRANT USAGE, SELECT, UPDATE
    ON SEQUENCES
    TO luster;