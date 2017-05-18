CREATE TABLE IF NOT EXISTS udb_process_run (
  run_id BIGINT(20) NOT NULL AUTO_INCREMENT,
  date TIMESTAMP NULL,
  status VARCHAR(10) NOT NULL,
  PRIMARY KEY (run_id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table udb_process_summary_data
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_process_summary_data (
  run_id BIGINT(20) NOT NULL,
  total_running_time INT(11) NULL,
  total_events BIGINT NULL,
  total_unique_users BIGINT NULL,
  total_unique_users_with_matching BIGINT NULL,
  total_matching BIGINT NULL,
  total_unique_matched_titles BIGINT NULL,
  link_matched_titles_full_report VARCHAR(150) NULL,
  link_matched_aka_full_report VARCHAR(150) NULL,
  link_unique_urls_data_full_report VARCHAR(150) NULL,
  PRIMARY KEY (run_id),
  CONSTRAINT fk_summary_data_run_status_runId
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table udb_evaluation_step_results
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_evaluation_step_results (
  run_id BIGINT(20) NOT NULL,
  evaluation_set_name VARCHAR(150) NOT NULL,
  total_rows INT(11) NULL,
  success_rows INT(11) NULL,
  partial_success_rows INT(11) NULL,
  failed_to_match_rows INT(11) NULL,
  failed_to_parse_rows INT(11) NULL,
  link_full_report VARCHAR(150) NULL,
  PRIMARY KEY (evaluation_set_name, run_id),
  CONSTRAINT fk_TestSetEvaluation_1
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table udb_top_matched_titles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_top_matched_titles (
  run_id BIGINT(20) NOT NULL,
  content_id BIGINT(20) NOT NULL,
  unique_users BIGINT NULL,
  matched_events BIGINT NULL,
  matched_from_aka BIGINT NULL,
  imdb_trending_index FLOAT NULL,
  content_popularity FLOAT NULL,
  title_confidence FLOAT NULL,
  PRIMARY KEY (content_id, run_id),
  INDEX fk_TopJinniMatchedTitles_1_idx (run_id ASC),
  CONSTRAINT fk_Ttop_matched_titles_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COMMENT = 'only top 500';


-- -----------------------------------------------------
-- Table udb_top_aka_matched_titles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_top_aka_matched_titles (
  run_id BIGINT(20) NOT NULL,
  contentId BIGINT(20) NOT NULL,
  title_name VARCHAR(255) NOT NULL,
  aka VARCHAR(255) NOT NULL,
  unique_users BIGINT NULL,
  matched_events BIGINT NULL,
  imdb_trending_index FLOAT NULL,
  title_confidence FLOAT NULL,
  PRIMARY KEY (run_id, contentId, title_name, aka),
  INDEX fk_TopJinniMatchedTitles_1_idx (run_id ASC),
  CONSTRAINT fk_top_aka_matched_titles_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COMMENT = 'only top 500';


-- -----------------------------------------------------
-- Table udp_domain_data
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udp_domain_data (
  run_id BIGINT(20) NOT NULL,
  domain_data_id INT(11) NOT NULL,
  domain VARCHAR(150) NOT NULL,
  events BIGINT NULL,
  unique_users BIGINT NULL,
  unique_users_with_contents BIGINT NULL,
  matched_events BIGINT NULL,
  distinct_contents BIGINT NULL,
  filtered_urls_count BIGINT NULL,
  domain_confidence FLOAT NULL,
  is_balcklisted BIT NULL,
  PRIMARY KEY (run_id, domain_data_id, domain),
  CONSTRAINT fk_domain_data_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COMMENT = 'more than 10 events';


-- -----------------------------------------------------
-- Table udb_source_data
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_source_data (
  run_id BIGINT(20) NOT NULL,
  source_name VARCHAR(150) NOT NULL,
  events BIGINT NULL,
  unique_users BIGINT NULL,
  unique_users_with_contents BIGINT NULL,
  matched_events BIGINT NULL,
  distinct_contents BIGINT NULL,
  unique_domains BIGINT NULL,
  distinct_user_and_content_pairs BIGINT NULL,
  PRIMARY KEY (run_id, source_name),
  CONSTRAINT fk_source_data_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COMMENT = 'distinctUserAndContentPairs - ??';


-- -----------------------------------------------------
-- Table udb_unique_url_data
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_unique_url_data (
  run_id BIGINT(20) NOT NULL,
  host VARCHAR(150) NOT NULL,
  uri VARCHAR(400) NOT NULL,
  parameters VARCHAR(400) NOT NULL,
  source_name VARCHAR(150) NULL,
  unique_users BIGINT NULL,
  matched_content_Id BIGINT(20) NULL,
  matched_aka VARCHAR(255) NULL,
  url_confidence FLOAT NULL,
  matched_type VARCHAR(20) NULL,
  domain_blacklisted BIT NULL,
  is_url_filtered BIT NULL,
  PRIMARY KEY (run_id, host, parameters, uri),
  CONSTRAINT fk_unique_url_data_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COMMENT = 'delete weekly';


-- -----------------------------------------------------
-- Table udb_step_data
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS udb_step_data (
  run_id BIGINT(20) NOT NULL,
  step_name VARCHAR(150) NULL,
  amazon_step_id VARCHAR(150) NOT NULL,
  step_elapsed_time INT(11) NULL,
  PRIMARY KEY (run_id, amazon_step_id),
  CONSTRAINT fk_step_timing_run_status_run_id
    FOREIGN KEY (run_id)
    REFERENCES udb_process_run (run_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8;
