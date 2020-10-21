CREATE database account;
use account;

CREATE USER 'account-user'@'localhost' IDENTIFIED BY 'Q1w2e3r$';

GRANT ALL PRIVILEGES ON account.* TO 'account-user'@'localhost';

      
  INSERT INTO `role`(`roleId`, `roleName`, `roleCode`, `role`)
  VALUES
	(210, 'D1', '18.9000000' ,'CUSTOMER' ),
	(211, 'D2', '18.9900000' ,'ADMIN' );