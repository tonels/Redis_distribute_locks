package as.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import as.exceptions.CustomException;
import as.utils.common.Constant;
import as.utils.common.SerializableUtil;
import as.utils.common.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * JedisUtil(�Ƽ���Byte���飬��Json�ַ���Ч�ʸ���)
 */
@Component
public class JedisUtil {

    /**
     * ��̬ע��JedisPool���ӳ�
     * ����������ע��JedisUtil��������Controller��Service��ʹ�ã�������дShiro��CustomCache�޷�ע��JedisUtil
     * ���ڸ�Ϊ��̬ע��JedisPool���ӳأ�JedisUtilֱ�ӵ��þ�̬��������
     * https://blog.csdn.net/W_Z_W_888/article/details/79979103
     */
    private static JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

    /**
     * ��ȡJedisʵ��
     * @param 
     * @return redis.clients.jedis.Jedis
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new CustomException("��ȡJedis��Դ�쳣:" + e.getMessage());
        }
    }

    /**
     * �ͷ�Jedis��Դ
     * @param
     * @return void
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new CustomException("�ͷ�Jedis��Դ�쳣:" + e.getMessage());
        }
    }

    /**
     * ��ȡredis��ֵ-object
     * @param key
     * @return java.lang.Object
     */
    public static Object getObject(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(key.getBytes());
            if (StringUtil.isNotNull(bytes)) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            throw new CustomException("��ȡRedis��ֵgetObject�����쳣:key=" + key + " cause=" + e.getMessage());
        }
        return null;
    }

    /**
     * ����redis��ֵ-object
     * @param key
	 * @param value
     * @return java.lang.String
     */
    public static String setObject(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key.getBytes(), SerializableUtil.serializable(value));
        } catch (Exception e) {
            throw new CustomException("����Redis��ֵsetObject�����쳣:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * ����redis��ֵ-object-expiretime
     * @param key
	 * @param value
	 * @param expiretime
     * @return java.lang.String
     */
    public static String setObject(String key, Object value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (Constant.OK.equals(result)) {
                jedis.expire(key.getBytes(), expiretime);
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("����Redis��ֵsetObject�����쳣:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * ��ȡredis��ֵ-Json
     * @param key
     * @return java.lang.Object
     */
    public static String getJson(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            throw new CustomException("��ȡRedis��ֵgetJson�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * ����redis��ֵ-Json
     * @param key
     * @param value
     * @return java.lang.String
     */
    public static String setJson(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new CustomException("����Redis��ֵsetJson�����쳣:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * ����redis��ֵ-Json-expiretime
     * @param key
     * @param value
     * @param expiretime
     * @return java.lang.String
     */
    public static String setJson(String key, String value, int expiretime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key, value);
            if (Constant.OK.equals(result)) {
                jedis.expire(key, expiretime);
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("����Redis��ֵsetJson�����쳣:key=" + key + " value=" + value + " cause=" + e.getMessage());
        }
    }

    /**
     * ɾ��key
     * @param key
     * @return java.lang.Long
     */
    public static Long delKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new CustomException("ɾ��Redis�ļ�delKey�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * key�Ƿ����
     * @param key
     * @return java.lang.Boolean
     */
    public static Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            throw new CustomException("��ѯRedis�ļ��Ƿ����exists�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * ģ����ѯ��ȡkey����(keys���ٶȷǳ��죬����һ��������ݿ���ʹ������Ȼ��������������⣬�������Ƽ�ʹ��)
     * @param key
     * @return java.util.Set<java.lang.String>
     */
    public static Set<String> keysS(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key);
        } catch (Exception e) {
            throw new CustomException("ģ����ѯRedis�ļ�����keysS�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * ģ����ѯ��ȡkey����(keys���ٶȷǳ��죬����һ��������ݿ���ʹ������Ȼ��������������⣬�������Ƽ�ʹ��)
     * @param key
     * @return java.util.Set<java.lang.String>
     */
    public static Set<byte[]> keysB(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key.getBytes());
        } catch (Exception e) {
            throw new CustomException("ģ����ѯRedis�ļ�����keysB�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * ��ȡ����ʣ��ʱ��
     * @param key
     * @return java.lang.String
     */
    public static Long ttl(String key) {
        Long result = -2L;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            throw new CustomException("��ȡRedis������ʣ��ʱ��ttl�����쳣:key=" + key + " cause=" + e.getMessage());
        }
    }
}
