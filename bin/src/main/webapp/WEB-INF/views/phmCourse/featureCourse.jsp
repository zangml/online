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
        <span class="categoryTitle">特征提取与降维</span>
        <div style="clear:both"></div>
        <div>
            <h4>  一、特征提取</h4>
            <p></p>
            <h5>1.1 特征提取的目的</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特征提取的目的是通过合适的数据分析方法从原始数据中提取与建模相关的有效特征，从而减小数据量，提高算法效率。</p>
            <h5>1.2 特征提取的常用方法</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;特征提取常用的方法包括时域特征提取、频域特征提取和时频域特征提取。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时域特征包括：RMS（有效值）、峰峰值、峭度、歪度、均值、均方根、脉冲因数、波峰因数等</p>
            <br>
            <img src="http://image.phmlearn.com/3_1_1.jpg" width="100%">
            <br>
            <br>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;频域特征提取包括：频带能量提取和特征频率提取。频带能量提取是指在频谱里边制定的频段内提取该频段的能量，常在FFT频谱或功率谱里边进行，以FFT频谱为例，当需要提取某一段内的频谱能量，我们可以把这段内所有的幅值相加作为该段频谱的能量；特征频率提取是指在制定的频率点提取该频率点对应的幅值。</p>
            <br>
            <img src="http://image.phmlearn.com/3_1_2.jpg" width="100%">
            <br>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时频域特征提取常用方法包括短时傅里叶变换（STFT）、小波分析等。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 时频域分析适用于非平稳信号，针对非平稳信号的特征提取可考虑时频域分析，时频域分析的优点在于可以在时间、频率、幅值三个维度对信号进行分析。</p>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;小波分析为例，采用不同的小波基会对结果产生很大的影响，因此在实际中应该慎重选择恰当的小波基来获得更为准确的结果。</p>
            <br>
            <img src="http://image.phmlearn.com/3_1_3.jpg" width="100%">
            <br>
            <h4> 二、特征选择</h4>
            <p></p>
            <h5>2.1 特征选择的目的</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们在提取完特征之后，通常要对特征进行选择，其目的在于减少数据量，避免数据量过大，为后续数据处理提供更好的理解，同时可以减少传感器的安装数量，提高算法的计算效率等等。例如我们在进行轴承健康状况监测的时候，通常认为震动比温度对轴承的影响更大，因此在选择安装传感器的时候，可以选择安装震动传感器，不安装温度传感器。</p>
            <p></p>
            <h5>2.2 特征选择常用方法</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于经验的方法、基于封装的特征选择方法、过滤法。</p>
            <br>
            <img src="http://image.phmlearn.com/3_2_1.jpg" width="100%">
            <br>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fisher Score 原理是通过判断每个特征和各类样本之间方差大小来对各特征进行评分，若该特征与某类样本方差小，与其他类样本方差大，则该特征得分高，属于有效特征，利于数据分析。计算方法如下：</p>
            <br>
            <img src="http://image.phmlearn.com/3_2_2.jpg" width="20%">
            <img src="http://image.phmlearn.com/3_2_3.jpg" width="100%">
            <br>

            <h4> 三、 降维</h4>
            <p></p>
            <h5>3.1 降维的目的</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有时在特征选择完成后，要进行降维，将许多特征降到几个维度，降维的目的在于减少计算量，提高计算效率，同时可以减少原始数据之间的相关性，提高数据的泛化能力。</p>
            <br>
            <img src="http://image.phmlearn.com/3_3_1.jpg" width="100%">
            <br>
            <h5>3.2 降维常用方法</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 常用的降维方法是基于PCA（主成分）的降维，它是通过空间转换将高维的数据转化为低维数据，降维后的数据能够保留原始数据的绝大部分信息。</p>
            <br>
            <img src="http://image.phmlearn.com/3_3_2.jpg" width="100%">
            <br>
            <h5>3.3 PCA降维的流程</h5>
            <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 首先对原始数据进行特征提取，然后对提取到的特征进行标准化处理，接着计算标准化处理以后的特征的协方差矩阵，然后对协方差矩阵进行特征值分解，会得到多个特征值及其特征向量，为了使降维后的数据包含原始数据的绝大部分信息，我们通常选取较大的特征值及其特征向量，来计算主成分。</p>
            <br>
            <img src="http://image.phmlearn.com/3_3_3.jpg" width="100%">
            <br>
        </div>
        <div style="clear:both"></div>
    </div>
</div>
</html>