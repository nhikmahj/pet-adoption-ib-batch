CREATE TABLE `tbl_pet_profile` (
  `pet_id` varchar(5) NOT NULL,
  `pet_type` varchar(10) NOT NULL,
  `pet_age_year` int DEFAULT NULL,
  `pet_age_month` int DEFAULT NULL,
  `vaccinated_flag` tinyint NOT NULL,
  `sterilised_flag` tinyint NOT NULL,
  `owned_flag` tinyint NOT NULL,
  PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tbl_user_profile` (
  `user_profile_id` varchar(36) NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `user_nric` varchar(14) NOT NULL,
  `user_address` varchar(200) DEFAULT NULL,
  `pet_owner_flag` tinyint NOT NULL,
  `pet_id` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`user_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
