/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.atos.awl.tes;

import java.util.ArrayList;
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
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;
import net.atos.awl.tes.cert.vo.Technology;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Prashant Mahajan
 */
public class ReviewAction extends ActionSupport implements SessionAware {
    /**
	 * 
	 */
    Map session;

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private List<Technology> technologyList;

    private List<Integer> levelList;

    private Map<String, String> compLevelList;

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

    private int counter = 0;

    private int totalQuestions;

    private List<Result> testList;

    private List<String> review;

    private String accessLevel;

    private Result result;

    private Integer resultId;

    public List<String> getReview() {
        return review;
    }

    public void setReview(List<String> review) {
        this.review = review;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Result> getTestList() {
        return testList;
    }

    public void setTestList(List<Result> testList) {
        this.testList = testList;
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

    private List<String> disQuestionId;

    public List<String> getDisQuestionId() {
        return disQuestionId;
    }

    public void setDisQuestionId(List<String> disQuestionId) {
        this.disQuestionId = disQuestionId;
    }

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

    public Map<String, String> getCompLevelList() {
        return compLevelList;
    }

    public void setCompLevelList(Map<String, String> compLevelList) {
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

    public ReviewAction() {
        technologyService = new TechnologyDaoService();
        compLevelList = new HashMap<String, String>();
        resultService = new ResultDaoService();
        questionService = new QuestionDaoService();
        userService = new UserDaoService();
        questions = new ArrayList<QuestionVo>();
        answerMap = new HashMap<Integer, QuestionVo>();
        resultDaoService = new ResultDaoService();
        questionsDisplay = new ArrayList<QuestionVo>();
        levelList = new ArrayList<Integer>();
        // result = new Result();
    }

    public String resetReview() {
        super.clearErrorsAndMessages();
        technologyList = technologyService.getAllTechnologies(); // setMessage(getText(MESSAGE));
        setTechnologies("-1");
        System.out.println("inside select technology::::::: ");
        return "success";
    }

    // getting remaining descriptive questions
    public String reviewStart() {
        String returnString = "input";
        counter = 0;
        List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
        System.out.println("startCertification started...." + getTechnologies() + " and "
                + getLevels());
        List<QuestionVo> questionList;
        String selectedTech = getTechnologies();
        String das = (String) session.get("DASID");
        if (selectedTech.equalsIgnoreCase("-1") || getLevels().equalsIgnoreCase("-1")) {
            if (selectedTech.equalsIgnoreCase("-1")) {
                addActionMessage("Please select Technology ");
            } else if (getLevels().equalsIgnoreCase("-1")) {
                addActionMessage("Please select Levels ");
                levelList = technologyService.getAvailableLevel(selectedTech, das);
                System.out.println("Levelist "+ levelList);
                setLevelList(levelList);
            }
            technologyList = technologyService.getAllTechnologies();
            Map<String, String> techLevel = technologyService.getTechComplevels(das);
            // compLevelList = getLevelCount(techLevel, technologyList);
            return "input";
        }
        Integer selectedlevel = Integer.parseInt(getLevels());
        session.put("SelectedTech", selectedTech);
        session.put("selectedlevel", selectedlevel);
        testList = new ArrayList<Result>();
        testList = resultDaoService.getReviewResultList(selectedTech, selectedlevel);
        System.out.println("testList size " + testList.size());
        if (!testList.isEmpty()) {
            session.put("TESTLIST", testList);
            returnString = "success";
        } else {
            technologyList = technologyService.getAllTechnologies();
            addActionMessage("No test has been submitted for selected level " + selectedlevel);
            returnString = "input";
        }
        return returnString;
    }

    public String reviewBack() {
        System.out.println("review back action..");
        technologyList = technologyService.getAllTechnologies();
        levelList = new ArrayList<Integer>();
        return "success";
    }

    public String selectReviewLevels() {
        // String tech = getTechnologies();
        //String das = (String) session.get("DASID");
        String techName = getTechnologies();
        List<Integer> level = technologyService.getAvailableLevels(techName);
        if (level.size() > 0) {
            levelList.addAll(level);
        }        
        System.out.println("Level list in selectReviewLevels "+levelList);
        technologyList = new ArrayList<Technology>();
        if (technologyList.size() <= 0) {
            technologyList = technologyService.getAllTechnologies();
        }
        //setting same list again with setter ?
        setLevelList(levelList);
        return "input";
    }

    public String logout() throws Exception {

        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }

    public String setUpForCheck() {
        System.out.println(" result id " + resultId);
        session.put("RESULTID", resultId);
        if (resultId != null) {
            System.out.println("result Id " + resultId);
            questions = questionService.getAttemptedDescQuestionList(resultId);
        }
        return "success";
    }

    public void sessionClose() throws Exception {
        System.out.println("Strated the session close method");
        if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
            try {
                ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        System.out.println("End the session close method");
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

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
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