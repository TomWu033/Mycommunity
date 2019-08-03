create table feed (
  `id` bigint not null auto_increment,
  `gmt_create` bigint null,
  `user_id` bigint null,
  `data` tinytext null,
  `type` int null,
  primary key (`id`),
  index `user_index` (`user_id` asc) );
