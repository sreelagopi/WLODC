/**
 * 
 */
package net.atos.awl.tes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.service.TechnologyDaoService;
import net.atos.awl.tes.cert.service.TechnologyService;
import net.atos.awl.tes.cert.vo.ManagerReport;
import net.atos.awl.tes.cert.vo.QuestionVo;
import net.atos.awl.tes.cert.vo.ResultDetails;
import net.atos.awl.tes.cert.vo.Technology;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Ashish Modak
 * 
 */
public class ManagerAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	Map session;

	private String technologies;

	private String levels;

	private TechnologyService technologyService;

	private ResultService resultDaoService;

	private String userName;

	private String password;

	private List<Technology> technologyList;

	private List<Integer> levelList;

	private List<ManagerReport> managerReport;

	private boolean showLevel;

	private String das_id;

	public ManagerAction() {
		technologyService = new TechnologyDaoService();
		resultDaoService = new ResultDaoService();
		levelList = new ArrayList<Integer>();
		managerReport = new ArrayList<ManagerReport>();
		showLevel = true;
	}

	public String resetManager() {
		super.clearErrorsAndMessages();
		technologyList = technologyService.getAllTechnologies(); // setMessage(getText(MESSAGE));
		setTechnologies("-1");
		System.out.println("inside select technology::::::: ");
		showLevel=true;
		das_id="";
		return "success";
	}

	public String selectManageLevels() {
		String techName = getTechnologies();
		List<Integer> level = technologyService.getAvailableLevels(techName);
		if (level.size() > 0) {
			levelList.addAll(level);
		}
		System.out.println("Level list in selectManageLevels " + levelList);
		technologyList = new ArrayList<Technology>();
		if (technologyList.size() <= 0) {
			technologyList = technologyService.getAllTechnologies();
		}
		// setting same list again with setter ?
		setLevelList(levelList);
		return "input";
	}

	@SuppressWarnings("unused")
	public String manageReport() {
		String result = null;
		List<ResultDetails> resultDeatilList = new ArrayList<ResultDetails>();
		System.out.println("managerResult started...." + getTechnologies()
				+ " and " + getLevels());
		List<QuestionVo> questionList;
		String selectedTech = getTechnologies();
		Integer selectedlevel;
		String das = (String) session.get("DASID");
		if (selectedTech.equalsIgnoreCase("-1")
				&& getLevels().equalsIgnoreCase("-1")) {
			addActionMessage("Please select Technology and Level(Optional) and DASID(Optional)");
			result = "input";
		} else if (!selectedTech.equalsIgnoreCase("-1")
				&& !getLevels().equalsIgnoreCase("-1")) {
			selectedlevel = Integer.parseInt(getLevels());
			System.out.println("Filtering result on Technology : "
					+ selectedTech + " and Level : " + selectedlevel);
			setManagerReport(resultDaoService.getManagerReport(selectedTech,
					selectedlevel,getDas_id()));
			setTechnologies(selectedTech);
			setLevels(selectedlevel.toString());
			result = "success";
			showLevel = false;
		} else if (!selectedTech.equalsIgnoreCase("-1")) {
			System.out.println("Filtering result on Technology : "
					+ selectedTech);
			setManagerReport(resultDaoService
					.getManagerReport(selectedTech, -1,getDas_id()));
			result = "success";
			showLevel = true;
		}
		selectManageLevels();
		return result;
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

	public String getUserName() {
		if (userName == null || userName.length() == 0) {
			userName = (String) session.get("DASID");
			System.out.println("username:" + userName + ":");
		}
		return userName.toLowerCase();
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<ManagerReport> getManagerReport() {
		return managerReport;
	}

	public void setManagerReport(List<ManagerReport> managerReport) {
		this.managerReport = managerReport;
	}

	public boolean isShowLevel() {
		return showLevel;
	}

	public void setShowLevel(boolean showLevel) {
		this.showLevel = showLevel;
	}

	public String getDas_id() {
		return das_id;
	}

	public void setDas_id(String das_id) {
		this.das_id = das_id;
	}

}
