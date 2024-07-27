use calligraphy_evaluation_system;
create table tmp_area(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '区域id',
  name VARCHAR(25) NOT NULL comment '区域名',
  area_code int(11) DEFAULT NULL COMMENT '地区编码',
  city_code varchar(11) DEFAULT NULL COMMENT '城市编码',
  parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
  level tinyint(1) DEFAULT NULL COMMENT '地区等级'
);

create table citycode2(
id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '区域id',
  area_name VARCHAR(25) NOT NULL comment '区域名',
  adcode int(11) DEFAULT NULL COMMENT '地区编码',
  citycode varchar(11) DEFAULT NULL COMMENT '城市编码'
);
CREATE TABLE `sys_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(255) DEFAULT NULL COMMENT '地区名称',
  `area_code` int(11) DEFAULT NULL COMMENT '地区编码',
  `city_code` varchar(11) DEFAULT NULL COMMENT '城市编码',
  `level` tinyint(1) DEFAULT NULL COMMENT '地区等级',
  parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `sys_position`
RENAME TO `region`,
CHANGE `area_name` `name` varchar(255) DEFAULT NULL COMMENT '地区名称';


DROP FUNCTION IF EXISTS create_city;
DELIMITER $$
CREATE FUNCTION create_city(provinceCode VARCHAR(11))
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
  DECLARE intProvinceCode INT(11);
  DECLARE intCityCode INT(11);
  DECLARE loopTimes INT(11) DEFAULT 0;
  DECLARE areaCityCode VARCHAR(11);
  DECLARE i INT(11);
  DECLARE tempPage INT(11) DEFAULT 0;
  DECLARE parentId INT UNSIGNED;
  SET intProvinceCode = CONVERT(provinceCode,SIGNED);
  SELECT COUNT(1) INTO loopTimes FROM citycode2 WHERE adcode BETWEEN intProvinceCode AND intProvinceCode+10000 AND SUBSTRING(adcode,3) != "0000" AND SUBSTRING(adcode,5) ="00";
  SET i = 1;
  -- 插入省级地区的数据
  INSERT INTO sys_position (area_name,area_code,city_code,`level`, parent_id)
  SELECT area_name,adcode,citycode,1, NULL FROM citycode2
  WHERE adcode = provinceCode;
  SELECT id INTO parentId FROM sys_position WHERE area_code = provinceCode;
  -- 插入市级地区的数据
  INSERT INTO sys_position (area_name,area_code,city_code,`level`, parent_id)
  SELECT area_name,adcode,citycode,2, parentId FROM citycode2
  WHERE adcode BETWEEN intProvinceCode AND intProvinceCode+10000 AND SUBSTRING(adcode,3) != "0000" AND SUBSTRING(adcode,5) ="00";
  WHILE i <= loopTimes DO
   SET tempPage = i-1;
   SELECT adcode INTO areaCityCode FROM citycode2 WHERE adcode BETWEEN intProvinceCode AND intProvinceCode+10000 AND SUBSTRING(adcode,3) != "0000" AND SUBSTRING(adcode,5) ="00" LIMIT tempPage,1;
   SET intCityCode = CONVERT(CONCAT(SUBSTRING(areaCityCode,3,2),"00"),SIGNED);
   SET intCityCode = intProvinceCode+intCityCode;
   SELECT id INTO parentId FROM sys_position WHERE area_code = areaCityCode;
   -- 插入县级地区的数据
   INSERT INTO sys_position (area_name,area_code,city_code,`level`, parent_id)
   SELECT area_name,adcode,citycode,3, parentId FROM citycode2
   WHERE adcode BETWEEN intCityCode AND intCityCode+100 AND SUBSTRING(adcode,3) != "0000" AND SUBSTRING(adcode,5) !="00";
   SET i = i + 1;
  END WHILE;
  RETURN 'hello';
END$$
DELIMITER ;


select create_city(adcode) from citycode2 where substring(adcode,3)="0000" and adcode!="100000" and adcode!="900000"