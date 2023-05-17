# Spring5 第四天

## 第一章 Spring中的 JdbcTemplate

### 1.1 JdbcTemplate概述

它是 spring 框架中提供的一个对象，是对原始 Jdbc API 对象的简单封装。spring 框架为我们提供了很多的操作模板类。

操作关系型数据的：

- JdbcTemplate
- HibernateTemplate

操作 nosql 数据库的：

- RedisTemplate

操作消息队列的：

- JmsTemplate

### 1.2 JdbcTemplate 对象的创建

我们可以参考它的源码，来一探究竟：

```java
public JdbcTemplate() {
}

public JdbcTemplate(DataSource dataSource) {
		setDataSource(dataSource);
		afterPropertiesSet();
}

public JdbcTemplate(DataSource dataSource, boolean lazyInit) {
		setDataSource(dataSource);
		setLazyInit(lazyInit);
		afterPropertiesSet();
}
```

除了默认构造函数之外，都需要提供一个数据源。既然有set方法，依据我们之前学过的依赖注入，我们可以在配置文件中配置这些对象。

### 1.3 Spring中配置数据源

#### 搭建环境

导入HikariCP数据源的maven坐标

```xml
<!-- HikariCP连接池 -->
<dependency>
  <groupId>com.zaxxer</groupId>
  <artifactId>HikariCP</artifactId>
  <version>5.0.1</version>
</dependency>
```

#### 编写Spring的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
  
</beans>
```

#### 配置数据源

```xml
<!-- 配置dataSource -->
<bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
  <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
  <property name="jdbcUrl" value="jdbc:mysql://IP地址:端口号/数据库名称?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;autoReconnect=true"/>
  <property name="username" value="账户名"/>
  <property name="password" value="密码"/>
</bean>
```

#### 将数据库连接的信息配置到属性文件中

在resources目录下创建database目录，然后再创建mysql.properties文件

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://IP地址:端口号/数据库名称?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=账户名
jdbc.password=密码
```

Spring配置文件引入外部properties文件的两种方式

方式一：

```xml
<!-- 加载properties文件内容-->
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
  <property name="location" value="classpath:database/mysql.properties"/>
</bean>
```

方式二：

```xml
<!--加载properties文件-->
<context:property-placeholder location="classpath:database/mysql.properties"/>
```

### 1.4 JdbcTemplate 的增删改查操作

#### 前期准备

准备数据库脚本

```sql
-- 创建 spring5_super 数据库
create
database `spring5_super` character set utf8mb4 collate utf8mb4_general_ci;

-- 使用 spring5_super 数据库
use
`spring5_super`;

-- 创建表 `tb_account`
create table `tb_account`
(
    id    bigint auto_increment comment '主键',
    name  varchar(20) comment '姓名',
    money double comment '账户金额',
    primary key (id)
) engine=InnoDB character set utf8mb4 comment '账户表';

-- 给 `tb_account` 添加数据
insert into `tb_account`(id, name, money)
values (default, '张三', 1000),
       (default, '李四', 1000),
       (default, '王五', 1000);
```

导入maven依赖坐标

```xml
<!--spring-context-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.3.19</version>
</dependency>
<!--spring-jdbc-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-jdbc</artifactId>
  <version>5.3.19</version>
</dependency>
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.29</version>
</dependency>
<!--spring-tx-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-tx</artifactId>
  <version>5.3.19</version>
</dependency>
<!--lombok-->
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.24</version>
</dependency>
<!-- HikariCP连接池 -->
<dependency>
  <groupId>com.zaxxer</groupId>
  <artifactId>HikariCP</artifactId>
  <version>5.0.1</version>
</dependency>
<!--junit-->
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.13.2</version>
  <scope>test</scope>
</dependency>
<!--spring-test-->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>5.3.19</version>
</dependency>
```

#### 在Spring配置文件中配置JdbcTemplate

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!--配置JdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>


    <!-- 加载properties文件内容
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:database/mysql.properties"/>
    </bean>-->

    <!--加载properties文件-->
    <context:property-placeholder location="classpath:database/mysql.properties"/>

    <!--配置数据源-->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```

#### 创建实体

```java
// 账户实体
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
```

#### 保存操作

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void saveTest() {
        jdbcTemplate.execute("insert into tb_account(name, money) values ('测试用户', 3000)");
    }
}
```

