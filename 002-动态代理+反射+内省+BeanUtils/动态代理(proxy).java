1.Proxy  -- JDK提供的代理
	动态代理可实现对接口中的方法进行增强

	1.代理模式
		代理模式作用:
			屏蔽真实行为的访问，让程序更加安全。
			可以对真实行为的调用进行控制。

		通过一个案例：来说明代理的实现以及代理的作用

			代理模式实现:
				1.代理类与被代理类要实现同一个接口.
				2.在代理类中持有被代理对象.
				3.在代理类中调用被代理的行为。

**代理类只能调用接口中的共同方法，不能调用被代理类的特有方法

		AOP：面向切面的编程。
			AOP 的底层实现就是通过动态代理来做到的。

	2.动态代理
		它就是在代理模式基础上发展的，它不在是对单一的类型进行代理，
		而是可以对任意的一个实现了接口的类的对象做代理。



	3.动态代理实现
		有两种方式:
			1.通过jdk中提供的Proxy类来实现
				这种方式要求，被代理类必须实现接口。
				简单说，只能为接口做代理.
			2.通过cglib来实现。
				它不要求被代理类实现接口。

	在Java中java.lang.reflect包下提供了一个Proxy类和一个InvocationHandler接口，通过使用这个类和接口就可以生成动态代理对象。
	JDK提供的代理只能针对接口做代理。我们有更强大的代理cglib

	Proxy类中的方法创建动态代理类对象
	Proxy.newProxyInstance
		创建的代理对象是在jvm运行时动态生成的一个对象

		public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)

		Proxy类中创建动态代理对象的方法的三个参数；
			ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载。一般选择当前类的类加载器
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
	// 这里创建的 userProxy 对象实际上可理解为 UserDaoImpl 的兄弟，都实现了 UserDao 这个接口
	UserDao userProxy = Proxy.newProxyInstance(DemoProxy.class.getClassLoader(),user.getClass().getInterfaces(), new InvocationHandler(){
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println(method.getName());
						return method.invoke(user, args);  // 调用接口中本身的方法
			}
		});

	userProxy.eat();  该代理对象调用所有的方法，本质上都是去调用上面重写的 invoke()方法
		输出结果:	eat 吃饭

应用：  动态代理实现全局编码过滤(代替装饰者模式)

出错
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // HttpServletRequestWrapper 实现了 HttpServletRequest 接口
        // 这里创建的 代理对象可理解为 HttpServletRequestWrapper 的兄弟, 都实现了 HttpServletRequest 接口
        HttpServletRequest reqProxy = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("getParameter".equals(method.getName())) {
                    String param = request.getParameter((String) args[0]);
                    return new String(param.getBytes("ISO8859-1"), "UTF-8");

                }else{
                    return method.invoke(request,args);
                }
            }
        });

        chain.doFilter(reqProxy, resp);
}



