-- order table: `t_order`
DROP TABLE IF EXISTS t_order;
CREATE TABLE `t_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(32) NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- order item table: `t_order_item`
DROP TABLE IF EXISTS t_order_item;
CREATE TABLE `t_order_item` (
   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
   `order_id` bigint(20) unsigned NOT NULL,
   `product_id` bigint(20) unsigned NOT NULL,
   `price` decimal(10,2) unsigned NOT NULL,
   PRIMARY KEY (`id`),
   KEY `idx_order_id` (`order_id`) USING BTREE,
   KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;