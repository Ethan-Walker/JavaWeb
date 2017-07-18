元数据
	问题:元数据是什么，有什么作用?
		元数据(metaData)  指数据库中 库、表、列的定义信息

	1.DataBaseMetaData 数据库元数据（了解）
		问题:怎样获取一个DataBaseMetaData?

			Connection接口中定义了一个方法 getMetaData();

		常用API
		•	getURL()：返回一个String类对象，代表数据库的URL。
		•	getUserName()：返回连接当前数据库管理系统的用户名。
		•	getDriverName()：返回驱动驱动程序的名称。
		• 	getDatabaseProductName(); //获取数据库名称
		•	getDatabaseProductVersion();//获取数据库版本.

		•   获得数据库、表、列、主键、外键 定义信息
			getTables
			getColumns
			getPrimaryKeys(String catalog, String schema, String table)：返回指定表主键的    结果集

			获取表中主键相关描述
				每个主键列描述都有以下列：
				TABLE_CAT String => 表类别（可为 null）
				TABLE_SCHEM String => 表模式（可为 null）
				TABLE_NAME String => 表名称
				COLUMN_NAME String => 列名称
				KEY_SEQ short => 主键中的序列号（值 1 表示主键中的第一列，值 2 表示主键中的第二列）。
				PK_NAME String => 主键的名称（可为 null）


	2.ParameterMetaData 参数元数据
		参数元数据主要用于获取:sql语句中占位符的相关信息.

		问题:怎样获取ParameterMetaData?
			在PreparedStatement中有一个方法getParameterMetaData可以获取.

		问题:怎样使用?
			PreparedStatement.getParameterMetaData()
				•	获得代表PreparedStatement元数据的ParameterMetaData对象。
					Select * from user where name=? And password=?
			ParameterMetaData对象
				•	getParameterCount()  获得指定参数的个数
				•	getParameterTypeName(int param)  获得指定参数的sql类型


			注意：在获取参数类型时会产生异常
				java.sql.SQLException: Parameter metadata not available for the given statement
			解决方案:
				在url后添加参数
				jdbc:mysql:///day18?generateSimpleParameterMetadata=true
			添加这个参数后，我们在获取，它的结果也是varchar,原因:是mysql驱动的支持问题。

	3.ResultSetMetaData 结果集元数据

		ResultSet. getMetaData()
			•	获得代表ResultSet对象元数据的ResultSetMetaData对象。
		ResultSetMetaData对象
			•	getColumnCount()
			•	返回resultset对象的列数
			•	getColumnName(int column)
			•	获得指定列的名称
			•	 getColumnTypeName(int column)
			•	获得指定列的类型

