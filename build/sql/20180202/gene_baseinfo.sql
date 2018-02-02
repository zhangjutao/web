#gens_baseinfo表操作记录

#将locus字段分解
SELECT
  substring_index(locus, ':', 1) AS chromosome,
  substring_index(substring_index(substring_index(substring_index(locus, ':', 2), ':', -1), '-', 1), 'bp', 1) AS start,
  substring_index(substring_index(substring_index(substring_index(locus, ':', 2), ':', -1), '-', -1), 'bp', 1) AS end,
  CASE when substring_index(locus, ':', -1) = '-' THEN 0
  WHEN substring_index(locus, ':', -1) = '+' THEN 1
  END AS symbol
FROM gens_baseinfo;

#添加四个字段（chromosome、start、end、symbol(0: -, 1: +)）
ALTER TABLE gens_baseinfo ADD COLUMN chromosome VARCHAR(10);
ALTER TABLE gens_baseinfo ADD COLUMN start INT(11);
ALTER TABLE gens_baseinfo ADD COLUMN end INT(11);
ALTER TABLE gens_baseinfo ADD COLUMN symbol INT(1);

#分别将locus字段更新到新建的四个字段上
UPDATE gens_baseinfo SET chromosome = substring_index(locus, ':', 1);
UPDATE gens_baseinfo SET start = substring_index(substring_index(substring_index(substring_index(locus, ':', 2), ':', -1), '-', 1), 'bp', 1);
UPDATE gens_baseinfo SET end = substring_index(substring_index(substring_index(substring_index(locus, ':', 2), ':', -1), '-', -1), 'bp', 1);
UPDATE gens_baseinfo SET symbol = (CASE when substring_index(locus, ':', -1) = '-' THEN 0
                                   WHEN substring_index(locus, ':', -1) = '+' THEN 1
                                   END);

#创建fpkm中大于30的一级组织视图
CREATE VIEW fpkm_root AS
  (SELECT pod_All, seed_All, root_All, shoot_All, leaf_All, seedling_All, flower_All, stem_All FROM fpkm
  WHERE pod_All > 30 OR seed_ALL > 30 OR root_ALL > 30 OR shoot_ALL > 30 OR leaf_ALL > 30 OR seedling_ALL > 30 OR flower_ALL > 30 OR stem_ALL > 30);