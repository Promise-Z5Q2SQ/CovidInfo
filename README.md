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

前端还包含以下.xml文件：

![image-20200911143345527](/Users/sj-zhang/Library/Application Support/typora-user-images/image-20200911143345527.png)

以上文件包括了新闻主界面、新闻滚动栏界面、搜索界面、标签界面、数据显示界面、图谱界面等。

#### 1.2、后端

后端包括以下java类：

```
前后端接口：
ConnectInterface 
疫情数据读取及存储类：
DataLoader
Data
疫情图谱实体读取及存储类：
EntityLoader
Entity
新闻读取及存储类：
NewsLoader
News
疫情学者信息读取及存储类：
ScholarLoader
Scholar
聚类搜索关键字：
Keyword
数据库及数据操作接口：
Server
```

### 二、具体实现

#### 2.1、前端实现

#### 2.2、后端实现

##### 2.2.1、文件接口的读取

在以Loader结尾的各文件中，采用了org.json包函数进行json文件的解析，例如：

```
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

分析每一文件的结构后可以相似地完成这部分操作



##### 2.2.2、数据接口的实现

在数据接口的处理上，我们尽可能把单位操作都分装在不同的函数中，以便前端根据需求进行调用；对于几种Loader类，均采用静态方法；对于Server类，采用单例模式。

##### 2.2.3、多线程

考虑到从url阅读json对内存资源的高需求，以及新闻客户端对前后端操作顺序的严格要求，我们利用RXjava库，利用观察者模式对各个接口函数的实现顺序进行规划。通过传递Single类型参数实现前后端的进程切换与相互连接，例如ConnectInterface类的以下获得实体的函数：

```
public static Single<List<Entity>> GetEntities(final String name) {
    return Flowable.fromCallable(new Callable<List<Entity>>() {
        @Override
        public List<Entity> call() throws Exception {
            return EntityLoader.GetEntity(name);
        }
    }).flatMap(new Function<List<Entity>, Publisher<Entity>>() {
        @Override
        public Publisher<Entity> apply(List<Entity> entities) {
            return Flowable.fromIterable(entities);//fixme 如果运行了这一句代表网络出现问题没有正常返回
        }
    }).toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
}
```

在前端通过subsribe()函数的嵌套可以提取出包裹在Single模板类中的所需数据并进行处理。







