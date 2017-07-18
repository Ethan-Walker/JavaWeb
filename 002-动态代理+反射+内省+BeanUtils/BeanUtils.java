BeanUtils工具包。
　　BeanUtils工具包：下载：http://commons.apache.org/beanutils/　注意：应用的时候还需要一个logging包 http://commons.apache.org/logging/

    BeanUtils.populate(bean, properties);  properties 为 Map<String,Object[]>  类型
    BeanUtils.getProperty(user,"userName")
    BeanUtils.setProperty(user,"age",8)
    ConvertUtils.register(new MyDateConverter(),Date.class);

　　使用BeanUtils工具包完成上面的测试代码:

    User user = new User();
　　1.获得属性的值，例如，BeanUtils.getProperty(user,"userName")，返回字符串
　　2.设置属性的值，例如，BeanUtils.setProperty(user,"age",8)，参数是字符串或基本类型自动包装。设置属性的值是字符串，获得的值也是字符串，不是基本类型。　
　　3.BeanUtils的特点：
　　　　1). 对基本数据类型的属性的操作：在WEB开发、使用中，录入和显示时，值会被转换成字符串，但底层运算用的是基本类型，这些类型转到动作由BeanUtils自动完成。
　　　　2）. 对引用数据类型的属性的操作：首先在类中必须有对象，不能是null

            例如，private Date birthday=new Date();。操作的是对象的属性而不是整个对象
            例如，  BeanUtils.setProperty(user,"birthday.time",111111);　　　
                    BeanUtils.setProperty(user, "birthday.time","111111");
                    Object obj = BeanUtils.getProperty(user, "birthday.time");
                    System.out.println(obj);

PropertyUtils类和BeanUtils不同在于，运行getProperty、setProperty操作时，没有类型转换，使用属性的原有类型或者包装类。由于age属性的数据类型是int，所以方法PropertyUtils.setProperty(userInfo, "age", "8")会爆出数据类型不匹配，无法将值赋给属性。

        把字符串转换成日期类型
        方法一:
               1. * 编写一个类，实现Converter接口。重写该方法。把字符串转换日期
                    public class MyDateConverter implements Converter {

                        @Override
                        public Object convert(Class class1, Object obj) {
                            // obj 为要被转换的参数，class 为 要转换的目标 类
                            String date = (String) obj;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date res = null;
                            try {
                                res = sdf.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return res;
                        }

                    }

                2. * 在封装数据之前进行注册。ConvertUtils.register(Converter converter, Class clazz)

                ConvertUtils.register(new MyDateConverter(),Date.class);


        方法二:
                使用实现类  DateLocaleConverter();  扩展性差
                 ConvertUtils.register(new DateLocaleConverter(), Date.class);


    BeanUtils 实现 DBUtils 中的 ResultSetHandler接口  ===>  BeanListHandler
    @Override
    public List<T> handle(ResultSet resultset) throws SQLException {

        List<T> list = new ArrayList<>();
        ResultSetMetaData rsmd = resultset.getMetaData();
        int count = rsmd.getColumnCount();

        while(resultset.next()){

            T t = (T)clazz.newInstance();
            for(int i=1;i<=count;i++){
                String propertyName= rsmd.getColumnName(i);
                Object value  = resultset.getObject(propertyName);
                //  String value = resultset.getString(propertyName);   // 数据库中所有的类型都可以通过 getString()获得
                System.out.println(propertyName+"==>"+value);
                BeanUtils.setProperty(t,propertyName, value);  //无论值是 设置Object类型还是字符串类型,都能自动转换
            }
            list.add(t);
        }
        return list;

    }
