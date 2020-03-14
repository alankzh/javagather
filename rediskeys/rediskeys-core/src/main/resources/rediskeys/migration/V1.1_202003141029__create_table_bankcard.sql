CREATE TABLE t_bankcard (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  customer_id varchar(32) NOT NULL,
  index_card_id  varchar(32) NOT NULL,
  mobile   varchar(32) NOT NULL,
  information varchar(255),
  create_time Timestamp not null default CURRENT_TIMESTAMP,
  update_time Timestamp not null default CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;