/*DROP Database Schemas*/
DROP SCHEMA IF EXISTS cpd ;
/*cpd*/

CREATE SCHEMA cpd ;

CREATE TABLE  `cpd`.`server_configuration` (
  `name` varchar(43) NOT NULL,
  `ip` varchar(43) NOT NULL,
  `port` varchar(10) NOT NULL,
  `service` varchar(150) DEFAULT NULL,
  `uuid` varchar(43) NOT NULL
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;