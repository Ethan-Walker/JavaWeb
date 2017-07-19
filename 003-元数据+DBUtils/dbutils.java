dbutils工具
	问题:dbutils是什么，有什么作用?
		它就是一个简单的jdbc封装工具.
		使用dbutils可以简化 增删改查 操作.简化对结果集的处理操作
		要使用dbutils需要导入jar包.

	dbutils核心
		1.QueryRunner类
			它是用于执行sql语句的类。
			1.query 用于执行select
			2.update 用于执行update delete insert
			3.batch 批处理
		2.ResultSetHandler接口
			用于定义结果集的封装
			它提供九个实现类，可以进行不同的封装。
		3.DbUtils类
			它提供关于关闭资源以及事务rollback,commit操作。
			例: DbUtils.close(con);
				DbUtils.rollbackAndClose(conn);
				DbUtils.commitAndClose(conn);
	-----------------------------------------------------
	Dbutlis详解
		1.QueryRunner
			1.QueryRunner怎样获取
				1.new QueryRunner()
					如果是使用这种构造创建的QueryRunner,它的事务是手动控制.
				2.new QueryRunner(DataSource ds);
					如果是使用这种构造，它的事务是自动事务，简单说，一条sql一个事务。

			2.QueryRunner中的三个核心方法
				query
				update
				batch
				对于上述三个方法，它们提供很多重载。

				如果QueryRunner在创建时，没有传递DataSource参数，那么在使用
				query,update,batch方法时，要传递Connection参数

				如果QueryRunner在创建时，传递了Dataource参数，好么在使用
				query,update,batch方法时，不需要传递Connection参数。

			总结:
				怎样配套使用:
				1. 无参 QueryRunner()
					QueryRunner runner=new QueryRunner();
					Connection con = JDBCUtils.getConnection();
					// 由于需要传入connection对象，这里可以进行事务控制
					con.setAutoCommit(false);
					runner.query(Connection,sql,ResultSetHandler,Object... param);
					runner.update(Connection,sql,Object...param);
					runner.batch(Connection con,sql,Object[][] objs);
					DbUtils.rollback(con);

					注意这里的 Connection 对象需要自己关闭

				2. 有参 QueryRunner(DataSource ds)
					// 传递了 DataSource， DBUtils 内部会自动获取 Connecton 对象，外部代码中
					// 无法获得该Connection对象，也就无法对事务 进行控制，故一条语句即一条事务
					QueryRunner runner=new QueryRunner(DataSource ds);
					runner.query(sql,ResultSetHandler,Object... param);
					runner.update(sql,Object...param);
					runner.batch(sql,Object[][] objs);

		-----------------------------------------------------------------
		ResultSetHandler接口 ：
			用于封装结果集.

			一. 自己手动封装
			优点：灵活 缺点：麻烦

			1.	先创建一个 JavaBean，各字段名 和表中各字段名对应

			2.  创建 ResultSetHandler接口的实现类（一般用匿名内部类的方式实现）
				List<Account> accounts = qr.query(con,sql,new ResultSetHandler<List<Account>>(){
				//new ResultSetHandler<T>  传入的类型 即为方法执行返回后的类型
					@Override
					public List<Account> handle(ResultSet rs) throws SQLException {
						List<Account> accounts = new ArrayList<Account>();
						while(rs.next()){
							Account ac = new Account();
							ac.setAccount_name(rs.getString("account_name"));
							ac.setMoney(rs.getDouble("money"));
							accounts.add(ac);
						}
						return  accounts;
					}
				});

			二、使用已实现ResultSetHandler接口的实现类:例 BeanListHandler
			List<Account> accounts = qr.query(con,sql,new BeanListHandler<Account>(Account.class));
			缺点：不够灵活。优点：简单，方便
	============================================================================
	模仿QueryRunner
		1.query方法模仿
			public <T> T query(Connection con, String sql, MyResultSetHandler<T> mrs,Object... params) throws SQLException {

					PreparedStatement pst = con.prepareStatement(sql); // 得到一个预处理的Statement.
					// 问题:sql语句中可能存在参数，需要对参数赋值。

					ParameterMetaData pmd = pst.getParameterMetaData();
					// 可以得到有几个参数
					int count = pmd.getParameterCount();
					for (int i = 1; i <= count; i++) {
						pst.setObject(i, params[i - 1]);
					}

					ResultSet rs = pst.executeQuery(); // 得到了结果集，要将结果集封装成用户想要的对象，但是，工具不可能知道用户需求。

					return mrs.handle(rs);
				}
		2.update方法模仿
			public int update(Connection con, String sql, Object... params) throws SQLException {

				PreparedStatement pst = con.prepareStatement(sql); // 得到一个预处理的Statement.
				// 问题:sql语句中可能存在参数，需要对参数赋值。

				ParameterMetaData pmd = pst.getParameterMetaData();
				// 可以得到有几个参数
				int count = pmd.getParameterCount();
				for (int i = 1; i <= count; i++) {
					pst.setObject(i, params[i - 1]);
				}

				int row = pst.executeUpdate();
				// 关闭资源
				pst.close();
				return row;
			}
===============================================================================
ResulsetHandler九个实现类

			 ArrayHandler, 将结果集中第一条记录封装到Object[],数组中的每一个元素就是记录中的字段值。
			 ArrayListHandler, 将结果集中每一条记录封装到Object[],数组中的每一个元素就是记录中的字段值。在将这些数组装入到List集合。

			 BeanHandler（重点）, 将结果集中第一条记录封装到一个javaBean中。
			 BeanListHandler(重点), 将结果集中每一条记录封装到javaBean中，在将javaBean封装到List集合.

			 ColumnListHandler, 将结果集中指定列的值封装到List集合.

			 MapHandler, 将结果集中第一条记录封装到Map集合中，集合的 key就是字段名称，value就是字段值
			 MapListHandler, 将结果集中每一条记录封装到Map集合中，集合的 key就是字段名称，value就是字段值，在将这些Map封装到List集合

			 KeyedHandler,在使用指定的列的值做为一个Map集合的key,值为每一条记录的Map集合封装。
			 	Map<String,Map<String,Object>>

				map中的一项:
				name==> {name=ethan ,age=12,sex=男}

			 ScalarHandler 进行单值查询 select count(*) from account;
			 				如果是 select * from account  ，返回第一行的第一列的值

		---------------------------------------------------------
		扩展:实现BeanHandler
			使用BeanUtils实现
				Object obj = null;

				Map<String, String[]> map = new HashMap<String, String[]>();

				ResultSetMetaData md = rs.getMetaData();
				int count = md.getColumnCount();

				if (rs.next()) {
					try {
						obj = clazz.newInstance();
						for (int i = 1; i <= count; i++) {
							map.put(md.getColumnName(i),
									new String[] { rs.getString(md.getColumnName(i)) });
						}
						BeanUtils.populate(obj, map);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}

				return obj;

		// 使用 jdk 内置反射实现
		public class MyBeanHandler2<T> implements ResultSetHandler<T> {

			private Class clazz;
			public MyBeanHandler2(Class clazz){
				this.clazz = clazz;
			}

			@SuppressWarnings("unchecked")
			@Override
			public T handle(ResultSet resultset) throws SQLException {
				T t = null;
				try {
					t= (T) clazz.newInstance();
					Field[] declaredFields = clazz.getDeclaredFields();
					int column = declaredFields.length;

					if(resultset.next()){
						for(int i=1;i<=column;i++){
							declaredFields[i-1].setAccessible(true);
							declaredFields[i-1].set(t, resultset.getObject(i));
						}
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				return t;
			}

		}
