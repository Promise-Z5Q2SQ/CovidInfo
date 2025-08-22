## JAVA 小学期大作业报告

计83 朱书琦 2018011358 主要负责前端代码

计85 张庶杰 2018011415 主要负责后端代码

### 一、代码结构

代码主要分为前端界面与后端接口：

#### 1.1、前端

前端界面包括以下主要的功能java类：

> 新闻显示：
>
> NewsFragment //新闻显示框架
>
> NewsHolderFragment //新闻浏览界面，包括上拉下拉工具栏与新闻浏览窗口
>
> NewsPageActivity //新闻详情界面

> 疫情数据显示：
>
> DataFragment //疫情数据显示界面，包含搜索栏和图表栏

> 疫情知识图谱显示：			
>
> KnowledgeFragment //知识图谱数据显示界面

> 新闻聚类显示：
>
> ClusterActivity //新闻聚类主界面
>
> ClusterHolderFragment //新闻聚类显示框架，包括关键字标签和新闻浏览窗口

> 学者信息显示：
>
> ScholarHolderFragment //学者浏览框架
>
> ScholarPage //学者详情界面
>
> ScholarFragmentActivity //学者列表界面

> 标签：
>
> TabSelectActivity //标签增删等设置界面

> 浏览历史显示：
>
> HistoryActivity //查看历史记录界面

以及`MainAcitivity`和一系列的组件适配器`Adapter`类，在布局方面前端还包含以下xml文件：

> 新闻显示：
>
> fragment_news.xml //新闻显示组件
>
> news_list.xml
>
> news_item.xml
>
> activity_news_page.xml //新闻正文界面
>
> search_history_item.xml //搜索历史记录项单元

> 疫情数据显示：
>
> fragment_data.xml //数据显示组件
>
> data_chart.xml

> 疫情知识图谱显示：
>
> fragment_knowledge.xml //知识图谱显示组件
>
> scholar_list.xml
>
> knowledge_item.xml
>
> relation_item.xml //关系或属性标签单元

> 新闻聚类显示：
>
> activity_cluster.xml //聚类显示组件
>
> cluster_list.xml

> 学者信息显示：
>
> fragment_scholar.xml //学者信息组件
>
> scholar_list.xml
>
> scholar_item.xml
>
> activity_scholar_page.xml //学者详情页面

> 标签修改界面：
>
> tab_select_page.xml
>
> tag_item.xml //标签增减单元

> 浏览历史记录：
>
> activity_history.xml

以上文件包括了新闻主界面、新闻滚动栏界面、搜索界面、标签界面、数据显示界面、图谱界面等。

#### 1.2、后端

后端包括以下java类：

> 前后端接口：
> 
>ConnectInterface 

> 疫情数据读取及存储类：
> 
>DataLoader
> 
> Data

> 疫情图谱实体读取及存储类：
> 
>EntityLoader
> 
> Entity

> 新闻读取及存储类：
> 
>NewsLoader
> 
> News

> 疫情学者信息读取及存储类：
> 
>ScholarLoader
> 
> Scholar

> 聚类搜索关键字：
> 
>Keyword

> 数据库及数据操作接口：
> 
>Server

### 二、具体实现

#### 2.1、前端实现

##### 2.1.1、新闻界面

新闻界面由顶部搜索栏（带图片装饰）与新闻展示栏两部分组成。

其中新闻展示栏集`RecyclerView`、`SmartRefreshLayout`两个类为一体，添加了装饰线和加载动画，加载流畅。新闻的点击进入详情页利用`CardView`类的`click`事件实现，加载内容为新闻的正文内容。

<img src="/README.assets/image-20200911231209720.png" alt="image-20200911231209720" style="zoom:25%;" />

##### 2.1.2、标签增减

标签增减功能位于主页右下角的设置栏中，我们利用`ChipsView`组件及其相关动作函数实现了这一功能，方便且带有动态效果。我们利用`Intent`在主界面`Activity`与工具栏`Activity`之间传递信息，从而实现了近乎同步的标签增删操作。

<img src="/README.assets/image-20200911231255362.png" alt="image-20200911231255362" style="zoom:25%;" />

##### 2.1.3、疫情知识图谱

知识图谱界面经过精心设计，支持搜索功能，利用`Bitmap`类对词条图片进行读取并展示。同时对于一个确定的实体，能够展示所有与之相关的其余所有实体并利用`CardView`类，重写其点击事件，以此实现了在实体详情页面之间的跳转。

<img src="/README.assets/image-20200911231302563.png" alt="image-20200911231302563" style="zoom:25%;" />

##### 2.1.4、疫情学者

疫情学者界面分为高关注学者与追忆学者两个标签，每个标签栏下有对应学者的信息（与新闻列表类似），且能够点击进入学者名片，查看学者头像、生平简介、主要贡献等信息。

<img src="/README.assets/image-20200911231334351.png" alt="image-20200911231334351" style="zoom:25%;" />

