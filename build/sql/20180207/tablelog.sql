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

#在fpkm表中增加与gens_baseinfo表关联的外键baseinfo_id字段
ALTER TABLE fpkm ADD COLUMN baseinfo_id INT(11);
#填充baseinfo字段
UPDATE fpkm a
  LEFT JOIN gens_baseinfo b ON a.gene_id = b.gene_id
SET a.baseinfo_id = b.id;
#增加fpkm表与gens_baseinfo表外键关联
ALTER TABLE fpkm ADD CONSTRAINT fr_fpkm_gene FOREIGN KEY (baseinfo_id) REFERENCES gens_baseinfo(id);

#在qtl_gene中增加gene_id、qtl_name字段
ALTER TABLE qtl_gene ADD COLUMN gene_id VARCHAR(20);
ALTER TABLE qtl_gene ADD COLUMN qtl_name VARCHAR(100);
#给gene_id、qtl_name建立联合外键
create index qtl_name_gene_id
  on qtl_gene (gene_id, qtl_name);
#为qtl_gene中增加的gene_id填充数据
UPDATE qtl_gene a
  LEFT JOIN gens_baseinfo b ON a.gene_info_id = b.id
SET a.gene_id = b.gene_id;
#为qtl_gene中增加的qtl_name填充数据
UPDATE qtl_gene a
  LEFT JOIN associatedgenes b ON a.qtl_id = b.id
SET a.qtl_name = b.qtl_name;
#在qtl_gene中增加trait_category关联字段qtl_trait_id
ALTER TABLE qtl_gene ADD COLUMN qtl_trait_id INT(11);

#将trait_category中主键id字段同步到qtl_gene表中qtl_trait_id列
UPDATE qtl_gene a
  JOIN qtl b ON a.qtl_id = b.associatedGenes_id
  JOIN trait_list c ON c.trait_name = b.trait
  JOIN trait_category d ON d.id = c.qtl_id
SET a.qtl_trait_id = d.id;

#在qtl_gene中qtl_trait_id添加与trait_category表之间的外键关联
ALTER TABLE qtl_gene ADD CONSTRAINT qtl_gene_trait_category FOREIGN KEY (qtl_trait_id) REFERENCES trait_category(id);

#增加用户行为记录表
create table user_associate_trait_fpkm
(
  id int auto_increment
    primary key,
  user_id int null,
  trait_category_id int null,
  fpkm_str varchar(500) null,
  create_time datetime null
);