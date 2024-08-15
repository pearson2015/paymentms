CREATE TABLE IF NOT EXISTS payment (
    payment_id varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    price decimal(10,2) NOT NULL,
    room_id varchar(100) NOT NULL,
    payment_transaction_id varchar(100) NOT NULL,
    payment_method varchar(50) NOT NULL,
    payment_status varchar(50) NOT NULL,
    payment_date datetime NOT NULL,
    PRIMARY KEY (payment_id)
);