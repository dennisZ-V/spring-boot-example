create table if not exists t_department
(
    dept_id   int auto_increment comment '部门id'
    primary key,
    dept_name varchar(64) not null comment '部门名称'
    )
    comment '部门表';

create table if not exists t_order
(
    id             int auto_increment
    primary key,
    order_no       varchar(64)      not null comment '工单编号',
    order_type     int              not null comment '工单类型，0-交办，1-直接答复，2-无效工单',
    title          varchar(64)      not null comment '标题',
    content        text             not null comment '内容',
    handle_dept_id int              null comment '处理部门',
    create_time    timestamp        not null comment '创建时间',
    fenpai_time    timestamp        null comment '分派时间',
    is_overdue     bit              null comment '是否超期，0-否，1-是',
    is_removed     bit default b'0' not null comment '是否删除，0-未删除，1-已删除',
    constraint t_order_order_no_uindex
    unique (order_no)
    )
    comment '工单表';

create index t_order_fenpai_time_index
    on demo.t_order (fenpai_time);

create index t_order_handle_dept_id_index
    on demo.t_order (handle_dept_id);