package net.atos.awl.tes;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import net.atos.awl.tes.cert.service.TechnologyDaoService;
import net.atos.awl.tes.cert.service.TechnologyService;
import net.atos.awl.tes.cert.vo.Technology;

import com.opensymphony.xwork2.ActionSupport;
/**
 * @author  Gauri Chiplunkar
 */
public class TechnologyAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static TechnologyService techService = new TechnologyDaoService();
    private Technology technology;
    private List<Technology> technologies;
    
	public String getAllTechnologies(){
		technologies = techService.getAllTechnologies();
    	return "success";
    }


	public String setUpForInsertOrUpdate(){
		prep();
		if (technology != null && technology.getAcronym() != null) {
			technology = techService.getTechnology(technology.getAcronym());
		}
		return "success";
	}
	
	
    public String insertOrUpdate(){
    	if(!validationSuccessful()){
    		return "input";
    	}else{
    		if (technology.getAcronym() == null) {
    			techService.insertTechnology(technology);
    		} else {
    			techService.updateTechnology(technology);
    		}
        	
    	}
    	return "success";
    }
    
    public String deleteTechnology(){
		techService.deleteTechnology(technology.getAcronym());
		return "success";
	}
    
    private void prep() {

	}
    
   
	private boolean validationSuccessful(){
    	if(technology.getName()==null||technology.getName().trim().length()<1){
    		addActionMessage("FirstName is required");
    	}
    	if(technology.getAcronym()==null||technology.getAcronym().trim().length()<1){
    		addActionMessage("LastName is required");
    	}
    	
    	if(this.hasActionMessages()){
    		return false;
    	}else{
    		return true;
    	}
    		
    	
    }


	/**
	 * @return the technology
	 */
	public Technology getTechnology() {
		return technology;
	}


	/**
	 * @param technology the technology to set
	 */
	public void setTechnology(Technology technology) {
		this.technology = technology;
	}


	/**
	 * @return the technologies
	 */
	public List<Technology> getTechnologies() {
		return technologies;
	}


	/**
	 * @param technologies the technologies to set
	 */
	public void setTechnologies(List<Technology> technologies) {
		this.technologies = technologies;
	}
    
  

	

}