#### 修改操作

```java
@Test
public void updateTest() {
  jdbcTemplate.update("update tb_account set name=?, money=? where id=?", "Matrix", 2000, 4L);
}
```

#### 删除操作

```java
@Test
public void deleteTest() {
  jdbcTemplate.update("delete from tb_account where id = ?", 4);
}
```

#### 查询所有

```java
@Test
public void finAllTest() {
  List<Account> accounts = jdbcTemplate.query("select * from tb_account", new AccountRowMapper(), null);
  accounts.forEach(System.out::println);
}

/**
  * 定义Account的封装策略
  */
public class AccountRowMapper implements RowMapper<Account> {
  @Override
  public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
    Account account = new Account();
    account.setId(rs.getLong("id"));
    account.setName(rs.getString("name"));
    account.setMoney(rs.getDouble("money"));
    return account;
  }
}
```

#### 查询单条数据

```java
@Test
public void findByIdTest() {
  List<Account> accounts = jdbcTemplate.query("select * from tb_account where id = ?", new AccountRowMapper(), 1);
  System.out.println(accounts.isEmpty()?"没有数据":accounts.get(0));
}

/**
  * 定义Account的封装策略
  */
public class AccountRowMapper implements RowMapper<Account> {
  @Override
  public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
    Account account = new Account();
    account.setId(rs.getLong("id"));
    account.setName(rs.getString("name"));
    account.setMoney(rs.getDouble("money"));
    return account;
  }
}
```

#### 查询一行一列数据

```java
@Test
public void countTest() {
  Long count = jdbcTemplate.queryForObject("select count(id) from tb_account where money > ?", Long.class, 1000);
  System.out.println(count);
}
```

### 1.5 在持久层中使用JdbcTemplate

#### 方式一：在持久层接口中定义JdbcTemplate

定义实体类的封装策略

```java
// 定义Account的封装策略
public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setMoney(rs.getDouble("money"));
        return account;
    }
}
```

持久层接口

```java
// 账户持久层
public interface IAccountDao {

    /**
     *  根据 id 查询账户信息
     * @param id 账户id
     * @return 账户信息
     */
    Account selectAccountById(Long id);

    /**
     * 根据 name 查询账户信息
     * @param name 账户名称
     * @return 账户信息
     */
    Account selectAccountByName(String name);
}
```

持久层接口实现类

```java
public class AccountDaoImpl implements IAccountDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account selectAccountById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account where id = ?", new AccountRowMapper(), id);
        if (accounts.isEmpty()) {
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account selectAccountByName(String name) {
        List<Account> accounts = jdbcTemplate.query("select * from tb_account where name = ?", new AccountRowMapper(), name);
        if (accounts.isEmpty()) {
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }
}
```

修改Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    
    <!-- 配置accountDao -->
    <bean id="accountDao" class="com.matrix.study.dao.impl.AccountDaoImpl">
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>

    <!--配置JdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>
    </bean>


    <!-- 加载properties文件内容
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="location" value="classpath:database/mysql.properties"/>
    </bean>-->

    <!--加载properties文件-->
    <context:property-placeholder location="classpath:database/mysql.properties"/>

    <!--配置数据源-->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>
</beans>
```

单元测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class JdbcTemplateTest {

    @Autowired
    private IAccountDao accountDao;
  
  	@Test
    public void selectAccountByIdTest() {
        Account account = accountDao.selectAccountById(1L);
        System.out.println(account);
    }

    @Test
    public void selectAccountByName() {
        Account account = accountDao.selectAccountByName("李四");
        System.out.println(account);
    }
}
```

#### 方式二：让持久层接口继承JdbcDaoSupport类

JdbcDaoSupport 是 spring 框架为我们提供的一个类，该类中定义了一个 JdbcTemplate 对象，我们可以直接获取使用，但是要想创建该对象，需要为其提供一个数据源：具体源码如下：

