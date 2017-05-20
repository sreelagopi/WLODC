package net.atos.awl.tes.cert.service;

import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.vo.Technology;

public interface TechnologyService {
    public List<Technology> getAllTechnologies();
    public void updateTechnology(Technology tech);
    public void deleteTechnology(String techId);
    public Technology getTechnology(String techId);
    public void insertTechnology(Technology tech);
    public List<Integer> getAvailableLevel(String technology, String dasId);
    public List<Integer> getAvailableLevels(String technology);
    public Map<String, String> getTechComplevels(String dasId);
}
