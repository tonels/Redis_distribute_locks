package as.redis2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

	private static JedisPool pool;// jedis���ӳ�

	private static int maxTotal = 20;// ���������

	private static int maxIdle = 10;// ������������

	private static int minIdle = 5;// ��С����������

	private static boolean testOnBorrow = true;// ��ȡ����ʱ�������ӵĿ�����

	private static boolean testOnReturn = false;// �ٻ�����ʱ���������ӵĿ�����

	static {
		initPool();// ��ʼ�����ӳ�
	}

	public static Jedis getJedis() {
		return pool.getResource();
	}

	public static void close(Jedis jedis) {
		jedis.close();
	}

	private static void initPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		config.setBlockWhenExhausted(true);
		pool = new JedisPool(config, "118.25.104.54", 6379, 5000);
	}
}
