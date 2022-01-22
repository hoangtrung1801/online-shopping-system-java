create table order_item
(
    id int identity(1,1) primary key,
    productId int foreign key references product(id),
    orderId int foreign key references orders(orderNumber),
    price float,
    quantity int
)