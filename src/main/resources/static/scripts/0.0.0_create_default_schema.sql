create table if not exists image
(
    id int auto_increment primary key,
    name varchar(255) not null,
    url_to_image varchar(255) not null
)