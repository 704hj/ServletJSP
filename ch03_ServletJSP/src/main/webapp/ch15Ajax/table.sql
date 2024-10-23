create table todo(
id number primary key,
todo varchar2(150) not null,
created date default sysdate not null,
completed number(1) default 0 not null
);

create sequence todo_seq;