```java
public abstract class JdbcDaoSupport extends DaoSupport {

	@Nullable
	private JdbcTemplate jdbcTemplate;


	/**
	 * Set the JDBC DataSource to be used by this DAO.
	 */
	public final void setDataSource(DataSource dataSource) {
		if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
			this.jdbcTemplate = createJdbcTemplate(dataSource);
			initTemplateConfig();
		}
	}

	/**
	 * Create a JdbcTemplate for the given DataSource.
	 * Only invoked if populating the DAO with a DataSource reference!
	 * <p>Can be overridden in subclasses to provide a JdbcTemplate instance
	 * with different configuration, or a custom JdbcTemplate subclass.
	 * @param dataSource the JDBC DataSource to create a JdbcTemplate for
	 * @return the new JdbcTemplate instance
	 * @see #setDataSource
	 */
	protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	/**
	 * Return the JDBC DataSource used by this DAO.
	 */
	@Nullable
	public final DataSource getDataSource() {
		return (this.jdbcTemplate != null ? this.jdbcTemplate.getDataSource() : null);
	}

	/**
	 * Set the JdbcTemplate for this DAO explicitly,
	 * as an alternative to specifying a DataSource.
	 */
	public final void setJdbcTemplate(@Nullable JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		initTemplateConfig();
	}

	/**
	 * Return the JdbcTemplate for this DAO,
	 * pre-initialized with the DataSource or set explicitly.
	 */
	@Nullable
	public final JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	/**
	 * Initialize the template-based configuration of this DAO.
	 * Called after a new JdbcTemplate has been set, either directly
	 * or through a DataSource.
	 * <p>This implementation is empty. Subclasses may override this
	 * to configure further objects based on the JdbcTemplate.
	 * @see #getJdbcTemplate()
	 */
	protected void initTemplateConfig() {
	}

	@Override
	protected void checkDaoConfig() {
		if (this.jdbcTemplate == null) {
			throw new IllegalArgumentException("'dataSource' or 'jdbcTemplate' is required");
		}
	}


	/**
	 * Return the SQLExceptionTranslator of this DAO's JdbcTemplate,
	 * for translating SQLExceptions in custom JDBC access code.
	 * @see org.springframework.jdbc.core.JdbcTemplate#getExceptionTranslator()
	 */
	protected final SQLExceptionTranslator getExceptionTranslator() {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		Assert.state(jdbcTemplate != null, "No JdbcTemplate set");
		return jdbcTemplate.getExceptionTranslator();
	}

	/**
	 * Get a JDBC Connection, either from the current transaction or a new one.
	 * @return the JDBC Connection
	 * @throws CannotGetJdbcConnectionException if the attempt to get a Connection failed
	 * @see org.springframework.jdbc.datasource.DataSourceUtils#getConnection(javax.sql.DataSource)
	 */
	protected final Connection getConnection() throws CannotGetJdbcConnectionException {
		DataSource dataSource = getDataSource();
		Assert.state(dataSource != null, "No DataSource set");
		return DataSourceUtils.getConnection(dataSource);
	}

	/**
	 * Close the given JDBC Connection, created via this DAO's DataSource,
	 * if it isn't bound to the thread.
	 * @param con the Connection to close
	 * @see org.springframework.jdbc.datasource.DataSourceUtils#releaseConnection
	 */
	protected final void releaseConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, getDataSource());
	}

}
```

创建AccountDaoImpl2类

```java
public class AccountDaoImpl2 extends JdbcDaoSupport implements IAccountDao {

    @Override
    public Account selectAccountById(Long id) {
        List<Account> accounts = getJdbcTemplate().query("select * from tb_account where id = ?", new AccountRowMapper(), id);
        if(accounts.isEmpty()){
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account selectAccountByName(String name) {
        List<Account> accounts = getJdbcTemplate().query("select * from tb_account where name = ?", new AccountRowMapper(), name);
        if(accounts.isEmpty()){
            throw new RuntimeException("数据不存在");
        } else {
            return accounts.get(0);
        }
    }
}
```

单元测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class JdbcTemplateTest {

    @Autowired
    private IAccountDao accountDao;
  
  	@Test
    public void selectAccountByIdTest() {
        Account account = accountDao.selectAccountById(1L);
        System.out.println(account);
    }

    @Test
    public void selectAccountByName() {
        Account account = accountDao.selectAccountByName("李四");
        System.out.println(account);
    }
}
```

---

## 第二章 Spring中的事务控制

### 2.1 Spring事务控制我们需要明确的

第一：JavaEE 体系进行分层开发，事务处理位于业务层，Spring 提供了分层设计**业务层**的事务处理解决方案。

第二：spring 框架为我们提供了一组事务控制的接口。具体在后面的第二小节介绍。这组接口是在spring-tx依赖中。

第三：spring 的事务控制都是基于 AOP 的，它既可以使用编程的方式实现，也可以使用配置的方式实现。

### 2.2 Spring事务控制的API介绍

#### PlatformTransactionManager

此接口是 spring 的事务管理器，它里面提供了我们常用的操作事务的方法如下：

```java
public interface PlatformTransactionManager extends TransactionManager {
  
