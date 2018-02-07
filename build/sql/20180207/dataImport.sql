#将associateGene表中漏掉的20个QTL补齐，并插入到qtl_gene表中，需要传入associateGene ID主键
DROP PROCEDURE IF EXISTS splitStr;
CREATE PROCEDURE splitStr(IN num INT)
  BEGIN
    DECLARE a INT DEFAULT 1;
    DECLARE _text TEXT DEFAULT NULL ;
    DECLARE _next VARCHAR(500) DEFAULT NULL ;
    DECLARE _value VARCHAR(100) DEFAULT NULL ;
    DECLARE _nextLen INT DEFAULT NULL ;
    DECLARE _baseinfo_id INT DEFAULT NULL ;
    DECLARE _gene_id VARCHAR(40) DEFAULT NULL ;
    DECLARE _qtl_name VARCHAR(100) DEFAULT NULL ;
    DECLARE _trait_id INT DEFAULT NULL ;
    SELECT round((length(associated_genes)-length(replace(associated_genes, ',', '')))/length(',')) INTO a FROM associatedgenes WHERE id = num;
    SELECT associated_genes, qtl_name INTO _text, _qtl_name FROM associatedgenes WHERE id = num;
    SELECT c.id INTO _trait_id FROM qtl a
      INNER JOIN trait_list b ON a.trait = b.trait_name
      INNER JOIN trait_category c ON c.id = b.qtl_id
    WHERE a.associatedGenes_id = num;
    iterator:
    LOOP
      IF length(trim(_text)) = 0 OR _text IS NULL THEN
        LEAVE iterator;
      END IF;
      -- 拿到第一个值
      SET _next = substring_index(_text, ',', 1);

      -- 该值作为remove length+1的条件，这样可以获取下一个字串
      SET _nextLen = length(_next);

      -- 获取到该数据的值
      SET _value = trim(_next);

      -- 上gens_baseinfo表中找到对应的GeneId
      SELECT id, gene_id INTO _baseinfo_id, _gene_id FROM gens_baseinfo WHERE gene_id = _value OR gene_id_old = _value;

      -- 在qtl_gene表中补齐关联关系
      INSERT INTO qtl_gene(gene_info_id, qtl_id, gene_id, qtl_name, qtl_trait_id) VALUES (_baseinfo_id , num, _gene_id, _qtl_name, _trait_id);

      SET _text = insert(_text, 1, _nextLen+1, '');
    END LOOP;
  END;

#删除qtl_gene中v1版本的关联行
DELETE a.* FROM qtl_gene a WHERE a.qtl_id IN (
  SELECT id FROM associatedgenes WHERE version = 'Glycine_max.V1.0.23.dna.genome');