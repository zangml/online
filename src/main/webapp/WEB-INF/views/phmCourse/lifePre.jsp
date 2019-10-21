<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link href="<%=basePath%>assets/styles/course.css" rel="stylesheet">

</head>
<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
<div class="homepageCategoryProducts">
    <div class="left-mark"></div>
    <span class="categoryTitle">剩余寿命预测</span>
    <div style="clear:both"></div>
    <div>
        <h4>1 剩余寿命预测概念介绍 </h4>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;各种部件都有其额定的使用寿命期限，剩余寿命预测就是要监测部件从当前到失效的时长，根据当前已经积累的数据，来估计部件接下来的衰退模式和趋势，计算出到失效的时长，从而更好地指导我们对部件进行预测性维护，更好地对部件进行管理，降低实际工作中部件突然失效的风险。</p>
        <br>
        <img src="http://image.phmlearn.com/6_1_1.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一般部件的寿命衰退模式都是像图中那样，寿命的大概前90%都是平稳期，只有到了寿命的后10%的阶段，才会进入快速衰退期，我们进行剩余寿命预测一般都是在快速衰退期进行的，而且由于在进行预测时，部件并没有发生故障，因此这是属于回归问题，因此都是采用回归算法。此外工况是影响剩余寿命的一个非常重要的因素。</p>
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在实际操作中，我们往往会选择多种曲线拟合方式，然后从中选择一条拟合度最好的曲线。</p>

        <img src="http://image.phmlearn.com/6_1_2.jpg" width="80%">
        <br>
        <h4>2 剩余寿命预测常用算法</h4>

        <h5>2.1 线性回归和非线性回归</h5>

        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这种回归类的算法通常都是通过之前的健康值或者特征值去作为自变量X预测Y,然后会用最小二乘法去求解最优的参数。</p>
        <br>
        <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回归分为线性回归和非线性回归，实际中会根据设备具体的衰退特性来选择适当的回归算法模型</p>
        <img src="http://image.phmlearn.com/6_2_1.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对于非线性可分问题，通常是通过核函数把低维的特征映射到高维的空间中。</p>
        <br>

        <h5>2.2  基于轨迹相似度的剩余寿命预测方法</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于轨迹相似度的剩余寿命预测（TSBP)就是通过一组全生命周期的数据来构建衰退曲线，当测试数据进入之后，会滑动的选择这段数据分布与曲线分许最相似的地方，以此作为当前的运行状态，并通过衰退曲线剩余的长度来给出设备的剩余使用寿命.</p>
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用此算法关键是要有设备的全生命周期的数据来建立衰退曲线。</p>
        <img src="http://image.phmlearn.com/6_2_2.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; TSBP的优点：它是一种非参数模型，不依赖对衰退模式的预先的假设；在预测时可以将多种衰退曲线的加权平均来作为最终曲线，减小误差，不需要对其中原理进行了解，特别适合场景复杂度特别高的情况。</p>
        <img src="http://image.phmlearn.com/6_2_3.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 剩余寿命评估有以下几个常用的指标：</p>
        <br>
        <img src="http://image.phmlearn.com/6_2_4.jpg" width="80%">
        <br>


        <h4> 三、航空发动机剩余寿命预测案例</h4>
        <br>
        <img src="http://image.phmlearn.com/6_2_5.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 训练数据是很多个发动机全生命周期的数据
            测试数据是部分时间的衰退数据，缺少使用条件等因素的影响</p>
        <br>
        <img src="http://image.phmlearn.com/6_2_6.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 选择基于轨迹相似度的训练模型TSBP
        </p>
        <img src="http://image.phmlearn.com/6_2_7.jpg" width="80%">
        <br>
        <img src="http://image.phmlearn.com/6_2_8.jpg" width="80%">
        <br>
        <img src="http://image.phmlearn.com/6_2_9.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择Sensor 11 观测到的数据作为工况特征 不同发动机该数据呈现很明的不同分布
            PCA进行降维</p>
        <br>
        <img src="http://image.phmlearn.com/6_2_10.jpg" width="80%">
        <br>
        <img src="http://image.phmlearn.com/6_2_11.jpg" width="80%">

        <div style="clear:both"></div>
    </div>
</div>
    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>
</html>