package as.redis1;

import redis.clients.jedis.Jedis;

public class RedisTool_tryLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * ���Ի�ȡ�ֲ�ʽ��
     * @param jedis Redis�ͻ���
     * @param lockKey ��
     * @param requestId �����ʶ
     * @param expireTime ����ʱ��
     * @return �Ƿ��ȡ�ɹ�
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

}

/*
���Կ��������Ǽ�����һ�д��룺jedis.set(String key, String value, String nxxx, String expx, int time)�����set()����һ��������βΣ�

��һ��Ϊkey������ʹ��key����������Ϊkey��Ψһ�ġ�

�ڶ���Ϊvalue�����Ǵ�����requestId���ܶ�ͯЬ���ܲ����ף���key��Ϊ�����͹�����Ϊʲô��Ҫ�õ�value��ԭ��������������潲���ɿ���ʱ��
	�ֲ�ʽ��Ҫ������ĸ��������廹��ϵ���ˣ�ͨ����value��ֵΪrequestId�����Ǿ�֪����������ĸ�����ӵ��ˣ��ڽ�����ʱ��Ϳ��������ݡ�
	requestId����ʹ��UUID.randomUUID().toString()�������ɡ�

������Ϊnxxx������������������NX����˼��SET IF NOT EXIST������key������ʱ�����ǽ���set��������key�Ѿ����ڣ������κβ�����

���ĸ�Ϊexpx������������Ǵ�����PX����˼������Ҫ�����key��һ�����ڵ����ã�����ʱ���ɵ��������������

�����Ϊtime������ĸ��������Ӧ������key�Ĺ���ʱ�䡣

�ܵ���˵��ִ�������set()������ֻ�ᵼ�����ֽ����
	1. ��ǰû������key�����ڣ�����ô�ͽ��м������������������ø���Ч�ڣ�ͬʱvalue��ʾ�����Ŀͻ��ˡ�
	2. ���������ڣ������κβ�����

��ϸ��ͯЬ�ͻᷢ���ˣ����ǵļ��������������ǿɿ���������������������
	���ȣ�set()������NX���������Ա�֤�������key���ڣ�����������óɹ���Ҳ����ֻ��һ���ͻ����ܳ����������㻥���ԡ�
	��Σ��������Ƕ��������˹���ʱ�䣬��ʹ���ĳ����ߺ�������������û�н�������Ҳ����Ϊ���˹���ʱ����Զ���������key��ɾ���������ᷢ��������
	�����Ϊ���ǽ�value��ֵΪrequestId����������Ŀͻ��������ʶ����ô�ڿͻ����ڽ�����ʱ��Ϳ��Խ���У���Ƿ���ͬһ���ͻ��ˡ���������ֻ����Redis��������ĳ����������ݴ��������ݲ����ǡ�
*/