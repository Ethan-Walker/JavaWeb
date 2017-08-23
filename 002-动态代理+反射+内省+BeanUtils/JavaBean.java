一、JavaBean简介

JavaBean是使用Java语言开发的一个可重用的组件，在JSP的开发中可以使用JavaBean减少重复代码，使整个JSP代码的开发更简洁。JSP搭配JavaBean来使用，有以下的优点：

1.可将HTML和Java代码分离，这主要是为了日后维护的方便。如果把所有的程序代码（HTML和Java）写到JSP页面中，会使整个程序代码又多又复杂，造成日后维护上的困难。

2.可利用JavaBean的优点。将日常用到的程序写成JavaBean组件，当在JSP要使用时，只要调用JavaBean组件来执行用户所要的功能，不用再重复写相同的程序，这样以来也可以节省开发所需的时间。

二、JavaBean开发要求
标准JavaBean 四个要求:
(1) 属性私有
(2) getter/setter 方法
(3) 无参构造
(4) 实现 Serializable 接口(序列化)    (在 session 中添加JavaBean， session 活化时，要求JavaBean序列化)

1.JavaBean本身就是一个类，属于Java的面向对象编程。

2.在JSP中如果要应用JSP提供的Javabean的标签来操作简单类的话，则此类必须满足如下的开发要求：

(1)所有的类必须放在一个包中，在WEB中没有包的是不存在的；

(2)所有的类必须声明为public class，这样才能够被外部所访问；

(3)类中所有的属性都必须封装，即：使用private声明；

(4)封装的属性如果需要被外部所操作，则必须编写对应的setter、getter方法；

(5)一个JavaBean中至少存在一个无参构造方法，此为JSP中的标签所使用。

第一个简单JavaBean
public class SimpleBean{
    private String name;
    private int age;
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
}
如果在一个类中只包含属性、setter、getter方法，那么这种类就成为简单JavaBean。

对于简单的JavaBean也有几个名词：

(1)VO：与简单Java对象对应，专门用于传递值的操作上

(2)POJO：简单Java对象

(3)TO：传输对象，进行远程传输时，对象所在的类必须实现java.io.Serializable接口。
