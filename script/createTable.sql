create table entry (
id bigint auto_increment,
description varchar2,
comments clob,
branch varchar2,
location varchar2,
serial varchar2 primary key,
unit_type varchar2,
date_purchased date,
amount number(19,2),
quantity number(19)
);