  // 获取事务
	TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
			throws TransactionException;
  
  // 提交事务
	void commit(TransactionStatus status) throws TransactionException;
  
  // 回滚事务
	void rollback(TransactionStatus status) throws TransactionException;

}
```

我们在开发中都是使用它的实现类，如下：

```java
// 使用 Spring JDBC 或 iBatis 进行持久化数据时使用
org.springframework.jdbc.datasource.DataSourceTransactionManager
  
// 使用Hibernate 版本进行持久化数据时使用
org.springframework.orm.hibernate5.HibernateTransactionManager
```

#### TransactionDefinition

TransactionDefinition：它是事务的定义信息对象，里面的方法有

```java
// 获取事务对象名称
default String getName()
  
// 获取事务的隔离级别
default int getIsolationLevel()
  
// 获取事务的传播行为
default int getPropagationBehavior()

// 获取事务超时时间
int getTimeout()
  
// 获取事务是否只读（读写型事务：增删改，只读事务：执行查询时，也会开启事务）
default boolean isReadOnly()
```

### 2.3 事务概述

事务（Transaction）是关系型数据库中，由一组 SQL 组成的一个执行单元，该单元要么整体执行成功，要么整体执行失败。 

![image-20220511163911606](assets/image-20220511163911606.png)

#### 事务的ACID特性

- 原子性：指事务包含的所有操作SQL，**要么都执行成功，要么都执行失败**。

![image-20220511165125939](assets/image-20220511165125939.png)

- 一致性：指事务前后**数据的完整性**必须保持一致。 

![image-20220511170020211](assets/image-20220511170020211.png)

- 隔离性：一个事务的执行不能被其他事务干扰。即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。

- 持久性：指一个事务一旦被提交，那么数据就**永久的存储在系统磁盘**中，即使系统发生故障，数据仍然不会丢失。

#### 事务之间没有隔离会出现什么糟糕的情况

##### 1. 第一个严重问题：**`脏读`**

- 指一个事务处理过程中读取了另一个未提交（即回滚）的事务的数据。

![脏读](assets/脏读.JPG)

- **如何解决脏读呢？** 
  - 如果AB这2个动作是操作的**同一个数据库**，那么可以把AB的动作`放在同一个事务`中，C就不会出现脏读。 
  - 如果AB操作的是**不同的数据库**，即所谓的微服务架构中多服务对应多个业务库，那只能引入`分布式事务`来解决。

##### 2. 第二个严重问题：**`不可重复读`**

- 指多次查询却返回了不同的数据值，这是由于查询隔离原因，被另一个事务修改并提交了。

![不可重复读](assets/不可重复读.JPG)

- **如何解决不可重复读呢？** 
  - 在数据库读取数据时**加锁**，类似 "select * from t_xxx where … for update" 这样的**排它锁**，明确数据读取后是为了更新操作，所以加了一把行级锁，防止别人修改这些数据。
- **脏读** 和 **不可重复读** 区别在哪？ 
  - 脏读：读到的数据是前一个事务**`未提交`**的数据。
  - 不可重复读：读到的是前一个事务**`已提交`**的数据。

##### 3. 第三个严重问题：**`幻读`**

- 指当A事务在读取`某个范围内`的数据时，B事务又在该范围内新插入了数据记录，此时A事务再次读取`该范围内`的数据时，就会产生幻读。

![幻读](assets/幻读.JPG)

- **幻读** 和 **不可重复读** 区别在哪？ 
  - 相同点：幻读和不可重复读都是读取了**另一个事务已提交**的数据。
  - 不同点：幻读是针对**一批数据**。不可重复读是针对的**同一条数据**。

### 2.4 事务的隔离级别

事务隔离级别反映事务提交并发访问的处理态度

- ISOLATION_DEFAULT：默认级别，归属下列的某一种。
- ISOLATION_READ_UNCOMMITTED：可以读取未提交的数据。
- ISOLATION_READ_COMMITTED：只能读取已经提交的数据，解决了脏读的问题（Oracle数据库默认的级别）
- ISOLATION_REPEATABLE_READ：是否读取其他事务提交修改后的数据，解决了不可重复读的问题（MySQL数据库默认的级别）
- ISOLATION_SERIALIZABLE：是否读取其他事务提交添加后的数据，解决了幻读的问题。

### 2.5 Spring事务的传播行为

REQUIRED:如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。一般的选择（默认值）

SUPPORTS:支持当前事务，如果当前没有事务，就以非事务方式执行（没有事务）

MANDATORY：使用当前的事务，如果当前没有事务，就抛出异常

REQUERS_NEW:新建事务，如果当前在事务中，把当前事务挂起。

NOT_SUPPORTED:以非事务方式执行操作，如果当前存在事务，就把当前事务挂起

NEVER:以非事务方式运行，如果当前存在事务，抛出异常

NESTED:如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行 REQUIRED 类似的操作。

### 2.6 事务超时时间

```text
默认值是-1，没有超时限制，如果有，以秒为单位进行设置。
```

### 2.7 TransactionStatus

TransactionStatus接口的实现类DefaultTransactionStatus类

此类提供的是事务具体的运行状态，方法如下介绍：

```java
/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.transaction.test;

