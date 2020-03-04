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

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/css/api.css">
    <title>基于机器学习的PHM系统</title>
</head>
<body>

<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp"/>

    <div class="container">
        <div class="row">
            <div class="col-2" style="border-right: 2px solid #f5f5f5">
                <section>
                    <div class="navHeader">API列表</div>
                    <div class="accordion" id="accordionExample">

                        <div class="card">
                            <div class="card-header" id="headingSix">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                                        用户认证
                                    </button>
                                </h2>
                            </div>

                            <div id="collapseSix" class="collapse show" aria-labelledby="headingSix" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <li><a href="/api/get_list/17">获取Api身份信息</a></li>
                                        <li><a href="/api/get_list/18">获取access_token</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header" id="headingOne">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                        数据预处理
                                    </button>
                                </h2>
                            </div>

                            <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <li><a href="/api/get_list/3">非均衡处理</a></li>
                                        <li><a href="/api/get_list/4">异常值检测</a></li>
                                        <li><a href="/api/get_list/5">数据归一化</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header" id="headingTwo">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                        特征提取
                                    </button>
                                </h2>
                            </div>
                            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <li><a href="/api/get_list/6">时域</a></li>
                                        <li><a href="/api/get_list/7">频域</a></li>
                                        <li><a href="/api/get_list/8">时频域</a></li>
                                        <li><a href="/api/get_list/20">时间窗</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="card">
                            <div class="card-header" id="headingThree">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                        机器学习分类
                                    </button>
                                </h2>
                            </div>
                            <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <li><a href="/api/get_list/10">GBDT</a></li>
                                        <li><a href="/api/get_list/11">随机森林</a></li>
                                        <li><a href="/api/get_list/12">SVM</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header" id="headingFour">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                                        机器学习回归
                                    </button>
                                </h2>
                            </div>
                            <div id="collapseFour" class="collapse" aria-labelledby="headingFour" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <li><a href="/api/get_list/13">LightGBM</a></li>
                                        <li><a href="/api/get_list/14">随机森林</a></li>
                                        <li><a href="/api/get_list/15">线性回归</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-header" id="headingFive">
                                <h2 class="mb-0">
                                    <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse" data-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                                        我的API
                                    </button>
                                </h2>
                            </div>
                            <div id="collapseFive" class="collapse" aria-labelledby="headingFive" data-parent="#accordionExample">
                                <div class="card-body">
                                    <ul>
                                        <c:forEach items="${myApis}" var="myApi">
                                            <li><a href="/api/get_list/${myApi.id}">${myApi.name}</a></li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <div class="col-10" style="box-sizing: border-box">
                <div class="api_desc_header">
                    <div class="api_name">${api.name}</div>
                </div>
                <div class="api_nav">
                    <ul>
                        <li><a href="#jiekou">接口描述</a></li>
                        <li><a href="#qingqiu">请求说明</a></li>
                        <li><a href="#fanhui">返回说明</a></li>
                    </ul>
                </div>
                <div class="api_desc">
                    <h2 class="api_desc_title" id="jiekou">接口描述</h2>
                    <p>${api.desc}</p>
                    <h2 class="api_desc_title" id="qingqiu">请求说明</h2>
                    <p>
                        <strong>请求示例</strong>
                    </p>
                    <p>
                        HTTP方法：<span id="http_method">${api.requestMethod}</span>
                    </p>
                    <p>
                        请求URL:&nbsp;&nbsp;${api.url}
                    </p></pre>

                    </p>

                    <p>
                        Header如下：
                    <table>
                        <thead>
                        <tr>
                            <th>参数名</th>
                            <th>参数值</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td id="param_name2">Content-Type</td>
                            <td id="param_value2">application/x-www-form-urlencoded</td>
                        </tr>
                        </tbody>
                    </table>
                    </p>
                    <p>
                        Body中放置请求参数，参数详情如下：
                    </p>
                    <p>
                        <strong>请求参数</strong>
                    <table>
                        <thead>
                        <tr>
                            <th>参数名</th>
                            <th>是否必选</th>
                            <th>类型</th>
                            <th>默认值</th>
                            <th>说明</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${apiParams}" var="apiParam">
                            <tr>
                                <td>${apiParam.name}</td>
                                <td>${apiParam.isNecessary}</td>
                                <td>${apiParam.paramType}</td>
                                <td>${apiParam.defaultValue}</td>
                                <td>${apiParam.paramDesc}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </p>
                    <p>
                        <strong>请求代码示例</strong>
                    </p>
                    <p>
                        <strong>提示一：</strong>使用示例代码前，请记得替换其中的示例Token、图片地址或Base64信息。
                    </p>
                    <p>
                        <strong>提示二：</strong>部分语言依赖的类或库，请在代码注释中查看下载地址。
                    </p>
                    <%--<pre>--%>
                    <%--<code>curl -i -k 'https://aip.baidubce.com/rest/2.0/image-classify/v1/animal?--%>
                        <%--access_token=【调用鉴权接口获取的token】' --data--%>
                        <%--'image=【图片Base64编码，需UrlEncode】'--%>
                        <%---H 'Content-Type:application/x-www-form-urlencoded'--%>
                    <%--</code>--%>
                    <%--</pre>--%>
                    <h2 class="api_desc_title" id="fanhui">返回说明</h2>

                    <c:if test="${api.id ==18}">
                        <p>
                            <strong>返回参数</strong>
                        <table>
                            <thead>
                            <tr>
                                <th>参数名</th>
                                <th>是否必选</th>
                                <th>类型</th>
                                <th>说明</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>access_token</td>
                                <td>是</td>
                                <td>string</td>
                                <td>access_token，身份认证信息</td>
                            </tr>
                            <tr>
                                <td>expired</td>
                                <td>是</td>
                                <td>string</td>
                                <td>access_token过期时间，有效期为一个月</td>
                            </tr>
                            </tbody>
                        </table>
                        </p>
                        <p>
                            <strong>返回示例</strong>
                        </p>
                        <pre>
                             <code>
{
    "status": 0,
    "msg": null,
    "data": {
        "access_token": "5a17c156d5ab49059392f14dca82e22d.593f5260a202171018f06a4d4c1e5d07",
        "expired": "2020-03-21 01:02:50"
    },
    "success": true
}
                    </code>
                    </pre>

                    </c:if>
                    <c:if test="${api.id ==17}">
                        <p>
                            <strong>返回参数</strong>
                        <table>
                            <thead>
                            <tr>
                                <th>参数名</th>
                                <th>是否必选</th>
                                <th>类型</th>
                                <th>说明</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>apiKey</td>
                                <td>是</td>
                                <td>string</td>
                                <td>apiKey</td>
                            </tr>
                            <tr>
                                <td>apiSecret</td>
                                <td>是</td>
                                <td>string</td>
                                <td>apiSecret</td>
                            </tr>
                            </tbody>
                        </table>
                        </p>
                        <p>
                            <strong>返回示例</strong>
                        </p>
                        <pre>
                             <code>
{
    "status": 0,
    "msg": null,
    "data": {
        "apiKey": "5a17c156d5ab490593932f1dca82e22d",
        "apiSecret": "3B648CD3B72A171A4CB72678AA45A152"
    },
    "success": true
}
                    </code>
                    </pre>

                    </c:if>
                        <c:if test="${api.apiType ==1 || api.apiType ==2}">
                    <p>
                        <strong>返回参数</strong>
                    <table>
                        <thead>
                        <tr>
                            <th>参数名</th>
                            <th>是否必选</th>
                            <th>类型</th>
                            <th>说明</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>file_name</td>
                            <td>是</td>
                            <td>string</td>
                            <td>处理完成之后的数据集名称，使用它进行后续操作</td>
                        </tr>
                        </tbody>
                    </table>
                    </p>
                    <p>
                        <strong>返回示例</strong>
                    </p>
                    <pre>
                             <code>
{
    "status": 0,
    "msg": null,
    "data": {
        "file_name": "out_data_109505878914501370.csv"
    },
    "success": true
}
                    </code>
                    </pre>

                        </c:if>

                        <c:if test="${api.apiType ==3}">
                            <p>
                                <strong>返回参数</strong>
                            <table>
                                <thead>
                                <tr>
                                    <th>参数名</th>
                                    <th>是否必选</th>
                                    <th>类型</th>
                                    <th>说明</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>accuracy</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>准确率</td>
                                </tr>
                                <tr>
                                    <td>recall</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>召回率</td>
                                </tr>
                                <tr>
                                    <td>precision</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>精确率</td>
                                </tr>
                                <tr>
                                    <td>fMeasure</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>F值</td>
                                </tr>
                                <tr>
                                    <td>rocArea</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>roc值</td>
                                </tr>
                                </tbody>
                            </table>
                            </p>
                            <p>
                                <strong>返回示例</strong>
                            </p>
                            <pre>
                             <code>
{
    "status": 0,
    "msg": null,
    "data": {
        "accuracy": 0.36941964285714285,
        "recall": 0.04745166959578207,
        "precision": 0.54,
        "fMeasure": 0.08723747980613893,
        "rocArea": 0.4885576390792366,
    },
    "success": true
}
                    </code>
                    </pre>
                        </c:if>
                        <c:if test="${api.apiType ==4}">
                            <p>
                                <strong>返回参数</strong>
                            <table>
                                <thead>
                                <tr>
                                    <th>参数名</th>
                                    <th>是否必选</th>
                                    <th>类型</th>
                                    <th>说明</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>varianceScore</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>可释方差值</td>
                                </tr>
                                <tr>
                                    <td>absoluteError</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>平均绝对误差</td>
                                </tr>
                                <tr>
                                    <td>squaredError</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>均方根误差</td>
                                </tr>
                                <tr>
                                    <td>medianSquaredError</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>中值绝对误差</td>
                                </tr>
                                <tr>
                                    <td>r2Score</td>
                                    <td>是</td>
                                    <td>float</td>
                                    <td>R方值</td>
                                </tr>
                                </tbody>
                            </table>
                            </p>
                            <p>
                                <strong>返回示例</strong>
                            </p>
                            <pre>
                             <code>
{
    "status": 0,
    "msg": null,
    "data": {
        "varianceScore": -0.07641535403809496,
        "absoluteError": 1878.4875374999997,
        "squaredError": 2207.758364305771,
        "medianSquaredError": 1803.7,
        "r2Score": -0.07998949505622344
    },
    "success": true
}
                    </code>
                    </pre>
                        </c:if>
                </div>
            </div>
        </div>
    </div>




    <c:import url="/WEB-INF/views/common/footer.jsp"/>
</div>
</body>

</html>