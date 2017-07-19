<!-- TOC -->

- [1. XML总结](#1-xml总结)
    - [1.1. XML简介](#11-xml简介)
    - [1.2. XML基本语法](#12-xml基本语法)
    - [1.3. XML约束](#13-xml约束)
        - [1.3.1. XML约束--DTD 约束](#131-xml约束--dtd-约束)
        - [1.3.2. XML约束--schema 约束](#132-xml约束--schema-约束)
    - [1.4. XML 解析（重点）](#14-xml-解析重点)
        - [1.4.1. 解析方式简介](#141-解析方式简介)
            - [1.4.1.1. DOM(JAXP Crimson解析器)](#1411-domjaxp-crimson解析器)
            - [1.4.1.2. SAX](#1412-sax)
            - [1.4.1.3. JDOM](#1413-jdom)
            - [1.4.1.4. DOM4J(重点)](#1414-dom4j重点)
        - [1.4.2. 解析](#142-解析)
            - [1.4.2.1. JAXP 下的 DOM 解析](#1421-jaxp-下的-dom-解析)
            - [1.4.2.2. JAXP 下的 SAX 解析](#1422-jaxp-下的-sax-解析)
        - [1.4.3. DOM4j](#143-dom4j)
        - [1.4.4. Pull解析](#144-pull解析)

<!-- /TOC -->

# 1. XML总结 #

## 1.1. XML简介 ##
XML	: 可扩展的标记语言。（和HTML非常类似的）
- 可扩展的。
- 自定义的标签。

与HTML区别： XML传输数据，HTML是显示数据。

XML的版本：	XML1.0（几乎都使用该版本） XML1.1（不向下兼容）

做什么用？=>描述有关系的数据

**应用**
1. 作为配置文件。
2. 可以在系统与系统之间进行数据的传输。
    * webserivice	soap	XML封装数据
    * json	和XML概念相似


## 1.2. XML基本语法 ##

* 文档声明（*****）
    * 写法：	<?xml version="1.0"  ?>  注意：<? 和xml之间不能有空格. xml和version、encoding、standalone 必须全部小写
    * 文档声明必须出现在xml文件的|--第一行和第一列--|(即该语句前面不能有空格，空行)的位置。

    * 属性：
        * version="1.0"				XML的版本	（必须写）
        * encoding="UTF-8"			编码集		（可选的）决定解码的方式
        * standalone="yes或者no"	代表xml的文件是否是独立的。（如果是no，不独立，可以引入外部的文件）（可选的）
            * 该属性没什么卵用，因为不写该属性，也可以引入外部的文件，即该值默认为 no 。

    * 乱码会伴随你们一生？
        * 产生的原因：保存文件时和打开文件时采用的编码不一致。
        * 解决办法：保存文件和打开文件采用的编码一致就ok。（MyEclipse不会产生乱码问题,自动转换好了）


* 元素（***）
    * 开始标签和结束标签。
        * 包含标签主体：	<abc>文本</abc>
        * 不包含标签主体：	<abc/>

    * 不能交叉嵌套
    * 只能有一个根元素（必须有，并且只能有一个）

    * 命名规范：
        * 区分大小写					错误的：<a></A>	代表两个标签
        * 不能以数字和-中划线开头		错误的：<1a>		<-a>
        * 不能以XML（Xml	XML  xml）开头	错误的：<xmlaa>
        * 不能包含空格和冒号。


* 属性（***）
    * 自定义：命名规范同上。
    * 在同一个元素上，不能有相同的属性。（*****）
    * 属性值可以使用双引号或者单引号。


* 注释（*）
    * 和HTML的注释相同
    <!-- XML的注释 -->

    * 注释不能嵌套。

* 特殊字符
                            转义字符
    * 		<				&lt;
    * 		>				&gt;
    * 		&				&amp;
    * 		单个双引号		    &quot;
    * 		单个单引号		    &apos;

**其中 < 和 & 符号必须转义，其他三个可以不用转义**

> 如何在网页上显示特定的字符串？(包含一堆特殊字符，全部转义特别麻烦)

* CDATA区
    * 把标签中的内容作为字符串。
    * 语法：
    
    ```xml
        <![CDATA[
            内容：当成字符串直接显示
        ]]>
    ```
    例: 
    ```xml
        <![CDATA[
            if(2<3&&32>8){
                "das"&&'ds';
            }
        ]]>
    ```
* PI（处理指令）（没用）
    * 替换HTML
    
## 1.3. XML约束 ##
约束的分类
* DTD
* schema

<myspring>
    <bean>hello.java</bean>
    <猫/>
</myspring>

* 格式良好的XML：遵循语法规范。
* 有效的XML：有约束。


### 1.3.1. XML约束--DTD 约束 ###

* DTD的约束
    * 快速入门
    * 快速入门的步骤：
        * 需要出现哪些标签？
        * 在DTD的文件中编写元素
            <!ELEMENT 元素名称 (元素类型)>
        * 判断元素是否是复杂还是简单元素？
            * 如果是简单元素：(#PCDATA)	代表是字符串，必须有()
            * 如果是复杂元素：(子节点1，子节点2 ...)   内部是子结点的元素名称

        * 需要在book.xml引入外部DTD的文件
            * <!DOCTYPE 根节点元素名 SYSTEM "DTD文件的地址">

    * DTD与XML文档的关联方式
        * 可以在XML的文件中直接书写DTD的代码。（经常使用）
            <!DOCTYPE 根节点 [
                DTD的代码
            ]>

        * 引入本地的DTD文件（经常使用）
            <!DOCTYPE 根节点 SYSTEM "DTD文件的地址">

        * 引入网络上的DTD文件（偶尔使用）
            <!DOCTYPE 根节点 PUBLIC "DTD文件名称" "DTD文件的地址">


    * 元素定义
        * 语法：<!ELEMENT 元素名称	元素类型>

            * (#PCDATA)		字符串，中间可以为空
            * EMPTY			空，标签内部不能包含数据，不能包括空格、换行
            * ANY			任意的，中间可以为空，可以为字符串，可以为(子元素)
            * (子元素)

            * 子元素：
                * 子元素之间的关系
                        ,		子元素出现是有顺序的
                        |		子元素只能出现一个

                * 子元素出现的次数
                        +		子元素出现1次或多次
                        *		子元素出现0次或多次
                        ?		子元素出现0次或1次

                在子元素名后,加上数量的限定

                ```xml
                <!ELEMENT MYFILE ((TITLE*, AUTHOR?, EMAIL)* | COMMENT)>

                    <MYFILE>
                        <TITLE></TITLE>
                        <AUTHOR></AUTHOR>
                        <EMAIL></EMAIL>
                        <TITLE></TITLE>
                        <AUTHOR></AUTHOR>
                        <EMAIL></EMAIL>
                        <TITLE></TITLE>
                        <AUTHOR></AUTHOR>
                        <EMAIL></EMAIL>
                    </MYFILE>
                ```
    * 属性定义(AttributeList)
    
        * 写法：	
            ```xml
                <!ATTLIST 元素名称
                    属性名称 属性类型 属性约束      和数据库中表中数据的定义 相似
                    属性名称 属性类型 属性约束
                >
            ```
        * 属性类型
            *  CDATA		字符串
            *  枚举（没有提供关键字）	(男人|女人)-->枚举可能结果视为枚举类型声明
            *  ID	代表唯一的值，不能以数字开头

        * 属性的约束

            *  #IMPLIED		可选的
            *  #REQUIRED		必须出现的

            以下两个属性，在前面定义了，即使相应的元素中没有写，也存在，相应属性是固定值/默认值
            *  #FIXED		固定值	，格式为： #FIXED "值"
            *  默认值     直接在属性类型后加上该默认值 (很少用)

            注意：一个元素内只能有一个 ID 属性，且 ID 属性后面的约束只能是
                    #REQUIRED 或者 #IMPLIED，不能是 #FIXED 或者 默认值

            例：
            ```xml
			 <!ARRLIST 书
				页数  CDATA  #REQUIRED           必须出现的
				赠送  (赠送|非赠送) #IMPLIED     可选
				编号  ID #REQUIRED               ID类型
				二维码  CDATA #FIXED "哈哈哈"    固定值
				出版国家  CDATA "中国"           默认值
			>
            ```
        * 实体定义（用的不多）
				*  <!ENTITY 别名 "值" >
				    在元素中引用 &别名;   最终在浏览器中显示的是 "值"

				*  需要在xml中引入别名，浏览器打开文件后，在引入的位置上显示值的。

			例：
				dtd 内容：
				<!DOCTYPE 书架 [
					<!ELEMENT  ...>

					<!ATTLIST ...>

					<!ENTITY haha "卧槽">
				]>

				xml 内容：
				    <书>&haha;</书>
					 这里的&haha; 在浏览器中显示为 "卧槽"
示例: student.dtd

```xml

    <?xml version="1.0" encoding="UTF-8"?>
    <!ELEMENT  班级 (学生+)>
    <!ELEMENT  学生 (姓名,年龄,身高,体重)>
    <!ELEMENT  姓名 (#PCDATA)>
    <!ELEMENT  年龄 (#PCDATA)>
    <!ELEMENT  身高 (#PCDATA)>
    <!ELEMENT  体重 (#PCDATA)>


    <!ATTLIST  班级
        编号  ID #REQUIRED
        年级  CDATA "三年级"
        学校名 CDATA  #FIXED "西北工业大学"

    >

    <!ATTLIST  学生
            编号  ID #REQUIRED
            性别  (男|女)  #REQUIRED
            癖好 CDATA  #IMPLIED
    >

```

对应的 xml文件:

```xml
<?xml version="1.0" encoding="utf-8" ?>

<班级 编号="des3" 年级="大一" 学校='西工大' xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
						 xmlns="http://www.example.org/student" 
						 xsi:schemaLocation=" http://www.example.org/student  student.xsd">
	<学生 编号="d3" 性别="女" >
		<姓名>阿娇</姓名>
		<年龄>50</年龄>
		<身高>150</身高>
		<体重>40</体重>
		
	</学生>
	
	<学生 编号="df3" 性别="男">
		<姓名>薛鹏</姓名>
		<年龄>55</年龄>
		<身高>175</身高>
		<体重>70.32</体重>
	</学生>

</班级>
```

### 1.3.2. XML约束--schema 约束 ###
* schema约束：
    * schema和DTD的对比（面试题）：
        * schema符合XML的语法结构。
        * DOM、SAX 可以很好地解析schema文档。
        * schema对名称空间支持的好。
        * schem支持更多的数据类型，自定义的数据类型。


    * 预先定义元素和属性
    * schema的后缀名是.xsd
    * 必须只能有一个根节点，名称是schema。

* 开发步骤
    * 开发schema的约束文档
    * schema文档声明
        ```
        <schema xmlns ="http://www.w3.org/2001/XMLSchema"
                targetNamespace="http://www.itcast.cn/1110"
                elementFormDefault="qualified"
        >
        ```
    * 引入W3C的名称
        * 在根节点上schema，使用属性xmlns       (全称： xml namespace)
        * xmlns="http://www.w3.org/2001/XMLSchema"

    * 起名：targetNamespace	目标名称空间（起名）
         * 值是任意的："http://www.itcast.cn/1110"       一般为域名
    *
        * elementFormDefault
            qualified（使用）：质量好的
            unqualified		：质量不好的↑
    * 在XML文档中的--“根标签”---引入自己编写的schema文档
    ```xsd
        <根标签 属性1="值1" 属性2="值2"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns="http://www.itcast.cn/1110"
                xsi:schemaLocation="http://www.itcast.cn/1110 student.xsd"
        >
    ```

1. 引入W3C名称空间，我是实例文档。注意多了个  -instance
    * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  (这里的xsi 是别名)

2. 引入自己编写的schema的文档
    * xmlns="http://www.itcast.cn/1110"          xsd文档中的 targetNamespace 内容

    * 问题：元素上不能有相同的属性名称
        * 解决：起别名		:aa
        * 技巧：在下面出现标签的概率小起别名

3. 引入自己编写的schema文档的地址
    * schemaLocation属性是W3C提供的，如果W3C名称空间要是有别名的话，先把别名写上。
        xsi:schemaLocation="名称空间   schema文件的地址"
		* 名称空间的概念
			* 编写完schema文档，起唯一的名称空间。
			* 在编写XML文档，通过xmlns来引入名称空间。
		* 当标签为简单标签，没有属性时，简单文本的类型可直接在 主标签中定义
				<element name="name" type="double"></element>
			    表示标签名为 name，标签内内容为double 类型

		* 当标签有属性，不包含子标签时，也必须设置成complexType，
			且attribute属性必须在文本类型标签extension内，
            ```xsd
			<element name="name">
				<complexType>
					<simpleContent>
						<extension base="string">
                		    <attribute name="salary" type="double" use="required"></attribute>  <!-- attribute位置-->
						</extension>
					</simpleContent>
				</complexType>
			</element>
            ```

		* 当标签有属性，且包含子标签时， 需要Sequence/choice/all标签，attribute标签必须在sequence结束标签和complexType标签之间，
           
        ```xsd
            <element name="父标签">
                <complexType>
                <sequence>
                    <element name="子标签1"></element>
                    <element name="子标签2"></element>
                    <element name="子标签3"></element>
                </sequence>
	           <attribute name="身高" ></attribute>     <!--  attribute位置-->
                </complexType>

            </element>
        ```
            
```xsd
student.xsd
<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://www.w3.org/2001/XMLSchema"
	targetNamespace="http://www.example.org/student"
	xmlns:tns="http://www.example.org/student" elementFormDefault="qualified">

    <element name="班级" >
        <complexType>
            <sequence>
                <element name="学生" maxOccurs="2">
                    <complexType>
                        <sequence>
                            <element name="姓名" type="string"></element>
                            <element name="年龄" type="int"></element>
                            <element name="身高" type="double"> </element>
                            <element name="体重" type="double"></element>
                        </sequence>
                        <attribute name="编号" type="string" use="required"> </attribute>
                        <attribute name="性别" type="string" use="required"> </attribute>
                    </complexType>
                </element>
            </sequence>
            <attribute name="编号" type="string" use="required"></attribute>
            <attribute name="年级" type="string" use= "required"></attribute>
            <attribute name="学校" type="string" ></attribute>

        </complexType>
    </element>
</schema>
```
------------------------------------------------------------------------------
student.xml

```xml
<?xml version="1.0" encoding="utf-8" ?>

<班级 编号="des3" 年级="大一" 学校='西工大'
						 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
						 xmlns="http://www.example.org/student"
						 xsi:schemaLocation=" http://www.example.org/student  student.xsd"
>
	<学生 编号="d3" 性别="女" >
		<姓名>阿娇</姓名>
		<年龄>50</年龄>
		<身高>150</身高>
		<体重>40</体重>

	</学生>

	<学生 编号="df3" 性别="男">
		<姓名>薛鹏</姓名>
		<年龄>55</年龄>
		<身高>175</身高>
		<体重>70.32</体重>
	</学生>

</班级>
```


## 1.4. XML 解析（重点） ##

### 1.4.1. 解析方式简介 ###
* 常用的有两种？DOM和SAX
* 区别：
    * DOM解析XML
        * 在内存中形成树状结构
        * 缺点：如果文档过大，容易产生内存溢出的问题。
        * 优点：方便做增删改的操作

    * SAX解析
        * 基于事件驱动，边读边解析
        * 优点：不会产生内存溢出问题。
        * 缺点：不能做增删改操作。

        
**（DOM4J在内存生成树状结构,但是很少出现内存溢出，故一般使用 DOM4J 解析 xml）**

    
#### 1.4.1.1. DOM(JAXP Crimson解析器) 

DOM是用与平台和语言无关的方式表示XML文档的官方W3C标准。DOM是以层次结构组织的节点或信息片断的集合。这个层次结构允许开发人员在树中寻找特定信息。分析该结构通常需要加载整个文档和构造层次结构，然后才能做任何工作。由于它是基于信息层次的，因而DOM被认为是基于树或基于对象的。DOM以及广义的基于树的处理具有几个优点。首先，由于树在内存中是持久的，因此可以修改它以便应用程序能对数据和结构作出更改。它还可以在任何时候在树中上下导航，而不是像SAX那样是一次性的处理。DOM使用起来也要简单得多。

#### 1.4.1.2. SAX

SAX处理的优点非常类似于流媒体的优点。分析能够立即开始，而不是等待所有的数据被处理。而且，由于应用程序只是在读取数据时检查数据，因此不需要将数据存储在内存中。这对于大型文档来说是个巨大的优点。事实上，应用程序甚至不必解析整个文档；它可以在某个条件得到满足时停止解析。一般来说，SAX还比它的替代者DOM快许多。 
 　　
 
>选择DOM还是选择SAX？ 对于需要自己编写代码来处理XML文档的开发人员来说，  选择DOM还是SAX解析模型是一个非常重要的设计决策。 DOM采用建立树形结构的方式访问XML文档，而SAX采用的事件模型。 
    
各自的优点/缺点: 
- DOM解析器把XML文档转化为一个包含其内容的树，并可以对树进行遍历。用DOM解析模型的优点是编程容易，开发人员只需要调用建树的指令，然后利用navigation APIs访问所需的树节点来完成任务。可以很容易的添加和修改树中的元素。然而由于使用DOM解析器的时候需要处理整个XML文档，所以对性能和内存的要求比较高，尤其是遇到很大的XML文件的时候。由于它的遍历能力，DOM解析器常用于XML文档需要频繁的改变的服务中。 

- SAX解析器采用了基于事件的模型，它在解析XML文档的时候可以触发一系列的事件，当发现给定的tag的时候，它可以激活一个回调方法，告诉该方法制定的标签已经找到。SAX对内存的要求通常会比较低，因为它让开发人员自己来决定所要处理的tag。特别是当开发人员只需要处理文档中所包含的部分数据时，SAX这种扩展能力得到了更好的体现。但用SAX解析器的时候编码工作会比较困难，而且很难同时访问同一个文档中的多处不同数据。

 
#### 1.4.1.3. JDOM  
JDOM的目的是成为Java特定文档模型，它简化与XML的交互并且比使用DOM实现更快。由于是第一个Java特定模型，JDOM一直得到大力推广和促进。正在考虑通过“Java规范请求JSR-102”将它最终用作“Java标准扩展”。从2000年初就已经开始了JDOM开发。

JDOM与DOM主要有两方面不同。首先，JDOM仅使用具体类而不使用接口。这在某些方面简化了API，但是也限制了灵活性。第二，API大量使用了Collections类，简化了那些已经熟悉这些类的Java开发者的使用。

JDOM文档声明其目的是“使用20%(或更少)的精力解决80%(或更多)Java/XML问题”(根据学习曲线假定为20%)。JDOM对于大多数Java/XML应用程序来说当然是有用的，并且大多数开发者发现API比DOM容易理解得多。JDOM还包括对程序行为的相当广泛检查以防止用户做任何在XML中无意义的事。然而，它仍需要您充分理解XML以便做一些超出基本的工作(或者甚至理解某些情况下的错误)。这也许是比学习DOM或JDOM接口都更有意义的工作。

JDOM自身不包含解析器。它通常使用SAX2解析器来解析和验证输入XML文档(尽管它还可以将以前构造的DOM表示作为输入)。它包含一些转换器以将JDOM表示输出成SAX2事件流、DOM模型或XML文本文档。JDOM是在Apache许可证变体下发布的开放源码。

#### 1.4.1.4. DOM4J(重点) 
             
虽然DOM4J代表了完全独立的开发结果，但最初，它是JDOM的一种智能分支。它合并了许多超出基本XML文档表示的功能，包括集成的XPath支持、XML Schema支持以及用于大文档或流化文档的基于事件的处理。它还提供了构建文档表示的选项，它通过DOM4J API和标准DOM接口具有并行访问功能。从2000下半年开始，它就一直处于开发之中。

为支持所有这些功能，DOM4J使用接口和抽象基本类方法。DOM4J大量使用了API中的Collections类，但是在许多情况下，它还提供一些替代方法以允许更好的性能或更直接的编码方法。直接好处是，虽然DOM4J付出了更复杂的API的代价，但是它提供了比JDOM大得多的灵活性。

在添加灵活性、XPath集成和对大文档处理的目标时，DOM4J的目标与JDOM是一样的：针对Java开发者的易用性和直观操作。它还致力于成为比JDOM更完整的解决方案，实现在本质上处理所有Java/XML问题的目标。在完成该目标时，它比JDOM更少强调防止不正确的应用程序行为。

DOM4J是一个非常非常优秀的Java XML API，具有性能优异、功能强大和极端易用使用的特点，同时它也是一个开放源代码的软件。如今你可以看到越来越多的Java软件都在使用DOM4J来读写XML，特别值得一提的是连Sun的JAXM也在用DOM4J。 



### 1.4.2. 解析 ###

#### 1.4.2.1. JAXP 下的 DOM 解析 ####

**解析**
1. 获取 DocumentBuilderFactory（解析器工厂类）对象
```java
    public static DocumentBuilderFactory newInstance();     获取 DocumentBuilderFactory 的新实例。
```
2. 获取 DocumentBuilder（解析器）对象
```java
    public abstract DocumentBuilder newDocumentBuilder() （虽然是抽象方法，但多态调用子类的实现方法）使用当前配置的参数创建一个新的 DocumentBuilder 实例。
```
3. 解析XML文档
```java
    public Document parse(String path)      解析XML文档，返回Document对象
```

4. 根据需求获得NodeList 集合
```java
    NodeList nodeList = document.getElementsByTagName("作者");
```
5. 遍历NodeList集合 获取每个Node内的内容

```java
    NodeList:
        Node item(int index);  根据下标获取NodeList集合中的Node对象
    Node:
        String getTextContent();  获取Node对象（标签）中的文本内容

    for(int i=0;i<nodeList.getLength();i++){
        Node node = nodeList.item(i);
        System.out.println(node.getTextContent());
    }
```
**示例：**
```java
    解析XML（Document parse(String uri) ）

    // 获取解析器工厂类
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    // 获取解析器对象
    DocumentBuilder builder = factory.newDocumentBuilder();

    // 解析XML的文档，返回document对象
    Document document = builder.parse("src/book2.xml");

    // 获取作者元素对象的集合，返回NodeList，注意返回的是NodeList(ELement的父类)而不是Element

    NodeList nodeList = document.getElementsByTagName("作者");

    // 循环遍历，拿到每一个作者，打印文本的内容，getTextContent()
    for(int i=0;i<nodeList.getLength();i++){
        Node node = nodeList.item(i);
        System.out.println(node.getTextContent());
    }
```


```java
    //获取标签的属性值
    Document doc = JaxpDomutil.getDocument("src/book1.xml");
    Node node = doc.getElementsByTagName("书").item(0);

    NamedNodeMap attMap = node.getAttributes();
            注意这里返回的是NamedNodeMap子类对象（实际上是属性集合）
    System.out.println("--------------------");
    System.out.println(attMap.getLength());
    for(int i=0;i<attMap.getLength();i++){
        Node att=attMap.item(i);                  node ---content
        System.out.println(att.getTextContent());
    }


```


```java
    //递归得到 Node 中的所有节点名称（前序遍历）
    public static void getNodeName(Node node){

        if(node.getNodeType() == Node.ELEMENT_NODE){
            System.out.println(node.getNodeName());
        }

        NodeList nodeList = node.getChildNodes();

        for(int i=0;i<nodeList.getLength();i++){
            Node child = nodeList.item(i);
            getNodeName(child);
        }
    }
```

**回写**
* 获取回写的工厂类
* 获取回写对象
* 调用回写的方法进行回写。

修改 document对象中的内容之后进行回写
```java
    // 创建回写类的工厂
    TransformerFactory transformerFactory =  TransformerFactory.newInstance();

    // 获取回写类
    Transformer transformer = transformerFactory.newTransformer();

    // 调用回写的方法
    transformer.transform(new DOMSource(document), new StreamResult("src/book2.xml"));
```

```java
//将获取Document对象和 回写封装到方法中
public class JaxpDomutil {

    public static Document getDocument(String path) throws Exception{
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = builderFactory.newDocumentBuilder();
    return builder.parse(path);
    }

    public static void writeXML(Document doc,String path) throws Exception{

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.transform(new DOMSource(doc), new StreamResult(path));

    }
    //	在指定的节点之前添加子节点
	public static void InsertBefore(Node newNode,Node refNode){
		Node parentNode = refNode.getParentNode();
		parentNode.insertBefore(newNode, refNode);
	}

}
```
#### 1.4.2.2. JAXP 下的 SAX 解析 ####
**SAX的解析原理：**

解析器采用SAX方式在解析某个XML文档时，它只要解析到XML文档的一个组成部分（边读边解析）
都会去调用事件处理器的一个方法，解析器在调用事件处理器的方法时，
会把当前解析到的xml文件内容作为方法的参数传递给事件处理器。

事件处理器由程序员编写，程序员通过事件处理器中方法的参数，
就可以很轻松地得到sax解析器解析到的数据，从而可以决定如何对数据进行处理。


**解析过程**
1. 获取解析器的工厂
```java
    SAXParserFactory saxParseFactory = SAXParserFactory.newInstance();
```
2. 获取解析器对象
```java
    SAXParser saxParser = saxParseFactory.newSAXParser();
```

3. 解析XML(XML的文件的地址，事件处理器)
```java
    saxParser.parse(path,new MyHandler());   // 其中MyHandler() 继承自DefaultHandler()
```

* 事件处理器（DefaultHandler)？
    自己编写的类MyHandler()，需要继承DefalutHandler类，重写三个方法。
    * startElement(qName)  							 开始标签名
    * characters(char[] ch,int start,int length)     标签内部文本内容
    * endElement(qName)    							 结束标签名

    通过传进来的参数qName、char[] 、qName 获取开始标签内容、文本内容、结束标签内容

### 1.4.3. DOM4j ###
* DOM4J的解析（必须会，企业中用的多）
    * 先下载DOM4J相应的jar包。导入工程中，才能使用。
    * 把dom4j-1.6.1.jar导入到工程中。
    * WEB项目：复制dom4j-1.6.1.jar到	WebRoot -- WEB-INF -- lib里面。就ok。
    * Java项目：右键工程项目，选择Build Path

**解析过程**
1. 获取解析器对象
```java
    SAXReader saxReader=new SAXReader();
```
2. 解析获取Document对象
```java
    Document doc = saxReader.read("src/book1.xml");
```
3. 获取文档根结点
```java
    Element root = doc.getRootElement();
```


**DOM4j中，获得Document对象的方式有三种：**

1. 读取XML文件,获得document对象
```java
    SAXReader reader = new SAXReader();
    Document  document = reader.read(new File("input.xml"));
```
2. 解析XML形式的文本,得到document对象.
```java
    String text = "<members></members>";
    Document document = DocumentHelper.parseText(text);
```
3. 主动创建document对象.
```java
    Document document = DocumentHelper.createDocument();  //创建根节点
    Element root = document.addElement("members");
```

**节点方法**
1. 获取文档的根节点.
```java
    Element root = document.getRootElement();
```
2. 取得某个节点的子节点.
```java
    Element element=root.element("书名");
```
3. 取得节点的文本内容
```java
    String text=node.getText();
```
4. 取得某节点下所有名为“member”的子节点，并进行遍历.
```java
    List nodes = rootElm.elements("member");         -->获取所有子元素结点

    for (Iterator it = nodes.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
            // do something
    }
```
5. 对某节点下的所有子节点进行遍历.
```java
    for(Iterator it=root.elementIterator();it.hasNext();){
            Element element = (Element) it.next();
        // do something??
    }
```
6. 在某节点下添加子节点.
```java
    Element ageElm = newMemberElm.addElement("age");
    (可通过父结点root添加
        List<Element> eles = root.elements();
        Element newEle=DocumentHelper.createElement("age");
        newEle.setText("16");
        eles.add(1,newEle);         -->插入子节点集合中的1位置
        )
```
7. 设置节点文字.
```java
    element.setText("29");
```
8. 删除某节点.
```java
    parentElm.remove(childElm);		//childElm是待删除的节点,parentElm是其父节点
```
9. 添加一个CDATA节点.
```java
    Element contentElm = infoElm.addElement("content");
    contentElm.addCDATA(diary.getContent());
```

**属性方法：**
```java
		attribute.getQualifiedName()       getValue()/getText()
                            属性名                   属性值
```

1. 取得某节点下的某属性
```java
    Element root=document.getRootElement();	 	//属性名name
    Attribute attribute=root.attribute("size");
```
2. 取得属性的文字
```java
    String text=attribute.getText();
```
3. 删除某属性
```java
    Attribute attribute=root.attribute("size");
    root.remove(attribute);
```
4. 遍历某节点的所有属性
```java
    Element root=document.getRootElement();
    方法一：推荐
    for(int i=0;i<root.attributeCount();i++){
        Attribute attribute = root.attribute(i);
        System.out.println(attribute.getQualifiedName()+"\""+attribute.getValue+"\"");
    }

    方法二：
    for(Iterator it=root.attributeIterator();it.hasNext();){
        Attribute attribute = (Attribute) it.next();
        String text=attribute.getText();
        System.out.println(text);
    }
```
5. 设置某节点的属性和文字.
```java
    newMemberElm.addAttribute("name", "sitinspring");
```
6.  设置属性的文字
```java
    Attribute attribute=root.attribute("name");    ---> 获取属性名为 name 的属性对象
    attribute.setText("sitinspring");              ---> 设置属性值
```
**将文档写入XML**
1. 文档中全为英文,不设置编码,直接写入的形式.
```java
XMLWriter writer = new XMLWriter(new  FileWriter("output.xml"));
writer.write(document);
writer.close();
```
2. 文档中含有中文,设置编码格式写入的形式.
```java
    OutputFormat format = OutputFormat.createPrettyPrint();       // 指定XML编码
    format.setEncoding("GBK");
    XMLWriter writer = new XMLWriter(newFileWriter("output.xml"),format);
    writer.write(document);
    writer.close();
```
**字符串与XML的转换**

1. 将字符串转化为XML
```java 
    String text = "<members> <member>sitinspring</member></members>";
    Document document = DocumentHelper.parseText(text);
```
2.将文档或节点的XML转化为字符串.
```java
    SAXReader reader = new SAXReader();
    Document  document = reader.read(new File("input.xml"));

    String docXmlText=document.asXML();      --> 将XML文档转换成XML格式的字符串

    Element root=document.getRootElement();
    String rootXmlText=root.asXML();		  --> 将根结点转换成XML格式的字符串
    Element memberElm=root.element("member");
    String memberXmlText=memberElm.asXML();  --> 将元素结点转换成XML格式的字符串
```
* DOM4J对XPATH的支持
    * 导入包。jaxen-1.1-beta-6.jar。
    * 怎么使用？
        selectNodes("/AAA")			返回集合
        selectSingleNode()		一个Node对象

    * 参数就是xpath的语法
        / 考虑层级关系 / / 不考虑层级关系

            /AAA/BBB			获取BBB的节点
            //AAA/BBB         A不考虑层级，B考虑层级
            / /BBB				无论层级关系，找到BBB的节点
            *					代表是所有

            /AAA/BBB[1]		找到BBB的第一个	 /AAA/BBB[last()]	最后一个
            上面这种只能用单斜线 不能用双斜线

            @					属性
            /*/*/*/BBB  代表三层标签下的 BBB标签
            
**将文档写入XML** 
1.文档中全为英文,不设置编码,直接写入的形式.
```java
    XMLWriter writer = new XMLWriter(new  FileWriter("output.xml"));
    writer.write(document);
    writer.close();
```
2.文档中含有中文,设置编码格式写入的形式.
```java
    OutputFormat format = OutputFormat.createPrettyPrint();// 指定XML编码????? ???????????? 
    format.setEncoding("GBK");
    XMLWriter writer = new XMLWriter(newFileWriter("output.xml"),format);
    writer.write(document);
    writer.close();
```
### 1.4.4. Pull解析 ###

与Sax一样.都属于事件驱动的解析方式.
相比Sax解析过程更加灵活.
sax一旦开始解析就是从头读到尾.不解析完整个文档不会停
pull解析较为灵活.是以事件为单位.手动向下继续. 如果获得到我们要找的内容. 可以停止继续解析.

缺点：只能进行查询，不能做增删改

```java
	public static List<Student> parseXML(InputStream is) throws Exception{
		//1:创建解析器工厂
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

		//2:使用工厂获得解析器
		XmlPullParser parser = factory.newPullParser();

		//3: 使用解析器读取XML流
		parser.setInput(is, "UTF-8");

		//4: 获得当前事件的状态
		int type = parser.getEventType();


		List<Student> list =null;
		Student stu = null;
		//5:判断当前事件状态
		while(type!=XmlPullParser.END_DOCUMENT){
			switch(type){
				case XmlPullParser.START_TAG:
					if("students".equals(parser.getName())){
						list = new ArrayList<Student>();

					}else if("student".equals(parser.getName())){
						stu = new Student();

					}else if("name".equals(parser.getName())){
						String name = parser.nextText();
						stu.setName(name);
					}else if("age".equals(parser.getName())){
						int age = Integer.parseInt(parser.nextText());
						stu.setAge(age);

					}else if("height".equals(parser.getName())){
						double height = Double.parseDouble(parser.nextText());
						stu.setHeight(height);
					}
					break;
				case XmlPullParser.END_TAG:
					if("student".equals(parser.getName())){
						list.add(stu);
						stu = null;
					}
					break;
				default:
					break;
			}
			//让解析器向下解析一行,并返回改行的事件常量
			type = parser.next();
		}
		return list;
	}

```

