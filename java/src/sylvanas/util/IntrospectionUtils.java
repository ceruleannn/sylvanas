package sylvanas.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description:
 *
 * 反射相关工具类 //
 *
 */
public class IntrospectionUtils {


    /**
     * find specify method by its object, method name and params types;
     * 根据对象, 方法名和参数类型数组找到对应方法
     *
     * @param obj     方法的对象
     * @param method  方法名
     * @param typeParams  参数类型数组
     */
    public static Method findMethod(Object obj, String method, Class<?>[] typeParams) throws NoSuchMethodException {

        return  obj.getClass().getMethod(method, typeParams);
    }

    /**
     * find specify method, try call it then return the method's return value;
     * 寻找指定的方法, 尝试调用并返回该方法的返回值
     *
     * @param target
     * @param methodN
     * @param params
     * @param typeParams
     * @return
     * @throws Exception
     */
    public static Object callMethodN(Object target, String methodN,
                Object params[], Class<?> typeParams[]) throws Exception {

        Object object = null;
        try {
            Method method = findMethod(target, methodN, typeParams);
            object = method.invoke(target, params);

        } catch (NoSuchMethodException e) {

            return null;
        } catch (InvocationTargetException ie){
            throw ie;
        }

        return object;
    }


    /**
     *  根据字符串为特定类型创建实际对象 //String, int , boolean ,InetAddress
     *  form tomcat 6.0 source
     *
     * @param object
     * @param paramType
     * @return
     */

    public static Object convert(String object, Class<?> paramType) {
        Object result = null;
        if ("java.lang.String".equals(paramType.getName())) {
            result = object;
        } else if ("java.lang.Integer".equals(paramType.getName())
                || "int".equals(paramType.getName())) {
            try {
                result = new Integer(object);
            } catch (NumberFormatException ex) {
            }
            // Try a setFoo ( boolean )
        } else if ("java.lang.Boolean".equals(paramType.getName())
                || "boolean".equals(paramType.getName())) {
            result = Boolean.valueOf(object);

            // Try a setFoo ( InetAddress )
        } else if ("java.net.InetAddress".equals(paramType
                .getName())) {
            try {
                result = InetAddress.getByName(object);
            } catch (UnknownHostException exc) {
                //if (log.isDebugEnabled())
                //    log.debug("IntrospectionUtils: Unable to resolve host name:" +
                //           object);
            }

            // Unknown type
        } else {
            //if (log.isDebugEnabled())
            //    log.debug("IntrospectionUtils: Unknown type " +
            //           paramType.getName());
        }
        if (result == null) {
            throw new IllegalArgumentException("Can't convert argument: " + object);
        }
        return result;
    }

}
