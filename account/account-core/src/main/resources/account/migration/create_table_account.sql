CREATE TABLE t_account (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  login_account varchar(255) NOT NULL COMMENT '登录名',
  pwd   varchar(255) COMMENT '密码md5',
  email varchar(255) COMMENT 'email',
  mobile varchar(255) COMMENT 'mobile',
  create_time Timestamp not null default sysdate(),
  update_time Timestamp not null default sysdate(),
  is_delete boolean not null default false,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户账户表';