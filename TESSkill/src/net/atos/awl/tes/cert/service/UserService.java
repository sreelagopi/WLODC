package net.atos.awl.tes.cert.service;

import java.util.List;

import net.atos.awl.tes.cert.vo.FinalResultVo;
import net.atos.awl.tes.cert.vo.StatusVo;
import net.atos.awl.tes.cert.vo.UserVo;

public interface UserService {

    public Integer createUser(UserVo userVo);

    public Integer updateUserData(UserVo userVo);
    
    public UserVo findUser(UserVo userVo);

    public Integer findUserforPending(String dasId, int level, String techName);

    public List<StatusVo> findUserStatus(String dasId);

    public boolean getReviewRole(String dasId);
    
    public boolean getManagerRole(String dasId);

    public FinalResultVo findUserResult(UserVo userVo);

}
