create table login_ticket
(
	id int auto_increment,
	userId int not null,
	ticket varchar(45) not null,
	expired bigint not null,
	status int null default 0,
	constraint login_ticket_pk
		primary key (id)
);

create unique index login_ticket_ticket_uindex
	on login_ticket (ticket);

