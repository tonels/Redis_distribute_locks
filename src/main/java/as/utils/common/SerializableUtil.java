package as.utils.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import as.exceptions.CustomException;

import java.io.*;

/**
 * Serializable����(JDK)(Ҳ����ʹ��Protobuf���аٶ�)
 */
public class SerializableUtil {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializableUtil.class);

    /**
     * ���л�
     * @param object
     * @return byte[]
     */
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            LOGGER.error("SerializableUtil���������л�����IOException�쳣:" + e.getMessage());
            throw new CustomException("SerializableUtil���������л�����IOException�쳣:" + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                LOGGER.error("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
                throw new CustomException("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
            }
        }
    }

    /**
     * �����л�
     * @param bytes
     * @return java.lang.Object
     */
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error("SerializableUtil�����෴���л�����ClassNotFoundException�쳣:" + e.getMessage());
            throw new CustomException("SerializableUtil�����෴���л�����ClassNotFoundException�쳣:" + e.getMessage());
        } catch (IOException e) {
            LOGGER.error("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
            throw new CustomException("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                LOGGER.error("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
                throw new CustomException("SerializableUtil�����෴���л�����IOException�쳣:" + e.getMessage());
            }
        }
    }

}

