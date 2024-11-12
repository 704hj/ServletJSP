--회원관리
create table zmember(
 mem_num number not null,
 id varchar2(12) unique not null,
 auth number(1) default 2 not null,--회원등급:0탈퇴,1정지,2일반,9관리자
 constraint zmember_pk primary key (mem_num)
);
create table zmember_detail(
 mem_num number not null,
 name varchar2(30) not null,
 passwd varchar2(12) not null,
 phone varchar2(15) not null,
 email varchar2(50) not null,
 zipcode varchar2(50) not null,
 address1 varchar2(90) not null,
 address2 varchar2(90) not null,
 photo varchar2(400),
 reg_date date default sysdate not null,
 modify_date date,
 constraint zmember_detail_pk primary key (mem_num),
 constraint zmember_detail_fk foreign key (mem_num) references zmember (mem_num)
);
create sequence zmember_seq;

--게시판
create table zboard(
 board_num number not null,
 title varchar2(150) not null,
 content clob not null,
 hit number(9) default 0 not null,
 reg_date date default sysdate not null,
 modify_date date,
 filename varchar2(400),
 ip varchar2(40) not null,
 mem_num number not null,
 constraint zboard_pk primary key (board_num),
 constraint zboard_fk foreign key (mem_num) references zmember (mem_num)
 );
 create sequence zboard_seq;
 
 --좋아요 
 --컬럼이 두개 이상이면 VO 만드는게 이점이다.
create table zboard_fav(
 board_num number not null,
 mem_num number not null,
 constraint zboard_fav_fk1 foreign key (board_num) references zboard (board_num),
 constraint zboard_fav_fk2 foreign key (mem_num) references zmember (mem_num)
 );
 
 --댓글
 create table zboard_reply(
  re_num number not null,
  re_content varchar2(900) not null,
  re_date date default sysdate not null,
  re_modifydate date,
  re_ip varchar2(40) not null,
  board_num number not null,
  mem_num number not null,
  constraint zboard_reply_pk primary key (re_num),
  constraint zboard_reply_fk1 foreign key (board_num) references zboard (board_num),
  constraint zboard_reply_fk2 foreign key (mem_num) references zmember (mem_num) 
 );
 create sequence zreply_seq;
 
 --1대1 채팅
create table zchatone(
 chat_num number not null,
 send_num number not null, --보내는 사람
 recv_num number not null, --받는 사람
 message varchar2(4000) not null,
 read_check number(1) default 1 not null, --읽기 여부 -> 1:읽지 않음, 0:읽음
 chat_date date default sysdate not null,
 constraint zchatone_pk primary key (chat_num),
 constraint zchatone_fk1 foreign key (send_num) references zmember (mem_num),
 constraint zchatone_fk2 foreign key (recv_num) references zmember (mem_num)
 );
create sequence zchatone_seq;
 
 
 
 