package net.atos.awl.tes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.service.QuestionDaoService;
import net.atos.awl.tes.cert.service.QuestionService;
import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Prashant Mahajan
 */
public class ResultAction extends ActionSupport implements SessionAware {

    Map session;

    private static final long serialVersionUID = 1L;

    public static final String vOUTPUT_DATE_FORMAT = "yyyy-MM-dd HH:mms";

    private static final SimpleDateFormat vOutPutDate = new SimpleDateFormat(vOUTPUT_DATE_FORMAT);

    // private QuestionVo question;
    private List<QuestionVo> questions;

    private List<Result> testList;

    private List<String> review;

    private String accessLevel;

    private Result result;

    private int resultId = 0;

    private List<String> disQuestionId;

    private ResultService resultDaoService;

    public ResultService getResultDaoService() {
        return resultDaoService;
    }

    public void setResultDaoService(ResultService resultDaoService) {
        this.resultDaoService = resultDaoService;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public List<String> getDisQuestionId() {
        return disQuestionId;
    }

    public void setDisQuestionId(List<String> disQuestionId) {
        this.disQuestionId = disQuestionId;
    }

    public List<QuestionVo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionVo> questions) {
        this.questions = questions;
    }

    public List<Result> getTestList() {
        return testList;
    }

    public void setTestList(List<Result> testList) {
        this.testList = testList;
    }

    public List<String> getReview() {
        return review;
    }

    public void setReview(List<String> review) {
        this.review = review;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    private Map<Integer, QuestionVo> answerMap;

    private QuestionService questionService;

    public QuestionService getQuestionService() {
        return questionService;
    }

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Map<Integer, QuestionVo> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Integer, QuestionVo> answerMap) {
        this.answerMap = answerMap;
    }

    private String[] yourOption;

    public String[] getYourOption() {
        return yourOption;
    }

    public void setYourOption(String[] yourOption) {
        this.yourOption = yourOption;
    }

    public List<QuestionVo> getQuestionVos() {
        return questions;
    }

    public void setQuestionVos(List<QuestionVo> questions) {
        this.questions = questions;
    }

    public ResultAction() {
        questions = new ArrayList<QuestionVo>();
        answerMap = new HashMap<Integer, QuestionVo>();
        resultDaoService = new ResultDaoService();
        questionService = new QuestionDaoService();
    }

    public String reviewListBack() {
        String selectedTech = (String) session.get("SelectedTech");
        int selectedlevel = (Integer) session.get("selectedlevel");
        testList = new ArrayList<Result>();
        testList = resultDaoService.getReviewResultList(selectedTech, selectedlevel);
        return "success";
    }

    public String resultCertification() {
        System.out.println("resultCertification started....");
        List<QuestionVo> qstions = getQuestionVos();
        System.out.println("number of qsn " + qstions.size());
        for (QuestionVo qns : qstions) {
            String op = qns.getSelectedAnswer();
            System.out.println("your anww " + op);
        }

        return "success";
    }

    public String reviewQuestions() {
        System.out.println("final result calculation..." + resultId);
        List<ResultDetails> updateResultList = new ArrayList<ResultDetails>();
        int testId = (Integer) session.get("RESULTID");
        for (int i = 0; i < review.size(); i++) {
            String revComment = review.get(i);
            System.out.println("revComment is " + revComment + "for " + i);
            if (!revComment.equalsIgnoreCase("-1")) {
                ResultDetails res = new ResultDetails();
                String qId = disQuestionId.get(i);
                res.setResultId(testId);
                res.setQuestionId(Integer.parseInt(qId));
                res.setCorrectOption(revComment.equalsIgnoreCase("Correct") ? 1 : 0);
                updateResultList.add(res);
            }
        }
        System.out.println("update result of size " + updateResultList.size());
        if (updateResultList.size() > 0) {
            resultDaoService.updateResultDetailsForReview(updateResultList);
            int result = 0;
            int notAttempt = 0;
            List<ResultDetails> finalResultList = resultDaoService.getResultDetailsByType(String
                    .valueOf(resultId), 0);
            for (ResultDetails resultVo : finalResultList) {
                int qNo = resultVo.getQuestionId();
                String correctAns = resultVo.getCorrectOption().toString();

                if (resultVo.getAnswerOption().equals("0")) {
                    notAttempt++;
                } else {
                    String submitedAns = resultVo.getAnswerOption();
                    System.out.println("Final result correctAns " + correctAns + " submitedAns "
                            + submitedAns + "question num " + qNo);
                    if (submitedAns.equals(correctAns)) {
                        result++;
                    }
                }
            }

            int wrongDescAns = 0;
            int correctDescAns = 0;
            List<ResultDetails> descResultList = resultDaoService.getResultDetailsByType(String
                    .valueOf(resultId), 1);
            System.out.println("descResultList size::::" + descResultList.size());
            for (ResultDetails resultVo : descResultList) {
                if (null != resultVo.getCorrectOption()) {
                    if (resultVo.getCorrectOption() == 0) {
                        wrongDescAns++;
                        System.out.println("wrong desc ans::::" + wrongDescAns);
                    } else if (resultVo.getCorrectOption() == 1) {
                        correctDescAns++;
                        System.out.println("correct desc ans::::" + correctDescAns);
                    }
                }
            }

            int descCorrectAns = correctDescAns;
            int descWrongAns = wrongDescAns;

            int totalResult = result + descCorrectAns;

            LevelExam levelExam = null;
            String techName = null;
            int levelNum = -1;
            // if (session.get("SelectedTech") != null &&
            // session.get("selectedlevel") != null) {
            techName = (String) session.get("SelectedTech");
            levelNum = (Integer) session.get("selectedlevel");
            levelExam = resultDaoService.getLevelExamResult(techName, levelNum);

            int passingCount = levelExam.getPassingCount();
            int questionCount = levelExam.getQuestionCount();
            int descQuesCount = levelExam.getDescriptiveQuestionCount();

            int totalQuestCount = questionCount + descQuesCount;

            int finalRes = 0;
            if (totalResult >= passingCount) {
                System.out.println("Test PASS " + result);
                finalRes = 1;
            } else {
                System.out.println("Test FAIL " + result);
            }
            String das = (String) session.get("DASID");
            System.out.println("result value:::::" + result + "passing count" + passingCount
                    + "questionCount::::" + questionCount);

            double percentage = (double) Math.round(((double) totalResult / totalQuestCount) * 100);
            System.out.println("percentage value::::" + percentage);
            Timestamp time = getCurrentSystemTimeStamp();
            resultDaoService.updateResult(resultId, finalRes, percentage, time);
        }
        String selectedTech = (String) session.get("SelectedTech");
        int selectedlevel = (Integer) session.get("selectedlevel");
        testList = new ArrayList<Result>();
        testList = resultDaoService.getReviewResultList(selectedTech, selectedlevel);
        return "success";
    }

    private Timestamp getCurrentSystemTimeStamp() {
        java.sql.Timestamp timestamp = null;
        Calendar cal = Calendar.getInstance();
        String newdate = vOutPutDate.format(cal.getTime());
        java.util.Date parsedDate;

        try {
            parsedDate = vOutPutDate.parse(newdate);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
