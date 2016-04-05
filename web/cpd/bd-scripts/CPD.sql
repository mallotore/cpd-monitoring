/*DROP Database Schemas*/
DROP SCHEMA IF EXISTS cpd ;
/*cpd*/

CREATE SCHEMA cpd ;

CREATE TABLE  `cpd`.`server_configuration` (
  `name` varchar(43) NOT NULL,
  `ip` varchar(43) NOT NULL,
  `port` int NOT NULL,
  `probe_interval_in_seconds` int NOT NULL DEFAULT 0,
  `uuid` varchar(43) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  `cpd`.`temperature_configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `probe_interval_in_seconds` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;