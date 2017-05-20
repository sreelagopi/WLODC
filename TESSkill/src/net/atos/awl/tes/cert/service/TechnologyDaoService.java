package net.atos.awl.tes.cert.service;

import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.dao.TechnologyDao;
import net.atos.awl.tes.cert.dao.TechnologyDaoImpl;
import net.atos.awl.tes.cert.dao.TechnologyTestDao;
import net.atos.awl.tes.cert.vo.Technology;

public class TechnologyDaoService implements TechnologyService {
    private TechnologyDao dao;

    public TechnologyDaoService() {
        this.dao = new TechnologyDaoImpl();
    }

    public List<Technology> getAllTechnologies() {
        return dao.getAllTechnologies();
    }

    public List<Integer> getAvailableLevel(String technology, String dasId) {
        return dao.getAvailableLevel(technology, dasId);
    }
    public List<Integer> getAvailableLevels(String technology){
    	 return dao.getAvailableLevels(technology);
    }
    
    public void updateTechnology(Technology tech) {
        dao.update(tech);
    }

    public void deleteTechnology(String id) {
        dao.delete(id);
    }

    public Technology getTechnology(String id) {
        return dao.getTechnology(id);
    }

    public void insertTechnology(Technology tech) {
		dao.insert(tech);
    }
    public Map<String, String> getTechComplevels(String dasId){
    	return dao.getTechComplevels(dasId);
    }
}
