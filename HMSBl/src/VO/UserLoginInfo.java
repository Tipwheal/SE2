package VO;

/**
 * Created by Administrator on 2016/11/29.
 */
public class UserLoginInfo {
    private final String userId;
    private final String password;

    public UserLoginInfo(String userId,String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
