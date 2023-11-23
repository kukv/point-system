\connect point_system
set role bradmin;
set search_path to point;

--#########################################
--# drop
--#########################################
DROP TABLE IF EXISTS expiration_date;

DROP TABLE IF EXISTS point_transaction_event;
DROP TABLE IF EXISTS transaction_event;

DROP TABLE IF EXISTS canceled_point_transaction;
DROP TABLE IF EXISTS unreflected_earn_point_transaction;
DROP TABLE IF EXISTS point_transaction;
DROP TABLE IF EXISTS point_transaction_number;

--#########################################
--# create
--#########################################
CREATE TABLE point_transaction_number
(
    transaction_number  uuid                        NOT NULL PRIMARY KEY,
    buyer_id            integer                     NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created             varchar(255)                NOT NULL
);
COMMENT ON TABLE point_transaction_number                      IS 'ポイント取引番号';
COMMENT ON COLUMN point_transaction_number.buyer_id            IS '顧客ID';
COMMENT ON COLUMN point_transaction_number.transaction_number  IS '取引番号';
COMMENT ON COLUMN point_transaction_number.created_at          IS '作成日時';
COMMENT ON COLUMN point_transaction_number.created             IS '作成者';

CREATE TABLE point_transaction
(
    transaction_number  uuid                        NOT NULL PRIMARY KEY,
    transaction_source  varchar(200)                NOT NULL,
    requested_at        timestamp without time zone NOT NULL,
    point               integer                     NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created             varchar(255)                NOT NULL,

    FOREIGN KEY (transaction_number) REFERENCES point_transaction_number (transaction_number)
);
COMMENT ON TABLE point_transaction                      IS 'ポイント取引';
COMMENT ON COLUMN point_transaction.transaction_number  IS '取引番号';
COMMENT ON COLUMN point_transaction.transaction_source  IS '取引発生元';
COMMENT ON COLUMN point_transaction.requested_at        IS '取引発生日時';
COMMENT ON COLUMN point_transaction.point               IS 'ポイント数';
COMMENT ON COLUMN point_transaction.created_at          IS '作成日時';
COMMENT ON COLUMN point_transaction.created             IS '作成者';

CREATE TABLE earn_point_reservation
(
    id                  serial                      NOT NULL PRIMARY KEY,
    transaction_number  uuid                        NOT NULL,
    reason              varchar(200)                NOT NULL,
    point               integer                     NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created             varchar(255)                NOT NULL,

    FOREIGN KEY (transaction_number) REFERENCES point_transaction_number (transaction_number)
);
COMMENT ON TABLE earn_point_reservation                     IS 'ポイントの獲得予約';
COMMENT ON COLUMN earn_point_reservation.id                 IS 'ID';
COMMENT ON COLUMN earn_point_reservation.transaction_number IS '取引番号';
COMMENT ON COLUMN earn_point_reservation.reason             IS 'ポイント付与理由';
COMMENT ON COLUMN earn_point_reservation.point              IS 'ポイント数';
COMMENT ON COLUMN earn_point_reservation.created_at         IS '作成日時';
COMMENT ON COLUMN earn_point_reservation.created            IS '作成者';

CREATE TABLE canceled_point_transaction
(
    transaction_number          uuid                        NOT NULL PRIMARY KEY,
    cancel_transaction_number   uuid                        NOT NULL,
    created_at                  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created                     varchar(255)                NOT NULL,

    FOREIGN KEY (transaction_number) REFERENCES point_transaction_number (transaction_number),
    FOREIGN KEY (cancel_transaction_number) REFERENCES point_transaction_number (transaction_number)
);
COMMENT ON TABLE canceled_point_transaction                             IS 'キャンセル済のポイント取引';
COMMENT ON COLUMN canceled_point_transaction.transaction_number         IS '取引番号';
COMMENT ON COLUMN canceled_point_transaction.cancel_transaction_number  IS 'キャンセル対象の取引番号';
COMMENT ON COLUMN canceled_point_transaction.created_at                 IS '作成日時';
COMMENT ON COLUMN canceled_point_transaction.created                    IS '作成者';

CREATE TABLE transaction_event
(
    event varchar(10) NOT NULL PRIMARY KEY
);
COMMENT ON TABLE transaction_event        IS '取引イベント';
COMMENT ON COLUMN transaction_event.event IS 'イベント';

INSERT INTO transaction_event(event) VALUES ('利用');
INSERT INTO transaction_event(event) VALUES ('利用キャンセル');
INSERT INTO transaction_event(event) VALUES ('獲得');
INSERT INTO transaction_event(event) VALUES ('獲得キャンセル');
INSERT INTO transaction_event(event) VALUES ('失効');

CREATE TABLE point_transaction_event
(
    transaction_number  uuid                        NOT NULL PRIMARY KEY,
    transaction_event   varchar(10)                 NOT NULL,
    created_at          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created             varchar(255)                NOT NULL,

    FOREIGN KEY (transaction_number) REFERENCES point_transaction_number (transaction_number),
    FOREIGN KEY (transaction_event)   REFERENCES transaction_event (event)
);
COMMENT ON TABLE point_transaction_event                      IS 'ポイント取引イベント';
COMMENT ON COLUMN point_transaction_event.transaction_number  IS '取引番号';
COMMENT ON COLUMN point_transaction_event.transaction_event   IS '取引イベント';
COMMENT ON COLUMN point_transaction_event.created_at          IS '作成日時';
COMMENT ON COLUMN point_transaction_event.created             IS '作成者';

CREATE TABLE expiration_date
(
    id          serial                      NOT NULL PRIMARY KEY,
    buyer_id    integer                     NOT NULL,
    expired_at  date                        NOT NULL,
    created_at  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created     varchar(255)                NOT NULL
);
COMMENT ON TABLE expiration_date              IS '有効期限';
COMMENT ON COLUMN expiration_date.id          IS 'ID';
COMMENT ON COLUMN expiration_date.buyer_id    IS '顧客ID';
COMMENT ON COLUMN expiration_date.expired_at  IS '有効期限';
COMMENT ON COLUMN expiration_date.created_at  IS '作成日時';
COMMENT ON COLUMN expiration_date.created     IS '作成者';
