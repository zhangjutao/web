package com.gooalgene.iqgs.entity.condition;

import java.util.List;

/**
 * 高级搜索中QTL查询集合
 * @author crabime
 */
public class QTLCondition {
    private List<QTLConditionEntity> entities;

    public List<QTLConditionEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<QTLConditionEntity> entities) {
        this.entities = entities;
    }
}
