package net.atos.awl.tes.cert.vo;

public class LevelExam {

    private String examId;

    private int questionCount;

    private int passingCount;

    private int descriptiveQuestionCount;

    private int descriptivePassingCount;

    private String techName;

    private int level;

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDescriptiveQuestionCount() {
        return descriptiveQuestionCount;
    }

    public void setDescriptiveQuestionCount(int descriptiveQuestionCount) {
        this.descriptiveQuestionCount = descriptiveQuestionCount;
    }

    public int getDescriptivePassingCount() {
        return descriptivePassingCount;
    }

    public void setDescriptivePassingCount(int descriptivePassingCount) {
        this.descriptivePassingCount = descriptivePassingCount;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getPassingCount() {
        return passingCount;
    }

    public void setPassingCount(int passingCount) {
        this.passingCount = passingCount;
    }

}
