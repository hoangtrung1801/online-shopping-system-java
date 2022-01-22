create table orders
(
    id int identity(1,1) primary key,
    accountId int,
    paymentId int,
    status varchar(100),
    orderDate datetime,
    constraint fk_order_account foreign key accountId references account(id),
    constraint fk_order_payment foreign key paymentId references payment(id)
)