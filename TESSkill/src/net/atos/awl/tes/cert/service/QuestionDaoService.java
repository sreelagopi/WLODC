package net.atos.awl.tes.cert.service;

import java.util.List;

import net.atos.awl.tes.cert.dao.QuestionDao;
import net.atos.awl.tes.cert.dao.QuestionDaoImpl;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;

public class QuestionDaoService implements QuestionService {

    private QuestionDao questionDao;

    public QuestionDaoService() {
        questionDao = new QuestionDaoImpl();
    }

    @Override
    public List<Integer> getAvailableLevel(String technology, String dasId) {
        return questionDao.getAvailableLevel(technology, dasId);
    }

    @Override
    public List<QuestionVo> getQuestionList(String technology, int level, int type) {
        return questionDao.getQuestionList(technology, level, type);
    }

    public List<QuestionVo> getRemainingQuestionList(Integer resultId, int examId) {
        return questionDao.getRemainingQuestionList(resultId, examId);
    }

    public int getUserAttemptedQuestionCount(Integer resultId) {
        return questionDao.getUserAttemptedQuestionCount(resultId);
    }

    public int getUserAnswerQuestionCount(Integer resultId) {
        return questionDao.getUserAnswerQuestionCount(resultId);
    }

    public LevelExam getQuestionCountByExamId(int examId) {
        return questionDao.getQuestionCountByExamId(examId);
    }

    public List<QuestionVo> getRemainingDesQuestionList(Integer resultId, int examId) {
        return questionDao.getRemainingDesQuestionList(resultId, examId);
    }

    public int getUserAttemptedDescQuestionCount(Integer resultId) {
        return questionDao.getUserAttemptedDescQuestionCount(resultId);
    }

    public int getUserAttemptedDescQuestionList(Integer resultId) {
        return questionDao.getUserAttemptedDescQuestionList(resultId);
    }
    public List<QuestionVo> getAttemptedDescQuestionList(Integer resultId) {
    	 return questionDao.getAttemptedDescQuestionList(resultId);
    }
    
    public List<QuestionVo> getQuestions(Integer resultId) {
   	 return questionDao.getQuestions(resultId);
   }
}
