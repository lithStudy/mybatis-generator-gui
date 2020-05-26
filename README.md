mybatis-generator-gui
==============

mybatis-generator-gui是基于[mybatis plus generator]开发一款界面工具, 本工具可以使你非常容易及快速生成Mybatis的Java POJO文件及数据库Mapping文件。


### 核心特性
* 按照界面步骤轻松生成代码，省去XML繁琐的学习与配置过程
* 保存数据库连接与Generator配置，每次代码生成轻松搞定
* 符合微医代码接口

### 要求
本工具由于使用了Java 8的众多特性，所以要求JDK <strong>1.8.0.60</strong>以上版本，另外<strong>JDK 1.9</strong>暂时还不支持。

### 下载



### 启动本软件

* 方法一：下载软件包（仅win，mac用户自行打包）

* 方法二: 自助构建

  ```bash
  git clone https://github.com/zouzg/mybatis-generator-gui
  cd mybatis-generator-gui
  mvn jfx:jar
  cd target/jfx/app/
  java -jar mybatis-generator-gui.jar
  ```

* 方法三: IDE中运行

  Eclipse or IntelliJ IDEA中启动, 找到`com.zzg.mybatis.generator.MainUI`类并运行就可以了（主要你的IED运行的jdk版本是否符合要求）

* 方法四：打包为本地原生应用，双击快捷方式即可启动，方便快捷

  如果不想打包后的安装包logo为Java的灰色的茶杯，需要在pom文件里将对应操作系统平台的图标注释放开

  ```bash
  #<icon>${project.basedir}/package/windows/mybatis-generator-gui.ico</icon>为windows
  #<icon>${project.basedir}/package/macosx/mybatis-generator-gui.icns</icon>为mac
  mvn jfx:native
  ```

  另外需要注意，windows系统打包成exe的话需要安装WiXToolset3+的环境；由于打包后会把jre打入安装包，两个平台均100M左右，体积较大请自行打包；打包后的安装包在target/jfx/native目录下

### 注意事项
* 本自动生成代码工具只适合生成单表的增删改查，对于需要做数据库联合查询的，请自行写新的XML与Mapper；
* 部分系统在中文输入方法时输入框中无法输入文字，请切换成英文输入法；
* 如果不明白对应字段或选项是什么意思的时候，把光标放在对应字段或Label上停留一会然后如果有解释会出现解释；



- - -
Licensed under the Apache 2.0 License

Copyright 2017 by Owen Zou
