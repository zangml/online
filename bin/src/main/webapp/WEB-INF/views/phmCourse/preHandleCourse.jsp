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

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div class="homepageCategoryProducts">
        <div class="left-mark"></div>
        <span class="categoryTitle">数据预处理</span>
        <div style="clear:both"></div>
        <div>
            <h4>  一、  数据预处理目标</h4>
            <p></p>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对于数据处理来说，核心目标就是要降低工业场景中数据3B问题对后续建模过程的影响。2015年，李杰教授对工业场景工业大数据分析的几个挑战做了一个非常精辟的一个概括：在工业场景里主要有Broken、Bad Quality、Background这三个B的挑战。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一个Broken是指数据的碎片化，在现场的工业环境之中，数据是分散在很多个信息系统中的，包括常见的这种SCADA系统，还有可能一些震动的CMS系统，还有管理工单ERP等运维辅助相关的系统数据，很难像互联网场景那样规整。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二个挑战是数据的质量很差，同样也是来源于工业环境的特殊性，环境比较恶劣，所以采集出来的一些数据的噪声，包括有时会存在很多异常点的现象，有的时候还是非常严重的。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三个问题是Background，我们称之为数据的背景性，就是说采集上来数据是受到设备的参数、运行工况、运行状态，环境等很多背景信息的影响，甚至包括传感器安装的位置，这些都需要在数据处理的阶段都加以整体化的考虑。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所以，在数据做预处理的时候，几个最核心的目标就是要能够把这3B问题尽可能的降低，从而使得后面的建模过程更加的顺畅，而且达到模型理想的效果。
                总结下来数据处理的目标：第一个是要在这个阶段检测数据的质量，不管是传感器采上来的数据，还是主控系统接出来的数据，还是震动信号，要确保接下来在建模过程中的异常数据的干扰是非常小的，要在这个阶段把数据质量尽可能保证住。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二个目标就是要在这个过程中去识别数据的一些背景信息，包括刚刚提到的一些运行状态，或者叫工况，要在这个阶段能够把不同的工况分割出来，而且有些时候要对不同工况下的数据做一定的标准化处理，以便后面的一些特征提取等建模工作。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三个任务就是要在这个过程中整合碎片化的数据，包括比较常见的数据对齐的问题，比如刚刚举到这个例子，在传感器中，在控制器中，在其他的外接系统中采集出来的不同的数据，不同信息系统中提供的数据，要做数据的对齐和整合。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后一点就是在数据处理过程中，通过数据的变换来强化数据中能够指导后面建模的线索。比如在风机场景中，我们可能会计算一些叶间速比、风能利用指数等等更能够表征风机运行状态的物理量来辅助后续的建模。所以这个是我们数据预处理的一些目标。</p>
            <br>
            <img src="http://image.phmlearn.com/2_1_1.jpg" width="100%">

            <h4>  二、  数据预处理常用方法</h4>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在这一讲中介绍六种常见的数据处理的方法，包括工况分割，数据清洗，平滑，针对振动数据的质量检测，再到数据的规划，数据的样本平衡和数据的分割。 这六种常用的方法并不一定在每一次的建模场景中都需要用到，需要根据自己遇到的问题选择适当的方法来做适当的数据处理。</p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_1.jpg" width="100%">
            <p></p>
            <h5>2.1 数据预处理——工况分割</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工号分割的目的是要把设备在不同运行状态下的数据分割出来，之后做有针对性的特征提取，或者是后续的信号处理。常用的一些用于分割工况的参数，有速度，包括流速转速等；环境的参数，比如湿度温度；负载信息；流程性的信息，包括加工的任务，用户的ID或Recipe的ID等。</p>
            <p>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下图是一个典型的工况分割的案例。左图的数据可以明显看到有爬升态的过程，有稳态，有下降过程。通过使用一些其他参数作为工况参数来分割这个数据之后，可以得到右边的三个分割段，分别是两个暂态：爬升过程和下降过程，还有一个稳态的过程。得到不同的数据段之后，就可以采用不同的特征提取方法来做处理。比如爬升段，把爬升速度作为一个特征，而稳态看稳定性标准差或是均值等特征。
            </p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_2.jpg" width="100%">
            <p></p>
            <h5>2.2 数据预处理——数据清洗</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据清洗就是要把数据中的异常点尽可能的剔除，降低对模型训练的干扰。在数据清洗过程中有几种常用的算法。比如，基于数据分布的算法有One Class SVM、Robust covariance、Isolation forest、Local outlier factor等。在选取异常点检测方法的时候，有时会根据对数据分布的直观的理解去选择，有时也需要做一些尝试；针对时间序列的处理方法，有时候直接用Smoothing方法降低数据造成的影响。比如例图中原始的数据是蓝色的信号，它的波动范围是比较大的，最后通过Smoothing模型，在这个场景中选择的是running mean方法把这一个时间段的平均值取出来，然后作为最后特征。</p>
            <p>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;典型的案例就是对风机功率曲线做的异常点剔除，实际采集上来的信号的噪声是非常大的（红色标记）。这个就是用One Class SVM方法红色噪点识别出来，经过剔除之后，得到了清洗过的理想的功率曲线（右图），来做后续的风机的性能评估。
            </p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_3.jpg" width="100%">
            <p></p>
            <h5>2.3 数据预处理——（振动）数据质量检测</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 数据质量检测往往针对振动信号比较多。如下图，左边是一段比较正常的振动信号，右边是一段不太正常的振动信号，可能是电磁干扰，或是传感器松动的情况，导致有很多异常点，它的振动信号平均值也比零要偏低一些。常见的质量检测方法，这里列举了一些，包括平均值的检测，RMS值的检测，能量守恒的检测等等。在对振动信号分析之前，也会适当地采用这样的一些方法去检测所分析的对象是否正常。对于不正常的信号，在后面的提取过程中做一些特别的处理，尤其是信号频域分析中做一些特别处理，或者把这一段信号直接丢弃，避免它影响接下来建模的结果。</p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_4.jpg" width="100%">
            <p></p>
            <h5>2.4 数据预处理——数据归一化</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据归一化的含义是将数据中不同的变量要转换到同样的scale，同样的取值区间。它的目的一个是要提升数据建模的精度。第二是加速整个参数优化求解的过程，尤其是对于SVM支持向量机或者神经网络的建模方法，数据归一化显得特别重要，它的价值是保留数据中的pattern，而弱化取值大小对建模的影响。典型的案例是，CNC机床有不同类型的信号，比如电流信号，震动信号，经过归一化处理之后，可以把他们整合在同一个取值范围下。归一化常见的方法是减去平均值除以标准差。</p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_5.jpg" width="100%">
            <p></p>
            <h5>2.5 数据预处理——数据样本平衡</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据的样本平衡在工业场景的建模之中会经常用到，因为往往遇到的一个问题是：采集上来数据的标签非常不均等，尤其是针对小概率事件，时长一百小时的数据中可能只有两三分钟的数据是有异常的。所以在这种过程中往往会采用过采样、重采样、欠采样等不同的采用方法去改善数据不同类别之间的平衡性。比如当故障数据很少的时候，用过采样的方法把它的数量增多，或是把正常的数据降采样来保证不同类别数据之间的这种平衡，来提升分类模型建模的效果。比如在下图中，红色数据是故障数据，通过过采样之后，可以把它数据增强，数据的个数增多，然后做最后的建模。</p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_6.jpg" width="100%">
            <p></p>
            <h5>2.6 数据预处理——数据分割</h5>
            <p></p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据分割是把数据集分割成好几份来分别用作不同的目的。比如常用的生成的三类：训练集、验证集、测试集。训练集就是要训练模型的参数，验证集往往是对模型的参数进行调优，而且可以作为初步评估模型是否准确的数据样本，测试集往往是一类全新的数据，没有在训练和验证过程中出现，是用来得到最后的模型的指标。 在做分割的时候，会采用分层抽样的方法，对于分类模型采用分层抽样的方法来确保训练集、验证集之间不同类型的样本的比例，和整个population的比例是基本保持一致的。</p>
            <p></p>
            <img src="http://image.phmlearn.com/2_2_7.jpg" width="100%">
            <p></p>
        </div>
        <div style="clear:both"></div>
    </div>
</div>
</html>