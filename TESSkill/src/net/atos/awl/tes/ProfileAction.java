package net.atos.awl.tes;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.atos.awl.tes.cert.service.ResultDaoService;
import net.atos.awl.tes.cert.service.ResultService;
import net.atos.awl.tes.cert.vo.Result;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author  Gauri Chiplunkar
 */
public class ProfileAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static ResultService resultService = new ResultDaoService();
    private Result result;
    private List<Result> results;
    
	public String loadProfile(){
		//String dasId = (String) ServletActionContext.getRequest().getSession().getAttribute("DAS");
		String dasId = "A501088";
		results = resultService.getAllResults(dasId);
    	return "success";
    }

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the results
	 */
	public List<Result> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<Result> results) {
		this.results = results;
	}

}
