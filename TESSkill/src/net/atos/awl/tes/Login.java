/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.atos.awl.tes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.service.QuestionDaoService;
import net.atos.awl.tes.cert.service.QuestionService;
import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.service.TechnologyDaoService;
import net.atos.awl.tes.cert.service.TechnologyService;
import net.atos.awl.tes.cert.service.UserDaoService;
import net.atos.awl.tes.cert.service.UserService;
import net.atos.awl.tes.cert.vo.FinalResultVo;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;
import net.atos.awl.tes.cert.vo.ShowResultVo;
import net.atos.awl.tes.cert.vo.StatusVo;
import net.atos.awl.tes.cert.vo.Technology;
import net.atos.awl.tes.cert.vo.UserVo;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Gauri Chiplunkar
 */
public class Login extends ActionSupport implements SessionAware {
    /**
	 * 
	 */
    Map session;

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private String accessLevel;

    private List<Technology> technologyList;

    private List<Integer> levelList;

    private List<StatusVo> compLevelList;

    private Map<String, String> compLeveReturnlList;

    private ResultService resultDaoService;

    private String technologies;

    private String levels;

    private TechnologyService technologyService;

    private ResultService resultService;

    private UserService userService;

    private QuestionService questionService;

    private boolean returnHome;

    private int attempted = 0;

    private List<QuestionVo> questions;

    private Map<Integer, QuestionVo> answerMap;

    private List<QuestionVo> wrongAnswersList;
    
    private int counter = 0;

    private int totalQuestions;

    private String techName;

    private Integer levelId;

    private String reviewStatus;

    private FinalResultVo finalResultVo;

    private ShowResultVo showResultVo;

    
    public List<QuestionVo> getWrongAnswersList() {
		return wrongAnswersList;
	}

	public ShowResultVo getShowResultVo() {
        return showResultVo;
    }

    public void setShowResultVo(ShowResultVo showResultVo) {
        this.showResultVo = showResultVo;
    }

    public FinalResultVo getFinalResultVo() {
        return finalResultVo;
    }

    public void setFinalResultVo(FinalResultVo finalResultVo) {
        this.finalResultVo = finalResultVo;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
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

    public UserService getUserDaoService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<QuestionVo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionVo> questions) {
        this.questions = questions;
    }

