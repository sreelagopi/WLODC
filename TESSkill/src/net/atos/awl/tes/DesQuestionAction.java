/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.atos.awl.tes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.service.QuestionDaoService;
import net.atos.awl.tes.cert.service.QuestionService;
import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.ResultDetails;
import net.atos.awl.tes.cert.vo.Technology;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Gauri Chiplunkar
 */
public class DesQuestionAction extends ActionSupport implements SessionAware {
    /**
	 * 
	 */
    Map<String, Object> session;

    public static final String vOUTPUT_DATE_FORMAT = "yyyy-MM-dd HH:mms";

    private static final SimpleDateFormat vOutPutDate = new SimpleDateFormat(vOUTPUT_DATE_FORMAT);

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private List<Technology> technologyList;

    private List<Integer> levelList;

    private ResultService resultDaoService;

    private String technologies;

    private String levels;

    private String questionText;

    private ResultService resultService;

    private QuestionService questionService;

    private int attempted = 0;

    private List<QuestionVo> questions;

    private int counter = 1;

    private int totalQuestions;

    private int totalObjQuestions;

    private int attemptObjquestions;

    private int attemptDescQuestions = 0;
    
    private int pendingForreview = 0;

    private int correctAnswers;

    private int wrongAnswers;
    
    private String status;

	public String getStatus()
	{
		return status;
	}

	public Double getPercentile()
	{
		return percentile;
	}

	private Double percentile;


    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getTotalObjQuestions() {
        return totalObjQuestions;
    }

    public void setTotalObjQuestions(int totalObjQuestions) {
        this.totalObjQuestions = totalObjQuestions;
    }

    public int getAttemptObjquestions() {
        return attemptObjquestions;
    }

    public void setAttemptObjquestions(int attemptObjquestions) {
        this.attemptObjquestions = attemptObjquestions;
    }

    public int getAttemptDescQuestions() {
        return attemptDescQuestions;
    }

    public void setAttemptDescQuestions(int attemptDescQuestions) {
        this.attemptDescQuestions = attemptDescQuestions;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ResultService getResultDaoService() {
        return resultDaoService;
    }

    public void setResultDaoService(ResultService resultDaoService) {
        this.resultDaoService = resultDaoService;
    }

    public List<QuestionVo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionVo> questions) {
        this.questions = questions;
    }

    private List<QuestionVo> questionsDisplay;

    public List<QuestionVo> getQuestionsDisplay() {
        return questionsDisplay;
    }