package org.springframework.transaction.support;

import org.springframework.lang.Nullable;
import org.springframework.transaction.NestedTransactionNotSupportedException;
import org.springframework.transaction.SavepointManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.AbstractTransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.transaction.support.SmartTransactionObject;
import org.springframework.util.Assert;

/**
 * 管理事务状态的实现。
 *
 * 该类是{@link org.springframework.transaction.TransactionStatus}接口的默认实现，
 * 由{@link AbstractPlatformTransactionManager}使用。
 * 基于底层“事务对象”的概念。
 *
 * 包含{@link AbstractPlatformTransactionManager}内部需要的所有状态信息，
 * 包括由具体事务管理器实现确定的通用事务对象。
 *
 * 支持将与保存点相关的方法委托给实现{@link SavepointManager}接口的事务对象。
 *
 * 注意:这并不打算与其他的PlatformTransactionManager实现一起使用，
 * 特别是对于测试环境中的模拟事务管理器。
 * 使用另一个{@link SimpleTransactionStatus}类或普通{@link org.springframework.transaction.TransactionStatus}接口。
 *
 * @see AbstractPlatformTransactionManager
 * @see SavepointManager
 * @see #getTransaction
 * @see #createSavepoint
 * @see #rollbackToSavepoint
 * @see #releaseSavepoint
 * @see SimpleTransactionStatus
 */
public class DefaultTransactionStatus extends AbstractTransactionStatus {

	@Nullable
	private final Object transaction;

	private final boolean newTransaction;

	private final boolean newSynchronization;

	private final boolean readOnly;

	private final boolean debug;

	@Nullable
	private final Object suspendedResources;


	/**
	 * 创建一个新的{@code DefaultTransactionStatus}实例。
	 *
	 * @param transaction 可以保存内部事务实现状态的基础事务对象
	 * @param newTransaction 如果事务是新的，则参与现有事务
	 * @param newSynchronization 如果为给定的事务打开了新的事务同步
	 * @param readOnly 事务是否标记为只读
	 * @param debug 应该启用调试日志来处理这个事务吗?
	 *                 在这里缓存它可以防止重复调用日志记录系统，询问是否应该启用调试日志记录。
	 * @param suspendedResources 资源的持有者，该资源已被暂停，如果有的话
	 */
	public DefaultTransactionStatus(
			@Nullable Object transaction, boolean newTransaction, boolean newSynchronization,
			boolean readOnly, boolean debug, @Nullable Object suspendedResources) {

		this.transaction = transaction;
		this.newTransaction = newTransaction;
		this.newSynchronization = newSynchronization;
		this.readOnly = readOnly;
		this.debug = debug;
		this.suspendedResources = suspendedResources;
	}


	/**
	 * 返回底层事务对象。
	 * @throws IllegalStateException 如果没有活动的事务
	 */
	public Object getTransaction() {
		Assert.state(this.transaction != null, "No transaction active");
		return this.transaction;
	}

	/**
	 * 返回是否有实际活动的事务。
	 */
	public boolean hasTransaction() {
		return (this.transaction != null);
	}

