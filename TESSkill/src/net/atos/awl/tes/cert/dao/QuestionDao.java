package net.atos.awl.tes.cert.dao;

import java.util.List;

import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;

public interface QuestionDao {

    public int checkPaperAlreadyGiven(String technology, String dasId, int level);

    public List<Integer> getAvailableLevel(String technology, String dasId);

    public List<QuestionVo> getQuestionList(String technology, int level, int type);

    public List<QuestionVo> getRemainingQuestionList(Integer resultId, int examId);

    public int getUserAttemptedQuestionCount(Integer resultId);

    public int getUserAnswerQuestionCount(Integer resultId);

    public LevelExam getQuestionCountByExamId(int examId);

    public List<QuestionVo> getRemainingDesQuestionList(Integer resultId, int examId);

    public int getUserAttemptedDescQuestionCount(Integer resultId);

    public int getUserAttemptedDescQuestionList(Integer resultId);
    
    public List<QuestionVo> getAttemptedDescQuestionList(Integer resultId) ;
    
    public List<QuestionVo> getQuestions(Integer resultId);

}
