# Spring5 第二天

## 第一章 基于注解的IOC配置

### 1.1 常用注解

```xml
<!--   -->
<bean id="" class="" scope="" init-method="" destroy-method="">
  <property name="" value="" | ref=""/>
</bean>
```

#### 用于创建Bean对象的

相当于：**\<bean id="" class="">**

##### @Component

作用：将当前类的对象存入Spring容器中。

属性：

- value：用于指定bean的id，当我们不写时，它默认值是当前类名且首字母小写。

##### @Controller、@Service、@Repository

他们三个注解都是针对 **@Component** 注解的衍生注解，他们的作用及属性都是一模一样的。

他们只不过是提供了更加明确的语义化。

- @Controller：一般用于表现层的注解。
- @Service：一般用于业务层的注解。
- @Repository：一般用于持久层的注解。

属性：

- value：用于指定bean的id，当我们不写时，它默认值是当前类名且首字母小写。

#### 用于注入数据的

相当于：**\<property name="" ref=""> \</property>**

​				**\<property name="" value=""> \</property>**

##### @Autowired

作用：自动按照类型注入。当使用注解注入属性时，set 方法可以省略。它只能注入其他 bean 类型。当有多个类型匹配时，使用要注入的对象变量名称作为 bean 的 id，在 spring 容器查找，找到了也可以注入成功。找不到就报错。

##### @Qualifier

作用：在自动按照类型注入的基础之上，再按照 Bean 的 id 注入。它在给字段注入时不能独立使用，必须和 **@Autowired** 注解一起使用；但是给方法参数注入时，可以独立使用。

属性：

- value：指定bean的id

##### @Resource

作用：直接按照 Bean 的 id 注入。它也只能注入其他 bean 类型。

属性：

- name：指定bean的id

##### @Value

作用：注入基本数据类型和 String 类型数据的。

属性：

- value：用于指定值

#### 用于改变作用范围的

相当于：**\<bean id="" class="" scope="">**

##### @Scope

作用：指定bean的作用范围。

属性：

- value：指定范围的值。【取值：singleton prototype request session globalsession】

#### 和生命周期相关的

相当于：**\<bean id="" class="" init-method="" destroyoy-method="">**

##### @PostConstruct

作用：用于指定初始化方法。

##### @PreDestroy

作用：用于指定销毁方法。

### 1.2 案例说明

#### 使用XML的配置方式

创建项目导入需要的依赖坐标

```xml
<!-- spring-context -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-context</artifactId>
  <version>5.3.19</version>
</dependency>
<!-- lombok -->
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <version>1.18.24</version>
</dependency>
<!-- mysql数据库驱动-->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.29</version>
</dependency>
<!-- dbutils -->
<dependency>
  <groupId>commons-dbutils</groupId>
  <artifactId>commons-dbutils</artifactId>
  <version>1.7</version>
</dependency>
<!-- HikariCP连接池 -->
<dependency>
  <groupId>com.zaxxer</groupId>
  <artifactId>HikariCP</artifactId>
  <version>5.0.1</version>
</dependency>
<!-- junit -->
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.13.2</version>
  <scope>test</scope>
</dependency>
```

实体类

```java
// 账户实体类
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = -3564169976835948442L;

    private Long id;

    private String name;

    private Double money;
}
```

业务层

```java
// 账户业务层接口
public interface IAccountService {

    /**
     * 查询所有账户数据
     *
     * @return 所有的账户数据
     */
    List<Account> findAllAccount();

    /**
     * 根据账户id获取账户信息
     *
     * @param id 账户id
     * @return 账户信息
     */
    Account findAccountById(Long id);

    /**
     * 保存账户信息
     *
     * @param account 账户信息
     */
    void saveAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     */
    void updateAccount(Account account);

    /**
     * 根据账户id删除账户信息
     *
     * @param id 账户id
     */
    void deleteAccount(Long id);
}
```

```java
// 账户业务层接口实现类
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao;

    /**
     * 使用set方法注入IAccountDao
     * @param accountDao 账户持久层接口
     */
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }
}
```

持久层

```java
// 持久层接口
public interface IAccountDao {

    /**
     * 查询所有账户数据
     *
     * @return 所有的账户数据
     */
    List<Account> findAllAccount();

    /**
     * 根据账户id获取账户信息
     *
     * @param id 账户id
     * @return 账户信息
     */
    Account findAccountById(Long id);

    /**
     * 保存账户信息
     *
     * @param account 账户信息
     */
    void saveAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     */
    void updateAccount(Account account);

    /**
     * 根据账户id删除账户信息
     *
     * @param id 账户id
     */
    void deleteAccount(Long id);
}
```

