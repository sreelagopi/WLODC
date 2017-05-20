package net.atos.awl.tes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.formula.functions.Cell;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.atos.awl.tes.cert.service.QuestionDaoService;
import net.atos.awl.tes.cert.service.QuestionService;
import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.service.TechnologyDaoService;
import net.atos.awl.tes.cert.service.TechnologyService;
import net.atos.awl.tes.cert.service.UserDaoService;
import net.atos.awl.tes.cert.service.UserService;
import net.atos.awl.tes.cert.vo.LevelExam;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.Result;
import net.atos.awl.tes.cert.vo.ResultDetails;
import net.atos.awl.tes.cert.vo.StatusVo;
import net.atos.awl.tes.cert.vo.Technology;

/**
 * @author Prashant Mahajan
 */
public class CertificationAction extends ActionSupport implements ModelDriven<QuestionVo>,
        SessionAware {
    private static final long serialVersionUID = 1L;

    Map session;
    
    private String userName;

    public static final String vOUTPUT_DATE_FORMAT = "yyyy-MM-dd HH:mms";

    private static final SimpleDateFormat vOutPutDate = new SimpleDateFormat(vOUTPUT_DATE_FORMAT);

    private List<QuestionVo> questions;

    private Map<Integer, QuestionVo> answerMap;

    private String technologies;

    private List<Technology> technologyList;

	private String levels;

    private List<Integer> levelList;

    private TechnologyService technologyService;

    private QuestionService questionService;

    private ResultService resultDaoService;

    private List<String> selAnswer;

    private List<String> options;

    private List<String> optionkeys;

    private Integer questionId;

    private List<String> disQuestionId;

    private List<QuestionVo> questionsDisplay;

    private Integer counter = 0;

    private static boolean resultFlag = false;

    private Result displayResult;

    private int totalQuestions;

    private int correctAnswers;

    private int totalAttempt;

    private int wrongAnswers;

    private int attempted = 0;
    
    private int passingCount=0;
    
    private int totalCount=0;

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    private List<StatusVo> compLevelList;

    public static boolean isResultFlag() {
        return resultFlag;
    }

    public static void setResultFlag(boolean resultFlag) {
        CertificationAction.resultFlag = resultFlag;
    }

    public List<StatusVo> getCompLevelList() {
        return compLevelList;
    }

    public void setCompLevelList(List<StatusVo> compLevelList) {
        this.compLevelList = compLevelList;
    }

    public int getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(int totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

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

    public Result getDisplayResult() {
        return displayResult;
    }

    public void setDisplayResult(Result displayResult) {
        this.displayResult = displayResult;
    }

    public List<String> getDisQuestionId() {
        return disQuestionId;
    }

    public void setDisQuestionId(List<String> disQuestionId) {
        this.disQuestionId = disQuestionId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getCounter() {
        return this.counter;
    }

    public void setCounter(Integer counter) {
    	System.out.println("called set counter with "+counter);
        this.counter = counter;
    }

    public List<QuestionVo> getQuestionsDisplay() {
        return questionsDisplay;
    }

    public void setQuestionsDisplay(List<QuestionVo> questionsDisplay) {
        this.questionsDisplay = questionsDisplay;
    }

    public List<String> getOptionkeys() {
        return optionkeys;
    }

    public void setOptionkeys(List<String> optionkeys) {
        this.optionkeys = optionkeys;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    ActionContext context = ActionContext.getContext();

    HttpServletRequest request = (HttpServletRequest) context
            .get(ServletActionContext.HTTP_REQUEST);

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

    public List<String> getSelAnswer() {
        return selAnswer;
    }

    public void setSelAnswer(List<String> selAnswer) {
        this.selAnswer = selAnswer;
    }

    public String selectLevels() {
        // String tech = getTechnologies();
        String das = getUserName();
        String techName = getTechnologies();
        List<Integer> level = technologyService.getAvailableLevel(techName, das);
        if (level.size() > 0) {
            levelList.add(level.get(0));
        }
        technologyList = new ArrayList<Technology>();
        if (technologyList.size() <= 0) {
            technologyList = technologyService.getAllTechnologies();
        }
        Map<String, String> techLevel = technologyService.getTechComplevels(das);
        compLevelList = getLevelCount(das);
        setLevelList(levelList);
        return "success";
    }

    private int getPrevIndex(int count) {
        float f = count % 3;
        System.out.println("float " + f);
        int end = count - (int) f;
        int start = end - 3;
        System.out.println("Starting index " + start + " end " + end);
        return end;
    }

    public String previousList() {
        // resultFlag = true;
        Integer listSize = ((List<QuestionVo>) session.get("QuesionList")).size();
        totalQuestions = listSize;
        counter = (Integer)session.get("counter");
        System.out.println("prev counter is " + counter + " total question size " + listSize);
        
        if (0 <= (counter - 3)) {
            float reminder = counter % 3;
            if (reminder == 0) {
                List<QuestionVo> tempQuestionList = ((List<QuestionVo>) session.get("QuesionList"))
                        .subList(counter - 6, counter - 3);
                questionsDisplay = returnDisplayList(tempQuestionList, counter - 6);
                // questionsDisplay = ((List<QuestionVo>)
                // session.get("QuesionList")).subList(
                // counter - 6, counter - 3);
                counter = counter - 3;
            } else {
                int end = getPrevIndex(counter);
                List<QuestionVo> tempQuestionList = ((List<QuestionVo>) session.get("QuesionList"))
                        .subList(end - 3, end);
                questionsDisplay = returnDisplayList(tempQuestionList, end - 3);
                counter = end;
            }
            System.out.println("counter value previous after decrease:::" + counter);
            Map prevAnswerMap = new HashMap();
            setQuestionsDisplay(questionsDisplay);
            // session.put("LastDispQs", questionsDisplay);
            selAnswer = new ArrayList<String>();
            for (int i = 0; i < questionsDisplay.size(); i++) {
                QuestionVo qs = (QuestionVo) questionsDisplay.get(i);
                prevAnswerMap.put(i, qs);
                selAnswer.add(i, qs.getSelectedAnswer());
                System.out.println("your anww " + qs.getSelectedAnswer() + " question num "
                        + qs.getQuestionId());
            }
            setAnswerMap(prevAnswerMap);
            if (counter == 0) {
                counter = 3;
            }
            session.put("counter",counter);
            
            return "success";
        } else {
            return "success";
        }
    }

    public CertificationAction() {
        technologyService = new TechnologyDaoService();
        resultDaoService = new ResultDaoService();
        userService = new UserDaoService();
        questions = new ArrayList<QuestionVo>();
        options = new ArrayList<String>();
        answerMap = new HashMap<Integer, QuestionVo>();
        questionService = new QuestionDaoService();
        levelList = new ArrayList<Integer>();
        optionkeys = new ArrayList<String>();
        optionkeys.add("A");
        optionkeys.add("B");
        optionkeys.add("C");
        optionkeys.add("D");
        // finalResult = new HashMap<Integer, String>();
    }

    public Map<Integer, QuestionVo> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Integer, QuestionVo> answerMap) {
        this.answerMap = answerMap;
    }

    public List<QuestionVo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionVo> questions) {
        this.questions = questions;
    }

    /*
     * private List<StatusVo> getLevelCount(String dasId) { List<StatusVo>
     * statusList = new ArrayList<StatusVo>(); statusList =
     * userService.findUserStatus(dasId); for (int i = 0; i < statusList.size();
     * i++) { StatusVo vo = statusList.get(i); System.out.println("result is " +
     * vo.getResult()); if (vo.getResult() == 1) {
     * vo.setReviewStatus("Completed"); } else { vo.setReviewStatus("Pending");
     * } statusList.remove(i); statusList.add(i, vo); }
     * 
     * return statusList; }
     */
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

    public String startCertification() {
        counter = 0;
        List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
        System.out.println("startCertification started...." + getTechnologies() + " and "
                + getLevels());
        List<QuestionVo> questionList;
        String selectedTech = getTechnologies();
        String das = getUserName();
        if (selectedTech.equalsIgnoreCase("-1") || getLevels().equalsIgnoreCase("-1")) {
            if (selectedTech.equalsIgnoreCase("-1")) {
                addActionMessage("Please select Technology ");
            } else if (getLevels().equalsIgnoreCase("-1")) {
                addActionMessage("Please select Levels ");
                List<Integer> level = technologyService.getAvailableLevel(selectedTech, das);
                if (level.size() > 0) {
                    levelList.add(level.get(0));
                }
                setLevelList(levelList);
            }
            technologyList = technologyService.getAllTechnologies();
            Map<String, String> techLevel = technologyService.getTechComplevels(das);
            compLevelList = getLevelCount(das);
            return "input";
        	}

        Integer selectedlevel = Integer.parseInt(getLevels());
        if (null != selectedlevel && null != selectedTech && null != das) {
            System.out.println("dasId::" + das + "level::" + selectedlevel + "technology name:::"
                    + selectedTech);
            int pending = userService.findUserforPending(das, selectedlevel, selectedTech);
            List<Integer> level = technologyService.getAvailableLevel(selectedTech, das);
            if (level.size() > 0) {
                levelList.add(level.get(0));
            }
            setLevelList(levelList);
            technologyList = technologyService.getAllTechnologies();
            Map<String, String> techLevel = technologyService.getTechComplevels(das);
            compLevelList = getLevelCount(das);
            if (pending != 0) {
                addActionMessage(" Your Result is pending for Review");
                return "input";
            }
        }

        // Integer selectedlevel = Integer.parseInt(getLevels());
        session.put("SelectedTech", selectedTech);
        session.put("selectedlevel", selectedlevel);
        Integer attamptCount = resultDaoService.getAttemptCount(das, selectedTech, selectedlevel);
        System.out.println("attempt count value in action" + attamptCount);
        if (attamptCount == 0) {
            questionList = questionService.getQuestionList(selectedTech, selectedlevel, 0);
            totalQuestions = questionList.size();
            System.out.println("question size " + questionList.size() + " with das id " + das);            
            if (questionList.size() > 0) {
                for (int i = 0; i < questionList.size(); i++) {
                    List<String> op = new ArrayList<String>();
                    QuestionVo q = questionList.get(i);
                    op.add(q.getOption1());
                    op.add(q.getOption2());
                    op.add(q.getOption3());
                    op.add(q.getOption4());
                    op.add(q.getHint());
                    q.setOptions(op);
                    questions.add(q);
                    answerMap.put(i, q);
                }
                setAnswerMap(answerMap);
                setQuestions(questions);
                session.put("QuesionList", questions);
                List<QuestionVo> tempQuestionList = questions.subList(counter, counter + 3);
                questionsDisplay = returnDisplayList(tempQuestionList, counter);

                // questionsDisplay = questions.subList(counter, counter + 3);
                counter = counter + 3;
                System.out.println("counter at startCertification " + counter);
                
                session.put("counter",counter);
                
                LevelExam levelExam = resultDaoService.getLevelExamResult(selectedTech,
                        selectedlevel);
                
                passingCount = levelExam.getPassingCount();
                totalCount = levelExam.getQuestionCount() + levelExam.getDescriptiveQuestionCount();
                
                int resultId = resultDaoService.createResult(das, "Core Java", levelExam.getExamId(), 0,
                        0);
                System.out.println("Result id " + resultId + " first 3 questions are being inserted...");
                // adding first 3 questions to result_details
                for (QuestionVo preQ : questionsDisplay) {
                    ResultDetails rDeatails = new ResultDetails();
                    int selAns = 0;
                    rDeatails.setAnswerOption(new Integer(selAns).toString());
                    rDeatails.setQuestionId(preQ.getQuestionId());
                    rDeatails.setResultId(resultId);
                    rDeatails.setCorrectOption(preQ.getCorrectAnswer());
                    resultDeatilList.add(rDeatails);
                }
                resultDaoService.createResultDetails(resultDeatilList);
                session.put("RESULT_ID", resultId);
                session.put("EXAM_ID", Integer.parseInt(levelExam.getExamId()));
                System.out.println("set resultid "+resultId + " exam id "+levelExam.getExamId());;
                return "success";
            } else {
                technologyList = technologyService.getAllTechnologies();
                Map<String, String> techLevel = technologyService.getTechComplevels(das);
                compLevelList = getLevelCount(das);
                addActionMessage("No Questions available ");
                return "input";
            }

        } else {
            technologyList = technologyService.getAllTechnologies();
            Map<String, String> techLevel = technologyService.getTechComplevels(das);
            compLevelList = getLevelCount(das);
            addActionMessage("You have already attempted level "+selectedlevel+" for "+selectedTech+". You can attempt only after 1 day of previous attempt.");
            return "input";
        }
    }

    public String execute() throws Exception {
        List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
        int result_id = (Integer) session.get("RESULT_ID");
        System.out.println("resultCertification started....");
        Map<Integer, QuestionVo> answerMap = getAnswerMap();
        System.out.println("number of answerMap submit  " + answerMap.size());
        List<QuestionVo> questionList = (List<QuestionVo>) session.get("QuesionList");
        // List<QuestionVo> dispQuestions = ((List<QuestionVo>)
        // session.get("LastDispQs"));
        int locInt = 0;
        if (session.get("ATTEMPTED") != null) {
            counter = (Integer) session.get("remainCount");
        }
        
        System.out.println("Counter is "+counter + " questionList "+questionList);
        
        counter = (Integer)session.get("counter");
        System.out.println("counter from session "+(Integer)session.get("counter"));
        
        List<Integer> inQstions = new ArrayList<Integer>();
        
        if (counter % 3 > 0 && questionList.size() > 0) {
            int last = getPrevIndex(counter);
            for (int cnt = last; cnt < counter; cnt++) {
                String seleAnswer = "null";
                QuestionVo preQ = questionList.get(cnt);
                if (null != (QuestionVo) answerMap.get(locInt)) {
                    seleAnswer = ((QuestionVo) answerMap.get(locInt)).getSelectedAnswer();
                }
                locInt++;
                ResultDetails rDeatails = new ResultDetails();
                int selAns = 0;
                if (seleAnswer != null) {
                    selAns = getOptionInt(seleAnswer);
                }
                rDeatails.setAnswerOption(new Integer(selAns).toString());
                inQstions.add(preQ.getQuestionId());
                rDeatails.setQuestionId(preQ.getQuestionId());
                rDeatails.setResultId(result_id);
                rDeatails.setCorrectOption(preQ.getCorrectAnswer());
                resultDeatilList.add(rDeatails);
                System.out.println("Current your anww " + seleAnswer + " fetched question id "
                        + preQ.getQuestionId());
                preQ.setSelectedAnswer(seleAnswer);
                questionList.remove(cnt);
                questionList.add(cnt, preQ);
                session.put("QuesionList", questionList);
            }
        } else {
            if (counter > 0 && questionList.size() > 0) {
                for (int prevInd = counter - 3; prevInd < counter; prevInd++) {
                    String seleAnswer = "null";
                    QuestionVo preQ = questionList.get(prevInd);
                    if (null != (QuestionVo) answerMap.get(locInt)) {
                        seleAnswer = ((QuestionVo) answerMap.get(locInt)).getSelectedAnswer();
                    }
                    locInt++;
                    ResultDetails rDeatails = new ResultDetails();
                    int selAns = 0;
                    if (seleAnswer != null) {
                        selAns = getOptionInt(seleAnswer);
                    }
                    rDeatails.setAnswerOption(new Integer(selAns).toString());
                    inQstions.add(preQ.getQuestionId());
                    rDeatails.setQuestionId(preQ.getQuestionId());
                    rDeatails.setResultId(result_id);

                    resultDeatilList.add(rDeatails);
                    System.out.println("Current your anww " + seleAnswer + "fected question id "
                            + preQ.getQuestionId());
                    preQ.setSelectedAnswer(seleAnswer);
                    questionList.remove(prevInd);
                    questionList.add(prevInd, preQ);
                    session.put("QuesionList", questionList);
                }
            }
        }
        
        System.out.println("resultDeatilList "+resultDeatilList);
        
        if (counter > 0) {
            resultDaoService.updateResultDetails(resultDeatilList);
        }
        
        
        Integer EXAM_ID=0;
        if (session.get("EXAM_ID") != null) {
            EXAM_ID = (Integer) session.get("EXAM_ID");
        }
        System.out
                .println("final QuesionList size " + questionList.size() + " examId::::" + EXAM_ID);
        int result = 0;
        int notAttempt = 0;
        List<ResultDetails> finalResultList = resultDaoService.getResultDetails(String
                .valueOf(result_id));
        for (ResultDetails resultVo : finalResultList) {
            int qNo = resultVo.getQuestionId();
            String correctAns = resultVo.getCorrectOption().toString();

            if (resultVo.getAnswerOption().equals("0")) {
                notAttempt++;
            } else {
                String submitedAns = resultVo.getAnswerOption();
                System.out.println("Final result correctAns " + correctAns + " submitedAns "
                        + submitedAns + " question num " + qNo);
                if (submitedAns.equals(correctAns)) {
                    result++;
                }
            }
        }
        LevelExam levelExam = null;
        String techName = null;
        int levelNum = -1;
        if (session.get("SelectedTech") != null && session.get("selectedlevel") != null) {
            techName = (String) session.get("SelectedTech");
            levelNum = (Integer) session.get("selectedlevel");
            levelExam = resultDaoService.getLevelExamResult(techName, levelNum);
        } else {
            levelExam = questionService.getQuestionCountByExamId(EXAM_ID);
        }
        int passingCount = levelExam.getPassingCount();
        int questionCount = levelExam.getQuestionCount();
        int finalRes = 0;
        if (result >= passingCount) {
            System.out.println("Test PASS " + result);
            finalRes = 1;
        } else {
            System.out.println("Test FAIL " + result);
        }
        String das = getUserName();
        System.out.println("result value:::::" + result + "passing count" + passingCount
                + "questionCount::::" + questionCount);

        double percentage = (double) Math.round(((double) result / questionCount) * 100);
        System.out.println("percentage value::::" + percentage);
        Timestamp time = getCurrentSystemTimeStamp();
        // resultDaoService.updateResult(result_id, finalRes, percentage, time);
        counter = 0;
        totalAttempt = questionCount - notAttempt;
        correctAnswers = result;
        int total = questionCount;
        wrongAnswers = totalAttempt - result;
        System.out.println("correctAnswers " + correctAnswers + " wrongAnswers " + wrongAnswers);
        if (techName == null || levelNum == -1) {
            displayResult = resultDaoService.getResult(result_id);
        } else {
            displayResult = resultDaoService.getResult(das, techName, levelNum);
        }
        session.put("TOTAL_OBJECTIVE", total);
        session.put("OBJECT_ATTEMPT", totalAttempt);
        /*
         * session.remove("SelectedTech"); session.remove("selectedlevel");
         * session.remove("QuesionList");
         */
        // session.remove("finalResult");
        return "success";
    }

    public int getOptionInt(String option) {
        ArrayList<String> optArray = new ArrayList<String>(Arrays.asList("A", "B", "C", "D"));
        int index = optArray.indexOf(option);
        System.out.println("*****getOptionInt index " + index);
        return index + 1;
    }

    /*
     * Next button action
     */
    public String nextList() {
        List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
        int resul_id = 0;
        String das = getUserName();
        System.out.println(Thread.currentThread()+" Next fundtion DAS id " + das + " Counter value:::::::" + counter );
        List<QuestionVo> questionsVo = (List<QuestionVo>) session.get("QuesionList");
        counter = (Integer)session.get("counter");
        System.out.println( " counter from session "+getCounter());
        System.out.println(Thread.currentThread()+" question VO "+questionsVo);
        if (session.get("RESULT_ID") != null) {
        	System.out.println(" result id "+session.get("RESULT_ID"));
            resul_id = (Integer) session.get("RESULT_ID");
        }
        if (session.get("ATTEMPTED") != null) {
            System.out.println("Next counter is:::: " + counter);
            int attempt = (Integer) session.get("ATTEMPTED"); //What is the use of this variable ?? just to print ?
            System.out.println("attempted= "+attempt);
            counter = 3;
            session.remove("ATTEMPTED");
        }
        totalQuestions = questionsVo.size();
        System.out.println("Next counter is " + counter + " total question size "
                + questionsVo.size());
        Map prevAnswerMap = getAnswerMap();
        int locInt = 0;
        List<Integer> inQstions = new ArrayList<Integer>();
        for (int prevInd = counter - 3; prevInd < counter; prevInd++) {
            String seleAnswer = "null";
            QuestionVo preQ = questionsVo.get(prevInd);
            if (null != (QuestionVo) prevAnswerMap.get(locInt)) {
                seleAnswer = ((QuestionVo) prevAnswerMap.get(locInt)).getSelectedAnswer();
            }
            locInt++;
            // String id = getDisQuestionId().get(prevInd);
            System.out.println("Current your anww " + seleAnswer + " fetched question id " + preQ.getQuestionId());
            ResultDetails rDeatails = new ResultDetails();
            int selAns = 0;
            if (seleAnswer != null) {
                selAns = getOptionInt(seleAnswer);
            }
            rDeatails.setAnswerOption(new Integer(selAns).toString());
            inQstions.add(preQ.getQuestionId());
            rDeatails.setQuestionId(preQ.getQuestionId());
            rDeatails.setResultId(resul_id);
            rDeatails.setCorrectOption(preQ.getCorrectAnswer());

            resultDeatilList.add(rDeatails);
            preQ.setSelectedAnswer(seleAnswer);
            questionsVo.remove(prevInd);
            questionsVo.add(prevInd, preQ);
            session.put("QuesionList", questionsVo);
            // finalResult.put(preQ.getQuestionId(), seleAnswer);
        }
        
        System.out.println("inQstions "+inQstions);
        boolean found = resultDaoService.searchQuestions(resul_id, inQstions);
        if (!found) {
            // Create result Details
            System.out.println("***********found is " + found);
            resultDaoService.createResultDetails(resultDeatilList);
        } else {
            // Update result Details
            System.out.println("***********found is " + found);
            resultDaoService.updateResultDetails(resultDeatilList);
        }
        inQstions.clear();
        resultDeatilList.clear();
        // List<QuestionVo> qList = getQuestions();
        if (counter < questionsVo.size() - counter) {
            List<ResultDetails> nextDeatilList = new ArrayList<ResultDetails>();
            List<QuestionVo> tempQuestionList = ((List<QuestionVo>) session.get("QuesionList"))
                    .subList(counter, counter + 3);
            questionsDisplay = returnDisplayList(tempQuestionList, counter);
            // questionsDisplay = ((List<QuestionVo>)
            // session.get("QuesionList")).subList(counter,
            // counter + 3);
            this.counter = counter + 3;
            System.out.println("NEXT counter value :::" + counter);
            session.put("counter",counter);
            Map answerMap = getAnswerMap();
            System.out.println("Next questionsDisplay size " + questionsDisplay.size());
            for (int i = 0; i < questionsDisplay.size(); i++) {
                QuestionVo qs = (QuestionVo) questionsDisplay.get(i);
                answerMap.put(i, qs);
                ResultDetails rDeatails = new ResultDetails();
                int selAns = 0;
                rDeatails.setAnswerOption(new Integer(selAns).toString());
                inQstions.add(qs.getQuestionId());
                rDeatails.setQuestionId(qs.getQuestionId());
                rDeatails.setResultId(resul_id);
                rDeatails.setCorrectOption(qs.getCorrectAnswer());
                nextDeatilList.add(rDeatails);
            }
            found = resultDaoService.searchQuestions(resul_id, inQstions);
            System.out.println("next questuions " + found);
            if (!found) {
                resultDaoService.createResultDetails(nextDeatilList);
            }
            nextDeatilList.clear();
            inQstions.clear();
            setAnswerMap(answerMap);
            setQuestionsDisplay(questionsDisplay);
            return "success";

        } else {
            List<ResultDetails> nextDeatilList = new ArrayList<ResultDetails>();
            int totalQstn = questionsVo.size();
            int flag = 0;
            if (counter + 3 < totalQstn) {
                List<QuestionVo> tempQuestionList = ((List<QuestionVo>) session.get("QuesionList"))
                        .subList(counter, counter + 3);
                questionsDisplay = returnDisplayList(tempQuestionList, counter);
                // questionsDisplay = ((List<QuestionVo>)
                // session.get("QuesionList")).subList(counter,
                // counter + 3);
                counter = counter + 3;
            } else {
                List<QuestionVo> tempQuestionList = ((List<QuestionVo>) session.get("QuesionList"))
                        .subList(counter, totalQstn);
                questionsDisplay = returnDisplayList(tempQuestionList, counter);
                // questionsDisplay = ((List<QuestionVo>)
                // session.get("QuesionList")).subList(counter,
                // totalQstn);
                counter = totalQstn;
                flag = 1;
            }
            System.out.println("counter value Next last:::" + counter);
            Map answerMap = getAnswerMap();
            System.out.println("Next questionsDisplay size " + questionsDisplay.size());
            for (int i = 0; i < questionsDisplay.size(); i++) {
                QuestionVo qs = (QuestionVo) questionsDisplay.get(i);
                answerMap.put(i, qs);
                ResultDetails rDeatails = new ResultDetails();
                int selAns = 0;
                rDeatails.setAnswerOption(new Integer(selAns).toString());
                rDeatails.setQuestionId(qs.getQuestionId());
                inQstions.add(qs.getQuestionId());
                rDeatails.setResultId(resul_id);
                rDeatails.setCorrectOption(qs.getCorrectAnswer());
                nextDeatilList.add(rDeatails);
            }
            found = resultDaoService.searchQuestions(resul_id, inQstions);
            System.out.println("next questuions " + found);
            if (!found) {
                resultDaoService.createResultDetails(nextDeatilList);
            }
            inQstions.clear();
            nextDeatilList.clear();
            session.put("counter",counter);
            if (flag == 1) {
                return "success1";
            } else {
                return "success";
            }
        }
    }

    /*
     * public String nextSection() {
     * 
     * String technology = (String) session.get("SelectedTech"); Integer level =
     * (Integer) session.get("selectedlevel"); counter = 1; questionsDisplay =
     * questionService.getQuestionList(technology, level, 1);
     * 
     * return "success"; }
     */
    private List<QuestionVo> returnDisplayList(List<QuestionVo> voList, int counter) {

        List<QuestionVo> questionList = new ArrayList<QuestionVo>();

        for (int i = 0; i < voList.size(); i++) {
            QuestionVo questVo = new QuestionVo();
            QuestionVo tempVo = voList.get(i);
            /*if ((tempVo.getQuestionText()).contains(")")) {
                questVo.setQuestionText(tempVo.getQuestionText());
            } else {
            */    questVo.setQuestionText(counter + (i + 1) + ".)" + " " + tempVo.getQuestionText());
            //}
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

    public String reset() {
        super.clearErrorsAndMessages();
        technologyList = technologyService.getAllTechnologies(); // setMessage(getText(MESSAGE));
        setTechnologies("-1");
        compLevelList = getLevelCount(getUserName());
        System.out.println("inside select technology::::::: ");
        return SUCCESS;
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

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    @Override
    public QuestionVo getModel() {
        QuestionVo qmodel = new QuestionVo();
        return qmodel;
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

    public int getPassingCount() {
		return passingCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

    
}
