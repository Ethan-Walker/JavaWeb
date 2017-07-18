package mydbutils;


import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.ResultSetHandler;

public class MyBeanListHandler<T> implements ResultSetHandler<List<T>> {

	private Class clazz;
	public MyBeanListHandler(Class clazz) {
		this.clazz = clazz;
	}

	 // ����һ: ���� ÿ�� ResultSetMetaData Ԫ���ݵ� �������� Ȼ����������� ƥ�� PropertyDescriptor ����
		// ��ResultSet�е����� ��װ�� List ��
	@Override
	public List<T> handle(ResultSet resultset) throws SQLException {
		List<T> list= new ArrayList<>();


		ResultSetMetaData rsmd = resultset.getMetaData();
		int columnCount = rsmd.getColumnCount();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			while(resultset.next()){
				@SuppressWarnings("unchecked")
				T t = (T) clazz.newInstance();
				for(int i=1;i<=columnCount;i++){
					String columnName = rsmd.getColumnName(i);
//					System.out.println("columnName=>"+columnName);
					for(PropertyDescriptor pd:pds){
						if(pd.getName().equals(columnName)){
//							System.out.println("������==>"+pd.getName());
							Object value = resultset.getObject(i);
//							System.out.println(value);
							Method method= pd.getWriteMethod();
							method.invoke(t,value );
							break;
						}
					}
				}
				list.add(t);
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}


		return list;
	}




	  	//�������� ͨ������ PropertyDescriptor ���飬��ȡÿһ�����������ٻ�ȡƥ��� resultSet ��Ӧ����
		// ע�� : ÿ�� PropertyDescriptor �ж���һ����������� class
	@Override
	public List<T> handle(ResultSet resultset) throws SQLException {
		List<T> list = new ArrayList<>();


		try {
			BeanInfo beanInfo =  Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			while(resultset.next()){
				@SuppressWarnings("unchecked")
				T t = (T) clazz.newInstance();

				if(pds!=null&pds.length>1){
					for(PropertyDescriptor pd:pds){
						String propertyName = pd.getName();

						if(!propertyName.equals("class")){// �ų� �������� class ʱ����ȡ���� ֵ����
							Object value =resultset.getObject(propertyName);
							Method method = pd.getWriteMethod();
							method.invoke(t,value);
						}
					}
					list.add(t);
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return list;
	}


	// ������ , ͨ��BeanUtils ʵ�����ݷ�װ
	@Override
	public List<T> handle(ResultSet resultset) throws SQLException {

		List<T> list = new ArrayList<>();
		ResultSetMetaData rsmd = resultset.getMetaData();
		int count = rsmd.getColumnCount();

		while(resultset.next()){
			try {
				T t = (T)clazz.newInstance();
				for(int i=1;i<=count;i++){
					String propertyName= rsmd.getColumnName(i);
					Object value  = resultset.getObject(propertyName);
//					String value = resultset.getString(propertyName);   // ���ݿ������е����Ͷ�����ͨ�� getString()���
					System.out.println(propertyName+"==>"+value);
					BeanUtils.setProperty(t,propertyName, value);
				}
				list.add(t);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	// ������:  BeanUtils �� populate ����

	@Override
	public List<T> handle(ResultSet resultset) throws SQLException {
		List<T> list= new ArrayList<>();
		ResultSetMetaData rsmd = resultset.getMetaData();
		int count = rsmd.getColumnCount();
		try {
			while(resultset.next()){
				T t = (T) clazz.newInstance();
//				һ������һ��map����
				Map<String,Object[]> map = new HashMap<>();
				for(int i=1;i<=count;i++){
					String propertyName = rsmd.getColumnName(i);
					Object[] objs = new Object[]{resultset.getObject(propertyName)};
					map.put(propertyName,objs);
				}
				BeanUtils.populate(t, map);
				list.add(t);
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return list;
	}
}
