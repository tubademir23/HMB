CREATE DATABASE mulakat WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
ALTER DATABASE mulakat OWNER TO postgres;
connect mulakat;
CREATE SCHEMA mulakat;
ALTER SCHEMA mulakat OWNER TO postgres;