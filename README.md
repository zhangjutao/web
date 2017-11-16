QTLDB
====
>1. **common:** 用于三个模块公共部分
>2. **dna:** DNA分支
>3. **mrna:** MRNA分支，对qtl模块有依赖
>4. **qtl:** QTL分支

**注意：** 构建步骤及信息见:build/README.md 文件

开发者注意事项:
----
- 由于在目中使用了阿里大鱼第三方jar文件(没有maven依赖),所以这里需要手动将它install到maven本地仓库,
步骤可以参见build/README.md文件
- MRNA模块对QTL模块有依赖,所以这里我们需要先将QTL模块install到本地仓库,然后MRNA中依赖才能生效。
执行步骤:到QTL模块root目录,执行`mvn clean install -DskipTests=true`,然后查看本地仓库中qtl-1.0.jar包有没有,
最初使用3.0.2版本jar plugin,后来发现不支持finalName属性,这里仍退回到2.3.2

git分支管理:
----
- dev：开发
- release：发布
- unstable：不稳定版本
- i18n:国际化
- sso:单点登录分支