  /**
	 * 判断是否是一个新事务。
	 */
	@Override
	public boolean isNewTransaction() {
		return (hasTransaction() && this.newTransaction);
	}

	/**
	 * 返回为该事务是否打开了新的事务同步。
	 */
	public boolean isNewSynchronization() {
		return this.newSynchronization;
	}

	/**
	 * 返回此事务是否定义为只读事务
	 */
	public boolean isReadOnly() {
		return this.readOnly;
	}

	/**
	 * 返回该事务的进程是否被调试。
	 * 这被{@link AbstractPlatformTransactionManager}用作优化，
	 * 为了防止重复调用{@code logger.isDebugEnabled()}。
	 * 不适合客户端代码。
	 */
	public boolean isDebug() {
		return this.debug;
	}

	/**
	 * 返回此事务中已挂起的资源的持有者(如果有)。
	 */
	@Nullable
	public Object getSuspendedResources() {
		return this.suspendedResources;
	}


	//---------------------------------------------------------------------
	// 通过底层事务对象启用功能
	//---------------------------------------------------------------------

	/**
	 * 如果事务对象实现{@link SmartTransactionObject}接口，则通过检查事务对象来确定仅回滚标志。
	 *
	 * 如果全局事务本身仅由事务协调器标记为rollback，则返回{@code true}，例如在超时的情况下。
	 *
	 * @see SmartTransactionObject#isRollbackOnly()
	 */
	@Override
	public boolean isGlobalRollbackOnly() {
		return ((this.transaction instanceof SmartTransactionObject) &&
				((SmartTransactionObject) this.transaction).isRollbackOnly());
	}

	/**
	 * 将刷新委托给事务对象，前提是后者实现{@link SmartTransactionObject}接口。
	 *
	 * @see SmartTransactionObject#flush()
	 */
	@Override
	public void flush() {
		if (this.transaction instanceof SmartTransactionObject) {
			((SmartTransactionObject) this.transaction).flush();
		}
	}

	/**
	 * 这个实现公开了底层事务对象的{@link SavepointManager}接口(如果有的话)。
	 * @throws NestedTransactionNotSupportedException 如果不支持保存点
	 * @see #isTransactionSavepointManager()
	 */
	@Override
	protected SavepointManager getSavepointManager() {
		Object transaction = this.transaction;
		if (!(transaction instanceof SavepointManager)) {
			throw new NestedTransactionNotSupportedException(
					"Transaction object [" + this.transaction + "] does not support savepoints");
		}
		return (SavepointManager) transaction;
	}

	/**
	 * 返回基础事务是否实现{@link SavepointManager}接口，因此是否支持保存点。
	 * @see #getTransaction()
	 * @see #getSavepointManager()
	 */
	public boolean isTransactionSavepointManager() {
		return (this.transaction instanceof SavepointManager);
	}

}
```

### 2.8 基于XML的声明式事务控制（配置的方式）

> spring中基于XML的声明式事务配置
>
> ​        1、配置事务管理器
>
> ​        2、配置事务的通知
>
> ​            需要导入xml对于事务的约束 xmlns:tx的命名空间的约束
>
> ​            使用tx:advice标签配置事务通知
>
> ​                属性：
>
> ​                    id：给事务通知起一个唯一标识
>
> ​                    transaction-manager：给事务通知提供一个事务管理器的bean引用
>
> ​        3、配置aop中的通用切入点表达式
>
> ​        4、建立事务通知和切入点表达式的对应关系
>
> ​        5、配置事务的属性
>
> ​            是在事务的通知tx:advice标签内的内部配置

业务层接口

```java
public interface IAccountService {

    /**
     * 根据账户id获取账户信息
     * @param id 账户id
     * @return
     */
    Account findByAccountById(Long id);

    /**
     * 转账
     *
     * @param sourceName 转出账户
     * @param targetName 转入账户
     * @param money 金额
     */
    void transfer(String sourceName, String targetName, Double money);
}
```

实体类

```java
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
```

封装实体策略

```java
public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setMoney(rs.getDouble("money"));
        return account;
    }
}
```

业务层接口实现类

```java
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account findByAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public void transfer(String sourceName, String targetName, Double money) {
        // 1.根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        // 2.根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        // 3.转出账户减钱
        source.setMoney(source.getMoney() - money);
        // 4.转入账户加钱
        target.setMoney(target.getMoney() + money);
        // 5.更新转出账户
        accountDao.updateAccount(source);
        // 模拟异常
        int ext = 10 / 0;
        // 6.更新转入账户
        accountDao.updateAccount(target);
    }
}
```

持久层接口

```java
public interface IAccountDao {

