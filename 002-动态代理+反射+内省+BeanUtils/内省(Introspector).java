内省(Introspector) 是Java 语言对 JavaBean 类属性、事件的一种缺省处理方法。

一、 JavaBean
　　JavaBean 是一种特殊的类，主要用于传递数据信息，这种类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。如果在两个模块之间传递信息，可以将信息封装进JavaBean中，这种对象称为“值对象”(Value Object)，或“VO”。方法比较少。这些信息储存在类的私有变量中，通过set()、get()获得。

例 JavaBean 类
public class User {

	private String name;
	private int age;
	private double height;
	...getter/setter 方法
}
　　在类User中有属性 Name, 那我们可以通过 getName,setName来得到其值或者设置新的值。通过 getUserName/setUserName来访问 userName属性，这就是默认的规则。 Java JDK中提供了一套 API 用来访问某个属性的 getter/setter 方法，这就是内省。

二、 JDK内省类库：

1.　PropertyDescriptor类:  JavaBean 对象的每一个属性 对应着一个 PropertyDescriptor 对象

　　PropertyDescriptor 类表示 JavaBean类通过存储器导出一个属性。主要方法：

	1. 构造函数
	 	 PropertyDescriptor(String propertyName, Class<?> beanClass)  通过属性名和 JavaBean 的 class对象获取 属性对应的对象

	2. getName()  获得属性名
	3. getWriteMethod() 获得写如属性方法
	4. getReadMethod()，获得用于读取属性值的方法；getWriteMethod(), 获得用于写入属性值的方法;
　　5. getPropertyType()，获得属性的Class对象;

　　6. setReadMethod(Method readMethod)，设置用于读取属性值的方法;
　　7. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法。

　　实例代码如下：

	 /*PropertyIntrospector */
	public class PropertyIntrospectorUtil {
		public static  void setProperty(String propertyName, Object value, User user) {
			PropertyDescriptor  pd = new PropertyDescriptor(propertyName,user.getClass());
			Method method=pd.getWriteMethod();
			method.invoke(user, value);
		}

		public static Object getProperty(String propertyName,User user){
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, user.getClass());
			Method method = pd.getReadMethod();
			return method.invoke(user);
		}
	}


2.  Introspector 类:

　　将JavaBean中的属性封装起来进行操作。在程序把一个类当做 JavaBean 来看，就是调用Introspector.getBeanInfo()方法，得到的BeanInfo对象封装了把这个类当做JavaBean看的结果信息，即属性的信息。

　　主要方法:   BeanInfo beanInfo=Introspector.getBeanInfo(UserInfo.class);
			    PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();

	获得属性的描述，可以采用遍历BeanInfo的方法，来查找、设置类的属性。具体代码如下：

	public class BeanInfoUtil {
		public static void setProperty(User user, String propertyName, Object value) {
				BeanInfo beanInfo = (BeanInfo) Introspector.getBeanInfo(User.class);
				PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
				if (pds != null && pds.length > 0) {
					for (PropertyDescriptor pd : pds) {
						if (pd.getName().equals(propertyName)) {
							Method method = pd.getWriteMethod();
							method.invoke(user, value);
						}
					}
				}
		}
		public static Object getProperty(User user, String propertyName) {
			BeanInfo beanInfo = Introspector.getBeanInfo(User.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			if (pds != null && pds.length > 0) {
				for (PropertyDescriptor pd : pds) {
					if (pd.getName().equals(propertyName)) {
						Method method = pd.getReadMethod();
						return method.invoke(user);
					}
				}
			}
		}
	}

    通过这两个类的比较可以看出，都是需要获得PropertyDescriptor，只是方式不一样：前者通过创建对象直接获得，后者需要遍历，所以使用PropertyDescriptor类更加方便。

3.  测试
	User user =  new User();
	user.setName("peida");

    BeanInfoUtil.getProperty(user,"name");
    BeanInfoUtil.setProperty(user, "name","ethan");
    BeanInfoUtil.setProperty(userInfo, "age","18"); // 报错，不能将 整型以字符串的类型赋入
　　说明：BeanInfoUtil.setProperty(userInfo, "age");报错是应为age属性是int数据类型，而setProperty方法里面默认给age属性赋的值是String类型。所以会爆出argument type mismatch参数类型不匹配的错误信息。

　　BeanUtils工具包：



　　由上述可看出，内省操作非常的繁琐，所以所以Apache开发了一套简单、易用的API来操作Bean的属性——BeanUtils
