package as.utils.common;


/**
 * ����
 */
public class Constant {

    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis����ʱ�䣬����Ϊ��λ��һ����
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis����ʱ�䣬����Ϊ��λ��һСʱ
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis����ʱ�䣬����Ϊ��λ��һ��
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-ǰ׺-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-ǰ׺-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";

    /**
     * redis-key-ǰ׺-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

}

