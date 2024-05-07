INSERT INTO JAMES_USER (USER_NAME, PASSWORD_HASH_ALGORITHM, PASSWORD, version)
VALUES ('admin', 'NONE', '$2a$10$TfCHDCPSRNC85C3mq/FIB.CX94NMDNmrKn.BPqxIInQvcrYCWf7R.', 1)
ON DUPLICATE KEY UPDATE PASSWORD_HASH_ALGORITHM = VALUES(PASSWORD_HASH_ALGORITHM),
                        PASSWORD                = VALUES(PASSWORD),
                        version                 = VALUES(version);

INSERT INTO User_roles (User_USER_NAME, roles)
VALUES ('admin', 0),
       ('admin', 1)
ON DUPLICATE KEY UPDATE User_USER_NAME = VALUES(User_USER_NAME),
                        roles          = VALUES(roles);