Spring配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 配置AccountService-->
    <bean id="accountService" class="com.matrix.study.service.impl.AccountServiceImpl">
        <!-- set方式注入AccountDao-->
        <property name="accountDao" ref="accountDao"/>
    </bean>
    <!-- 配置AccountDao-->
    <bean id="accountDao" class="com.matrix.study.dao.impl.AccountDaoImpl">
        <property name="queryRunner" ref="queryRunner"/>
    </bean>
    <!-- 配置QueryRunner-->
    <bean id="queryRunner" class="org.apache.commons.dbutils.QueryRunner">
        <constructor-arg name="ds" ref="dataSource"/>
    </bean>
    <!-- 配置dataSource -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="jdbcUrl" value="jdbc:mysql://IP地址:端口号/数据库名称?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;autoReconnect=true"/>
        <property name="username" value="账户名"/>
        <property name="password" value="密码"/>
    </bean>

</beans>
```

单元测试类

```java
// 单元测试(账户crud基于xml的方式)
public class AccountTest {

    private IAccountService accountService = null;

    @Before
    public void init() {
        // 1.读取Spring配置文件创建IOC容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-beans.xml");
        // 2.根据bean名称获取bean对象
        accountService = (IAccountService) applicationContext.getBean("accountService");
    }

    @Test
    public void testFindAll() {
        accountService.findAllAccount().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        // 2.根据bean名称获取bean对象
        Account account = accountService.findAccountById(3L);
        System.out.println(account);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("Matrix");
        account.setMoney(2000.0);
        accountService.saveAccount(account);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setId(4L);
        account.setName("Test");
        account.setMoney(3500.0);
        accountService.updateAccount(account);
    }

    @Test
    public void testDelete() {
        accountService.deleteAccount(4L);
    }
}
```

#### 使用注解的方式

创建配置类

```java
// 配置类用于扫描配置bean的注解
@Configuration
@ComponentScan(basePackages = "com.matrix.study")
public class ScanConfig {
    
}
```

```java
// QueryRunner配置类
@Configuration
public class QueryRunnerConfiguration {

    @Autowired
    @Qualifier(value = "hikariDataSource")
    private HikariDataSource hikariDataSource;

    /**
     * 设置数据源信息
     * @return 获取到QueryRunner的bean对象
     */
    @Bean(name = "queryRunner")
    @Scope(value = "prototype")
    public QueryRunner getQueryRunner() {
        return new QueryRunner(hikariDataSource);
    }
}
```

```java
// 配置Hikari数据源配置类
@Configuration
public class HikariDataSourceConfiguration {

