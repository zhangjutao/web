#增加trait_list与trait_category中间多对多关系sql

#加关联表
CREATE TABLE category_associate_trait
(
  trait_category_id INT COMMENT 'trait category primary key',
  trait_list_id INT COMMENT 'trait list primary key',
  PRIMARY KEY (trait_category_id, trait_list_id)
);

#增加存储过程
DROP PROCEDURE IF EXISTS supplementToCategoryAssociateTrait;
CREATE PROCEDURE supplementToCategoryAssociateTrait()
  BEGIN
    DECLARE _trait_list_id INT;
    DECLARE _trait_name VARCHAR(100);
    DECLARE _trait_category_id INT;
    -- 获取有一对一请求关联的trait（sum不为2的所有qtl）
    DECLARE fetchTraitList CURSOR FOR SELECT a.trait_name, a.id, a.qtl_id FROM trait_list a
      INNER JOIN (SELECT count(trait_name) AS sum, trait_name FROM trait_list GROUP BY trait_name) b ON a.trait_name = b.trait_name AND b.sum <> 2;
    -- 对sum大于1的值进行集中处理（在一个新的游标中）
    DECLARE fetchMultipleTraitList CURSOR FOR SELECT a.trait_name FROM trait_list a
      INNER JOIN (SELECT count(trait_name) AS sum, trait_name FROM trait_list GROUP BY trait_name) b
        ON a.trait_name = b.trait_name AND b.sum = 2
    GROUP BY a.trait_name;

    OPEN fetchTraitList;
    BEGIN
      DECLARE ergodic INT DEFAULT 1;
      DECLARE EXIT HANDLER FOR NOT FOUND SET ergodic = 0;
      REPEAT
        FETCH fetchTraitList INTO  _trait_name, _trait_list_id, _trait_category_id;
        INSERT INTO category_associate_trait VALUES (_trait_category_id, _trait_list_id);
      UNTIL ergodic = 0 END REPEAT ;
    END ;
    CLOSE fetchTraitList;

    OPEN fetchMultipleTraitList;
    BEGIN
      DECLARE ergodic INT DEFAULT 1;
      -- 选择的trait_list id主键
      DECLARE chosen_trait_list_id INT;
      -- 选中的trait_list行对应的qtl_id字段
      DECLARE chosen_qtl_id INT;
      DECLARE delete_trait_list_id INT;
      DECLARE under_insert_qtl_id INT;
      DECLARE EXIT HANDLER FOR NOT FOUND SET ergodic = 0;
      REPEAT
        FETCH fetchMultipleTraitList INTO  _trait_name;
        -- 选择添加到关联表的trait_list行
        SELECT id INTO chosen_trait_list_id FROM trait_list WHERE trait_name = _trait_name LIMIT 1;
        -- 拿到选中的trait_list行中qtl_id列值
        SELECT qtl_id INTO chosen_qtl_id FROM trait_list WHERE id = chosen_trait_list_id;
        -- 向关联表中插入数据
        INSERT INTO category_associate_trait(trait_category_id, trait_list_id) VALUES (chosen_qtl_id, chosen_trait_list_id);
        -- 选择删除的trait_list行
        SELECT id INTO delete_trait_list_id FROM trait_list WHERE trait_name = _trait_name AND id <> chosen_trait_list_id;
        -- 找到待删除trait_list行的qtl_id并插入到关联表中
        SELECT qtl_id INTO under_insert_qtl_id FROM trait_list WHERE id = delete_trait_list_id;
        INSERT INTO category_associate_trait(trait_category_id, trait_list_id) VALUES (under_insert_qtl_id, chosen_trait_list_id);
        -- 删除trait_list中条数为2且多余的trait_name
        DELETE FROM trait_list WHERE id = delete_trait_list_id;
      UNTIL ergodic = 0 END REPEAT ;
    END;
    CLOSE fetchMultipleTraitList;
  END;

#执行存储过程
CALL supplementToCategoryAssociateTrait();

#修改qtl_gene表中qtl_trait_id外键，先删除，后重新建指向category_associate_trait中trait_list_id字段
ALTER TABLE qtl_gene DROP FOREIGN KEY qtl_gene_trait_category;
#更新qtl_trait_id字段
UPDATE qtl_gene a
  LEFT JOIN qtl b ON a.qtl_name = b.qtl_name
  LEFT JOIN trait_list c ON c.trait_name = b.trait
  LEFT JOIN category_associate_trait d ON d.trait_list_id = c.id
SET a.qtl_trait_id = d.trait_list_id;

#删除trait_list中关联trait_category主键的外键字段
ALTER TABLE trait_list DROP FOREIGN KEY qtk_id;
ALTER TABLE trait_list DROP COLUMN qtl_id;