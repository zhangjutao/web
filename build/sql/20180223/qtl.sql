UPDATE qtl SET version = 'Glycine_max.V1.0.23.dna.genome' WHERE version IS NULL ;

#创建存储过程，对UTR5;UTR3该种consequencetype缺失的基因重新补录进fpkm_associate_indel关联表
DROP PROCEDURE IF EXISTS insertLostGene;
CREATE PROCEDURE insertLostGene(IN geneConsequence VARCHAR(500), IN consequnceCount INT(11))
  BEGIN
    DECLARE compareConsequenceType VARCHAR(20);
    DECLARE existRow BOOLEAN;
    DECLARE fpkmId INT DEFAULT 1;
    DECLARE indelId INT DEFAULT 1;
    -- 设置比较值
    SET compareConsequenceType := 'UTR5;UTR3';
    DROP TABLE IF EXISTS temp;
    CREATE TABLE temp(log VARCHAR(300));
    IF (locate(',', geneConsequence) > 0) THEN
      SELECT geneConsequence;
    -- todo 增加对字符串的解析
    ELSE
      INSERT INTO temp VALUE ('no comma and insert single row(check row exists first)');
      -- 检查该条记录是否存在
      SELECT exists(SELECT b.* FROM fpkm a
        LEFT JOIN fpkm_associate_indel b ON a.id = b.fpkm_id
        LEFT JOIN indel_consequencetype c ON c.id = b.indel_id
      WHERE a.gene_id = geneConsequence AND c.consequencetype = compareConsequenceType) INTO existRow;
      INSERT INTO temp VALUE (concat('existRow: ', existRow));
      IF existRow THEN
        INSERT INTO temp VALUE (concat(geneConsequence, 'already exists'));
      ELSE
        INSERT INTO temp VALUE (concat(geneConsequence, 'not exists in fpkm_associate_indel table, insert into it'));
        SELECT id INTO fpkmId FROM fpkm WHERE gene_id = geneConsequence;
        SELECT id INTO indelId FROM indel_consequencetype WHERE consequencetype = compareConsequenceType;
        -- 插入记录
        INSERT INTO fpkm_associate_indel (fpkm_id, indel_id, consequencetype_count) VALUES (fpkmId, indelId, consequnceCount);
      END IF;
    END IF ;
    -- 查看已经打印的日志
    SELECT * FROM temp;
  END;