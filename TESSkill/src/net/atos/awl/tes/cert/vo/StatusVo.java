package net.atos.awl.tes.cert.vo;

public class StatusVo implements Comparable<StatusVo>{

    private String techName;

    private Integer techLevel;

    private String reviewStatus;

    private Integer result;

    
    public StatusVo() {

    }
    
    
    public StatusVo(String techName, Integer techLevel, Integer result) {
        this.techName = techName;
        this.techLevel = techLevel;
        this.result = result;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

	@Override
	public int compareTo(StatusVo o) {

		return this.toString().compareTo(o.toString());
	}


	@Override
	public boolean equals(Object obj) {		
		return this.toString().equals(obj.toString());
	}


	@Override
	public String toString() {
		return techName+techLevel+result;
	}	

}
