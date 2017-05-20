package net.atos.awl.tes.cert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.atos.awl.tes.cert.vo.FinalResultVo;
import net.atos.awl.tes.cert.vo.StatusVo;
import net.atos.awl.tes.cert.vo.UserVo;

public class UserDaoImpl implements UserDao {

    public Integer createUser(UserVo userVo) {

        Connection con = null;
        PreparedStatement stmt = null;
        Integer val = 0;
        try {
            // levelExam = getLevelExamResult(technology, level);
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("create.user"));
            stmt.setString(1, userVo.getDasId());
            stmt.setString(2, userVo.getName());
            stmt.setString(3, userVo.getDasIdMgr());
            val = stmt.executeUpdate();
            System.out.println("val:::::" + val);

        } catch (SQLException e) {
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

        return val;

    }
    
    public Integer updateUserData(UserVo userVo)
    {
    	Connection con = null;
        PreparedStatement stmt = null;
        Integer val = 0;
        try {
            // levelExam = getLevelExamResult(technology, level);
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("update.user"));
            stmt.setString(1, userVo.getName());
            stmt.setString(2, userVo.getDasId());
           
            //stmt.setString(3, userVo.getDasIdMgr());
            val = stmt.executeUpdate();
            System.out.println("val:::::" + val);

        } catch (SQLException e) {
            System.out.println("exception " + e.getMessage());            
            e.printStackTrace();
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException logOrIgnore) {
                    System.out.println("exception on DB closing ");
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException logOrIgnore) {
                    System.out.println("exception on DB closing ");
                }
        }

        return val;
    	
    }

    public UserVo findUser(UserVo userVo) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserVo user = null;
        try {
            // levelExam = getLevelExamResult(technology, level);
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("find.user"));
            stmt.setString(1, userVo.getDasId());
            rs = stmt.executeQuery();
            System.out.println("val:::::" + rs);
            while (rs.next()) {
                user = new UserVo();
                user.setDasId(rs.getString("das_id"));
                user.setName(rs.getString("name"));
                user.setDasIdMgr(rs.getString("das_id_mgr"));
            }

        } catch (SQLException e) {
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

        return user;

    }

    public FinalResultVo findUserResult(UserVo userVo) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        FinalResultVo finalResult = null;
        try {
            // levelExam = getLevelExamResult(technology, level);
            Integer pendingCount = reviewPendingCount(userVo);
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.final.result"));
            stmt.setString(1, userVo.getDasId());
            stmt.setString(2, userVo.getTechName());
            stmt.setInt(3, userVo.getTechLevel());
            stmt.setInt(4, userVo.getResult());
            rs = stmt.executeQuery();
            System.out.println("Techname:::::" + userVo.getTechName());
            while (rs.next()) {
                finalResult = new FinalResultVo();
                if (rs.getInt("result") == 1) {
                    finalResult.setResult("Pass");
                } else if(rs.getInt("result") == 0) {
                    finalResult.setResult("Fail");
                } else{
                    finalResult.setResult("Pending");
                }
                finalResult.setResultId(rs.getInt("result_id"));
                finalResult.setExamId(rs.getInt("exam_id"));
                finalResult.setPercentile(rs.getDouble("percentile"));
                finalResult.setTechName(userVo.getTechName());
                finalResult.setTechLevel(userVo.getTechLevel());
                finalResult.setPendingCount(pendingCount);
            }

        } catch (SQLException e) {
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

        return finalResult;

    }

    public Integer reviewPendingCount(UserVo userVo) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer val = 0;
        try {
            System.out.println("Inside the findUser for pending method::::");
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.review.pending.count"));
            stmt.setString(1, userVo.getDasId());
            stmt.setString(2, userVo.getTechName());
            stmt.setInt(3, userVo.getTechLevel());

            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Inside while loop reviewPendingCount method::::");
                val = rs.getInt("reviewPending");
            }
            System.out.println("val:::::" + val);

        } catch (SQLException e) {
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

        return val;

    }

    public Integer findUserforPending(String dasId, int level, String techName) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer val = 0;
        try {
            System.out.println("Inside the findUser for pending method::::");
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.result.pending"));
            stmt.setString(1, dasId);
            stmt.setInt(2, level);
            stmt.setString(3, techName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Inside while loop findUser for pending method::::");
                val = rs.getInt("pending");
            }
            System.out.println("val:::::" + val);

        } catch (SQLException e) {
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

        return val;

    }

    public List<StatusVo> findUserStatus(String dasId) {

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<StatusVo> statusVoList = new ArrayList<StatusVo>();
        StatusVo status = null;
        try {
            // levelExam = getLevelExamResult(technology, level);
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.status"));
            stmt.setString(1, dasId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String tName = rs.getString("name");
                int level = rs.getInt("level");
                Integer res = rs.getInt("result");
                System.out.println("res in DAO " + res);
               // if (res != 0) {
                    status = new StatusVo();
                    status.setResult(res);
                    status.setTechLevel(level);
                    status.setTechName(tName);
                    statusVoList.add(status);
               // }
            }
            System.out.println("val:::::" + statusVoList.size());
        } catch (SQLException e) {
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

        return statusVoList;
    }

    // Chech for reviewer access rihts
    public boolean getReviewRole(String dasId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean returnString = false;
        String userDas = null;
        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.check.reviewer"));
            stmt.setString(1, dasId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userDas = rs.getString("das_id");
                returnString = true;
                System.out.println("user as reviewer found.." + userDas);
            }

        } catch (SQLException e) {
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
        return returnString;
    }
    
    public boolean getManagerRole(String dasId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean returnString = false;
        String userDas = null;
        try {
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(DBUtils.getQuery("user.check.manager"));
            stmt.setString(1, dasId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                userDas = rs.getString("das_id");
                returnString = true;
                System.out.println("user as manager found.." + userDas);
            }

        } catch (SQLException e) {
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
        return returnString;
    }
}
