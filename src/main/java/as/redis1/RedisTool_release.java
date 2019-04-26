package as.redis1;

import java.util.Collections;

import redis.clients.jedis.Jedis;

public class RedisTool_release {

	private static final Long RELEASE_SUCCESS = 1L;

    /**
     * �ͷŷֲ�ʽ��
     * @param jedis Redis�ͻ���
     * @param lockKey ��
     * @param requestId �����ʶ
     * @return �Ƿ��ͷųɹ�
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
}

/*
 ����ֻ��Ҫ���д���͸㶨�ˣ���һ�д��룬д��һ���򵥵�Lua�ű����룬���ǽ�Lua���봫��jedis.eval()�����
 ��ʹ����KEYS[1]��ֵΪlockKey��ARGV[1]��ֵΪrequestId��eval()�����ǽ�Lua���뽻��Redis�����ִ�С�

��ô���Lua����Ĺ�����ʲô�أ���ʵ�ܼ򵥣����Ȼ�ȡ����Ӧ��valueֵ������Ƿ���requestId��ȣ���������ɾ��������������
��ôΪʲôҪʹ��Lua������ʵ���أ���ΪҪȷ������������ԭ���Եġ����ڷ�ԭ���Ի����ʲô���⣬��
��ôΪʲôִ��eval()��������ȷ��ԭ���ԣ�Դ��Redis�����ԣ�
*/
//Lua ��һ������С�ɵĽű����ԣ��ñ�׼C���Ա�д����Դ������ʽ���ţ� �����Ŀ����Ϊ��Ƕ��Ӧ�ó����У��Ӷ�ΪӦ�ó����ṩ������չ�Ͷ��ƹ��ܡ