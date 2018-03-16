#在gens_sequence表中增加一个自增的主键
ALTER TABLE gens_sequence ADD id INT PRIMARY KEY AUTO_INCREMENT;

#检查用户登录信息存储过程
CREATE PROCEDURE checkUserLoginInfo(IN advance INT)
  BEGIN
    DECLARE searchDate TIMESTAMP;
    IF advance IS NULL THEN
      SELECT 'advance can not be null!!!';
    ELSE
      SET searchDate = date_add(current_timestamp, INTERVAL -advance DAY);
      SELECT a.logintime, b.username FROM login_info a
        LEFT JOIN user b ON a.userid = b.id
      WHERE username <> 'crabime' AND logintime > searchDate
      ORDER BY a.logintime DESC ;
    END IF;
  END;

#用户登录信息表中增加ip字段
ALTER TABLE login_info ADD COLUMN ip VARCHAR(20);