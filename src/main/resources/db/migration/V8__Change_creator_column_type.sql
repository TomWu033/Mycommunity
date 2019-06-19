alter table QUESTION alter column CREATOR bigint default not null auto_increment;
alter table COMMENT alter column COMMENTATOR bigint default not null auto_increment;