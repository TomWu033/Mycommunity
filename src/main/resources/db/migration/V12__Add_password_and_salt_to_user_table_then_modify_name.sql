alter table USER1
	add password varchar(128) default '' not null;

alter table USER1
	add salt varchar(32) default '' not null;

alter table USER1 alter column NAME set not null;

create unique index USER1_NAME_uindex
	on USER1 (NAME);
