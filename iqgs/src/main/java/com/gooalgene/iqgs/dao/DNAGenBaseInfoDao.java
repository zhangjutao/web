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
    public List<DNAGenBaseInfo> findByConditions(DNAGenBaseInfo bi);

    public DNAGenBaseInfo findByGeneId(DNAGenBaseInfo bean);

    public List<DNAGenSequence> findGenSequenceByGeneId(DNAGenSequence bean);

    public List<DNAGenAnnoGo> findGenAnnoGoByGeneId(DNAGenAnnoGo bean);

    public List<DNAGenAnnoIpr> findGenAnnoIprByGeneId(DNAGenAnnoIpr bean);

    public List<DNAGenAnnoKegg> findGenAnnoKeggByGeneId(DNAGenAnnoKegg bean);

    public List<DNAGenStructure> findGenStructureByGeneId(DNAGenStructure bean);

    public List<DNAGenStructure> findGenByChr(DNAGenStructure dnaGenStructure);

    public List<DNAGenHomologous> findGenHomologousByGeneId(DNAGenHomologous bean);

	public List<DNAGenBaseInfo> findBaseInfoByFamilyId(DNAGenBaseInfo bean);

	public List<DNAGenFamilyRel> findFamilyByGeneId(String geneId);

	public List<DNAGenOffset> findOffsetByFamilyId(String familyId);

	public DNAGenFamily findFamilyByFamilyId(String familyId);

	public List<DNAGenFamilyStructure> findStructureByFamilyId(String familyId);

	public int findMaxLengthByFamilyId(String familyId);

	public String findSequenceByTranscriptId(String transcriptId);
}