    public Map<Integer, QuestionVo> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Integer, QuestionVo> answerMap) {
        this.answerMap = answerMap;
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

    public boolean isReturnHome() {
        return returnHome;
    }

    public void setReturnHome(boolean returnHome) {
        this.returnHome = returnHome;
    }

    public List<StatusVo> getCompLevelList() {
        return compLevelList;
    }

    public void setCompLevelList(List<StatusVo> compLevelList) {
        this.compLevelList = compLevelList;
    }

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

    public Login() {
        technologyService = new TechnologyDaoService();
        compLevelList = new ArrayList<StatusVo>();
        resultService = new ResultDaoService();
        questionService = new QuestionDaoService();
        userService = new UserDaoService();
        questions = new ArrayList<QuestionVo>();        
        answerMap = new HashMap<Integer, QuestionVo>();
        resultDaoService = new ResultDaoService();
        questionsDisplay = new ArrayList<QuestionVo>();
    }

    public String executeLogin() {
        Result resultVo = null;
        UserVo userVo = null;
        String returnString = "input";
        Map<String, String> techLevel = new HashMap<String, String>();
        if (!validateSuccessful()) {
            return returnString;
        } else {
           
            System.out.println("**************executing**************");
          //  UserProfile userProfile = LdapTest.dasAuthenticate(getUserName(), getPassword());
            UserProfile userProfile = LDAPAuthenticator.validateLogin(getUserName(), getPassword());
            if (null != userProfile) {
            	session.put("DASID", getUserName());
                System.out.println(" Login successful.."
                        + userProfile.getDasId() + " user " + userProfile.getName());
                resultVo = resultService.getUserResultHistory(getUserName());
                if (resultVo == null) {
                    userVo = new UserVo();
                    userVo.setDasId(userProfile.getDasId());
                    userVo.setName(userProfile.getName());
                    userVo.setDasIdMgr("IN03390");
                    UserVo user = userService.findUser(userVo);
                    if (null == user) {
                        Integer create = userService.createUser(userVo);
                        System.out.println("create user value::::" + create);
                    }
                    else
                    {
	                    //update name if not the same
	                    //patch as older user doesn't have full name
	                    if(!user.getName().equalsIgnoreCase(userProfile.getName())){
	                    	Integer updateCount = userService.updateUserData(userVo);
	                    	System.out.println("update user count::::" + updateCount);
	                    	
	                    }
                    }
                    
                    System.out.println("User has not incomplete certification.." + accessLevel);
                    technologyList = technologyService.getAllTechnologies();
                    levelList = new ArrayList<Integer>();
                    techLevel = technologyService.getTechComplevels(getUserName()); //Not used ???
                    compLevelList = getLevelCount(getUserName());
                    System.out.println("compLevelList size " + compLevelList.size());
                    if (accessLevel.equalsIgnoreCase("User")) {
                        returnString = "success";
                    } else if (accessLevel.equalsIgnoreCase("Reviewer")) {
                        boolean accessLevel = userService.getReviewRole(getUserName());
                        System.out.println("reviewer return value.........." + accessLevel);
                        if (accessLevel) {
                            returnString = "reviewer";
                        } else {
                            addActionMessage("Not having Reviewer access level");
                            returnString = "input";
                        }
                    }else if(accessLevel.equalsIgnoreCase("Manager")){
                    	boolean accessLevel = userService.getManagerRole(getUserName());
                        System.out.println("manager return value.........." + accessLevel);
                        if (accessLevel) {
                            returnString = "manager";
                        } else {
                            addActionMessage("Not having Manager access level");
                            returnString = "input";
                        }
                    }
                    return returnString;
                } else {
                    List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
                    System.out.println("User has incomplete certification.." + resultVo.getExamId()
                            + " result Id " + resultVo.getResultId());

                    session.put("RESULT_ID", resultVo.getResultId());
                    session.put("EXAM_ID", resultVo.getExamId());
                    
                    List<QuestionVo> tempQuestionList = questionService.getRemainingQuestionList(
                            resultVo.getResultId(), resultVo.getExamId());
                    if (tempQuestionList.size() == 0) {
                        String nextPage = displayDesQuestions(resultVo);
                        return nextPage;
                    }
                    questionsDisplay = returnDisplayList(tempQuestionList);
                    totalQuestions = tempQuestionList.size();
                    int tempListSize = tempQuestionList.size();
                    int remainCount = 0;
                    if (tempListSize % 3 > 0) {
                        remainCount = tempListSize % 3;
                    } else {
                        remainCount = 3;
                    }
                    
                    System.out.println("remaining count" + remainCount);
                    
                    session.put("remainCount", remainCount);
                    /*
                     * questionsDisplay =
                     * questionService.getRemainingQuestionList(resultVo
                     * .getResultId(), resultVo.getExamId());
                     */

                    for (int i = 0; i < questionsDisplay.size(); i++) {
                        List<String> op = new ArrayList<String>();
                        QuestionVo q = questionsDisplay.get(i);
                        op.add(q.getOption1());
                        op.add(q.getOption2());
                        op.add(q.getOption3());
                        op.add(q.getOption4());
                        q.setOptions(op);
                        questions.add(q);
                        answerMap.put(i, q);
                    }
                    setAnswerMap(answerMap);
                    setQuestions(questions);
                    attempted = questionService.getUserAttemptedQuestionCount(resultVo
                            .getResultId());
                    session.put("QuesionList", questionsDisplay);
                    session.put("ATTEMPTED", attempted);
                    System.out.println("User has incomplet question size.."
                            + questionsDisplay.size() + " attempted " + attempted);
                    if (questionsDisplay.size() > 3) {
                        questionsDisplay = questionsDisplay.subList(0, 3);
                        returnString = "questionStart";
                    } else {

                        returnString = "questionLast";
                    }
                    for (QuestionVo q : questionsDisplay) {
                        ResultDetails rDeatails = new ResultDetails();
                        int selAns = 0;
                        rDeatails.setAnswerOption(new Integer(selAns).toString());
                        rDeatails.setQuestionId(q.getQuestionId());
                        rDeatails.setResultId(resultVo.getResultId());
                        rDeatails.setCorrectOption(q.getCorrectAnswer());
                        resultDeatilList.add(rDeatails);
                    }
                    if (questionsDisplay.size() > 0) {
                        resultDaoService.createResultDetails(resultDeatilList);
                    }
                    counter = remainCount;
                    session.put("counter",counter);
                    // resultDeatilList.clear();
                    return returnString;
                }
            } else {
                addActionMessage("User Name/Password invalid");
                returnString = "input";
                userName= null;
                return returnString;
                // return "input";
            }
        }
    }

    // getting remaining descriptive questions
    private String displayDesQuestions(Result resultVo) {
        System.out.println("Getting descriptive questions ");
        session.put("RESULT_ID", resultVo.getResultId());
        session.put("EXAM_ID", resultVo.getExamId());
        List<QuestionVo> tempQuestionList = questionService.getRemainingDesQuestionList(resultVo.getResultId(),
                resultVo.getExamId());
        System.out.println("Descriptive questions size " + tempQuestionList.size());
        session.put("DESQUESTIONS", tempQuestionList);
        totalQuestions = tempQuestionList.size();
        // questionsDisplay.clear();
        LevelExam levelExam = questionService.getQuestionCountByExamId(resultVo.getExamId());
        attempted = levelExam.getDescriptiveQuestionCount() - tempQuestionList.size();
        if (tempQuestionList.size() > 0) {
            questionsDisplay.add(tempQuestionList.get(0));
            System.out.println("desquestions got of id " + tempQuestionList.get(0).getQuestionId());
            List<ResultDetails> rsultDetailsList = new ArrayList<ResultDetails>();
            ResultDetails rDetails = new ResultDetails();
            rDetails.setQuestionId(tempQuestionList.get(0).getQuestionId());
            rDetails.setAnswerOption(tempQuestionList.get(0).getSelectedAnswer());
            rDetails.setResultId(resultVo.getResultId());
            rDetails.setCorrectOption(null);
            rsultDetailsList.add(rDetails);
            setQuestions(tempQuestionList);
            resultService.createResultDetails(rsultDetailsList);
            counter = 1;
        } else {
            counter = 0;
        }
        session.put("counter", counter);

        System.out.println("questions in during first display "+questions + " size "+questions.size());
        return "desquestions";
    }

    public String showResult() {
        System.out.println("showing Result....");

        showResultVo = new ShowResultVo();

        UserVo userVo = new UserVo();
        userVo.setDasId((String) session.get("DASID"));

        if (null != getTechName() && null != getLevelId()) {
        	
            userVo.setTechName(getTechName());
            userVo.setTechLevel(getLevelId());
            if (getReviewStatus().equals("Completed")) {
                userVo.setResult(1);
            } else if (getReviewStatus().equals("Pending")){
                userVo.setResult(2);
            }
            else if(getReviewStatus().equals("Failed")){
            	userVo.setResult(0);
            }
            	            	
            finalResultVo = userService.findUserResult(userVo);
            int attemptDescQuestions = questionService
                    .getUserAttemptedDescQuestionList(finalResultVo.getResultId());
            System.out.println("attemptDescQuestions is " + attemptDescQuestions);

            int result = 0;
            int notAttempt = 0;
                      
            List<ResultDetails> finalResultList = resultDaoService.getResultDetailsByType(String
                    .valueOf(finalResultVo.getResultId()), 0);
            for (ResultDetails resultVo : finalResultList) {
                int qNo = resultVo.getQuestionId();               

                if (resultVo.getAnswerOption().equals("0")) {
                    notAttempt++;                    
                } else {
                    String submitedAns = resultVo.getAnswerOption();
                    String correctAns = resultVo.getCorrectOption().toString();
                    System.out.println("Final result correctAns " + correctAns + " submitedAns "
                            + submitedAns + "question num " + qNo);
                    if (submitedAns.equals(correctAns)) {
                        result++;
                        System.out.println("resut="+result+ " for "+qNo);
                    }
                    else
                    	System.out.println("wrong for "+qNo);
                }
            }

            int wrongDescAns = 0;
            int correctDescAns = 0;
            List<ResultDetails> descResultList = resultDaoService.getResultDetailsByType(String
                    .valueOf(finalResultVo.getResultId()), 1);
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

            LevelExam levelExam = questionService.getQuestionCountByExamId(finalResultVo
                    .getExamId());
            int totalObjQuestions = levelExam.getQuestionCount();
            int totalDescQuestions = levelExam.getDescriptiveQuestionCount();
            attemptDescQuestions = totalDescQuestions - attemptDescQuestions;
            System.out.println("incomplete descriptive section..." + totalQuestions);
            int notAnswered = questionService.getUserAnswerQuestionCount(finalResultVo
                    .getResultId());
            int attemptObjquestions = totalObjQuestions - notAnswered;
            int correctAnswers = result;
            int wrongAnswers = attemptObjquestions - result;
            
            System.out.println("attemptObjquestions="+attemptObjquestions+" correctAnswers="+correctAnswers+" wrongAnswers="+wrongAnswers+" result="+result);

            showResultVo.setIsPass(finalResultVo.getResult());
            showResultVo.setPercentile(finalResultVo.getPercentile());
            showResultVo.setTotalObjQuestions(totalObjQuestions);
            showResultVo.setAttemptObjquestions(attemptObjquestions);
            showResultVo.setCorrectAnswers(correctAnswers);
            showResultVo.setWrongAnswers(wrongAnswers);
            showResultVo.setTotalDescQuestions(totalDescQuestions);
            showResultVo.setAttemptDescQuestions(attemptDescQuestions);
            int pendingforReview = attemptDescQuestions - descResultList.size();
            showResultVo.setReviewForPending(pendingforReview);
            showResultVo.setCorrectDescAnswers(descCorrectAns);
            showResultVo.setWrongDescAnswers(descWrongAns);
            
            this.wrongAnswersList = questionService.getQuestions(finalResultVo.getResultId());
            
            System.out.println("Wrong answer list "+wrongAnswersList);

        }

        return "success";
    }

    public String resultBack() {
        technologyList = technologyService.getAllTechnologies();
        levelList = new ArrayList<Integer>();
        Map<String, String> techLevel = technologyService.getTechComplevels(getUserName());
        String dasid = (String) session.get("DASID");
        compLevelList = getLevelCount(dasid);
        System.out.println("compLevelList size " + compLevelList.size());
        System.out.println("Going Result back....");
        return "success";
    }

    public String home(){
    	
    	String userName = getUserName();
    	session.clear();
        session.put("DASID", userName);
        
        technologyList = technologyService.getAllTechnologies();
        levelList = new ArrayList<Integer>();
        Map<String, String> techLevel = technologyService.getTechComplevels(getUserName());
        compLevelList = getLevelCount(getUserName());
        System.out.println("compLevelList size " + compLevelList.size());
        
    	return "success";
    }
    private List<StatusVo> getLevelCount(String dasId) {
        List<StatusVo> statusList = new ArrayList<StatusVo>();
        List<StatusVo> statusList1 = new ArrayList<StatusVo>();
        statusList = userService.findUserStatus(dasId);
        System.out.println("statusList size " + statusList.size());
        List<Technology> techLevelList = technologyService.getAllTechnologies();
        for (int i = 0; i < statusList.size(); i++) {

            StatusVo vo = statusList.get(i);
            System.out.println("result is " + vo.getResult() + " i " + i);
            if (null != vo.getResult() && vo.getResult() == 1) {
                vo.setReviewStatus("Completed");
            } else if (null != vo.getResult() && vo.getResult() == 2) {
                vo.setReviewStatus("Pending");
            } 
            else if (null != vo.getResult() && vo.getResult() == 0) {
                vo.setReviewStatus("Failed");
            } 
            statusList.set(i, vo);
        }
        statusList1.addAll(statusList);
        for (Technology t : techLevelList) {
            boolean checkFlag = false;
            for (int i = 0; i < statusList.size(); i++) {
                if (t.getName().equalsIgnoreCase(statusList.get(i).getTechName())) {
                    checkFlag = true;
                    break;
                }
            }
            if (!checkFlag) {
                StatusVo voNew = new StatusVo();
                voNew.setTechName(t.getName());
                voNew.setReviewStatus("No level cleared");
                statusList1.add(voNew);
            }
        }
        statusList.clear();
        statusList.addAll(statusList1);
        Collections.sort(statusList);      
        return statusList;
    }

    /*
     * public String loginPage() throws Exception { String das = (String)
     * session.get("DASID"); System.out.println("Enrol start fundtion DAS id " +
     * das); technologyList = technologyService.getAllTechnologies(); levelList
     * = new ArrayList<Integer>(); Map<String, String> techLevel =
     * technologyService .getTechComplevels(das); compLevelList =
     * getLevelCount(techLevel, technologyList); return "success"; }
     */

    public String logout() throws Exception {

        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
        	System.out.println("Loging out");
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
                
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        userName = null;
        return "success";

    }

    public String sessionClose() throws Exception {
        System.out.println("Strated the session close method");
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        System.out.println("End the session close method");
        return "success";
    }

    private List<QuestionVo> returnDisplayList(List<QuestionVo> voList) {

        List<QuestionVo> questionList = new ArrayList<QuestionVo>();

        for (int i = 0; i < voList.size(); i++) {
            QuestionVo questVo = new QuestionVo();
            QuestionVo tempVo = voList.get(i);
            if ((tempVo.getQuestionText()).contains(")")) {
                questVo.setQuestionText(tempVo.getQuestionText());
            } else {
                questVo.setQuestionText((i + 1) + ".)" + " " + tempVo.getQuestionText());
            }
            questVo.setCorrectAnswer(tempVo.getCorrectAnswer());
            questVo.setExamId(tempVo.getExamId());
            questVo.setOption1(tempVo.getOption1());
            questVo.setOption2(tempVo.getOption2());
            questVo.setOption3(tempVo.getOption3());
            questVo.setOption4(tempVo.getOption4());
            questVo.setOptions(tempVo.getOptions());
            questVo.setQuestionId(tempVo.getQuestionId());
            questVo.setSelectedAnswer(tempVo.getSelectedAnswer());
            questionList.add(questVo);
        }

        return questionList;
    }

    private boolean validateSuccessful() {
        if (getUserName() == null || getUserName().trim().length() < 1) {
            addActionMessage("user.required");
        }
        if (getPassword() == null || getPassword().trim().length() < 1) {
            addActionMessage("password.required");
        }
        if (this.hasActionMessages()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return the userName
     */
    public String getUserName() {
    	
    	if(userName == null || userName.length()==0)
        	{
        		userName = (String) session.get("DASID");        		
        	}
    	
        /*java.util.Iterator sessions = session.keySet().iterator();
        
        System.out.println("printing session");
        while(sessions.hasNext())
        {
        	String key = (String)sessions.next();
        	System.out.println(key+"="+session.get(key));
        }
        */
        
    	if(userName==null)
    		userName="";
    	
    	System.out.println("returning username:"+userName+":");
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

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}