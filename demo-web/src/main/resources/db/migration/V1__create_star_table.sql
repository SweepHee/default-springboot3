-- auto-generated definition
create table star
(
    id         bigserial
        primary key,
    member_id  varchar(40)                            not null
        unique,
    name       varchar(40)                            not null,
    email      varchar(40)                            not null,
    password   varchar(80)                            not null,
    role       varchar(20)                            not null,
    created_at varchar(40) default now() not null,
    updated_at varchar(40) default now() not null,
    deleted_at varchar(40)
);

comment on table star is '회원 테이블';

comment on column star.member_id is '회원 아이디';

comment on column star.name is '회원명';

comment on column star.email is '회원 이메일';

comment on column star.password is '회원 비밀번호';

comment on column star.role is '회원 권한';