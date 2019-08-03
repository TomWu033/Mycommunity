
create table notification (
  `id` bigint not null auto_increment,
  `notifier` bigint not null,
  `receiver` bigint not null,
  `outerid` bigint not null,
  `type` int not null,
  `gmt_create` bigint not null,
  `status` int not null default 0,
  primary key (`id`));



