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
    <span class="categoryTitle">健康评估</span>
    <div style="clear:both"></div>
    <div>
        <h4>1  健康评估的概念</h4>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康评估就是将高维特征向量转为单个健康指数（健康状态、健康置信度CV等），从而对设备当前的健康状态进行评估。健康指标有多种形式，可能是一个物理量，例如桥梁裂纹、刀具磨损量等，也可能是基于概率的，例如是一个0~1之间的概率值；还可以是一个数学意义上的标量这样的虚拟健康指标。</p>
        <br>
        <img src="http://image.phmlearn.com/4_1_1.jpg" width="100%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 以数据驱动的将康评估通常分为两个阶段，包括训练阶段和验证阶段。</p>
        <br>
        <img src="http://image.phmlearn.com/4_1_2.jpg" width="100%">
        <br>
        <h4>2 健康评估常用算法</h4>

        <h5>2.1 逻辑回归</h5>

        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;逻辑回归是一种广义的线性回归分析模型，是一种监督式学习模式。</p>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;传统的线性回归是通过自变量的线性组合估计因变量：</p>
        <br>
        <img src="http://image.phmlearn.com/4_2_1.jpg" width="50%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;逻辑回归则是通过自变量的线性组合来估计因变量的概率:</p>
        <br>
        <img src="http://image.phmlearn.com/4_2_2.jpg" width="50%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;逻辑回归要求数据既要有健康状态，也要有故障状态的数据，同时数据量要比较均衡，在实际中通常将正常状态的概率设为0.95，故障状态的概率设为0.05。</p>
        <br>
        <img src="http://image.phmlearn.com/4_2_3.jpg" width="50%">
        <br>
        <img src="http://image.phmlearn.com/4_2_4.jpg" width="50%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是一个0~1之间的值，用来表示健康的概率值。</p>

        <h5>2.2 统计模式识别</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;适用于我们当前没有故障数据，而仅仅只有健康状态的数据时。其前提是假设数据整体服从高斯分布的，对于非高斯分布的情况，通常先使用高斯混合模型进行拟合。
            通过计算正常状态和当前状态的分布曲线的重叠部分面积（L2距离）来估算当前的健康状态。</p>
        <br>
        <img src="http://image.phmlearn.com/4_2_5.jpg" width="100%">
        <br>
        <img src="http://image.phmlearn.com/4_2_6.jpg" width="100%">
        <br>
        <h5>2.3 自组织映射神经网络</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自组织映射神经网络（SOM) 通过一些自学习、竞争学习的方式把训练数据映射到一张蜂窝图上，用来表示类别之间的距离大小，颜色越趋近于红色距离越远，趋近于蓝色代表距离越近。SOM既可以用于无监督学习，又可以用于训练带标签的数据。</p>
        <br>
        <img src="http://image.phmlearn.com/4_2_7.jpg" width="100%">
        <br>
        <img src="http://image.phmlearn.com/4_2_8.jpg" width="100%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计算出的MOE值（最小量化误差）就可以表示当前的健康值。整体步骤是先训练出一张U-matrix图，然后拿到当前状态的一系列的特征向量，把它放到这张U-matrix里面，看它现在处于的位置，找到离它最近到的一个单元（best matching unit) 计算它们之间的欧式距离作为MQE值。由图中右图可看到有故障的MQE值明显变大，正常状态趋于零。</p>

        <h5>2.4 自联想神经网络</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自联想神经网络（ANN）为三层结构，最外层是输入输出层，然后往里是映射层，最里面是瓶颈层。它适用于众多的特征向量之间都是非线性的关系，一般的方法无法挖掘信息。组织映射神经网络（SOM) 通过一些自学习、竞争学习的方式把训练数据映射到一张蜂窝图上，用来表示类别之间的距离大小，颜色越趋近于红色距离越远，趋近于蓝色代表距离越近。SOM既可以用于无监督学习，又可以用于训练带标签的数据。</p>
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;它是用健康状态数据训练出一个健康状态时的模型，然后把当前状态数据拿到模型中，通过计算当前状态数据与健康状态数据之间的模型残差，残差值越大，说明离健康状态越远。</p>
        <img src="http://image.phmlearn.com/4_2_9.jpg" width="100%">
        <br>
        <p>由图可以看出，正常状态和衰退的风速仪，对模型拟合的残差有明显区别。</p>
        <img src="http://image.phmlearn.com/4_2_10.jpg" width="100%">

        <h4> 三、 风速仪健康评估案例</h4>
        <br>
        <img src="http://image.phmlearn.com/4_3_1.jpg" width="100%">
        <br>
        <img src="http://image.phmlearn.com/4_3_2.jpg" width="100%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(使用AANN）自组织神经网络模型对数据进行训练，训练之间对数据进行归一化，得到结果后进行聚类并计算残差</p>
        <br>
        <img src="http://image.phmlearn.com/4_3_3.jpg" width="100%">
        <br>
        <img src="http://image.phmlearn.com/4_3_4.jpg" width="100%">
        <br>
        <img src="http://image.phmlearn.com/4_3_5.jpg" width="100%">

        <div style="clear:both"></div>
    </div>
</div>
</div>
</body>
</html>