1.Proxy  -- JDK提供的代理
	动态代理可实现对接口中的方法进行增强


	在Java中java.lang.reflect包下提供了一个Proxy类和一个InvocationHandler接口，通过使用这个类和接口就可以生成动态代理对象。
	JDK提供的代理只能针对接口做代理。我们有更强大的代理cglib

	Proxy类中的方法创建动态代理类对象
	Proxy.newProxyInstance
		创建的代理对象是在jvm运行时动态生成的一个对象

		public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)

		Proxy类中创建动态代理对象的方法的三个参数；
			ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
			Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它,那么这个代理对象就宣称实现了该接口(多态), 这样我就能调用这组接口中的方法了
			InvocationHandler对象, 表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上

		每一个动态代理类都必须要实现InvocationHandler这个接口，并且每个代理类的实例都关联到了一个handler，当我们通过代理对象调用一个方法的时候，这个方法的调用就会被转发为由InvocationHandler这个接口的invoke 方法来进行调用。

	InvocationHandler接口中invoke方法的三个参数：
	proxy:代表动态代理对象
	method:代表正在执行的方法
	args:代表调用目标方法时传入的实参


实例:
	1. 创建接口 interface UserDao{ void eat();}
	2. 实现类  class UserDaoImpl implements UserDao{ void eat(){System.out.println("吃饭")}};

	UserDao user = new UserDaoImpl();
	// 创建user 对象的动态代理对象
	UserDao userProxy = Proxy.newProxyInstance(user.getClass().getClassLoader(),user.getClass().getInterfaces(), new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println(method.getName());
						return method.invoke(user, args);  // 调用接口中本身的方法
			}
		});

	userProxy.eat();  该代理对象调用所有的方法，本质上都是去调用上面重写的 invoke()方法
		输出结果:	eat 吃饭




