# insert role data table
INSERT INTO `java_blog`.`role` (`role_name`) VALUES ('ADMIN');
INSERT INTO `java_blog`.`role` (`role_name`) VALUES ('USER');

# insert user table
INSERT INTO `java_blog`.`user` (`email`, `first_name`, `password`, `last_name`, `username`, `role_id`) VALUES ('my_mail@gmail.com', 'John', '$2a$10$v3dE2unXef23FSjKEQyKwOdtZoAPaiyBNnnqsu1/R9LbYnxJW6z/2', 'Smith', 'johny', '1');
INSERT INTO `java_blog`.`user` (`email`, `first_name`, `password`, `last_name`, `username`, `role_id`) VALUES ('mail@mail.ru', 'Nick', '$2a$10$v3dE2unXef23FSjKEQyKwOdtZoAPaiyBNnnqsu1/R9LbYnxJW6z/2', 'Boms', 'niki', '2');
