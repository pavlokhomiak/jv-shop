CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(225) NOT NULL,
  `product_price` DECIMAL(7,2) NOT NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB;

ALTER TABLE products Change productname product_name VARCHAR(225)

ALTER TABLE products Change productprice product_price DECIMAL(7,2)
