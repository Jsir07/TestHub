# TestHub
# 接口自动化测试一体式解决方案
（集成：Java+Testng+Maven+Jenkins+ExtentReports+Retrofit2+Git)

> 前戏叨逼叨：
  测试多年工作经验，很少有写文章、博客之类的东西。
  其实我这人不爱去写博客之类的东西，更多的是靠脑子的总结。不是脑子好用，其实就一句话：懒！就是懒！！！
  早在几年前就有记录的想法，但当时确实因为工作原因 加上懒，就借口了自己。
  好了，如今就要奔三的人，该面对的往往都要面对。
  于是觉得是该写点东西记录下，倒不是说要总结什么的；只是想让自己经历过的事情，通过记录的形式加强印象；与此同时，希望能够给像自己这样的小白，在遇到类似问题时能够带来想法。
  所以，第一次写类似测试技术博客，献丑。看官海涵~
  第一篇不谈理论，实践篇。
  叨逼叨结束！。

### 无聊的背景
  测试工作多年，一路以来一直都会伴随着服务端的测试。
  那么首当其冲的肯定是接口测试，  而接口测试中首先联想到的是接口自动化测试。
  注意：服务端测试  != 接口测试 != 接口自动化测试 。这个公式是不等！找机会再写篇文章详细聊聊...
  在经历过多中不同的平台后，毅然而然的觉得通过码代码的用例是最靠谱的。别相信那些带UI降低编写接口自动化用例难度的自动化平台，原因不详！（不想详细解释，入坑后自会明白...）
  好了，今天介绍一款接口自动化测试的一体式解决方案。（有点夸张，其实吧还真是一体式。）这是入坑后，个人认为在Java大背景服务开发下比较理想的解决方案。
 最后，接口自动化测试的框架和平台形形色色，不用评论哪个好，哪个差。只有最合适项目团队的才是最好的。（废话...）  

    本文代码github：https://github.com/Jsir07/TestHub  

    话不多说，先上Jenkins上自助运行用例，查看报告流程截图。也可结合持续集成自动触发测试服务。
