package com.redrum.webapp.weibo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class Util {
	public static void copy(Object dest, Object source){
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(dest.getClass());
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PropertyDescriptor[] pdList = beanInfo.getPropertyDescriptors();
		BeanInfo beanInfo2 = null;
		try {
			beanInfo2 = Introspector.getBeanInfo(source.getClass());
		} catch (IntrospectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PropertyDescriptor[] pdList2 = beanInfo2.getPropertyDescriptors();
		for (PropertyDescriptor pd : pdList) {
			Method writeMethod = null;
			try {
				writeMethod = pd.getWriteMethod();
			} catch (Exception e) {
			}

			if (writeMethod == null) {
				continue;
			}

				for (PropertyDescriptor pd2 : pdList2) {
					if(pd2.getName().equals(pd.getName())){

						Object val;
						try {
							val = pd2.getReadMethod().invoke(source);
							if(null != val){
								writeMethod.invoke(dest, val);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
//							e.printStackTrace();
						}
						break;
					}
				}
//				try{
//			writeMethod.invoke(dest, val);
//				}catch(Exception e){}
			}
	}

	public static String getLimitLengthString(String str, int len) {
		try {
			int counterOfDoubleByte = 0;
			byte[] b = str.getBytes("gb2312");
			if (b.length <= len)
				return str;
			for (int i = 0; i < len; i++) {
				if (b[i] < 0) {
					counterOfDoubleByte++;
				}
			}
			if (counterOfDoubleByte % 2 == 0)
				return new String(b, 0, len, "gb2312");
			else
				return new String(b, 0, len - 1, "gb2312");
		} catch (Exception ex) {
			return "";
		}
	}

    public static Date getChinaDate(){
        Date d = new Date();
        d.setTime(d.getTime() + 13*60*60*1000);
        return d;
    }
}
