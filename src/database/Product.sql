create table Product 
(
    id int identity(1, 1) primary key,
    name varchar(1000),
    description varchar(1000),
    price money,
    availableItemCount int,
    category varchar(1000),
    image varbinary(MAX)
)