package com.gooalgene.iqgs.entity.condition;

import java.util.List;

/**
 * 基因表达量查询条件构成的bean
 * @author crabime
 */
public class GeneExpressionCondition {
    private List<GeneExpressionConditionEntity> entities;

    public List<GeneExpressionConditionEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<GeneExpressionConditionEntity> entities) {
        this.entities = entities;
    }
}