##### 2.1.5、疫情数据展示

展示窗口中，我们利用了`hellocharts`库进行折线图的绘制，绘制内容为中数据库中有数据的地区的死亡人数、确诊人数、治愈人数数据，同时能够支持与用户的交互，用户输入查询地点，app进行对应数据的绘制。

<img src="/README.assets/image-20200911231310389.png" alt="image-20200911231310389" style="zoom:25%;" />

##### 2.1.6、浏览历史显示

在主界面右下角的工具栏中能够查看浏览历史，此处我们追踪了每一个新闻对象的”被浏览“事件，并在程序后台自动进行历史记录，同时能够在离线状态下进行查看。

##### 2.1.7、分享

我们利用系统自带的分享功能进行分享，目前能够通过任何能够发送文本的应用（包括蓝牙）分享包含新闻摘要与具体内容的纯文本。

<img src="/README.assets/image-20200911231406502.png" alt="image-20200911231406502" style="zoom:25%;" />

#### 2.2、后端实现

##### 2.2.1、文件接口的读取

在以`Loader`结尾的各文件中，采用了`org.json`包函数进行json文件的解析，例如：

```java
public static void RetrieveNewsFromJSON(JSONObject src, ArrayList<News> newsl) throws JSONException{
        JSONArray list;
        JSONObject obj;
        list = src.optJSONArray("data");
        for (int i = 0; i < list.length(); i++) {
            News news = new News();
            obj = list.getJSONObject(i);//这一页里的第i条新闻
            news.id = obj.optString("_id");
            news.content = obj.optString("content");
            news.date = obj.optString("date");
            news.seg_text = obj.optString("seg_text").split(" ");
            news.source = obj.optString("source");
            news.title = obj.optString("title");
            news.type = obj.optString("type");
            newsl.add(news);//把旧新闻一条一条加到新闻列表的末端
        }
    }
```

分析每一文件的结构后可以相似地完成这部分操作。



##### 2.2.2、数据接口的实现

在数据接口的处理上，我们尽可能把单位操作都分装在不同的函数中，以便前端根据需求进行调用；对于几种`Loader`类，均采用静态方法；对于`Server`类，采用单例模式。

##### 2.2.3、多线程

考虑到从url阅读json对内存资源的高需求，以及新闻客户端对前后端操作顺序的严格要求，我们利用`RXjava`库，利用观察者模式对各个接口函数的实现顺序进行规划。通过传递`Single`类型参数实现前后端的进程切换与相互连接，例如`ConnectInterface`类的以下获得实体的函数：

```java
public static Single<List<Entity>> GetEntities(final String name) {
    return Flowable.fromCallable(new Callable<List<Entity>>() {
        @Override
        public List<Entity> call() throws Exception {
            return EntityLoader.GetEntity(name);
        }
    }).flatMap(new Function<List<Entity>, Publisher<Entity>>() {
        @Override
        public Publisher<Entity> apply(List<Entity> entities) {
            return Flowable.fromIterable(entities);
        }
    }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
}
```

在前端通过`subsribe()`函数的嵌套可以提取出包裹在`Single`模板类中的所需数据并进行处理。在运行中较为困难的是异常的处理和离线状态下数据的传递，通过同种异常同一层处理的方法可以比较好地解决。

#### 2.3、聚类算法

本次实验中我们采用K-means聚类方法。具体操作方式为：先利用python的`sklearn`库中的tf-idf向量处理器将各新闻的seg-text列表处理为词向量矩阵，再进行K-means聚类（k=4），最后统计各类的关键字并进行输出。

聚类的数据为2020年9月11日早9：30从新闻列表接口获取的最新500条events类型新闻。

聚类结果为：

| 类别              | Cluster0     | Cluster1 | Cluster2 | Cluster3     |
| ----------------- | ------------ | -------- | -------- | ------------ |
| 关键字1，出现次数 | 冠状，37     | 新冠，43 | cov，38  | 临床试验，31 |
| 关键字2，出现次数 | 新型，27     | 病毒，33 | sar，31  | covid，29    |
| 关键字3，出现次数 | 冠状病毒，26 | 卫生，28 | 蛋白，14 | 试验，26     |
| 关键字4，出现次数 | 药物，25     | 疫情，25 | 感染，13 | 19，25       |
| 新闻总条数        | 74           | 229      | 70       | 127          |

聚类代码见附件`cluster.py`。



### 三、总结与心得

在第一次接触java语言和Android开发API后，就着手开发这样一个大的项目，着实是一次很大的挑战，我们小组主要按照前端后端的功能来进行分工，每个位置都需要学习很多的新知识，但还很高兴通过我们不懈的努力顺利完成了这个项目。这次项目提高了我们对java语言语法的熟练度，加深了对Android系统和接口的理解，让代码格局不再局限于小作业的一个文件几个函数，而是要统筹规划所有的接口和API的适配性，同时要兼顾前后端的交流顺畅性。
