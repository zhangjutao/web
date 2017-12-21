package com.gooalgene.iqgs.dao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.iqgs.entity.*;

import java.util.List;

/**
 * Created by sauldong on 2017/10/12.
 * Modified By Crabime on 2017/11/12
 */
@MyBatisDao
public interface DNAGenBaseInfoDao {
    List<DNAGenBaseInfo> findByConditions(DNAGenBaseInfo bi);

    /**
     * 查询集合中QTL所包含的所有基因
     * @param inputQTLId QTL集合
     * @return QTL所包含的基因集合
     */
    List<DNAGenBaseInfo> findGeneByQTLName(List<Integer> inputQTLId);

    DNAGenBaseInfo findByGeneId(DNAGenBaseInfo bean);

    List<DNAGenSequence> findGenSequenceByGeneId(DNAGenSequence bean);

    List<DNAGenStructure> findGenStructureByTranscriptId(DNAGenStructure bean);

    List<DNAGenAnnoGo> findGenAnnoGoByGeneId(DNAGenAnnoGo bean);

    List<DNAGenAnnoIpr> findGenAnnoIprByGeneId(DNAGenAnnoIpr bean);

    List<DNAGenAnnoKegg> findGenAnnoKeggByGeneId(DNAGenAnnoKegg bean);

    List<DNAGenStructure> findGenStructureByGeneId(DNAGenStructure bean);

    List<DNAGenStructure> findGenByChr(DNAGenStructure dnaGenStructure);

    List<DNAGenHomologous> findGenHomologousByGeneId(DNAGenHomologous bean);

	List<DNAGenBaseInfo> findBaseInfoByFamilyId(DNAGenBaseInfo bean);

	List<DNAGenFamilyRel> findFamilyByGeneId(String geneId);

	List<DNAGenOffset> findOffsetByFamilyId(String familyId);

	DNAGenFamily findFamilyByFamilyId(String familyId);

	List<DNAGenFamilyStructure> findStructureByFamilyId(String familyId);

	int findMaxLengthByFamilyId(String familyId);

	String findSequenceByTranscriptId(String transcriptId);
}
