package as.jedis;

import java.util.UUID;

import redis.clients.jedis.Jedis;

public class Demo1 {

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new MyThread().start();
		}
	}
}

class MyThread extends Thread {
	public void run() {
		String name = Thread.currentThread().getName(); // ��ǰ�߳���
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.connect();
		System.out.println(name + " ��ȡ��֮ǰ  a ���� " + jedis.get("a"));
		boolean b = get(jedis, "c", UUID.randomUUID().toString(), 5000);
		if (b) {
			System.out.println(name + "��ȡ���ɹ�" + "����ID�ǣ�" + jedis.get("c"));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jedis.decr("a");
			if (Long.valueOf(jedis.get("a")) == 0) {
				System.out.println("�������ˣ�������Ʒ�౻������ϣ�");
			}
			System.out.println(name + " ��ȡ���ɹ����Լ�֮��  a ����" + jedis.get("a"));
		} else {
			System.out.println(name + "��ȡ��ʧ��" + "����ID�ǣ�" + jedis.get("c"));
		}
		jedis.close();
	}

	// ���Ի�ȡ��
	private static boolean get(Jedis jedis, String lockKey, String requestId, int expireTime) {

		String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);

		if ("OK".equals(result)) {
			return true;
		}
		return false;
	}
}
