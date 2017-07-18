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

			c3p0-config 格式
			<c3p0-config>
			  <default-config>
			    <property name="DriverClass">com.mysql.jdbc.Driver</property>
			    <property name="jdbcUrl">jdbc:mysql:///summer</property>
				<property name="user">root</property>
				<property name="password">root</property>
			  </default-config>
			</c3p0-config>
