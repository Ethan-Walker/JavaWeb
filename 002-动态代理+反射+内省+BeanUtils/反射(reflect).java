JDK 内置反射

1.反射从Class类开始
要想使用反射，首先你需要得到Class对象，然后才能通过Class对象获取Constructor、Field、Method等对象。所有的反射对象都不可能自己来new，说白一点，这些反射对象对应的是class文件上的信息，你怎么可能自己去new呢？如果可以自己去new一个Class类的对象，那么是不是就不用我们再去编写.java文件，然后再通过编译器去编译成.class文件了呢？当然这是不可能的！
我们需要思考，Class除了可以返回当前对应类型的所有属性、方法、构造器的反射对象外，还有什么功能呢？例如对应类型的类名是什么？对应类型的父类是谁？对应类型是不是public类，是不是final类。对应类型有没有可能是个数组类型？有没有可能是接口类型？有没有可能是基本类型等等！如果你学会了这样思考，那么你今后学习新类是就方便多了！

2　得到Class对象
	通过对象获取Class对象：obj.getClass()；
	你很清楚要使用的Class类型：String.class、int.class，只需要在类型的后面添加.class就表示一个Class类型的实例了。
	通过类名来获取Class对象：在只有一个字符串（类名）时使用Class.forName(String className)来获取Class对象。
	Class c1 = “”.getClass();
	Class c2 = String.class;
	Class c3 = Class.forName(“java.lang.String”);
	System.out.println(c1 == c2);
	System.out.println(c2 == c3);

　　上面代码输出的都是true，这是因为一个.class文件，在方法区中只对应一个Class对象。

3　加载类
我们已经知道，main()方法是程序的入口。那是不是在main()方法开始执行之前，所有的class文件都已经加载到方法区中了呢？答案是：NO！通常只有需要执行到使用某个类的代码时，才会去CLASSPATH中加载class文件，如果程序从头到尾都没有使用某个类，那么这个类对应的class文件就不会被加载到内存。
可以导致一个类被加载可能有：
	使用一个类的静态方法；
	使用一个类的静态属性；
	创建这个类的对象；
	使用Class.forName()方法加载类；
	反序列化一个类的对象；
	加载一个类的子类时，也会加载其父类；
	加载一个类时，也会加载与该类相关的类。
上面给出的几个可能也只是可能而已，如果当前类没有被加载过，才会去加载，如果已经加载到方法区中了，那么就不可能再去加载。

4　Class类方法
getDeclared..() 获取到私有方法，私有变量，私有构造函数对象之后，必须先设置 setAccessable(true) ，然后才能创建对象/修改属性
　　方法1：
	String getName()：返回类名；
	String getSimpleName()：返回简单类名，不包含包名，但数组类型使用它比较方便；
	Class getSuperClass()：获取父类，Object.class.getSupperClass()返回null；
	int getModifiers()：获取类的所有修饰符信息；

方法2：
	Constructor getConstructor(Class… parameterTypes)：通过指定的参数类型获取公有构造器反射对象；
	Constructor[] getConstructors()：获取所有公有构造器对象；
	Constructor getDeclaredConstructor(Class… parameterTypes)：通过指定参数类型获取构造器反射对象。包含私有构造器对象；
	Constructor[] getDeclaredConstructors()：获取所有构造器对象。包含私有构造器；
	Field getField(String name)：通过名字获取公有属性反射对象，包含父类中声明的公有属性；
	Field[] getFields()：获取所有公有属性反射对象，包含父类中声明的公有属性；
	Field getDeclaredField(String name)：通过名字获取本类中某个属性，包含本类的private属性，但父类中声明的任何属性都不包含；
	Field[] getDeclaredFields()：获取本类中声明的所有属性，包含private属性，但不包含父类中声明的任何属性；
	Method getMethod(String methodName, Class… parameterTypes)：通过方法名和方法参数类型获取方法反射对象，包含父类中声明的公有方法，但不包含所有私有方法；
	Method[] getMethods()：获取所有公有方法，包含父类中的公有方法，但不包含任何私有方法；
	Method getDeclaredMethod(String name, Class… parameterTypes)：通过方法名和方法参数类型获取本类中声明的方法的反射对象，包含本类中的私有方法，但不包含父类中的任何方法；
	Method[] getDeclaredMethods()：获取本类中所有方法，包含本类中的私有方法，但不包含父类中的任何方法。

方法3：
	boolean isArray()：是否为数组类型；
	boolean isAnnotation()：是否为注解类型；
	boolean isAnnotationPresent(Class annotationClass)：当前类是否被annotationClass注解了；
	boolean isEnum()：是否为枚举类型；
	boolean isInterface()：是否为接口类型；
	boolean isPrimitive()：是否为基本类型；
	boolean isSynthetic()：是否为引用类型；

方法4：
	T newInstance()：使用本类无参构造器来创建本类对象；

其他反射类
　　其他反射类都在java.lang.reflect包下
1　AccessibleObject
AccessibleObject类是Constructor、Method、Field三个类的父类。
	Annotation getAnnotation(Class annotationClass)：获取作用在当前成员上的annotationClass类型的注解对象；
	Annotation[] getAnnotations()：获取作用在当前成员上的所有注解对象；
	boolean isAccessible()：判断当前成员是否可访问；
	void setAccessible(boolean flag)：设置当前成员是否可访问。

2　Construcator
	String getName()：获取构造器名；
	int getModifiers()：获取构造器上的所有修饰符信息；
	Class getDeclaringClass()：获取构造器所属的类型；
	Class[] getParameterTypes()：获取构造器的所有参数的类型；
	Class[] getExceptionTypes()：获取构造器上声明的所有异常类型；
	T newInstance(Object… initargs)：通过构造器反射对象调用构造器。

3　Method
	String getName()：获取方法名；
	int getModifiers()：获取方法上的所有修饰符信息；
	Class getDeclaringClass()：获取方法所属的类型；
	Class[] getParameterTypes()：获取方法的所有参数的类型；
	Class[] getExceptionTypes()：获取方法上声明的所有异常类型；
	Class getReturnType()：获取方法的返回值类型；
	Object invoke(Object obj, Object… args)：通过方法反射对象调用方法，如果当前方法是实例方法，那么当前对象就是obj，如果当前方法是static方法，那么可以给obj传递null。args表示是方法的参数；
	setAccessible(true);

4　Field
	String getName()：获取属性名；
	int getModifiers()：获取属性上的所有修饰符信息；
	Class getDeclaringClass()：获取属性所属的类型；
	Class getType()：获取当前属性的类型；
	Object get(Object obj)：获取obj对象的当前属性值；
	void set(Object obj, Object value)：设置obj对象的当前属性值为value；
	XXX getXXX(Object obj)：如果当前属性为基本类型，可以使用getXXX()系列方法获取基本类型属性值。假如当前属性为int类型，那么可以使用getInt(Object obj)方法获取obj对象的当前属性值；
	void setXXX(Object obj, XXX value)：如果当前属性为基本类型，可以使用setXXX()系统方法基本类型属性值。假如当前属性为int类型，那么可以使用setInt(Object obj, int value)方法设置obj对象的当前属性值为value。

5　Modifier
Modifier类有一系列的static方法用来解析其他getModifiers()方法返回的int值。
Method m = …
int m = m.getModifiers();
boolean b1 = Modifier.isAbstract(m);//解析m中是否包含abstract修饰
boolean b2 = Modifier.isStatic(m);//解析m中是否包含static修饰
String s = Modifiers.toString(m);//把所有修饰都转换成字符串













