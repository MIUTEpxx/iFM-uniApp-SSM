# iFM uniApp+SpringBoot
 播客App 前后端分离移动端项目

**前端:**

uni-app + Vue3 + Typescript + Pinia + Vite

**后端:**

SpringBoot+Maven+MyBatis+MySQL



### 项目运行

**数据库:**

以Navicat为例, 在MySQL连接中, 右键选择运行SQL文件

![{295CEFAB-5F3C-4CCB-81C0-E8F6C4311CB0}](C:/Users/Miute/AppData/Local/Packages/MicrosoftWindows.Client.CBS_cw5n1h2txyewy/TempState/ScreenClip/{295CEFAB-5F3C-4CCB-81C0-E8F6C4311CB0}.png)

选择SQL目录下的ifm.sql文件, 点击开始

![image-20241231220358796](README.assets/image-20241231220358796.png)

最终成功导入数据库

![image-20241231220458750](README.assets/image-20241231220458750.png)

**后端:**

以IDEA为例, 点击左上角"文件", 选择打开

![image-20241231220654529](README.assets/image-20241231220654529.png)

选择 iFM-server目录, 点击确定

![image-20241231220733860](README.assets/image-20241231220733860.png)

将数据库配置信息更改为自己的

![image-20241231220926768](README.assets/image-20241231220926768.png)

运行后端

![image-20241231222940141](README.assets/image-20241231222940141.png)

成功

![image-20241231223003712](README.assets/image-20241231223003712.png)

**客户端:**

以HBuilder X为例 ,点击左上角"文件",选择打开目录

![{22317AC0-8A70-40CD-878C-BC2EB5CF2C9E}](C:/Users/Miute/AppData/Local/Packages/MicrosoftWindows.Client.CBS_cw5n1h2txyewy/TempState/ScreenClip/{22317AC0-8A70-40CD-878C-BC2EB5CF2C9E}.png)

选择 iFM_client目录

![image-20241231221834769](README.assets/image-20241231221834769.png)

在iFM_client目录下, 右键选择"在终端中打开"

![{B445A489-E423-4734-BF02-416DA2C09F82}](C:/Users/Miute/AppData/Local/Packages/MicrosoftWindows.Client.CBS_cw5n1h2txyewy/TempState/ScreenClip/{B445A489-E423-4734-BF02-416DA2C09F82}.png)

终端中执行命令:

```cmd
npm install
```

回到HBuilder X, 点击上方"运行", 选择"运行到浏览器", 选择任意浏览器

![{E7BE2596-81DB-46F5-9036-90A8BBC514D3}](C:/Users/Miute/AppData/Local/Packages/MicrosoftWindows.Client.CBS_cw5n1h2txyewy/TempState/ScreenClip/{E7BE2596-81DB-46F5-9036-90A8BBC514D3}.png)

浏览器中按下"F12", 选择"切换设备仿真"

![image-20241231222848087](README.assets/image-20241231222848087.png)