    /**
     * 根据账户id获取账户信息
     * @param id 账户ID
     * @return 账户信息Account
     */
    Account findAccountById(Long id);

    /**
     * 根据名称获取账户信息
     *
     * @param accountName 账户名称
     * @return 账户信息Account
     */
    Account findAccountByName(String accountName);

    /**
     * 修改账户信息
     *
     * @param account 账户对象
     */
    void updateAccount(Account account);
}
```

持久层接口实现类

```java
public class AccountDaoImpl implements IAccountDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account findAccountById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where id = ?", new AccountRowMapper(), id);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where name = ?", new AccountRowMapper(), accountName);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public void updateAccount(Account account) {
        jdbcTemplate.update("update tb_account set name = ?, money = ? where  id = ?", account.getName(), account.getMoney(), account.getId());
    }
}
```

开始配置Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--配置accountService-->
    <bean id="accountService" class="com.matrix.study.service.impl.AccountServiceImpl">
        <!--set方式注入accountDao-->
        <property name="accountDao" ref="accountDao"/>
    </bean>

    <!--配置accountDao-->
    <bean id="accountDao" class="com.matrix.study.dao.impl.AccountDaoImpl">
        <!--set方式注入jdbcTemplate-->
        <property name="jdbcTemplate" ref="jdbcTemplate"/>
    </bean>

    <!--配置jdbcTemplate-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <!--set方式注入dataSource-->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置dataSource -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://localhost:13306/spring5_super?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;autoReconnect=true"/>
        <property name="username" value="root"/>
        <property name="password" value="cjw123456"/>
    </bean>

    <!--spring中基于XML的声明式事务配置
        1、配置事务管理器
        2、配置事务的通知
            需要导入xml对于事务的约束 xmlns:tx的命名空间的约束
            使用tx:advice标签配置事务通知
                属性：
                    id：给事务通知起一个唯一标识
                    transaction-manager：给事务通知提供一个事务管理器的bean引用
        3、配置aop中的通用切入点表达式
        4、建立事务通知和切入点表达式的对应关系
        5、配置事务的属性
            是在事务的通知tx:advice标签内的内部配置的tx:attributes标签的内部
    -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--配置事务通知-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--配置事务的属性
         isolation：用于指定事务的隔离级别。默认值是DEFAULT，表示使用数据库的默认隔离级别
         propagation：用于指定事务的传播行为。默认值是REQUIRED，表示一定会有事务，增删改的选择。查询方法可以选择SUPPORTS。
         read-only：用于指定事务是否只读。只有查询方法才能设置为true。默认值是false，表示读写。
         rollback-for：用于指定一个异常，当产生该异常是，事务回滚，产生其他异常时，事务不回滚。没有默认值。表示任何异常都可以回滚。
         no-rollback-for：用于指定一个异常，当产生该异常是，事务不回滚，产生其他异常时事务回滚。没有默认值。表示任何异常都可以回滚。
         timeout：用于指定事务的超时时间。默认值是-1，表示永不超时。如果指定了树枝，以秒为单位。
        -->
        <tx:attributes>
            <!--*表示所有-->
            <tx:method name="*" propagation="REQUIRED" read-only="false"/>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
        </tx:attributes>
    </tx:advice>

    <!--配置AOP-->
    <aop:config>
        <!--配置切入点表达式-->
        <aop:pointcut id="pt" expression="execution(* com.matrix.study.service.impl.*.*(..))"/>
        <!--建立切入点表达式和事务通知的对应关系-->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pt"/>
    </aop:config>
</beans>
```

测试类

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-beans.xml")
public class SpringTest {

    @Autowired
    private IAccountService accountService;

    @Test
    public void transfer() {
        accountService.transfer("张三", "李四", 500.0);
    }
}
```

运行单元测试transfer方法后，模拟转账时出现异常，然后观察数据库的事务是否回滚。

### 2.9 基于注解的声明式事务控制（配置的方式）

在resources目录下创建spring目录，然后再在spring目录下mysql.properties配置文件

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://IP地址:端口/数据库?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=用户名
jdbc.password=密码
```

