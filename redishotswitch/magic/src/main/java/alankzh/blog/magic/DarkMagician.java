package alankzh.blog.magic;

public class DarkMagician {
    private static volatile boolean initWithPwd;
    private static volatile String password;

    public static void setInitWithPwd(boolean initWithPwdP){
        initWithPwd = initWithPwdP;
    }

    public static boolean isMagicTime(){
        return ( !initWithPwd && password!=null && password.length()>0);
    }

    public static String getPassword(){
        return password;
    }

    public static void setPassword(String pwd){
        password = pwd;
    }
}
