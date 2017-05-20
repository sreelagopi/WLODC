package net.atos.awl.tes.cert.dao;

import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.vo.Technology;

public interface TechnologyDao {
    public List<Technology> getAllTechnologies();
    public Technology getTechnology(String id);
    public void update(Technology tech);
    public void insert(Technology tech);
    public void delete(String id);
    public List<Integer> getAvailableLevel(String technology, String dasId);
    public List<Integer> getAvailableLevels(String technology);
    public Map<String, String> getTechComplevels(String dasId);
}
