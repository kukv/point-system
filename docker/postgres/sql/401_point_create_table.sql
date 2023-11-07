\connect wagokoro
set role bradmin;
set search_path to point;

--#########################################
--# drop
--#########################################
DROP TABLE IF EXISTS point_transaction;
DROP TABLE IF EXISTS point_transaction_number;
DROP TABLE IF EXISTS transaction_type;
DROP TABLE IF EXISTS active_ownership_point;
DROP TABLE IF EXISTS ownership_point;

--#########################################
--# create
--#########################################
CREATE TABLE ownership_point
(
    id          serial                      NOT NULL PRIMARY KEY,
    diner_id    integer                     NOT NULL,
    point       integer                     NOT NULL,
    expired_at  date                        NOT NULL,
    created_at  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    audit       varchar(255)                NOT NULL
);
COMMENT ON TABLE ownership_point                IS '保有ポイント';
COMMENT ON COLUMN ownership_point.id            IS 'ID';
COMMENT ON COLUMN ownership_point.diner_id      IS '顧客ID';
COMMENT ON COLUMN ownership_point.point         IS 'ポイント数';
COMMENT ON COLUMN ownership_point.expired_at    IS '有効期限';
COMMENT ON COLUMN ownership_point.created_at    IS '作成日時';
COMMENT ON COLUMN ownership_point.audit         IS '作成者';

CREATE TABLE active_ownership_point
(
    ownership_point_id  integer  NOT NULL PRIMARY KEY,

    FOREIGN KEY (ownership_point_id) REFERENCES ownership_point (id)
);
COMMENT ON TABLE active_ownership_point                     IS '有効な保有ポイント';
COMMENT ON COLUMN active_ownership_point.ownership_point_id IS '保有ポイントID';

CREATE TABLE transaction_type
(
    type    varchar(10)                  NOT NULL PRIMARY KEY
);
COMMENT ON TABLE transaction_type                IS '取引種別';
COMMENT ON COLUMN transaction_type.type      IS '種別';

CREATE TABLE point_transaction_number
(
    transaction_number  uuid                        NOT NULL PRIMARY KEY,
    diner_id            integer                     NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    audit               varchar(255)                NOT NULL
);
COMMENT ON TABLE point_transaction_number                      IS 'ポイント取引番号';
COMMENT ON COLUMN point_transaction_number.diner_id            IS '顧客ID';
COMMENT ON COLUMN point_transaction_number.transaction_number  IS '取引番号';
COMMENT ON COLUMN point_transaction_number.created_at          IS '作成日時';
COMMENT ON COLUMN point_transaction_number.audit               IS '作成者';

CREATE TABLE point_transaction
(
    transaction_number  uuid                        NOT NULL PRIMARY KEY,
    transaction_type    varchar(10)                 NOT NULL,
    point               integer                     NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    audit               varchar(255)                NOT NULL,

    FOREIGN KEY (transaction_number) REFERENCES point_transaction_number (transaction_number),
    FOREIGN KEY (transaction_type)   REFERENCES transaction_type (type)
);
COMMENT ON TABLE point_transaction                      IS 'ポイント取引履歴';
COMMENT ON COLUMN point_transaction.transaction_number  IS '取引番号';
COMMENT ON COLUMN point_transaction.transaction_type    IS '取引種別';
COMMENT ON COLUMN point_transaction.point               IS 'ポイント数';
COMMENT ON COLUMN point_transaction.created_at          IS '作成日時';
COMMENT ON COLUMN point_transaction.audit               IS '作成者';
