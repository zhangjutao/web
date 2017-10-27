数据库文件
===
* authority.sql: 初始创建用户权限表，已在119上创建，开发环境无序重复创建

打包步骤:
---
>1. 从Gitlab上拉代码
>2. 初次部署该文件需要执行数据库脚本文件authority.sql，**注意:** 该脚本文件只能执行一次
>3. 执行打包程序：进入项目root目录，执行mvn clean install
>4. 拷贝dna、mrna、qtl目录下target目录中各自相应的 **.war** 文件至本机tomcat webapp中
