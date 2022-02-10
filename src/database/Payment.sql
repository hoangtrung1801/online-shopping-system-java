create table payment
(
    id int identity(1,1) primary key,
    orderId int,
    amount money,
    status varchar(100),
    constraint fk_payment_order foreign key (orderId) references orders(id)
)