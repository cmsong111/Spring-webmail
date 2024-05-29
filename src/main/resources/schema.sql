create table IF NOT EXISTS User_roles
(
    roles          tinyint check (roles between 0 and 1),
    User_USER_NAME varchar(255) not null,
    FOREIGN KEY (User_USER_NAME) REFERENCES JAMES_USER(USER_NAME)
);

CREATE TABLE IF NOT EXISTS Contact
(
    id               BIGINT NOT NULL auto_increment PRIMARY KEY,
    nickname         VARCHAR(255),
    friend_user_name VARCHAR(255),
    owner_user_name  VARCHAR(255),
    FOREIGN KEY (friend_user_name) REFERENCES JAMES_USER(USER_NAME),
    FOREIGN KEY (owner_user_name) REFERENCES JAMES_USER(USER_NAME)
);