![Jenkins自助运行流程](https://upload-images.jianshu.io/upload_images/1592745-11690347752e023f.GIF?imageMogr2/auto-orient/strip)

## 一、方案介绍
### ①. 选型：Java + Maven + Testng + ExtentReports + Git + Retrofit2 + Jenkins
- 使用Java作为项目编程语言。
- 使用Maven作为项目类型，方便管理架包。
- 使用TestNG作为项目运行框架，方便执行测试用例，生成测试报告。
- 使用ExtentReports作为代替TestNG报告的报告驱动，二次美化功能，界面更美观，内容清晰
- 使用Git作为仓库管理工具，方便管理项目代码。
- 使用Retrofit2作为API接口自动化项目底层服务驱动框架。
- 使用Jenkins作为自动化持续集成平台，方便自动编译，自动打包，自动运行测试脚本，邮件发送测试报告，通知等。

### ②. 功能介绍：
0.  实现持续集成测试，自助式测试，一站式测试平台。
1.  通过Retrofit2作为等常用接口定义与请求方法，使用方便简洁。同时可分离接口定义、实现请求、响应验证。
2.  参数化驱动用例运行方式，目前使用本地配置文件；可扩展为造数据。
3.  还有...自己体会...

## 二、环境安装与配置
#### （一）开发环境：
1. JDK1.7 及以上
2. IDEA 社区版（壕->pro）
3. Maven 不限
4. Git  不限
5. Jenkins 不限

#### （二）部分环境安装细节：
##### 1. JDK 安装请查阅。https://www.cnblogs.com/ottox/p/3313540.html
##### 2. Maven 安装与配置。
- setting配置，主要是国内镜像 阿里云。https://blog.csdn.net/liuhui_306/article/details/52822152
        - 加速。
    ```
    <mirror>
          <id>alimaven</id>
          <name>aliyun maven</name>
          <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
          <mirrorOf>central</mirrorOf>        
    </mirror>
    
    ```
- 本地localRepository配置。为什么要配置这个？因为默认是放在C盘的。```Default: ${user.home}/.m2/repository。```jar 太多、太大！
    
    ```
    <localRepository>E:\apache-maven-3.5.3\repository</localRepository>
    ```

 - 命令配置。https://www.cnblogs.com/eagle6688/p/7838224.html
        - 为啥配置这个？因为后面Jenkins如果需要在本地运行的话，需要用到mvn命令。
![](https://upload-images.jianshu.io/upload_images/1592745-e1794c63508719de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


##### 3. IDEA 安装请查阅，下载社区版本即可。 https://www.jetbrains.com/idea/
- 编码设置UTF-8：https://blog.csdn.net/frankcheng5143/article/details/50779149
- 字体设置：https://blog.csdn.net/qq_35246620/article/details/63253518
- 模板设置：https://blog.csdn.net/xiaoliulang0324/article/details/79030752
- JDK配置：选择JDK的存放路径。
- maven配置：file->settings->搜索maven->
    > Maven home deirectory: maven存放地址。    
    > User settings file: maven的setting路径。  
    > Local repository: repository 要存放的路径。   

- 以上配置最好设置成默认配置，这样不用针对每个项目配置。File->Other settings -> Default Settings.

##### 4. Git 安装。
参考：https://git-scm.com/book/zh/v2/%E8%B5%B7%E6%AD%A5-%E5%AE%89%E8%A3%85-Git
##### 5. Jenkins本地安装，只要安装不用创建任务，后面会有任务创建。
参考：https://www.cnblogs.com/c9999/p/6399367.html。  

*若遇网站需要翻墙，具体下载安装请自行百度。*

## 三、框架搭建
### （一） 项目基础工程
搭工程，建立基本工程框架，采用maven结构框架工程。  话不多说，先搭建工程。方式：
##### 1.1 IDEA 上，File ->New ->Project -> maven. 选maven后，不选任何模板，直接Next。  
![image.png](https://upload-images.jianshu.io/upload_images/1592745-cecb4b9d26f51ee5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 1.2 填写对应项目信息后，next。  
![image.png](https://upload-images.jianshu.io/upload_images/1592745-d7d63d3d9a86f7b2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
##### 1.3 继续填写 对应信息后，Finish。  
![image.png](https://upload-images.jianshu.io/upload_images/1592745-a2b383a44993315a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### （二）Maven pom.xml文件配置 与多环境切换
##### 2.1 依赖配置
需要使用到的依赖有testng、extentreports、retrofit、fastjson、okhttp等..

     <dependencies>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.47</version>
        </dependency>
        
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>3.10.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/logging-interceptor  日志拦截器-->
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>logging-interceptor</artifactId>
            <version>3.10.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.testng/testng -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.14.2</version>
            <!--<scope>test</scope>-->
            <!--作用范围，默认是test。验证部分被抽象，不仅test作用域需使用-->
        </dependency>
        
        <!-- https://mvnrepository.com/artifact/com.aventstack/extentreports -->
        <dependency>
            <groupId>com.aventstack</groupId>
            <artifactId>extentreports</artifactId>
            <version>3.1.5</version>
            <scope>provided</scope>
        </dependency>
    
        <!-- https://mvnrepository.com/artifact/com.vimalselvam/testng-extentsreport -->
        <dependency>
            <groupId>com.vimalselvam</groupId>
            <artifactId>testng-extentsreport</artifactId>
            <version>1.3.1</version>
        </dependency>
        
        <dependency>
            <groupId>com.squareup.retrofit2</groupId>
            <artifactId>retrofit</artifactId>
            <version>2.4.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson 对象转换器-->
        <dependency>
            <groupId>com.squareup.retrofit2</groupId>
            <artifactId>converter-gson</artifactId>
            <version>2.3.0</version>
        </dependency>
    </dependencies>

    
##### 2.2 配置多环境切换部分
  采用maven环境切换方式。
- 2.2.1 先配置pom.xml文件的 build节点。

     ```
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <!--扫描替换参数的文件路径-->
            </resource>
        </resources>
        <filters>
            <filter>src/main/filters/filter-${env}.properties</filter>
            <!--环境过滤器的配置方式，回头需要在该路径下建立对应文件-->
        </filters>
        <plugins>
            <plugin>
                <!--该插件是解决命令下执行mvn test指定testng xxx.xml 文件 的配置-->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.22.0</version>
                <configuration>
                    <!--为了解决在jenkins maven执行test 报告乱码问题，编码格式设置为UTF-8-->
                    <argLine>-Dfile.encoding=UTF-8</argLine>
                    <encoding>UTF-8</encoding>
                    <!--动态指定执行的xml文件。${project.basedir}项目目录，${xmlFileName}maven文件-->
                    <suiteXmlFiles>
                        <suiteXmlFile>${project.basedir}/target/classes/testNg/${xmlFileName}</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <encoding>UTF-8</encoding>
                    <source>8</source>
                    <target>8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
     ```
        
- 2.2.2 在pom.xml文件配置properties为了maven打包编译时后台一直输出警告信息。导致构建失败。
    ```
        <!--为了maven打包编译时后台一直输出警告信息。导致构建失败-->
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <xmlFileName></xmlFileName>
        </properties>
    ```
        
- 2.2.3 在pom.xml文件配置Properties环境。多环境配置参数切换。
    ```
        <profiles>
            <!-- 开发环境，默认激活 -->
            <profile>
                <id>dev</id>
                <properties>
                    <env>dev</env>
                </properties>
            </profile>
    
            <!-- 生产环境 -->
            <profile>
                <id>product</id>
                <properties>
                    <env>product</env>
                </properties>
            </profile>
    
            <!-- 测试环境 -->
            <profile>
                <id>debug</id>
                <properties>
                    <env>debug</env>
                </properties>
                <activation>
                    <activeByDefault>true</activeByDefault><!--默认启用的是dev环境配置-->
                </activation>
            </profile>
        </profiles>
    ```   
        
##### 2.3 配置环境切换-文件部分
- 2.3.1 在src/resource下创建env.properties文件。   
    该文件记录的信息是跟环境切换相关的参数。    
    如：接口请求不同环境的host，mongodb、mysql数据库等，因不同环境的信息。    
    例如：
    ```
        api.post.host=${api.post.host}
    ``` 
- 2.3.2 创建src/main/filters/下创建     
    filter-debug.properties、filter-dev.properties、filter-product.properties   
    用于环境信息记录。
    详细信息，如：
    ```
        api.post.host=http://apidebug.xxx.com:80/
    ```
如图：

  ![image.png](https://upload-images.jianshu.io/upload_images/1592745-2b03ef30d695a899.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### (三) testNg配置部分
##### 3.1. 在src/resources/下创建testNg目录.
src/resources/testNg 目录是存放测试集合的目录，可根据测试模块创建对应模块文件夹。

###### 3.1.1 每个文件夹可以是独立模块，每个模块下可以有模块的测试集合。
- 例如，在src/resources/testNg/下创建测试集合：api/search/search-TestSuite.xml。配置如下：
    ```
  <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >

  <suite name="search_tags-搜索分类-电影首页测试集合" verbose="1" preserve-order="true">
      <parameter name="report.config" value="src/main/resources/config/report/extent-config.xml"/>
      <parameter name="system.info" value="reporter.config.MySystemInfo"/>

      <test name="0100.搜索分类-电影首页-正常场景" preserve-order="true">
        <classes>
            <class name="com.jxq.douban.SearchTagsTest">
                <methods>
                    <include name="testcase1"/>
                </methods>
            </class>
        </classes>
      </test>

      <test name="0100.搜索分类-TV首页-正常场景" preserve-order="true">
        <classes>
            <class name="com.jxq.douban.SearchTagsTest">
                <methods>
                    <include name="testcase2"/>
                </methods>
            </class>
        </classes>
      </test>

      <listeners>
        <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
      </listeners>
  </suite>
    ```

###### 3.1.2.为了能够让所有接口统一运行测试，需建立一个所有的测试集合，测试集合一般放在src/resources/testNg 目录下。     
型如：
  ```
    <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >

    <suite name="接口测试集合" verbose="1" preserve-order="true">
      <parameter name="report.config" value="src/main/resources/config/report/extent-config.xml"/>
      <parameter name="system.info" value="reporter.config.MySystemInfo"/>

      <suite-files>
        <suite-file path="search/SearchTags-TestSuite.xml"/>
      </suite-files>

      <listeners>
          <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
      </listeners>
      </suite>
  ```

###### 3.1.3 说明
- 有同学会问：为什么会有这么多inclde name？     
原因是：test里的name是针对一整个testclass的名称，为了让每一个测试名称都能够展示。
故此把层级调整了。调整后的层级对应如下：

 | testng层级| 接口映射关系 | 
  | ------------- |:-------------:| 
  | test | 接口具体的testcase |
  | suite | 具体接口所有用例集合 |
  | 总suite | 所有接口测试集合 | 

*需要说明的是，在testng接口层级管理上，每个项目可以有各自的方式，只要符合你的是好的。*

-  这里添加了parameter、listener，是用于替换testng默认报告，使用ExtentReported。关于ExtentReported后面会有说明。
```
    <parameter name="report.config" value="src/main/resources/config/report/extent-config.xml"/>
    <parameter name="system.info" value="reporter.config.MySystemInfo"/>
    
    <listeners>
        <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
    </listeners>
```

### （四） TestCase测试用例
##### 4.1 Test用例类
- 首先，满足maven工程结构在src/test目录下建立对应的测试用例类。
- 其次，测试用例类的命名也满足maven在单元测试时的规范。类名以Test结尾.```如：SearchTagsTest.java```
- 最后，根据各模块分类建立对应模块包。

##### 4.2 测试用例怎么写？
其实网上有很多关于TestNg的文章，这边就不做过多介绍了。
> 官网：https://testng.org/doc/index.html       
> 也可查阅：https://www.yiibai.com/testng/

*写接口用例，不在与形式，形式网上很多。
最重要的是编写的层次与心得。回头有空再整理下。*

### （五） extentreports报告
> extentreports是什么？网上有很多关于使用extentreports替代TestNg自带报告。原因是什么？
> 漂亮。先上张图。

![](https://upload-images.jianshu.io/upload_images/1592745-caffacaa53a55671.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
官网很重要：http://extentreports.com/. 其实官网已经给了很多demo了，这里我根据自己的经验进行了配置。

testNg原有报告有点丑，信息整理有点乱。ExtentReports是用于替换testNg原有的报告。也可以使用ReportNg，个人偏好ExtentReports样式。

##### 5.1 强制重写ExtentTestNgFormatter类
强制重写EExtentTestNgFormatter类主要是以下两点原因：
①、为了能够在报告中展示更多状态类型的测试结果，例如：成功、失败、警告、跳过等测试状态结果。
②、因为不支持cdn.rawgit.com访问，故替css访问方式。

方式如下：下载ExtentReportes源码，找到ExtentTestNgFormatter类。
- 5.1.1 在创建类：src/main/java/reporter/listener路径下MyExtentTestNgFormatter.java类。
 MyExtentTestNgFormatter直接从ExtentTestNgFormatter继承。
  ```
  public class MyExtentTestNgFormatter extends ExtentTestNgFormatter {
  ```
- 5.1.2 构造方法加入```htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);```
MyExtentTestNgFormatter 类，代码如下：
  ```
    public MyExtentTestNgFormatter() {
        setInstance(this);
        testRunnerOutput = new ArrayList<>();
        String reportPathStr = System.getProperty("reportPath");
        File reportPath;

        try {
            reportPath = new File(reportPathStr);
        } catch (NullPointerException e) {
            reportPath = new File(TestNG.DEFAULT_OUTPUTDIR);
        }

        if (!reportPath.exists()) {
            if (!reportPath.mkdirs()) {
                throw new RuntimeException("Failed to create output run directory");
            }
        }

        File reportFile = new File(reportPath, "report.html");
        File emailReportFile = new File(reportPath, "emailable-report.html");

        htmlReporter = new ExtentHtmlReporter(reportFile);
        EmailReporter emailReporter = new EmailReporter(emailReportFile);
        reporter = new ExtentReports();
        //        如果cdn.rawgit.com访问不了，可以设置为：ResourceCDN.EXTENTREPORTS或者ResourceCDN.GITHUB
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        reporter.attachReporter(htmlReporter, emailReporter);
    }
  ```

- 5.1.3 接着在onstart方法重写功能。
    用了很粗暴的方式，新建了一个类名为MyReporter，一个静态ExtentTest的引用。
  - ①  reporter.Listener包下MyReporter.java
    ```
        public class MyReporter {
            public static ExtentTest report;
        }
    ```

  - ② MyExtentTestNgFormatter.java
    ```
        public void onStart(ITestContext iTestContext) {
            ISuite iSuite = iTestContext.getSuite();
            ExtentTest suite = (ExtentTest) iSuite.getAttribute(SUITE_ATTR);
            ExtentTest testContext = suite.createNode(iTestContext.getName());
            // 将MyReporter.report静态引用赋值为testContext。
            // testContext是@Test每个测试用例时需要的。report.log可以跟随具体的测试用例。另请查阅源码。
            MyReporter.report = testContext;
            iTestContext.setAttribute("testContext", testContext);
        }
    ```

- 5.1.4 顺带提一句，测试报告默认是在工程根目录下创建test-output/文件夹下，名为report.html、emailable-report.html。可根据各自需求在构造方法中修改。
    ```
        public MyExtentTestNgFormatter() {
            setInstance(this);
            testRunnerOutput = new ArrayList<>();
            // reportPath报告路径
            String reportPathStr = System.getProperty("reportPath");
            File reportPath;
    
            try {
                reportPath = new File(reportPathStr);
            } catch (NullPointerException e) {
                reportPath = new File(TestNG.DEFAULT_OUTPUTDIR);
            }
    
            if (!reportPath.exists()) {
                if (!reportPath.mkdirs()) {
                    throw new RuntimeException("Failed to create output run directory");
                }
            }
            // 报告名report.html
            File reportFile = new File(reportPath, "report.html");
            // 邮件报告名emailable-report.html
            File emailReportFile = new File(reportPath, "emailable-report.html");
    
            htmlReporter = new ExtentHtmlReporter(reportFile);
            EmailReporter emailReporter = new EmailReporter(emailReportFile);
            reporter = new ExtentReports();
            reporter.attachReporter(htmlReporter, emailReporter);
        }
    ```
- 5.1.5 顺带再提一句，report.log 可以有多种玩法。
    ```
    // 根据状态不同添加报告。型如警告
    MyReporter.report.log(Status.WARNING, "接口耗时(ms)：" + String.valueOf(time));
    ```
  *直接从TestClass中运行时会报MyReporter.report的空指针错误，需做个判空即可。*

##### 5.2 导入MyExtentTestNgFormatter监听类
在测试集合.xml文件中导入Listener监听类。
```
<listeners>
        <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
</listeners>
```

##### 5.3 配置报告信息
extent reporters支持报告的配置。目前支持的配置内容有title、主题等。

- 先在src/resources/目录下添加 config/report/extent-config.xml。    

    - 配置内容
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <extentreports>
        <configuration>
            <timeStampFormat>yyyy-MM-dd HH:mm:ss</timeStampFormat>
            <!-- report theme -->
            <!-- standard, dark 个人喜好暗色 -->
            <theme>dark</theme>
    
            <!-- document encoding -->
            <!-- defaults to UTF-8 -->
            <encoding>UTF-8</encoding>
    
            <!-- protocol for script and stylesheets -->
            <!-- defaults to https -->
            <protocol>https</protocol>
    
            <!-- title of the document -->
            <documentTitle>QA-接口自动化测试报告</documentTitle>
    
            <!-- report name - displayed at top-nav -->
            <reportName>QA-接口自动化测试报告</reportName>
    
            <!-- report headline - displayed at top-nav, after reportHeadline -->
            <reportHeadline>接口自动化测试报告</reportHeadline>
    
            <!-- global date format override -->
            <!-- defaults to yyyy-MM-dd -->
            <dateFormat>yyyy-MM-dd</dateFormat>
    
            <!-- global time format override -->
            <!-- defaults to HH:mm:ss -->
            <timeFormat>HH:mm:ss</timeFormat>
    
            <!-- custom javascript -->
            <scripts>
                <![CDATA[
            $(document).ready(function() {
    
            });
          ]]>
            </scripts>
    
            <!-- custom styles -->
            <styles>
                <![CDATA[
    
          ]]>
            </styles>
        </configuration>
    </extentreports>
    ```
##### 5.4 添加系统信息
不多说，上图。
    ![](https://upload-images.jianshu.io/upload_images/1592745-db9bbb5ef50eb56b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

    可用于添加系统信息，例如：db的配置信息，人员信息，环境信息等。根据项目实际情况添加。

- 在src/main/java/reporter/config目录下创建MySystemInfo.java类，继承SystemInfo接口。
    ```
    public class MySystemInfo implements SystemInfo {
        @Override
        public Map<String, String> getSystemInfo() {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("env.properties");
            Properties properties = new Properties();
            Map<String, String> systemInfo = new HashMap<>();
            try {
                properties.load(inputStream);
                systemInfo.put("environment", properties.getProperty("Environment"));
                systemInfo.put("sqlURL", properties.getProperty("ESsql.URL"));
                systemInfo.put("redisHost", properties.getProperty("redis.host"));
                systemInfo.put("redisPort", properties.getProperty("redis.port"));
                systemInfo.put("mongodbHost", properties.getProperty("mongodb.host"));
                systemInfo.put("mongodbPort", properties.getProperty("mongodb.port"));
                systemInfo.put("测试人员", "jxq");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return systemInfo;
        }
    }
    ```

    至此，extentreports美化报告完成。


### （六）. retrofit2.0--Http接口测试驱动原力
> 其实Java的Http客户端有很多，例如HTTPClient、OKHttp、retrofit等。。。       
> 为什么那么多Http客户端会选择retrofit？用一个图见证他的实力

![如此多的星星可知](https://upload-images.jianshu.io/upload_images/1592745-4e75bc87d014a097.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> 真正的原因
 **接口定义与实现分离** 
retrofit2.0可将Http接口定义与请求实现分离；通过制定interface定义接口。    
网上有很多关于retrofit2.0的教程，这里就不再班门弄斧了，度娘即可。参考：https://blog.csdn.net/carson_ho/article/details/73732076

附上本项目方式。

##### 6.1 具体Http Api的定义interface。新建ISearch interface。
```
public interface ISearch {
    @GET("j/search_tags")
    Call<MovieResponseVO> searchTags(@Query("type") String type, @Query("source") String source);
}
```

##### 6.2 HttpBase基础类提供原动力。
HttpBase类中提供了Retrofit基础。
同时，我考虑到了日常控制台和测试报告上都需要看到对应请求信息，故此在HttpClient中默认加入了日志拦截器；日志拦截器的实现方法里，用Reportes.log记录到日志中。
并且，考虑到实际项目中每个Http请求都会有对应类似RequestHeader、RequestBody的加密签名等，预留了拦截器。
可在HttpBase构造方法时传入对应拦截器。
对应的拦截器可以通过实现接口Interceptor，做对应项目需求操作。
先看代码。
```
public class HttpBase {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Retrofit retrofit;
    private String host;

    /**
     * 构造方法（1个参数）
     * 只传Host，默认没有使用拦截器。
     *
     * @param host 访问域名host
     */
    public HttpBase(String host) {
        init(host, null);
    }

    /**
     * 构造方法（2个参数）
     * 只传Host，默认使用日志拦截器。
     *
     * @param host        访问域名host
     * @param interceptor 自定义拦截器
     */
    public HttpBase(String host, Interceptor interceptor) {
        init(host, interceptor);
    }

    /**
     * 初始化方法
     *
     * @param host        访问域名host
     * @param interceptor 自定义拦截器
     */
    private void init(String host, Interceptor interceptor) {
        OkHttpClient.Builder client = getHttpClient(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(client.build())
                .addConverterFactory(RespVoConverterFactory.create())
                .build();
    }

    /**
     * 获取HttpClient.Builder 方法。
     * 默认添加了，基础日志拦截器
     *
     * @param interceptor 拦截器
     * @return HttpClient.Builder对象
     */
    private OkHttpClient.Builder getHttpClient(Interceptor interceptor) {
        HttpLoggingInterceptor logging = getHttpLoggingInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        builder.addInterceptor(logging);
        return builder;
    }

    /**
     * 日志拦截器
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Reporter.log("RetrofitLog--> " + message, true);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//Level中还有其他等级. 设置打印内容级别到Body。
        return logging;
    }

    /**
     * retrofit构建方法
     *
     * @param clazz 泛型类
     * @param <T>   泛型类
     * @return 泛型类
     */
    public <T> T create(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
```


##### 6.3 集成HttpBase的Http Api接口请求方法类
这里需要说明下，为什么需要有这个类的存在？
其实在Retrofit已经可以用4行的代码实现Http请求了，如下：
```
        HttpBase httpBase = new HttpBase(host);
        ISearch iSearch = httpBase.create(ISearch.class);
        Call<MovieResponseVO> call = iSearch.searchTags(type, source);
        Response<MovieResponseVO> response = call.execute();
```
看了上面的4行代码，每次都需要写也是挺麻烦的。
所以抽出来，让编写测试用例验证更简洁点。
抽取后的代码如下：
```
public class HttpSearch extends HttpBase {
    private ISearch iSearch;

    public HttpSearch(String host) {
        super(host);
        iSearch = super.create(ISearch.class);
    }

    public Response<MovieResponseVO> searchTags(String type, String source) throws IOException {
        Call<MovieResponseVO> call = iSearch.searchTags(type, source);
        return call.execute();
    }

//    同模块下，新增的接口可添加到这里。
//    public Response<MovieResponseVO> searchTags(String type, String source) throws IOException {
//        Call<MovieResponseVO> call = iSearch.searchTags(type, source);
//        return call.execute();
//    }
}
```

##### 6.4 使用JsonSchema验证基础响应体
  >  *Http响应体非Json格式，可跳过该步骤。*

  这里引入了JsonSchema来做基础验证，减少了Http响应返回带来的大量对象基础验证。
  方式如下：
  - 6.5.1 pom.xml 依赖引入
  ```
        <!--json schema start-->
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.9.6</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.6</version>
        </dependency>

        <dependency>
            <groupId>com.github.fge</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>2.2.6</version>
        </dependency>
        <!--json schema end-->
  ```
  - 6.5.2 简单抽象JsonSchemaUtils工具类。
    直接看代码。
  ```
/**
 * JsonSchema工具类
 */
public class JsonSchemaUtils {
    /**
     * 从指定路径读取Schema信息
     *
     * @param filePath Schema路径
     * @return JsonNode型Schema
     * @throws IOException 抛出IO异常
     */
    private static JsonNode readJSONfile(String filePath) throws IOException {
        InputStream stream = JsonSchemaUtils.class.getClassLoader().getResourceAsStream(filePath);
        return new JsonNodeReader().fromInputStream(stream);
    }

    /**
     * 将Json的String型转JsonNode类型
     *
     * @param str 需要转换的Json String对象
     * @return 转换JsonNode对象
     * @throws IOException 抛出IO异常
     */
    private static JsonNode readJSONStr(String str) throws IOException {
        return new ObjectMapper().readTree(str);
    }

    /**
     * 将需要验证的JsonNode 与 JsonSchema标准对象 进行比较
     *
     * @param schema schema标准对象
     * @param data   需要比对的Schema对象
     */
    private static void assertJsonSchema(JsonNode schema, JsonNode data) {
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schema, data);
        if (!report.isSuccess()) {
            for (ProcessingMessage aReport : report) {
                Reporter.log(aReport.getMessage(), true);
            }
        }
        Assert.assertTrue(report.isSuccess());
    }

    /**
     * 将需要验证的response 与 JsonSchema标准对象 进行比较
     *
     * @param schemaPath JsonSchema标准的路径
     * @param response   需要验证的response
     * @throws IOException 抛出IO异常
     */
    public static void assertResponseJsonSchema(String schemaPath, String response) throws IOException {
        JsonNode jsonSchema = readJSONfile(schemaPath);
        JsonNode responseJN = readJSONStr(response);
        assertJsonSchema(jsonSchema, responseJN);
    }
}
  ```
  这里已经将最后抽成简单方法供使用，只需传入schemaPath路劲、以及需要验证的对象。

  - 6.5.3 Http响应体保存到本地
    - ①、可以通过客户端抓包获取得到Http响应体、或者开发接口定义文档 等方式，得到最后Http响应体的Json对象。(注意：响应体内容尽量全面，这样在验证时也可以尽可能验证)
    - ②、将请求响应体通过 https://jsonschema.net/ ，在线验证得到JsonSchema信息。
    - ③、根据接口响应需求，做基础验证配置。例如，这里将tags字段认为是必须存在的参数。
          完整Schema约束文件如下，并将此文件保存到resources目录对应模块下。
```
  {
  "$id": "http://example.com/example.json",
  "type": "object",
  "properties": {
    "tags": {
      "$id": "/properties/tags",
      "type": "array",
      "items": {
        "$id": "/properties/tags/items",
        "type": "string",
        "title": "The 0th Schema ",
        "default": "",
        "examples": [
          "热门",
          "最新"
        ]
      }
    }
  },
  "required": [
    "tags" 
  ]
}
 ```

##### 6.5 TestCase测试用例编写。
```
public class SearchTagsTest {
    private static Properties properties;
    private static HttpSearch implSearch;
    private static String SCHEMA_PATH = "parameters/search/schema/SearchTagsMovie.json";

    @BeforeSuite
    public void beforeSuite() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("env.properties");
        properties = new Properties();
        properties.load(stream);
        String host = properties.getProperty("douban.host");
        implSearch = new HttpSearch(host);
        stream = this.getClass().getClassLoader().getResourceAsStream("parameters/search/SearchTagsParams.properties");
        properties.load(stream);
        stream = this.getClass().getClassLoader().getResourceAsStream("");
        stream.close();
    }

    @Test
    public void testcase1() throws IOException {
        String type = properties.getProperty("testcase1.req.type");
        String source = properties.getProperty("testcase1.req.source");
        Response<MovieResponseVO> response = implSearch.searchTags(type, source);
        MovieResponseVO body = response.body();
        Assert.assertNotNull(body, "response.body()");
//        响应返回内容想通过schema标准校验
        JsonSchemaUtils.assertResponseJsonSchema(SCHEMA_PATH, JSONObject.toJSONString(body));
//        再Json化成对象
        Assert.assertNotNull(body.getTags(), "tags");
    }

    @Test
    public void testcase2() throws IOException {
        String type = properties.getProperty("testcase2.req.type");
        String source = properties.getProperty("testcase2.req.source");
        Response<MovieResponseVO> response = implSearch.searchTags(type, source);
        MovieResponseVO body = response.body();
        Assert.assertNotNull(body, "response.body()");
        JsonSchemaUtils.assertResponseJsonSchema(SCHEMA_PATH, JSONObject.toJSONString(body));
        Assert.assertNotNull(body.getTags(), "tags");
    }
}
```
至此，TestNg测试用例部分全部完成。

### 四、Jenkins部分配置
> Jenkins的安装上面已有说明，这里不重复。

#### （一） Jenkins插件
##### 1.插件列表
需要使用到的插件有：
- Maven Integration plugin
- HTML Publisher plugin
- Dingding[钉钉] Plugin
- TestNG Results
- Groovy
- Parameterized Trigger Plugin

##### 2. Jenkins插件安装
怎么安装插件？
Jenkins-》系统管理-》插件管理-》搜索插件-》安装即可

 ![](https://upload-images.jianshu.io/upload_images/1592745-c726c78951c2ccd1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 3. 插件说明
  -  Maven Integration plugin -必备！
Maven构建插件，使用简单方便。 

- HTML Publisher plugin -必备！
extentreporets美化报告替换testng就是为了好看，但要在jenkins中展示必须安装此插件。

-  Groovy -必备！
Jenkins不支持异类样式CSS，所以Groovy插件是为了解决HTML Publisher plugin在展示extentreporets时能够正确美丽的作用。

- Dingding[钉钉] Plugin -必备！
测试用例构建结果的通知。网上很多说用邮件，说实话使用场景最频繁高效的应该是IM靠谱。这个插件就是解决测试结果的通知。

- TestNG Results - 非必备
TestNg测试结果收集，统计运行结果数据。

- Parameterized Trigger Pl ugin - 非必备
依赖构建传参插件。http://note.youdao.com/noteshare?id=c56333317d3078b36b2479fdf8fe68d7&sub=wcp1530172849180570

#### （二）Jenkins新建任务配置
在插件安装完后，开始任务的新建配置。
- 新建一个maven项目。
    ![image.png](https://upload-images.jianshu.io/upload_images/1592745-9d512595d8e7b610.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### （三）General配置
- 丢弃旧的构建配置 -可配
该配置根据需求配置。
  ![image.png](https://upload-images.jianshu.io/upload_images/1592745-f1e48f5e13c01fa0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### （四） 构建配置-maven配置
在Jenkins使用Maven构建项目自动化测试前，先通过本地使用maven测试是否通过。
这里本来要将参数化构建，但参数化构建前先说明下是如何利用maven构建测试的。

- 1. 检查pom.xml配置中指定的suiteXmlFile对象。
    ```
    <suiteXmlFiles>
        <!--用于根据maven传入的文件，运行maven test测试集合对象。 ${xmlFileName}是maven命令替换对象，别忘了添加properties中的xmlFileName-->
        <suiteXmlFile>${project.basedir}/target/classes/testNg/${xmlFileName}</suiteXmlFile>
        
        <!--在IEDA上运行maven test命令时，可打开该注释使用完整测试集合-->
        <!--<suiteXmlFile>${project.basedir}/target/classes/testNg/api/testng.xml</suiteXmlFile>-->
    </suiteXmlFiles>
    
    <properties>
        <xmlFileName></xmlFileName>
    </properties>
    ```

- 2. 先在IDEA上验证maven test是否生效？  

    在```<suiteXmlFile>${project.basedir}/target/classes/testNg/api/testng.xml</suiteXmlFile>``` 开启后，使用maven test验证是否成功。如下图：

![image.png](https://upload-images.jianshu.io/upload_images/1592745-d69bb8f5b7550773.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 3. 通过terminal命令验证maven test是否生效
    - 在2.3.1验证通过后，pom.xml注释```<suiteXmlFile>${project.basedir}/target/classes/testNg/api/testng.xml</suiteXmlFile>```.
    - 打开```<suiteXmlFile>${project.basedir}/target/classes/testNg/${xmlFileName}</suiteXmlFile>```
    - 进入terminal命令验证maven test是否生效。在命令行上输入```mvn clean test -DxmlFileName=testng.xml```
    - 验证maven test 是否正确。
    
    ![image.png](https://upload-images.jianshu.io/upload_images/1592745-06c56c65faaf9bea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 4. 唠叨编码问题
    > 我在执行上面的命令时，maven一直提示警告信息-编码问题；该警告信息原先我本不太在意，因为配置没有问题。      
    > 可后来，命令执行一直报错。看了报错信息都指向了非编码问题。也就把我引向了其他错误解决区域。        
    > 不得不说，maven的提示还是要重头到尾认真看。因为真正报错误的地方不一定是[error]提示。      
    > 警告信息是： [WARNING] File encoding has not been set, using platform encoding GBK, i.e. build is platform dependent!         
    
    如何解决该问题呢？在pom.xml上加入如下配置。       

    ```
  // 这个配置由于被误删了，导致花费了半天解决。。。
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <xmlFileName></xmlFileName>
    </properties>
    ```

#### （五） 配置构建-maven配置信息。
回到Jenkins界面配置maven信息。
  - 5.1 先在Jenkins新建任务，构建模块，增加构建步骤 - 调用顶层maven目标。         
    （需要注意下，这里写的是中文，根据不同的Jenkins版本该名称可能是英文的）
  ![](https://upload-images.jianshu.io/upload_images/1592745-7c90fe29582692cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
        
  - 5.2 然后配置信息如下。在目标加入命令信息：clean test -P%env%  -DxmlFileName=%xmlFileName%
  ![](https://upload-images.jianshu.io/upload_images/1592745-f0d0de23bb5a72ca.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  - 5.3命令解释：clean test -P%env%  -DxmlFileName=%xmlFileName% 
  > - maven参数化替换使用的占位符是 %xxx% 
  > - -P%env% 指定maven运行的环境，该环境信息与pom.xml 配置的信息一直。同时，-P%env% 用于参数化构建传参使用，后面会有介绍。
  > - -DxmlFileName=%xmlFileName% 指定maven test 运行的测试集合对象。用于参数化构建传参使用，后面介绍。

#### （六） 参数化构建过程 配置
  - 6.1添加参数 选择是 【选项参数】。
    ![](https://upload-images.jianshu.io/upload_images/1592745-0486fbca91a38367.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  - 6.2完整配置信息如下图。
        ![](https://upload-images.jianshu.io/upload_images/1592745-f586ae3356c5dd3b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
        
  - 6.3参数名称xmlFileName，对应maven构建中的-DxmlFileName=%xmlFileName%，再对应pom.xml中的```<suiteXmlFile>${project.basedir}/target/classes/testNg/${xmlFileName}</suiteXmlFile>```
    加入需要运行的集合选项。
  - 6.4 同样，env对应maven构建中的 -P%env% ，再对应pom.xml中的build信息。
    加入运行的环境选项。

#### （七） 源码管理配置
这个配置网上有很多详细文档，这里不重复。具体度娘查看。
    ![image.png](https://upload-images.jianshu.io/upload_images/1592745-f6d102928da390fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### （八） 构建触发器
    > 这个配置可根据实际项目需求配置。个人建议: 接口自动化测试中的自动化最核心的是结合持续构建。   
    > 所以建议配置“其他工程构建后触发”，填入所需测试的服务端项目名称即可。当然要在一个Jenkins中。
  ![image.png](https://upload-images.jianshu.io/upload_images/1592745-5c69a2115ce3327c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### （九） 构建信息配置    
    > 上面已经配置了“调用顶层Maven目标”，然后还需要配置Groovy script。  
    > 配置Groovy script的目的是让Http Reported插件css能用，同时不用担心jenkins重启。

- 配置Groovy script前保障Groovy 插件已经安装。
- 增加构建步骤“Execute system Groovy script” ，选择Groovy command，填入```System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")```
    
    ![](https://upload-images.jianshu.io/upload_images/1592745-9a3742f57a4ed0d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

### （十） 构建后操作信息配置
##### 9.1. publish html reports
    加入publish html reports步骤。
    - HTML directory to archive： 报告路径。 填写extentreports默认输出路径：test-output\
    - Index page[s] ： 报告索引名称。填写extentreports默认报告名称：report.html
    - Keep past HTML reports： 保留报告，勾选！不多说。
![](https://upload-images.jianshu.io/upload_images/1592745-9143ff5424476c8d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####  9.2 publish html reports
    publish testng results 配置。默认**/testng-results.xml 即可。 
    为什么要testng默认报告？ 因为需要统计分析时查看。 当然这个是可选的。
   ![image.png](https://upload-images.jianshu.io/upload_images/1592745-9625b6b2ec2e35df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####  9.3. 钉钉通知器配置
    怎么玩转钉钉消息？查看https://blog.csdn.net/workdsz/article/details/77531802
    - 填入access token。
![image.png](https://upload-images.jianshu.io/upload_images/1592745-4e1ca2a4d34564d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##### 4. 构建后操作信息配置 钉钉通知器配置 二次开发 - 可选      
    http://www.51testing.com/html/25/n-3723525.html

### (十一) 构建测试
  - 11.1 build with parameters
    ![](https://upload-images.jianshu.io/upload_images/1592745-c509b41047965ce8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  - 11.2  构建成功后 在 HTML Report上查看
  ![](https://upload-images.jianshu.io/upload_images/1592745-6a2c01b7815cc05f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  ![](https://upload-images.jianshu.io/upload_images/1592745-acc148199c4dd3fc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

  - 11.3 构建成功后 在 TestNG Results上查看
    ![](https://upload-images.jianshu.io/upload_images/1592745-f20e70dab17521d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    
  - 11.4 构建成功后 在 钉钉上查看
   ![](https://upload-images.jianshu.io/upload_images/1592745-08bc711f90a9fada.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    

### 五、工程目录讲解 与 接口测试用例编写步骤
#### (一) 工程目录讲解
     先上图说明
  ![](https://upload-images.jianshu.io/upload_images/1592745-009769b3a99eca47.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
  
  ![](https://upload-images.jianshu.io/upload_images/1592745-a79305d5a042c8e9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### (二) 接口测试用例编写步骤
##### 1.  目标：具体Htpp接口定义的熟悉、理解。
注意：Http 请求行、请求头、请求体；响应行、响应头、响应体
- 可通过查看开发wiki文档，或者通过抓包等手段达到。

##### 2. 具体Htpp接口的定义 - Host
- 2.1 将不同环境的Host地址配置在env.properties文件
- 2.2 然后在根据不同环境，配置不同properties文件，中对应的host信息。filter-debug.properties、filter-dev.properties、filter-product.properties。

##### 3. 具体Htpp接口的定义 - interface
- 3.1 在src/main/java/下com.xxx.api.下新建对应模块，例如article
    - 一个模块文件夹下存放：接口定义的interface、接口定义的实现类。
- 3.2 在article下新建具体的接口定义interface
  ```
  public interface IArticle {
    @POST("article/feed")
    Call<ResponseBody> articleFeed(@Query("tid") String tid, @Body RequestBody requestBody);
  }
  ```
   
##### 4. 具体Htpp接口的定义实现 - implement
- 4.1 在article下新建具体的接口定义实现
  ```
  public class ImplArticle {
    public static String articleFeed(String host, String tid, String requestBody) throws IOException {
       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .build();
        IArticle iArticle = retrofit.create(IArticle.class);
        Call<ResponseBody> call = iArticle.articleFeed(tid, RequestBody.create(CallHttp.JSON, requestBody));
        return CallHttp.doCall(call, request);
    }
  }
  ```

##### 5. 编写测试用例 -xxxTest
- 5.1 在test/java 目录下新建对应测试模块的文件夹 ，例如：article
    - 一个接口用例类，对应一个文件夹。
- 5.2 在test/java/article 目录下新建测试用例类。
    - 开始编写接口测试类。

  ```
  public class ArticleFeedTest {
    private static String HOST;
    private static Properties properties;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("env.properties");
        properties = new Properties();
        properties.load(stream);
        HOST = properties.getProperty("api.newsapi.host");
        stream = this.getClass().getClassLoader().getResourceAsStream("parameters/api/article/ArticleFeedParam.properties");
        properties.load(stream);
        stream.close();
    }

    @Test
    public void testcase1() throws IOException {
        String reques = properties.getProperty("testcase1.requestBody");

        String response = ImplArticle.articleFeed(HOST, "tid", reques);
        ResponseBodyVo responseBodyVo = JSONObject.parseObject(response, ResponseBodyVo.class);
        assertResponseBody(responseBodyVo);
    }
  }
  ```
    
##### 6. 具体接口测试用例suite集合制作
- 在testNg/api/article/ArticleFeed-TestSuite.xml下创建suite集合

  ```
    <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >

  <suite name="article/feed-接口测试集合" verbose="1" preserve-order="true">
    <parameter name="report.config" value="src/main/resources/config/report/extent-config.xml"/>
    <parameter name="system.info" value="reporter.config.MySystemInfo"/>

    <test name="0100.xxxx-正常" preserve-order="true">
        <classes>
            <class name="com.api.article.ArticleFeedTest">
                <methods>
                    <include name="testcase1"/>
                </methods>
            </class>
        </classes>
    </test>

    <listeners>
        <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
    </listeners>
  </suite>
  ```

##### 7. 所有接口测试用例suite集合制作    
- 在testNg/api/APICollection-TestSuite.xml下创建suite集合
  ```
  <!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >

  <suite name="接口测试集合" verbose="1" preserve-order="true">
    <parameter name="report.config" value="src/main/resources/config/report/extent-config.xml"/>
    <parameter name="system.info" value="reporter.config.MySystemInfo"/>

    <suite-files>
        <suite-file path="article/ArticleFeed-TestSuite.xml"/>
        <suite-file path="post/PostFeed-TestSuite.xml"/>
        <suite-file path="push/RegisterToken-TestSuite.xml"/>
        <suite-file path="sys/SysGetConfig-TestSuite.xml"/>
        <suite-file path="sys/SysGetRegions-TestSuite.xml"/>
        <suite-file path="registerDevice/RegisterDevice-TestSuite.xml"/>
    </suite-files>

    <listeners>
        <listener class-name="reporter.Listener.MyExtentTestNgFormatter"/>
    </listeners>
  </suite>
  ```
   
#### 写在最后
其实，接口自动化测试平台的搞起来不难。
推动平台接入到持续集成，将测试变成一种服务，更快更及时的服务于项目，才是重点。
正所谓：wiki一定，开发未动，接口已行。
而，服务端测试才挑战。知识储备的深度决定了，测试的深度。

个人GitHub:  https://github.com/Jsir07/TestHub
欢迎Watch + Fork
end...
    
    
