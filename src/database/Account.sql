create table Account 
(
    username varchar(100),
    password varchar(100),
    status varchar(100),
    name varchar(100),
    shippingAddress varchar(100),
    email varchar(100),
    phone varchar(100),
    role varchar(100),
    id int identity(1, 1) primary key
)