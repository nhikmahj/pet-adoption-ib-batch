CREATE TABLE `tbl_stage_pet` (
  `pet_batch_id` bigint NOT NULL AUTO_INCREMENT,
  `batch_status` varchar(255) DEFAULT NULL,
  `pet_id` varchar(255) DEFAULT NULL,
  `pet_type` varchar(255) DEFAULT NULL,
  `pet_age_year` varchar(255) DEFAULT NULL,
  `pet_age_month` varchar(255) DEFAULT NULL,
  `sterilised_flag` varchar(1) DEFAULT NULL,
  `vaccinated_flag` varchar(1) DEFAULT NULL,
  `owned_flag` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`pet_batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
