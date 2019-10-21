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
    <span class="categoryTitle">故障诊断</span>
    <div style="clear:both"></div>
    <div>
        <h4>1  故障诊断基本概念</h4>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 故障诊断的目标是将高维特征向量转换为状态标识（健康状态、失效模式一、失效模式二、......失效模式N）</p>
        <br>
        <img src="http://image.phmlearn.com/5_1_1.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于数据驱动的故障诊断通常也是分为训练阶段和测试阶段。先用历史数据训练出一个故障诊断模型，然后用当前状态的特征向量对其进行分类，计算出当前的状态标识，从而明确当前的状态是否出现故障和出现的哪种故障。</p>
        <br>
        <img src="http://image.phmlearn.com/5_1_2.jpg" width="80%">
        <br>
        <h4>2 故障诊断常用算法</h4>

        <h5>2.1  支持向量机</h5>

        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;支持向量机（Support Vector Machine, SVM）是一类按	监督学习方式对数据进行二分类的广义线性分类器（generalized linear classifier），其决策边界是对学习样本求解的最大边距超平面，其核心思想是找到最优分类面将正负样本分开，使得样本到分类面的距离尽可能远。</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_1.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对于非线性可分问题，通常是通过核函数把低维的特征映射到高维的空间中。</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_2.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SVM也可以进行多分类，其方法是采用迭代的方式，例如进行N分类可采用N-1次二分类来完成。</p>
        <br>

        <h5>2.2  自组织映射神经网络</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自组织是指学习和组织信息的能力，不需要为输入模式提供相应的依赖输出值，是一种无监督学习方式。</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_3.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 自组织映射神经网络（SOM) 可视化效果很好，通过一些自学习、竞争学习的方式把训练数据映射到一张蜂窝图上，颜色用来表示类别之间的距离大小，颜色越趋近于红色距离越远，趋近于蓝色代表距离越近。数字代表类别编号。在训练出故障诊断模型后，通过当前状态的特征向量输入到模型中计算出结果，结果打到那个编号趋于，当前状态就属于编号所对应的类，效果非常直观。</p>
        <img src="http://image.phmlearn.com/5_2_4.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; SOM的优缺点：</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_5.jpg" width="80%">
        <br>

        <h5>2.3  贝叶斯网络</h5>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PHM领域，有些特征的条件概率是非常重要的，利用不同时间之间的因果关系来判断也是一个非常重要的途径，贝叶斯网络就是利用条件概率来进行判断的。</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_6.jpg" width="80%">
        <br>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贝叶斯网络是通过条件独立性的联合概率分布的图形表。分布表中的数据通常是根据各种途径，包括实际经验、机理等，在一些复杂的图模型中，表的规模非常庞大，甚至有的数据是无法获得的。
        </p>
        <img src="http://image.phmlearn.com/5_2_7.jpg" width="80%">
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;利用贝叶斯网络进行故障诊断，首先第一步需要定义整个图模型的结构，节点数、形式和状态，这是整个贝叶斯网络的精髓所在，然后需要定义每个节点的条件概率分布，再利用数据训练BBN模型。</p>
        <br>
        <img src="http://image.phmlearn.com/5_2_8.jpg" width="80%">
        <br>

        <h4> 三、 轴承故障诊断案例</h4>
        <br>
        <img src="http://image.phmlearn.com/5_3_1.jpg" width="80%">
        <br>
        <img src="http://image.phmlearn.com/5_3_2.jpg" width="80%">
        <br>
        <img src="http://image.phmlearn.com/5_3_3.jpg" width="80%">

        <div style="clear:both"></div>
    </div>
</div>
</div>
</body>
</html>