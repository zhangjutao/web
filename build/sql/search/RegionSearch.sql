#根据区域高级搜索
SELECT
  a.*,
  g.gene_info_id,
  g.qtl_name
FROM (SELECT
        a.id,
        a.gene_id,
        a.pod_All,
        a.seed_All,
        a.root_All,
        a.shoot_All,
        a.leaf_All,
        a.seedling_All,
        a.flower_All,
        a.stem_All,
        CASE WHEN b.fpkm_id IS NULL
          THEN FALSE
        ELSE TRUE END AS existSNP,
        f.gene_name,
        f.description,
        f.gene_id_old
      FROM fpkm a
        #是否包含SNP变异位点
        LEFT JOIN (SELECT fpkm_id
                   FROM fpkm_associate_snp
                   WHERE snp_id = 12) AS b ON a.id = b.fpkm_id
        JOIN (SELECT *
              FROM gens_baseinfo
              WHERE chromosome = 'Chr03' AND
                    ((start >= 0 AND start < 1000000) OR (end >= 0 AND end < 1000000) OR (start <= 0 AND end > 0))) AS f
          ON a.baseinfo_id = f.id
        #增加SNP筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_snp
              WHERE 1 = 1 AND snp_id IN (12, 14, 16) GROUP BY fpkm_id) AS g ON a.id = g.fpkm_id
        #增加INDEL筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_indel
              WHERE 1 = 1 AND indel_id IN (12, 13) GROUP BY fpkm_id) AS h ON h.fpkm_id = a.id
        #增加QTL筛选条件
        JOIN (SELECT gene_info_id AS second_gene_info_id
              FROM qtl_gene a
              WHERE 1 = 1 AND a.qtl_id IN (4401)) AS i ON i.second_gene_info_id = f.id
      #增加基因表达量条件
      WHERE 1 = 1 AND (a.endosperm BETWEEN 1 AND 10 OR a.seed BETWEEN 1 AND 10 OR a.embryo BETWEEN 1 AND 10 OR
                       a.axis BETWEEN 1 AND 10 OR a.cotyledon BETWEEN 1 AND 10 OR
                       a.seed_coat BETWEEN 1 AND 10)
      LIMIT 0, 10) AS a
  LEFT JOIN qtl_gene g ON g.gene_info_id = a.id;


#根据geneId高级搜索
SELECT
  a.*,
  g.gene_info_id,
  g.qtl_name
FROM (SELECT
        a.id,
        a.gene_id,
        a.pod_All,
        a.seed_All,
        a.root_All,
        a.shoot_All,
        a.leaf_All,
        a.seedling_All,
        a.flower_All,
        a.stem_All,
        CASE WHEN b.fpkm_id IS NULL
          THEN FALSE
        ELSE TRUE END AS existSNP,
        f.gene_name,
        f.description,
        f.gene_id_old
      FROM fpkm a
        #是否包含SNP变异位点
        LEFT JOIN (SELECT fpkm_id
                   FROM fpkm_associate_snp
                   WHERE snp_id = 12) AS b ON a.id = b.fpkm_id
        JOIN (SELECT *
              FROM gens_baseinfo
              WHERE gene_id LIKE '%Glyma%' OR gene_id_old LIKE '%Glyma%') AS f
          ON a.baseinfo_id = f.id
        #增加SNP筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_snp
              WHERE 1 = 1 AND snp_id IN (12, 14, 16) GROUP BY fpkm_id) AS g ON a.id = g.fpkm_id
        #增加INDEL筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_indel
              WHERE 1 = 1 AND indel_id IN (12, 13) GROUP BY fpkm_id) AS h ON h.fpkm_id = a.id
        #增加QTL筛选条件
        JOIN (SELECT gene_info_id AS second_gene_info_id
              FROM qtl_gene a
              WHERE 1 = 1 AND a.qtl_id IN (4401)) AS i ON i.second_gene_info_id = f.id
      #增加基因表达量条件
      WHERE 1 = 1 AND (a.endosperm BETWEEN 1 AND 10 OR a.seed BETWEEN 1 AND 10 OR a.embryo BETWEEN 1 AND 10 OR
                       a.axis BETWEEN 1 AND 10 OR a.cotyledon BETWEEN 1 AND 10 OR
                       a.seed_coat BETWEEN 1 AND 10)
      LIMIT 0, 10) AS a
  LEFT JOIN qtl_gene g ON g.gene_info_id = a.id;


#根据geneId高级搜索
SELECT
  a.*,
  g.gene_info_id,
  g.qtl_name
FROM (SELECT
        a.id,
        a.gene_id,
        a.pod_All,
        a.seed_All,
        a.root_All,
        a.shoot_All,
        a.leaf_All,
        a.seedling_All,
        a.flower_All,
        a.stem_All,
        CASE WHEN b.fpkm_id IS NULL
          THEN FALSE
        ELSE TRUE END AS existSNP,
        f.gene_name,
        f.description,
        f.gene_id_old
      FROM fpkm a
        #是否包含SNP变异位点
        LEFT JOIN (SELECT fpkm_id
                   FROM fpkm_associate_snp
                   WHERE snp_id = 12) AS b ON a.id = b.fpkm_id
        JOIN (SELECT *
              FROM gens_baseinfo
              WHERE gene_name LIKE '%cy%' OR description LIKE '%cy%') AS f
          ON a.baseinfo_id = f.id
        #增加SNP筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_snp
              WHERE 1 = 1 AND snp_id IN (12, 14, 16) GROUP BY fpkm_id) AS g ON a.id = g.fpkm_id
        #增加INDEL筛选条件
        JOIN (SELECT fpkm_id
              FROM fpkm_associate_indel
              WHERE 1 = 1 AND indel_id IN (12, 13) GROUP BY fpkm_id) AS h ON h.fpkm_id = a.id
        #增加QTL筛选条件
        JOIN (SELECT gene_info_id AS second_gene_info_id
              FROM qtl_gene a
              WHERE 1 = 1 AND a.qtl_id IN (4401)) AS i ON i.second_gene_info_id = f.id
      #增加基因表达量条件
      WHERE 1 = 1 AND (a.endosperm BETWEEN 1 AND 10 OR a.seed BETWEEN 1 AND 10 OR a.embryo BETWEEN 1 AND 10 OR
                       a.axis BETWEEN 1 AND 10 OR a.cotyledon BETWEEN 1 AND 10 OR
                       a.seed_coat BETWEEN 1 AND 10)
      LIMIT 0, 10) AS a
  LEFT JOIN qtl_gene g ON g.gene_info_id = a.id;


