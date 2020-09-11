## JAVA 小学期大作业报告

​							计83 朱书琦 2018011358

​							计85 张庶杰 2018011415

### 一、代码结构

代码主要分为前端界面与后端接口：

#### 1.1、前端

前端界面包括以下java类：

```
疫情数据显示：
DataFragment         	//疫情数据显示界面，包含搜索栏和图表栏
疫情知识图谱显示：			
KnowledgeFragment			//知识图谱数据显示界面
新闻聚类显示：
ClusterActivity				//新闻聚类主界面
ClusterHolderFragment	//新闻聚类显示界面，包括关键字标签和新闻浏览窗口
新闻显示：
NewsFragment					//新闻显示界面
NewsHolderFragment		//新闻浏览界面，包括上拉下拉工具栏与新闻浏览窗口
NewsPagerAdapter			//新闻详情界面的适配器类
NewsPage							//新闻详情界面
学者信息显示：
ScholarFragment				//学者列表界面
ScholarHolderFragment	//学者浏览界面
ScholarPagerAdapter		//学者详情界面的适配器类
ScholarPage						//学者详情界面
浏览历史显示：
HistoryActivity				//历史记录界面
HistoryAdapter				//历史记录界面适配器类
标签：
TabSelectActivity			//标签增、删

```

其中，data文件夹实现疫情数据显示功能；knowledge文件夹实现疫情知识图谱功能；news文件夹实现新闻的分类、显示、刷新功能；scholar文件夹实现新冠相关学者检索功能；剩余类为app界面类。

前端还包含以下.xml文件：

![image-20200911143345527](/Users/sj-zhang/Library/Application Support/typora-user-images/image-20200911143345527.png)

以上文件包括了新闻主界面、新闻滚动栏界面、搜索界面、标签界面、数据显示界面、图谱界面等。

#### 1.2、后端

后端包括以下java类：

![image-20200911143846550](/Users/sj-zhang/Library/Application Support/typora-user-images/image-20200911143846550.png)

以Loader结尾的类实现了读取数据的方法，其余为数据包装类；Server类为数据库；ConnectInterface类实现了前后端连接的接口。



### 二、具体实现

#### 2.1、前端实现









