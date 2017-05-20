/**
 * 
 */
package net.atos.awl.tes.cert.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author A182131
 * 
 */
public class QuestionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String questionText;
	private Integer questionId;
	private String examId;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionType;
	private List<String> options;
	private int correctAnswer;
	private String selectedAnswer; 
	private String hint; 
	
	
	
	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	/**
	 * 
	 */
	public QuestionVo() {
		super();
	}

	public QuestionVo(String questionText, List<String> options, int corAnswer) {
		super();
		this.questionText = questionText;
		this.options = options;
		this.correctAnswer = corAnswer;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}	

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public String toString() {		
		return "questionid="+questionId+"examid="+examId+" answer="+selectedAnswer+" option1="+option1;
	}
	
	

}
