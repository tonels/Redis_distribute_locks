package as.utils.common;


/**
 * String����
 * @author liangshuai
 * @date 20190402
 */
public class StringUtil {
    /**
     * �����»���
     */
    private static final char UNDERLINE = '_';

    /**
     * StringΪ���ж�(������ո�)
     * @param str
     * @return boolean
     * @author liangshuai
 * @date 20190402
     */
    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * String��Ϊ���ж�(������ո�)
     * @param str
     * @return boolean
     * @author liangshuai
     * @date 20190402
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Byte����Ϊ���ж�
     * @param bytes
     * @return boolean
     * @author liangshuai
     * @date 20190402
     */
    public static boolean isNull(byte[] bytes) {
        // ����byte���鳤��Ϊ0�ж�
        return bytes == null || bytes.length == 0;
    }

    /**
     * Byte���鲻Ϊ���ж�
     * @param bytes
     * @return boolean
     * @author liangshuai
     * @date 20190402
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }

    /**
     * �շ�ת�»��߹���
     * @param param
     * @return java.lang.String
	 * @author liangshuai
	 * @date 20190402
     */
    public static String camelToUnderline(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * �»���ת�շ幤��
     * @param param
     * @return java.lang.String
     * @author liangshuai
     * @date 20190402
     */
    public static String underlineToCamel(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * ���ַ����������''
     * @param param
     * @return java.lang.String
     * @author liangshuai
     * @date 20190402
     */
    public static String addSingleQuotes(String param) {
        return "\'" + param + "\'";
    }
}

