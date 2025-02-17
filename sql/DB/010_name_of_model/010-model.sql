/* ---------------------------------------------------- */
/*  Generated by Enterprise Architect Version 15.2 		*/
/*  Created On : 15-���-2023 21:01:52 				*/
/*  DBMS       : PostgreSQL 						*/
/* ---------------------------------------------------- */

/* Drop Tables */
set search_path to topt;

DROP TABLE IF EXISTS model CASCADE
;

/* Create Tables */

CREATE TABLE model
(
	id bigint NOT NULL,	-- technical id
	created timestamp NOT NULL,
	cax bigint NOT NULL,
	eax bigint NULL,
	tech_comm varchar(1024) NULL,
	state varchar(32) NOT NULL,	-- active; blocked; deleted
	state_date timestamp NOT NULL,
	custom_field varchar(64) NOT NULL
) PARTITION BY RANGE (created);

/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON COLUMN com_user.id
	IS 'technical id'
;

COMMENT ON COLUMN com_user.state
	IS 'active; blocked; deleted'
;
