package net.atos.awl.tes.cert.service;

import java.util.List;

import net.atos.awl.tes.cert.dao.UserDao;
import net.atos.awl.tes.cert.dao.UserDaoImpl;
import net.atos.awl.tes.cert.vo.FinalResultVo;
import net.atos.awl.tes.cert.vo.StatusVo;
import net.atos.awl.tes.cert.vo.UserVo;

public class UserDaoService implements UserService {

    private UserDao userDao;

    public UserDaoService() {
        userDao = new UserDaoImpl();
    }

    public Integer createUser(UserVo userVo) {
        return userDao.createUser(userVo);
    }

    public UserVo findUser(UserVo userVo) {
        return userDao.findUser(userVo);
    }
    public Integer updateUserData(UserVo userVo)
    {
    	return userDao.updateUserData(userVo);
    }

    public Integer findUserforPending(String dasId, int level, String techName) {
        return userDao.findUserforPending(dasId, level, techName);
    }

    public List<StatusVo> findUserStatus(String dasId) {
        return userDao.findUserStatus(dasId);
    }

    public boolean getReviewRole(String dasId) {
        return userDao.getReviewRole(dasId);
    }
    
    public boolean getManagerRole(String dasId) {
        return userDao.getManagerRole(dasId);
    }

    public FinalResultVo findUserResult(UserVo userVo) {
        return userDao.findUserResult(userVo);
    }

}
