#用户表
CREATE TABLE user(
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(30) NOT NULL ,
  status BOOLEAN DEFAULT TRUE ,
  description VARCHAR(50)
);

#角色表
CREATE TABLE role(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL ,
  description VARCHAR(50)
);

#用户角色关联表
CREATE TABLE user_role(
  user_id INT,
  role_id INT,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES user(id),
  CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role(id)
);

#资源文件表
CREATE TABLE resc(
  id INT PRIMARY KEY AUTO_INCREMENT,
  res_string VARCHAR(50) NOT NULL ,
  res_type VARCHAR(10),
  description VARCHAR(50)
);

#角色资源文件关联表
CREATE TABLE role_resc(
  role_id INT,
  resc_id INT,
  PRIMARY KEY (role_id, resc_id),
  CONSTRAINT fk_role_resc_role FOREIGN KEY (role_id) REFERENCES role(id),
  CONSTRAINT fk_role_resc_resc FOREIGN KEY (resc_id) REFERENCES resc(id)
);