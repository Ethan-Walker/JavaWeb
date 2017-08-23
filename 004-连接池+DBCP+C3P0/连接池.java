连接池
	 问题：连接池是什么，有什么用?

		连接池:就是创建一个容器，用于装入多个Connection对象，在使用连接对象时，从容器中获取一个Connection，
			   使用完成后，在将这个Connection重新装入到容器中。这个容器就是连接池。(DataSource)
			   也叫做数据源.

		我们可以通过连接池获取连接对象.
		优点:
			节省创建连接与释放连接 性能消耗 ---- 连接池中连接起到复用的作用 ，提高程序性能
	-----------------------------------------------------------------------------------
	自定义连接池
		1.创建一个MyDataSource类，在这个类中创建一个LinkedList<Connection>
		2.在其构造方法中初始化List集合，并向其中装入5个Connection对象。
		3.创建一个public Connection getConnection();从List集合中获取一个连接对象返回.
		4.创建一个  public void reAdd(Connection) 这个方法是将使用完成后的Connection对象重新装入到List集合中.

	代码问题:
		1.连接池的创建是有标准的.
			在javax.sql包下定义了一个接口 DataSource
			简单说，所有的连接池必须实现javax.sql.DataSource接口，
		2.要通过连接池获取连接对象  DataSource接口中有一个  getConnection方法.
		3.将Connection重新装入到连接池   使用Connection的close()方法。
			我们的自定义连接池必须实现DataSource接口。
			public class MyDataSource implements DataSource {
				LinkedList<Connection> list;

				public MyDataSource() throws SQLException {
					list = new LinkedList<>();
					for (int i = 0; i < 5; i++) {
						Connection con = JDBCUtils2.getConnection();
						list.add(con);
					}
				}

				@Override
				public Connection getConnection() throws SQLException {
					if (list.isEmpty()) {
						for (int i = 0; i < 3; i++) {
							Connection con = JDBCUtils2.getConnection();
							list.add(con);
						}
					}
					final Connection con = list.removeFirst();  // 移除并返回第一个 Connection

					/**
					 * 通过反射 对Connection中的 close 方法进行增强
					 */
					Connection proxyCon = (Connection)Proxy.newProxyInstance(con.getClass().getClassLoader(),
							con.getClass().getInterfaces(),new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if(method.getName().equals("close")){
								list.add(con);
								return null;
							}
							return method.invoke(con,args);
						}
					} );
					return proxyCon;
				}

		2.我们操作时，要使用标准，怎样可以让 con.close()它不是销毁，而是将其重新装入到连接池.

			要解决这个问题，其本质就是将Connection中的close()方法的行为改变。

			怎样可以改变一个方法的行为(对方法功能进行增强)
				1.继承
				2.装饰模式
					1.装饰类与被装饰类要实现同一个接口或继承同一个父类  (实现Connection对象)
					2.在装饰类中持有一个被装饰类引用
					3.对方法进行功能增强。
				3.动态代理
					可以对行为增强
					//Proxy.newProxyInstance(ClassLoacer ,Class[],InvocationHandler);

					final Connection con = list.removeFirst();
					Connection proxyCon = (Connection)Proxy.newProxyInstance(con.getClass().getClassLoader(),
							con.getClass().getInterfaces(),new InvocationHandler() {

						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if(method.getName().equals("close")){
								list.add(con);
								return null;
							}
							return method.invoke(con,args);
						}
					} );

					return proxyCon;

			结论:Connection 对象如果是从连接池中获取到的，那么它的close方法的行为已经改变了，不在是销毁，而是重新装入到连接池。

==================================================================================================
开源连接池
	注意获取到 Connection 对象con后,用调用 con.close()  表示将该对象放回连接池中，不要关闭 DataSource（连接池)对象

	1.dbcp(了解)
		dbcp是apache的一个开源连接池。

		要想使用DBCP连接池，要下载jar包
			导入时要导入两个
				commons-dbcp-1.4.jar
				commons-pool-1.5.6.jar

			关于dbcp连接池使用
				1.手动配置(手动编码)
					BasicDataSource bds = new BasicDataSource();

					// 需要设置连接数据库最基本四个条件
					bds.setDriverClassName("com.mysql.jdbc.Driver");
					bds.setUrl("jdbc:mysql:///day18");
					bds.setUsername("root");
					bds.setPassword("abc");

					// 得到一个Connection
					Connection con = bds.getConnection();

					con.close(); // 将Connection 对象放回 连接池中

				2.自动配置(使用配置文件)
					Properties props = new Properties();
					// FileInputStream fis = new FileInputStream("D:\\java1110\\workspace\\day18_2\\src\\dbcp.properties");
					InputStream input =this.getClass().getResourceAsStream("/dbcp.properties");  // 获取src目录下的文件
					props.load(fis);

					DataSource ds = BasicDataSourceFactory.createDataSource(props);

	2.c3p0(必会)

		C3P0是一个开源的JDBC连接池，它实现了数据源和JNDI绑定，支持JDBC3规范和JDBC2的标准扩展。
		目前使用它的开源项目有Hibernate，Spring等。
		c3p0与dbcp区别

		dbcp没有自动回收空闲连接的功能

		c3p0有自动回收空闲连接功能

		c3p0连接池使用
			1.导包
				c3p0-0.9.1.2.jar

			使用
				1.手动
					ComboPooledDataSource cpds = new ComboPooledDataSource();
					cpds.setDriverClass("com.mysql.jdbc.Driver");
					cpds.setJdbcUrl("jdbc:mysql:///day18");
					cpds.setUser("root");
					cpds.setPassword("abc");

				2.自动(使用配置文件)

					c3p0的配置文件可以是properties也可以是xml.

					c3p0的配置文件如果名称叫做 c3p0.properties or c3p0-config.xml 并且放置在classpath路径下(对于web应用就是classes目录)
					那么c3p0会自动查找。

					注意：我们其时只需要将配置文件放置在src下就可以。

					使用：
						ComboPooledDataSource cpds = new ComboPooledDataSource();
						它会在指定的目录下查找指定名称的配置文件，并将其中内容加载。

