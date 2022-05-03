-- 创建 spring5_super 数据库
create
database `spring5_super` character set utf8mb4 collate utf8mb4_general_ci;

-- 使用 spring5_super 数据库
use
`spring5_super`;

-- 创建表 `tb_account`
create table `tb_account`
(
    id    bigint auto_increment comment '主键',
    name  varchar(20) comment '姓名',
    money double comment '账户金额',
    primary key (id)
) engine=InnoDB character set utf8mb4 comment '账户表';

-- 给 `tb_account` 添加数据
insert into `tb_account`(id, name, money)
values (default, '张三', 1000),
       (default, '李四', 1000),
       (default, '王五', 1000);