HikariDataSource配置类

```java
@PropertySource(value = {"classpath:database/mysql.properties"})
public class HikariDataSourceConfiguration {

    @Value(value = "${jdbc.driver}")
    private String driverClassName;

    @Value(value = "${jdbc.url}")
    private String url;

    @Value(value = "${jdbc.username}")
    private String username;

    @Value(value = "${jdbc.password}")
    private String password;

    @Bean(name = "hikariDataSource")
    @Scope(value = "singleton")
    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }
}
```

JdbcTemplate配置类

```java
// JdbcTemplate配置类
@Configuration
@Import({HikariDataSourceConfiguration.class})
public class JdbcTemplateConfiguration {

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }
}
```

创建事务管理配置类TransactionManagementConfiguration

```java
// 用于配置事务管理器
@Configuration
@EnableTransactionManagement // 开启事务支持
@Import({HikariDataSourceConfiguration.class})
public class TransactionManagementConfiguration {

    @Autowired
    @Qualifier(value = "hikariDataSource")
    private HikariDataSource hikariDataSource;

    @Bean("transactionManager")
    public DataSourceTransactionManager getTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(hikariDataSource);
        return transactionManager;
    }
}
```

创建扫描类用于扫描包创建SpringIOC容器

```java
// 配置类用于扫描创建SpringIOC容器
@Configuration
@EnableAspectJAutoProxy // 开启AOP支持
@ComponentScan(basePackages = {"com.matrix.study"}) // 包扫描
public class ScanConfiguration {

}
```

创建实体

```java
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
```

创建用于查询结果集封装策略的AccountRowMapper类

```java
public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setMoney(rs.getDouble("money"));
        return account;
    }
}
```

业务层接口

```java
public interface IAccountService {

    /**
     * 根据账户id获取账户信息
     * @param id 账户id
     * @return
     */
    Account findByAccountById(Long id);

    /**
     * 转账
     *
     * @param sourceName 转出账户
     * @param targetName 转入账户
     * @param money 金额
     */
    void transfer(String sourceName, String targetName, Double money);
}
```

业务层接口实现类

```java
@Service(value = "accountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements IAccountService {

    @Autowired
    @Qualifier(value = "accountDao")
    private IAccountDao accountDao;

    @Override
    public Account findByAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void transfer(String sourceName, String targetName, Double money) {
        // 1.根据名称查询转出账户
        Account source = accountDao.findAccountByName(sourceName);
        // 2.根据名称查询转入账户
        Account target = accountDao.findAccountByName(targetName);
        // 3.转出账户减钱
        source.setMoney(source.getMoney() - money);
        // 4.转入账户加钱
        target.setMoney(target.getMoney() + money);
        // 5.更新转出账户
        accountDao.updateAccount(source);
        // 模拟异常
        int ext = 10 / 0;
        // 6.更新转入账户
        accountDao.updateAccount(target);
    }
}
```

持久层接口

```java
public interface IAccountDao {

    /**
     * 根据账户id获取账户信息
     * @param id 账户ID
     * @return 账户信息Account
     */
    Account findAccountById(Long id);

    /**
     * 根据名称获取账户信息
     *
     * @param accountName 账户名称
     * @return 账户信息Account
     */
    Account findAccountByName(String accountName);

    /**
     * 修改账户信息
     *
     * @param account 账户对象
     */
    void updateAccount(Account account);
}
```

持久层接口实现类

```java
@Repository(value = "accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    @Qualifier(value = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account findAccountById(Long id) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where id = ?", new AccountRowMapper(), id);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        List<Account> accounts = jdbcTemplate.query("select id, name, money from tb_account where name = ?", new AccountRowMapper(), accountName);
        if(accounts.isEmpty()) {
            throw new RuntimeException("id对应的数据不存在");
        } else {
            return accounts.get(0);
        }
    }

    @Override
    public void updateAccount(Account account) {
        jdbcTemplate.update("update tb_account set name = ?, money = ? where  id = ?", account.getName(), account.getMoney(), account.getId());
    }
}
```

测试类

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= ScanConfiguration.class)
public class SpringTest {

    @Autowired
    private IAccountService accountService;

    @Test
    public void transfer() {
        accountService.transfer("张三", "李四", 500.0);
    }
}
```

