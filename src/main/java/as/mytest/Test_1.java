package as.mytest;

import java.util.UUID;

import as.redis1.RedisTool_tryLock;
import as.redis1.RedisTool_release;
import as.utils.JedisUtil;
import redis.clients.jedis.Jedis;

public class Test_1 {

	public static void main(String[] args) {
		operate();
	}
	
	public static void operate() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.connect();
		String a = jedis.get("a");
		String requestId = UUID.randomUUID().toString();
		boolean lock = RedisTool_tryLock.tryGetDistributedLock(jedis, a, requestId, 10000);
		if(lock) {
			Long l1 = Long.valueOf(a);
			System.out.println("此时 a 对应为" + jedis.get(a));
			l1--;
			JedisUtil.setObject(a, l1);
			RedisTool_release.releaseDistributedLock(jedis, a, requestId);
			System.out.println("此时 a 对应为" + jedis.get(a));
			jedis.close();
		}
		else {
			System.out.println("获取锁失败");
			jedis.close();	
		}
//		jedis.de
		
	}
}
