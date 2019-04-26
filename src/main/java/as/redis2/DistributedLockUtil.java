package as.redis2;

public class DistributedLockUtil {
	 
    private DistributedLockUtil(){
    }
 
    public static boolean lock(String lockName){//lockName����Ϊ�����������Ҳ����Ϊ����������Ҫ������ģ������Ϣ
        System.out.println(Thread.currentThread() + "��ʼ���Լ�����");
        Long result = RedisPoolUtil.setnx(lockName, String.valueOf(System.currentTimeMillis() + 5000));
        
//        ����ͻ��˻������SETNX����1����ô��lock.foo����Unixʱ������Ϊ���ڱ���Ϊ��Ч��ʱ�䡣�ͻ�������ʹ��DEL lock.fooȥ�ͷŸ�����
        if (result != null && result.intValue() == 1){
            System.out.println(Thread.currentThread() + "�����ɹ���");
            RedisPoolUtil.expire(lockName, 5);
            System.out.println(Thread.currentThread() + "ִ��ҵ���߼���");
            
            //����Ϊ����ҵ��ʵ��
            //
            
            // ɾ����
            RedisPoolUtil.del(lockName);
            return true;
        } 
        
//        ���SETNX����0���� null����ô�ü��Ѿ��������Ŀͻ����������������һ���������������������̷��ظ������ߣ����߳������»�ȡ������ֱ���ɹ����߹��ڳ�ʱ��
        else { // ˵����
            String lockValueA = RedisPoolUtil.get(lockName);
            if (lockValueA != null && Long.parseLong(lockValueA) >= System.currentTimeMillis()){
                String lockValueB = RedisPoolUtil.getSet(lockName, String.valueOf(System.currentTimeMillis() + 5000));
                if (lockValueB == null || lockValueB.equals(lockValueA)){
                    System.out.println(Thread.currentThread() + "�����ɹ���");
                    RedisPoolUtil.expire(lockName, 5);
                    System.out.println(Thread.currentThread() + "ִ��ҵ���߼���");
                    RedisPoolUtil.del(lockName);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }
}