package as.redis2;

public class DistributedLockUtil {
	 
    private DistributedLockUtil(){
    }
 
    public static boolean lock(String lockName){//lockName可以为共享变量名，也可以为方法名，主要是用于模拟锁信息
        System.out.println(Thread.currentThread() + "开始尝试加锁！");
        Long result = RedisPoolUtil.setnx(lockName, String.valueOf(System.currentTimeMillis() + 5000));
        
//        如果客户端获得锁，SETNX返回1，那么将lock.foo键的Unix时间设置为不在被认为有效的时间。客户端随后会使用DEL lock.foo去释放该锁。
        if (result != null && result.intValue() == 1){
            System.out.println(Thread.currentThread() + "加锁成功！");
            RedisPoolUtil.expire(lockName, 5);
            System.out.println(Thread.currentThread() + "执行业务逻辑！");
            
            //以下为具体业务实现
            //
            
            // 删除锁
            RedisPoolUtil.del(lockName);
            return true;
        } 
        
//        如果SETNX返回0，或 null，那么该键已经被其他的客户端锁定。如果这是一个非阻塞的锁，才能立刻返回给调用者，或者尝试重新获取该锁，直到成功或者过期超时。
        else { // 说明，
            String lockValueA = RedisPoolUtil.get(lockName);
            if (lockValueA != null && Long.parseLong(lockValueA) >= System.currentTimeMillis()){
                String lockValueB = RedisPoolUtil.getSet(lockName, String.valueOf(System.currentTimeMillis() + 5000));
                if (lockValueB == null || lockValueB.equals(lockValueA)){
                    System.out.println(Thread.currentThread() + "加锁成功！");
                    RedisPoolUtil.expire(lockName, 5);
                    System.out.println(Thread.currentThread() + "执行业务逻辑！");
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