    public void setQuestionsDisplay(List<QuestionVo> questionsDisplay) {
        this.questionsDisplay = questionsDisplay;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public DesQuestionAction() {
        resultService = new ResultDaoService();
        questionService = new QuestionDaoService();
        questions = new ArrayList<QuestionVo>();
        questionsDisplay = new ArrayList<QuestionVo>();
        resultDaoService = new ResultDaoService();
    }

    public String getDiscriptiveQuestions() {
        System.out.println("getDiscriptiveQuestions started....");
        String returnString = "input";
        int examId = 0;
        int resultId = (Integer) session.get("RESULT_ID");
        if (session.get("EXAM_ID") != null) {
            examId = (Integer) session.get("EXAM_ID");
        }

        System.out.println("session.get('EXAM_ID')" + examId);
        
        /*
         * if (!validateSuccessful()) { return returnString; } else {
         */
        if (null == session.get("SelectedTech") && null == session.get("selectedlevel")) {
            questions = questionService.getRemainingDesQuestionList(resultId, examId);
        } else {
            String selectedTech = (String) session.get("SelectedTech");
            int selectedlevel = (Integer) session.get("selectedlevel");
            System.out.println("selectedTech is " + selectedTech + " and selectedlevel is "
                    + selectedlevel);
            questions = questionService.getQuestionList(selectedTech, selectedlevel, 1);
        }

        session.put("DESQUESTIONS", questions);
    	System.out.println("questions in session " + questions);
    	
		if (questions.size() > 0) {					
			totalQuestions = questions.size();
			questionsDisplay.clear();
			questionsDisplay.add(questions.get(0));
			System.out.println("questions got of id "
					+ questions.get(counter - 1).getQuestionId());
			List<ResultDetails> rsultDetailsList = new ArrayList<ResultDetails>();
			ResultDetails rDetails = new ResultDetails();
			rDetails.setQuestionId(questions.get(counter - 1).getQuestionId());
			rDetails.setAnswerOption(questions.get(counter - 1)
					.getSelectedAnswer());
			rDetails.setResultId(resultId);
			rDetails.setCorrectOption(null);
			rsultDetailsList.add(rDetails);

			resultService.createResultDetails(rsultDetailsList);
			System.out.println("result created..on resultDetails ");
		}
		else
		{
			counter=0;
			attempted=questionService.getUserAttemptedDescQuestionCount(resultId);;
			
		}
        setQuestions(questions);
        returnString = "success";
        session.put("counter",counter);
        return returnString;
    }

    // Next button action
    @SuppressWarnings("unchecked")
    public String nextQuestions() {
        System.out.println("nextQuestions started... ");
        List<Integer> inQstions = new ArrayList<Integer>();
        String returnpage = "success";
        int resultId = (Integer) session.get("RESULT_ID");
        List<ResultDetails> rsultDetailsList = new ArrayList<ResultDetails>();
        List<QuestionVo> lQuestions = (List<QuestionVo>) session.get("DESQUESTIONS");
        
        System.out.println("questions in session "+lQuestions);
        
        int examId = 0;        
        if (session.get("EXAM_ID") != null) {
            examId = (Integer) session.get("EXAM_ID");
        }
        
        System.out.println("session.get('EXAM_ID')" + examId);
        System.out.println("counter is "+counter);
        counter = (Integer) session.get("counter");
        System.out.println("counter got from session "+counter);
        
        LevelExam levelExam = questionService.getQuestionCountByExamId(examId);
        if (null != levelExam && levelExam.getDescriptiveQuestionCount() > lQuestions.size()) {
            if (null != session.get("counter")) {
                counter = (Integer) session.get("counter");
                System.out.println("counter got from session "+counter);
                session.remove("counter");
            }
        }
        System.out.println("nextQuestions counter ... " + counter + " resultId " + resultId);
        totalQuestions = lQuestions.size();
        // updating current question with answer
        ResultDetails rDetails = new ResultDetails();
        List<QuestionVo> questions = getQuestions();
        System.out.println("questions "+questions + " size "+questions.size() );
        String selAns = questions.get(counter - 1).getSelectedAnswer();
        QuestionVo qvo = lQuestions.get(counter - 1);
        System.out.println("Questions size.. " + lQuestions.size() + " selectval " + selAns);
        System.out.println("answer "+qvo.getSelectedAnswer());
        rDetails.setQuestionId(qvo.getQuestionId());
        qvo.setSelectedAnswer(selAns);
        lQuestions.remove(counter - 1);
        lQuestions.add(counter - 1, qvo);
        if (selAns.trim().isEmpty()) {
            rDetails.setAnswerOption(null);
        } else {
            rDetails.setAnswerOption(selAns);
        }
        rDetails.setResultId(resultId);
        rDetails.setCorrectOption(null);
        resultService.updateResultDetails(rDetails);

        System.out.println("Original counr "+counter);
        counter++;
        questionsDisplay.clear();
        System.out.println("displaying for counter-1 "+(counter-1));
        questionsDisplay.add(lQuestions.get(counter - 1));

        // creating next question
        QuestionVo nextQvo = lQuestions.get(counter - 1);
        rDetails.setQuestionId(nextQvo.getQuestionId());
        inQstions.add(nextQvo.getQuestionId());
        rDetails.setAnswerOption(nextQvo.getSelectedAnswer());
        System.out.println("nextQuestions last question id ... " + nextQvo.getQuestionId());
        rDetails.setResultId(resultId);
        rDetails.setCorrectOption(null);
        rsultDetailsList.clear();
        rsultDetailsList.add(rDetails);
        boolean found = resultDaoService.searchQuestions(resultId, inQstions);
        System.out.println("found on next " + found);
        if (!found) {
            resultService.createResultDetails(rsultDetailsList);
        } else {
            questions.add(counter - 1, nextQvo);
        }
        setQuestions(questions);
        session.put("DESQUESTIONS", lQuestions);
        session.put("counter",counter);
        inQstions.clear();
        return returnpage;
    }

    @SuppressWarnings("unchecked")
    public String previousQuestion() {
        System.out.println("previousQuestion started... ");
        String returnpage = "success";
        // int resultId = (Integer)session.get("RESULT_ID");
        questions = (List<QuestionVo>) session.get("DESQUESTIONS");
        totalQuestions = questions.size();
        questionsDisplay.clear();        
        System.out.println("total questions "+totalQuestions);
        System.out.println("questions "+questions);
        counter = (Integer)session.get("counter");
        counter--;
        System.out.println("pre question id "+questions.get(counter-1).getQuestionId() + " for counter "+counter);
        System.out.println("previousQuestion answer is... "
                + questions.get(counter - 1).getSelectedAnswer());
        questionsDisplay.add(questions.get(counter - 1));        
        System.out.println("counter on prev " + counter);
        session.put("counter",counter);
        return returnpage;
    }

    @SuppressWarnings("unchecked")
    public String resultCalculation() {
        System.out.println("final submittion started...");
        counter = (Integer)session.get("counter");
        String returnString = "success";
        int resultId = (Integer) session.get("RESULT_ID");
        // List<ResultDetails> rsultDetailsList = new
        // ArrayList<ResultDetails>();
        List<QuestionVo> lQuestions = (List<QuestionVo>) session.get("DESQUESTIONS");
        if (lQuestions.size() > 0) {
            // updating current question with answer
            ResultDetails rDetails = new ResultDetails();
            List<QuestionVo> questions = getQuestions();
            String selAns = questions.get(counter - 1).getSelectedAnswer();
            QuestionVo qvo = lQuestions.get(counter - 1);
            System.out.println("Questions size.. " + lQuestions.size() + " selectval " + selAns);
            lQuestions.remove(counter - 1);
            lQuestions.add(counter - 1, qvo);

            rDetails.setQuestionId(qvo.getQuestionId());
            if (selAns.trim().isEmpty()) {
                rDetails.setAnswerOption(null);
            } else {
                rDetails.setAnswerOption(selAns);
            }
            rDetails.setResultId(resultId);
            rDetails.setCorrectOption(null);
            resultService.updateResultDetails(rDetails);
        }
        attemptDescQuestions = questionService.getUserAttemptedDescQuestionList(resultId);
        System.out.println("un attemptDescQuestions is " + attemptDescQuestions);

        System.out.println("final submittion for result Id " + resultId);
        // submitting all descriptive questions..
        Timestamp time = getCurrentSystemTimeStamp();

        pendingForreview = resultDaoService.getUnReviewedDescQuestionCount(String.valueOf(resultId));
                        
        int finalRes = 2; // result will be calculate after review.
        percentile = 0.0;
        status = "Pending";
        
        LevelExam levelExam = null;
        String techName = null;
        int levelNum = -1;

        //Added by Husain
        Integer EXAM_ID = 0;
        if (session.get("EXAM_ID") != null) {
            EXAM_ID = (Integer) session.get("EXAM_ID");
        }
        
        System.out.println("Exam id "+EXAM_ID);
        levelExam = questionService.getQuestionCountByExamId(EXAM_ID);
        
        System.out.println("level exam "+levelExam);
        int passingCount = levelExam.getPassingCount();
        totalObjQuestions = levelExam.getQuestionCount();
        totalQuestions = levelExam.getDescriptiveQuestionCount();

        int totalQuestCount = totalObjQuestions + totalQuestions;
        
        attemptDescQuestions = totalQuestions - attemptDescQuestions;

        System.out.println("Total "+totalQuestCount + " objective "+totalObjQuestions + " subjective "+totalQuestions);
        System.out.println("Attempted obj "+attemptObjquestions + " sub "+attemptDescQuestions + " pendingForreview "+pendingForreview);

        int resultObjective = 0;
        int resultTotal = 0;
        int notAttempt = 0;

		// Neeraj code
        List<ResultDetails> finalResultList = resultDaoService.getResultDetails(String
                .valueOf(resultId));
           
        System.out.println("Final result is of size "+finalResultList.size());
        
        for (ResultDetails resultVo : finalResultList) {
            int qNo = resultVo.getQuestionId();
            String correctAns = resultVo.getCorrectOption().toString();
            String submitedAns = resultVo.getAnswerOption();
            
            System.out.println("Final result correctAns " + correctAns + " submitedAns "
                    + submitedAns + " question num " + qNo);
            	            	
            if ("0".equals(resultVo.getAnswerOption()) || null == resultVo.getAnswerOption()) {
                notAttempt++;
            } else if (correctAns.equals(submitedAns)) {
            	resultObjective++; 
            	resultTotal++;
            }
        }
        // End

        // If all descriptive questions are unattempted or no pending review, we can show result now.
		if (pendingForreview==0) //totalQuestions == attemptDescQuestions)
		{

	        List<ResultDetails> descResultList = resultDaoService.getResultDetailsByType(String
	                .valueOf(resultId), 1);        
	        for (ResultDetails resultVo : descResultList) {
	            if (null != resultVo.getCorrectOption()) {
	                if (resultVo.getCorrectOption() == 1) {
	                	resultTotal++;                    
	                }
	            }
	            
	        }

			if (resultTotal >= passingCount)
			{
				System.out.println("Test PASS " + resultTotal);
				finalRes = 1;
			} else
			{
				System.out.println("Test FAIL " + resultTotal);
				finalRes = 0;
			}
			System.out.println("result value:::::" + resultTotal + "passing count" + passingCount + "questionCount::::" + totalQuestCount);

			percentile = (double) Math.round(((double) resultTotal / totalQuestCount) * 100);
			System.out.println("percentage value::::" + percentile);
			
			// can't we do in above if ?
	        if (finalRes == 1) {
	            status = "Pass";
	        } else if(finalRes == 0) {
	            status= "Fail";
	        } 
		}
               
        resultDaoService.updateResult(resultId, finalRes, percentile, time);
        
   
        if (null != session.get("EXAM_ID")) {           
            System.out.println("attempted descriptive section..." + attemptDescQuestions);
            int notAnswered = questionService.getUserAnswerQuestionCount(resultId);
            attemptObjquestions = totalObjQuestions - notAnswered;
            correctAnswers = resultObjective;
            wrongAnswers = attemptObjquestions - resultObjective;
            session.remove("EXAM_ID");
        } else {
            attemptObjquestions = (Integer) session.get("OBJECT_ATTEMPT");
            correctAnswers = resultObjective;
            wrongAnswers = attemptObjquestions - resultObjective;
            totalQuestions = lQuestions.size();           
            totalObjQuestions = (Integer) session.get("TOTAL_OBJECTIVE");
            attemptObjquestions = (Integer) session.get("OBJECT_ATTEMPT");
        }
        //session.clear();
        counter = 1;
        session.put("counter",counter);
        return returnString;
    }

    // to get Current time stamp
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

    /**
     * @return the userName
     */
    public String getUserName() {
    	if(userName == null || userName.length()==0)
    	{
    		userName = (String) session.get("DASID");
    		System.out.println("username:"+userName+":");
    	}
    
	return userName.toLowerCase();
	
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public List<Technology> getTechnologyList() {
        return technologyList;
    }

    public void setTechnologyList(List<Technology> technologyList) {
        this.technologyList = technologyList;
    }

    public List<Integer> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<Integer> levelList) {
        this.levelList = levelList;
    }

    
    public int getPendingForreview() {
		return pendingForreview;
	}

	public void setPendingForreview(int pendingForreview) {
		this.pendingForreview = pendingForreview;
	}

	/**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}