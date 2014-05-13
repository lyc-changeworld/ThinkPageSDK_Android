AndroidSDK
==========
# 简介

心知天气Android SDK 为第三方应用提供了简单易用的心知天气API调用服务，使第三方应用可以快速整合心知天气API的所有功能。v2.0版SDK主要有三大特点：

* 完美兼容：全面支持心知天气API 2.0，支持实时天气，预报天气，空气质量，生活指数的查询。 

* 简单使用：只需在您的应用中添加几行代码就能访问心知天气API 2.0版的全部服务。对API调用结果的解包封装，让您能抛开对心知天气API细节的研究，从而更加专注优化你的应用。 

* 专业性能：异步调用模式保证了你的应用程序的无延迟响应。对并发请求的高性能处理让你的应用开发更省心、高效。

# 下载使用
下载解压zip包，点击进入src目录，SDK源文件都在这里面。把loopj和thinkpage两个目录中的所有源文件添加到你的工程目录下，就可以使用SDK的全部功能了。

# TPWeatherManager 类
应用通过以下构造函数初始化一个TPWeatherManager实例：
```java
public TPWeatherManager(String apiKey, TPWeatherManagerDelegate delegate)
```
# 发送天气服务请求
在创建好TPWeatherManager对象实例后，调用以下几个fetch API，向心知天气服务器发起一个天气请求:
获取和城市当前的天气信息
```java
public void fetchCurrentWeather(TPCity city, TPWeatherReportLanguage language, TPTemperatureUnit unit)
```
获取城市未来几天的天气预报
```java
public void fetchFutureWeather(TPCity city, TPWeatherReportLanguage language, TPTemperatureUnit unit)
```
获取城市的空气质量信息
```java
public void fetchAirQuality(TPCity city, TPWeatherReportLanguage language, TPTemperatureUnit unit, TPAirQualitySource aqi)
```
获取城市的天气相关咨询（穿衣，洗车等）
```java
public void fetchWeatherSuggestions(TPCity city, TPWeatherReportLanguage language, TPTemperatureUnit unit)
```
获取和城市天气相关的所有信息
```java
public void fetchAllWeather(TPCity city, TPWeatherReportLanguage language, TPTemperatureUnit unit, TPAirQualitySource aqi)
```
这五个API和心知天气API对应，大家可以对照查看http://www.thinkpage.cn/weather/api/ API参数解释：

TPCity 代表一个城市，你可以用new TPCity("城市名"); 创建一个临时的城市对象

inLanguage 返回结果的语言： kEnglish：英语 kSimplifiedChinese：简体中文 kTraditionalChinese：繁体中文

unit 单位： kCelsiu：摄氏度 kFahrenheit：华氏度

aqi 空气质量的数据源： kAQINone：无 kAQICity：城市检测站 kAQIAll：城市所有监测站
# 获取天气请求结果
TPWeatherManager 的fetch API都是异步执行的。当你调用以上任意一个API后，TPWeatherManager会自动在后台线程下载最新的天气数据，并在准备就绪后通过以下接口通知用户：
```java
void OnRequestSuccess(TPCity city, TPWeather report);
void OnRequestFailure(TPCity city, String errorString);
```
# TPWeatherManagerDelegate interface
TPWeatherManager 需要API使用者实现TPWeatherManagerDelegate interface
```java
public interface TPWeatherManagerDelegate 
{
	void OnRequestSuccess(TPCity city, TPWeather report);
	void OnRequestFailure(TPCity city, String errorString);
}
```
在TPweatherManager完成天气数据的下载和解析后，会调OnRequestSuccess:接口，告诉应用程序对城市(TPCity )city的天气数据请求已经完成，所有信息解析好并存放在 TPWeather report 里面。

在TPweatherManager因为种种原因对特定请求失败的时候，会调OnRequestFailure 方法，告诉应用程序对城市(TPCity ) city的天气数据请求失败。
# 解析好的数据
void OnRequestSuccess(TPCity city, TPWeather report); 将解析好的天气信息封装在TPWeather里返回来。TPWeather结构常用方法如下：
```java
public class TPWeather {

	public String status; //返回的状态值
	public TPCity city; //城市
	public Date lastUpdate; //最后更新时间
	public TPWeatherNow currentWeather; //当前天气的信息
	public TPAirQuality[] airQualities; //空气质量信息数组
	public String sunriseTime; //当天的日出时间
	public String sunsetTime; //当天的日落时间
	public TPWeatherSuggestions weatherSuggestions; //生活指数信息
	public TPWeatherFuture[] futureWeathers; //未来几天的天气预报
}
```

# 更多信息
更多信息请访问心知天气网站： www.thinkpage.cn
