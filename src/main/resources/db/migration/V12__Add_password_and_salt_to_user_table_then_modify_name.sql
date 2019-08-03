alter table user1
	add password varchar(128) default '' not null;

alter table user1
	add salt varchar(32) default '' not null;

alter table user1 modify name varchar(50) not null;

create unique index user1_name_uindex
	on user1 (name);
