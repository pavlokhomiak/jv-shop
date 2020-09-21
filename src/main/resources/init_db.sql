CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(225) NOT NULL,
  `password` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`username` ASC) VISIBLE);

INSERT INTO `internet_shop`.`users` (`username`, `password`) VALUES ('bob', '1');

ALTER TABLE `internet_shop`.`users`
ADD COLUMN `userlogin` VARCHAR(255) NOT NULL AFTER `username`,
ADD UNIQUE INDEX `userlogin_UNIQUE` (`userlogin` ASC) VISIBLE,
DROP INDEX `user_name_UNIQUE` ;
;

UPDATE `internet_shop`.`users` SET `userlogin` = 'big' WHERE (`user_id` = '1');


CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `productname` VARCHAR(225) NOT NULL,
  `productprice` DECIMAL(7,2) NOT NULL,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB;