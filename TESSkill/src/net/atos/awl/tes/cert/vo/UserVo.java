package net.atos.awl.tes.cert.vo;

public class UserVo {

    private String dasId;

    private String name;

    private String dasIdMgr;

    private String techName;

    private Integer techLevel;

    private Integer result;

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public Integer getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(Integer techLevel) {
        this.techLevel = techLevel;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDasId() {
        return dasId;
    }

    public void setDasId(String dasId) {
        this.dasId = dasId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDasIdMgr() {
        return dasIdMgr;
    }

    public void setDasIdMgr(String dasIdMgr) {
        this.dasIdMgr = dasIdMgr;
    }

}
