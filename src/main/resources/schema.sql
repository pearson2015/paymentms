CREATE TABLE IF NOT EXISTS payment (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(100) NOT NULL,
    amount decimal(10,2) NOT NULL,
    payment_transaction_id varchar(100) NOT NULL,
    payment_method varchar(50) NOT NULL,
    payment_status varchar(50) NOT NULL,
    payment_date datetime NOT NULL,
    PRIMARY KEY (id)
);