    @Bean(name = "hikariDataSource")
    @Scope(value = "singleton")
    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        // 设置数据源信息
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setJdbcUrl("jdbc:mysql://IP地址:端口号/数据库名称?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true");
        hikariDataSource.setUsername("账户名");
        hikariDataSource.setPassword("密码");
        return hikariDataSource;
    }
}
```

修改业务层

```java
// 账户的业务层接口实现类
@Service(value = "accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    @Qualifier(value = "accountDao")
    private IAccountDao accountDao;

    @Override
    public List<Account> findAllAccount() {
        return accountDao.findAllAccount();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountDao.deleteAccount(id);
    }
}
```

修改持久层

```java
@Repository(value = "accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    @Qualifier(value = "queryRunner")
    private QueryRunner queryRunner;

    @Override
    public List<Account> findAllAccount() {
        try {
            return queryRunner.query("select * from tb_account", new BeanListHandler<>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountById(Long id) {
        try {
            return queryRunner.query("select * from tb_account where id = ?", new BeanHandler<>(Account.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        try {
            queryRunner.update("insert into tb_account(id, name, money) values (default , ?, ?)", account.getName(), account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            queryRunner.update("update tb_account set name = ?, money = ? where id = ?", account.getName(), account.getMoney(), account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            queryRunner.update("delete from tb_account where id = ?", id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

单元测试类

```java
// 单元测试(账户crud基于注解的方式)
public class AccountAnnoTest {

    private IAccountService accountService = null;

    @Before
    public void init() {
        // 1.读取Spring配置文件创建IOC容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScanConfiguration.class);
        // 2.根据bean名称获取bean对象
        accountService = (IAccountService) applicationContext.getBean("accountService");
    }

    @Test
    public void testFindAll() {
        accountService.findAllAccount().forEach(System.out::println);
    }

    @Test
    public void testFindById() {
        // 2.根据bean名称获取bean对象
        Account account = accountService.findAccountById(3L);
        System.out.println(account);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        account.setName("Matrix");
        account.setMoney(2000.0);
        accountService.saveAccount(account);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setId(4L);
        account.setName("Test");
        account.setMoney(3500.0);
        accountService.updateAccount(account);
    }

    @Test
    public void testDelete() {
        accountService.deleteAccount(4L);
    }
}
```

### 1.3 新注解说明

##### @Configuration

作用：用于指定当前类是一个 spring 配置类，当创建容器时会从该类上加载注解。获取容器时需要使用AnnotationApplicationContext（有@Configuration 注解的类.class）。

属性：

- value：用于指定配置类的字节码

##### @ComponentScan

作用：用于指定 spring 在初始化容器时要扫描的包。作用和在 spring 的 xml 配置文件中的：\<context:component-scan base-package=""/>是一样的。

属性：

- basePackages：用于指定要扫描的包。和该注解中的 value 属性作用一样。

示例代码：

```java
@Configuration
@ComponentScan(basePackages = "com.matrix.study.anno")
public class ScanConfiguration {

}
```

##### @Bean

作用：用于告诉方法，产生一个Bean对象，然后这个Bean对象交给Spring管理。

属性：

- name：表示bean的名称，用于获取bean。

- value：和name一样作用。
- autowire：表示bean的注入方式
  - Autowire.NO （默认设置）
  - Autowire.BY_NAME
  - Autowire.BY_TYPE
- initMethod：bean初始化调用的init方法。
- destroyMethod：bean销毁方法，会执行到该属性指定的方法。【不过，只是单实例的 bean 才会调用该方法，如果是多实例的情况下，不会调用该方法】

##### @PropertySource

作用：用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。

属性：

- value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:

示例代码：

```java
@Configuration
@PropertySource(value = "classpath:spring-anno/mysql.properties")
public class HikariDataSourceConfiguration {

    @Value("${jdbc.driver}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean(name = "hikariDataSource")
    @Scope(value = "singleton")
    public HikariDataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        // 设置数据源信息
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        return hikariDataSource;
    }
}
```

```java
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://IP地址:端口号/数据库?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&autoReconnect=true
jdbc.username=账户名
jdbc.password=密码
```

##### @Import

作用：用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问题。

属性：

- value[]：用于指定其他配置类的字节码。

示例代码：

```java
@Configuration
@Import(value = {HikariDataSourceConfiguration.class})
public class QueryRunnerConfiguration {

    @Autowired
    @Qualifier(value = "hikariDataSource")
    private HikariDataSource hikariDataSource;

    /**
     * 设置数据源信息
     * @return 获取到QueryRunner的bean对象
     */
    @Bean(name = "queryRunner")
    @Scope(value = "prototype")
    public QueryRunner getQueryRunner() {
        return new QueryRunner(hikariDataSource);
    }
}
```

### 1.4 通过注解获取IOC容器

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScanConfiguration.class);
```

---

## 第二章 Spring整合Junit

### 2.1 测试类中的问题

在测试类中，每个测试方法都有以下两行代码：

```java
// 1.读取Spring配置文件创建IOC容器
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ScanConfiguration.class);
// 2.根据bean名称获取bean对象
IAccountService accountService = (IAccountService) applicationContext.getBean("accountService");
```

这两行代码的作用是获取容器，如果不写的话，直接会提示空指针异常。所以又不能轻易删掉。

### 2.2 解决思路

针对上述问题，我们需要的是程序能自动帮我们创建容器。一旦程序能自动为我们创建 spring 容器，我们就无须手动创建了，问题也就解决了。

我们都知道，junit 单元测试的原理（在 web 阶段课程中讲过），但显然，junit 是无法实现的，因为它自己都无法知晓我们是否使用了 spring 框架，更不用说帮我们创建 spring 容器了。不过好在，junit 给我们暴露了一个注解，可以让我们替换掉它的运行器。

这时，我们需要依靠 spring 框架，因为它提供了一个运行器，可以读取配置文件（或注解）来创建容器。我们只需要告诉它配置文件在哪就行了。

### 2.3 配置步骤

导入maven坐标

```xml
<!-- spring-test -->
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-test</artifactId>
  <version>5.3.19</version>
  <scope>test</scope>
</dependency>
```

使用@RunWith注解替换原有运行器

```java
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountAnnoTest {
}
```

使用@ContextConfiguration注解指定Spring配置文件的位置

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ScanConfiguration.class)
public class AccountAnnoTest {
}
```

**@ContextConfiguration注解**：

- classes属性：用于指定注解的类。当不使用 xml 配置时，需要用此属性指定注解类的位置。
- locations属性：用于指定配置文件的位置。如果是类路径下，需要用 **classpath:**表明。

### 2.4 为什么不把测试类配到xml中

在解释这个问题之前，先解除大家的疑虑，配到 XML 中能不能用呢？

答案是肯定的，没问题，可以使用。

那么为什么不采用配置到 xml 中的方式呢？

这个原因是这样的：

- 第一：当我们在 xml 中配置了一个 bean，spring 加载配置文件创建容器时，就会创建对象。
- 第二：测试类只是我们在测试功能时使用，而在项目中它并不参与程序逻辑，也不会解决需求上的问题，所以创建完了，并没有使用。那么存在容器中就会造成资源的浪费。

所以，基于以上两点，我们不应该把测试配置到 xml 文件中。
