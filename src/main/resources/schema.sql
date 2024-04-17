create table users_role
(
    User_USER_NAME varchar(100) not null,
    roles          varchar(255) check ( roles in ('USER', 'ADMIN') ),
    FOREIGN KEY (users_id) REFERENCES JAMES_USER (USER_NAME)
)
