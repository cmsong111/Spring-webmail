create table User_roles
(
    roles          tinyint check (roles between 0 and 1),
    User_USER_NAME varchar(255) not null,
    FOREIGN KEY (User_USER_NAME) REFERENCES JAMES_USER(USER_NAME)
)
