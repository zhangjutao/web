#创建fpkm_score view
CREATE VIEW fpkm_score AS
  SELECT gene_id,
    CASE WHEN pod BETWEEN 0 AND 5 THEN 10
    WHEN pod BETWEEN 5 AND 15 THEN 20
    WHEN pod BETWEEN 15 AND 30 THEN 30
    WHEN pod BETWEEN 30 AND 60 THEN 40
    WHEN pod > 60 THEN 50
    END AS pod,
    CASE WHEN endosperm BETWEEN 0 AND 5 THEN 10
    WHEN endosperm BETWEEN 5 AND 15 THEN 20
    WHEN endosperm BETWEEN 15 AND 30 THEN 30
    WHEN endosperm BETWEEN 30 AND 60 THEN 40
    WHEN endosperm > 60 THEN 50
    END AS endosperm,
    CASE WHEN seed BETWEEN 0 AND 5 THEN 10
    WHEN seed BETWEEN 5 AND 15 THEN 20
    WHEN seed BETWEEN 15 AND 30 THEN 30
    WHEN seed BETWEEN 30 AND 60 THEN 40
    WHEN seed > 60 THEN 50
    END AS seed,
    CASE WHEN embryo BETWEEN 0 AND 5 THEN 10
    WHEN embryo BETWEEN 5 AND 15 THEN 20
    WHEN embryo BETWEEN 15 AND 30 THEN 30
    WHEN embryo BETWEEN 30 AND 60 THEN 40
    WHEN embryo > 60 THEN 50
    END AS embryo,
    CASE WHEN axis BETWEEN 0 AND 5 THEN 10
    WHEN axis BETWEEN 5 AND 15 THEN 20
    WHEN axis BETWEEN 15 AND 30 THEN 30
    WHEN axis BETWEEN 30 AND 60 THEN 40
    WHEN axis > 60 THEN 50
    END AS axis,
    CASE WHEN cotyledon BETWEEN 0 AND 5 THEN 10
    WHEN cotyledon BETWEEN 5 AND 15 THEN 20
    WHEN cotyledon BETWEEN 15 AND 30 THEN 30
    WHEN cotyledon BETWEEN 30 AND 60 THEN 40
    WHEN cotyledon > 60 THEN 50
    END AS cotyledon,
    CASE WHEN seed_coat BETWEEN 0 AND 5 THEN 10
    WHEN seed_coat BETWEEN 5 AND 15 THEN 20
    WHEN seed_coat BETWEEN 15 AND 30 THEN 30
    WHEN seed_coat BETWEEN 30 AND 60 THEN 40
    WHEN seed_coat > 60 THEN 50
    END AS seed_coat,
    CASE WHEN nodule BETWEEN 0 AND 5 THEN 10
    WHEN nodule BETWEEN 5 AND 15 THEN 20
    WHEN nodule BETWEEN 15 AND 30 THEN 30
    WHEN nodule BETWEEN 30 AND 60 THEN 40
    WHEN nodule > 60 THEN 50
    END AS nodule,
    CASE WHEN root_hair BETWEEN 0 AND 5 THEN 10
    WHEN root_hair BETWEEN 5 AND 15 THEN 20
    WHEN root_hair BETWEEN 15 AND 30 THEN 30
    WHEN root_hair BETWEEN 30 AND 60 THEN 40
    WHEN root_hair > 60 THEN 50
    END AS root_hair,
    CASE WHEN root BETWEEN 0 AND 5 THEN 10
    WHEN root BETWEEN 5 AND 15 THEN 20
    WHEN root BETWEEN 15 AND 30 THEN 30
    WHEN root BETWEEN 30 AND 60 THEN 40
    WHEN root > 60 THEN 50
    END AS root,
    CASE WHEN root_tip BETWEEN 0 AND 5 THEN 10
    WHEN root_tip BETWEEN 5 AND 15 THEN 20
    WHEN root_tip BETWEEN 15 AND 30 THEN 30
    WHEN root_tip BETWEEN 30 AND 60 THEN 40
    WHEN root_tip > 60 THEN 50
    END AS root_tip,
    CASE WHEN shoot BETWEEN 0 AND 5 THEN 10
    WHEN shoot BETWEEN 5 AND 15 THEN 20
    WHEN shoot BETWEEN 15 AND 30 THEN 30
    WHEN shoot BETWEEN 30 AND 60 THEN 40
    WHEN shoot > 60 THEN 50
    END AS shoot,
    CASE WHEN shoot_tip BETWEEN 0 AND 5 THEN 10
    WHEN shoot_tip BETWEEN 5 AND 15 THEN 20
    WHEN shoot_tip BETWEEN 15 AND 30 THEN 30
    WHEN shoot_tip BETWEEN 30 AND 60 THEN 40
    WHEN shoot_tip > 60 THEN 50
    END AS shoot_tip,
    CASE WHEN shoot_apical_meristem BETWEEN 0 AND 5 THEN 10
    WHEN shoot_apical_meristem BETWEEN 5 AND 15 THEN 20
    WHEN shoot_apical_meristem BETWEEN 15 AND 30 THEN 30
    WHEN shoot_apical_meristem BETWEEN 30 AND 60 THEN 40
    WHEN shoot_apical_meristem > 60 THEN 50
    END AS shoot_apical_meristem,
    CASE WHEN shoot_meristem BETWEEN 0 AND 5 THEN 10
    WHEN shoot_meristem BETWEEN 5 AND 15 THEN 20
    WHEN shoot_meristem BETWEEN 15 AND 30 THEN 30
    WHEN shoot_meristem BETWEEN 30 AND 60 THEN 40
    WHEN shoot_meristem > 60 THEN 50
    END AS shoot_meristem,
    CASE WHEN primaryleaf BETWEEN 0 AND 5 THEN 10
    WHEN primaryleaf BETWEEN 5 AND 15 THEN 20
    WHEN primaryleaf BETWEEN 15 AND 30 THEN 30
    WHEN primaryleaf BETWEEN 30 AND 60 THEN 40
    WHEN primaryleaf > 60 THEN 50
    END AS primaryleaf,
    CASE WHEN petiole BETWEEN 0 AND 5 THEN 10
    WHEN petiole BETWEEN 5 AND 15 THEN 20
    WHEN petiole BETWEEN 15 AND 30 THEN 30
    WHEN petiole BETWEEN 30 AND 60 THEN 40
    WHEN petiole > 60 THEN 50
    END AS petiole,
    CASE WHEN leaf BETWEEN 0 AND 5 THEN 10
    WHEN leaf BETWEEN 5 AND 15 THEN 20
    WHEN leaf BETWEEN 15 AND 30 THEN 30
    WHEN leaf BETWEEN 30 AND 60 THEN 40
    WHEN leaf > 60 THEN 50
    END AS leaf,
    CASE WHEN trifoliate BETWEEN 0 AND 5 THEN 10
    WHEN trifoliate BETWEEN 5 AND 15 THEN 20
    WHEN trifoliate BETWEEN 15 AND 30 THEN 30
    WHEN trifoliate BETWEEN 30 AND 60 THEN 40
    WHEN trifoliate > 60 THEN 50
    END AS trifoliate,
    CASE WHEN leafbud BETWEEN 0 AND 5 THEN 10
    WHEN leafbud BETWEEN 5 AND 15 THEN 20
    WHEN leafbud BETWEEN 15 AND 30 THEN 30
    WHEN leafbud BETWEEN 30 AND 60 THEN 40
    WHEN leafbud > 60 THEN 50
    END AS leafbud,
    CASE WHEN leaflet BETWEEN 0 AND 5 THEN 10
    WHEN leaflet BETWEEN 5 AND 15 THEN 20
    WHEN leaflet BETWEEN 15 AND 30 THEN 30
    WHEN leaflet BETWEEN 30 AND 60 THEN 40
    WHEN leaflet > 60 THEN 50
    END AS leaflet,
    CASE WHEN cotyledons_of_seedling BETWEEN 0 AND 5 THEN 10
    WHEN cotyledons_of_seedling BETWEEN 5 AND 15 THEN 20
    WHEN cotyledons_of_seedling BETWEEN 15 AND 30 THEN 30
    WHEN cotyledons_of_seedling BETWEEN 30 AND 60 THEN 40
    WHEN cotyledons_of_seedling > 60 THEN 50
    END AS cotyledons_of_seedling,
    CASE WHEN seedling BETWEEN 0 AND 5 THEN 10
    WHEN seedling BETWEEN 5 AND 15 THEN 20
    WHEN seedling BETWEEN 15 AND 30 THEN 30
    WHEN seedling BETWEEN 30 AND 60 THEN 40
    WHEN seedling > 60 THEN 50
    END AS seedling,
    CASE WHEN flower_bud BETWEEN 0 AND 5 THEN 10
    WHEN flower_bud BETWEEN 5 AND 15 THEN 20
    WHEN flower_bud BETWEEN 15 AND 30 THEN 30
    WHEN flower_bud BETWEEN 30 AND 60 THEN 40
    WHEN flower_bud > 60 THEN 50
    END AS flower_bud,
    CASE WHEN flower BETWEEN 0 AND 5 THEN 10
    WHEN flower BETWEEN 5 AND 15 THEN 20
    WHEN flower BETWEEN 15 AND 30 THEN 30
    WHEN flower BETWEEN 30 AND 60 THEN 40
    WHEN flower > 60 THEN 50
    END AS flower,
    CASE WHEN stem BETWEEN 0 AND 5 THEN 10
    WHEN stem BETWEEN 5 AND 15 THEN 20
    WHEN stem BETWEEN 15 AND 30 THEN 30
    WHEN stem BETWEEN 30 AND 60 THEN 40
    WHEN stem > 60 THEN 50
    END AS stem,
    CASE WHEN stem_internode BETWEEN 0 AND 5 THEN 10
    WHEN stem_internode BETWEEN 5 AND 15 THEN 20
    WHEN stem_internode BETWEEN 15 AND 30 THEN 30
    WHEN stem_internode BETWEEN 30 AND 60 THEN 40
    WHEN stem_internode > 60 THEN 50
    END AS stem_internode,
    CASE WHEN hypocotyl BETWEEN 0 AND 5 THEN 10
    WHEN hypocotyl BETWEEN 5 AND 15 THEN 20
    WHEN hypocotyl BETWEEN 15 AND 30 THEN 30
    WHEN hypocotyl BETWEEN 30 AND 60 THEN 40
    WHEN hypocotyl > 60 THEN 50
    END AS hypocotyl
  FROM fpkm;

#在qtl_gene中增加trait_category关联的qtl_trait_id列
ALTER TABLE qtl_gene ADD COLUMN qtl_trait_id INT(11);

#将trait_category中主键id字段同步到qtl_gene表中qtl_trait_id列
UPDATE qtl_gene a
  JOIN qtl b ON a.qtl_id = b.associatedGenes_id
  JOIN trait_list c ON c.trait_name = b.trait
  JOIN trait_category d ON d.id = c.qtl_id
SET a.qtl_trait_id = d.id;

#在qtl_gene中qtl_trait_id添加与trait_category表之间的外键关联
ALTER TABLE qtl_gene ADD CONSTRAINT qtl_gene_trait_category FOREIGN KEY (qtl_trait_id) REFERENCES trait_category(id);

#在qtl_gene中增加联合主键qtl_name_gene_id
CREATE INDEX qtl_name_gene_id ON qtl_gene(gene_id, qtl_name);