package net.atos.awl.tes.cert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.atos.awl.tes.cert.vo.Technology;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TechnologyDaoImpl implements TechnologyDao {
    private ArrayList<Technology> technologies;

    /*
     * static { technologies = new ArrayList(); }
     */

    Log logger = LogFactory.getLog(this.getClass());

    public List<Integer> getAvailableLevel(String technology, String dasId) {

        Connection con = null;
        PreparedStatement stmt = null;
        List<Integer> level = null;
        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("level.query"));
            stmt.setString(1, dasId);
            stmt.setString(2, technology);
            
            System.out.println(stmt.toString());
            
            ResultSet rs = stmt.executeQuery();
            
            level = new ArrayList<Integer>(rs.getFetchSize());
            while (rs.next()) {
                level.add(rs.getInt("level"));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("exception on DB closing ");
            }
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException logOrIgnore) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException logOrIgnore) {
                }
        }
        return level;

    }

    public List<Integer> getAvailableLevels(String technology){
    	 Connection con = null;
         PreparedStatement stmt = null;
         List<Integer> level = null;
         try {
             con = DBUtils.getConnection();
             stmt = con.prepareStatement(DBUtils.getQuery("level.from.tech.name"));
             stmt.setString(1, technology);
             ResultSet rs = stmt.executeQuery();
             level = new ArrayList<Integer>(rs.getFetchSize());
             while (rs.next()) {
                 level.add(rs.getInt("level"));
             }
             rs.close();
         } catch (Exception e) {
             System.out.println("exception " + e.getMessage());
             try {
                 if (con != null) {
                     con.close();
                 }
             } catch (Exception ex) {
                 System.out.println("exception on DB closing ");
             }
             e.printStackTrace();
         } finally {
             if (stmt != null)
                 try {
                     stmt.close();
                 } catch (SQLException logOrIgnore) {
                 }
             if (con != null)
                 try {
                     con.close();
                 } catch (SQLException logOrIgnore) {
                 }
         }
         return level;
    }
    public List getAllTechnologies() {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("technology.all"));
            ResultSet rs = stmt.executeQuery();
            technologies = new ArrayList<Technology>();
            while (rs.next()) {
                technologies.add(new Technology(rs.getString("name"), rs.getString("tech_id")));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("exception on DB closing ");
            }
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException logOrIgnore) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException logOrIgnore) {
                }
        }
        return technologies;
    }

    public Technology getTechnology(String id) {
        Technology tech = null;
        Iterator iter = technologies.iterator();
        while (iter.hasNext()) {
            tech = (Technology) iter.next();
            if (tech.getAcronym().equals(id)) {
                break;
            }
        }
        return tech;
    }

    public void update(Technology tech) {
        String id = tech.getAcronym();
        for (int i = 0; i < technologies.size(); i++) {
            Technology tempTech = (Technology) technologies.get(i);
            if (tempTech.getAcronym().equals(id)) {
                technologies.set(i, tech);
                break;
            }
        }
    }

    public void insert(Technology tech) {
        int lastId = 0;
        Iterator iter = technologies.iterator();
        technologies.add(tech);
    }

    public void delete(String id) {
        for (int i = 0; i < technologies.size(); i++) {
            Technology tempTech = (Technology) technologies.get(i);
            if (tempTech.getAcronym().equals(id)) {
                technologies.remove(i);
                break;
            }
        }
    }

    public Map<String, String> getTechComplevels(String dasId) {
        System.out.println("TechnologyDAO getTechComplevels das is " + dasId);
        Connection con = null;
        PreparedStatement stmt = null;
        Map<String, String> level = new HashMap<String, String>();
        try {
            // List<Technology> techList = getAllTechnologies();
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("tech.levels"));
            stmt.setString(1, dasId);
            ResultSet rs = stmt.executeQuery();
            // int techCount = 0;
            while (rs.next()) {
                String tech = rs.getString("name");
                System.out.println("map key putting " + tech);
                level.put(tech, String.valueOf(rs.getInt("levels")));
            }
        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                System.out.println("exception on DB closing ");
            }
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException logOrIgnore) {
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException logOrIgnore) {
                }
        }
        return level;
    }
}
