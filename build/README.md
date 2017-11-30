数据库文件
===
* authority.sql: 初始创建用户权限表，已在119上创建，开发环境无序重复创建

将阿里大鱼jar包打包到maven本地仓库
---
`mvn install:install-file -Dfile=F:\Core-Database\common\libs\aliyun-java-sdk-core-3.3.1.jar -DgroupId=com.aliyuncs -DartifactId=sdk.core -Dversion=1.0 -Dpackaging=jar`
`mvn install:install-file -Dfile=F:\Core-Database\common\libs\aliyun-java-sdk-dysmsapi-1.0.0.jar -DgroupId=com.aliyuncs -DartifactId=sdk.dysmsapi -Dversion=1.0 -Dpackaging=jar`
**注意：** 这里的-Dfile值得是common\libs目录下的jar包路径，上面的路径是我PC上的路径

打包步骤:
---
>1. 从Gitlab上拉代码
>2. 初次部署该文件需要执行数据库脚本文件authority.sql，**注意:** 该脚本文件只能执行一次
>3. 执行打包程序：进入项目root目录，执行步骤如下：<br/>
*测试环境执行* `mvn clean install -P test -DskipTests=true`<br/>
*生产环境执行* `mvn clean install -P production -DskipTests=true`
>4. 拷贝dna、mrna、qtl目录下target目录中各自相应的 **.war** 文件至本机tomcat webapp中
