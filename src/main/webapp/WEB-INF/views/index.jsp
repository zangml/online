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
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://how2j.cn/study/js/jquery/2.0.0/jquery.min.js"></script>
    <link href="http://how2j.cn/study/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>assets/styles/home.css" rel="stylesheet">
    <script src="http://how2j.cn/study/js/bootstrap/3.3.6/bootstrap.min.js"></script>
    <title>Learn</title>
</head>

<body>
<div class="categoryWithCarousel">
    <c:import url="/WEB-INF/views/common/header.jsp" />
    <div data-ride="carousel" class="carousel-of-product carousel slide" id="carousel-of-product">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li class="active" data-slide-to="0" data-target="#carousel-of-product"></li>
            <li data-slide-to="1" data-target="#carousel-of-product" class=""></li>
            <li data-slide-to="2" data-target="#carousel-of-product" class=""></li>
            <li data-slide-to="3" data-target="#carousel-of-product" class=""></li>
        </ol>
        <!-- Wrapper for slides -->
        <div role="listbox" class="carousel-inner">
            <div class="item active">
                <img src="https://upfiles.heclouds.com/123/banner/2017/10/30/50f20642fd91211e526fd5a17e3226f8.png" class=" carouselImage">
            </div>
            <div class="item">
                <img src="https://upfiles.heclouds.com/123/banner/2017/11/20/d3b48b1c351287369994fd636bd67383.jpg" class="carouselImage">
            </div>
            <div class="item">
                <img src="https://upfiles.heclouds.com/123/banner/2017/09/15/630933231b59c0c3f732ee4d2155da2e.jpg" class="carouselImage">
            </div>
            <div class="item">
                <img src="https://upfiles.heclouds.com/123/banner/2017/11/16/894f6f8c8dce7860e5abe45b1a5b6904.jpg" class="carouselImage">
            </div>
        </div>
    </div>
    <div class="carouselBackgroundDiv">
    </div>
</div>
<div class="homepageCategoryProducts">
    <div class="eachHomepageCategoryProducts">
        <div class="left-mark"></div>
        <span class="categoryTitle">合作公司</span>
        <br>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511282159616&di=56d5042be5a77d41e95d0eee3c1bfc35&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1778755498%2C3537150362%26fm%3D214%26gp%3D0.jpg "></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">阿里巴巴
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="www."></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">华为
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src=""></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">百度
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src=""></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">腾讯
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src=""></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">科大讯飞
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div style="clear:both"></div>
    </div>
    <div class="eachHomepageCategoryProducts">
        <div class="left-mark"></div>
        <span class="categoryTitle">合作学校</span>
        <br>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src=""></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">清华大学
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="  "></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">北京大学
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="   "></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">浙江大学
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="   "></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">北京邮电大学
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div class="productItem">
            <a href="#nowhere"><img width="100px" src="   "></a>
            <a href="#nowhere" class="productItemDescLink">
              <span class="productItemDesc">香港科技大学
              </span>
            </a>
            <span class="productPrice">

            </span>
        </div>
        <div style="clear:both"></div>
    </div>
    <img src="http://how2j.cn/tmall/img/site/end.png" class="endpng" id="endpng">
</div>
<div style="display: block;text-align:center;" class="footer" id="footer">
    <h1>中移物联网开放平台为您推荐全面完备的 IOT 云服务</h1><br>
    <h1>为IoT开发者提供智能设备自助开发工具、后台技术支持服务<br>为您提供物联网专网、短彩信、位置定位、设备管理、消息分发</h1>
</div>

<div style="display: block;" class="footer">

    <div class="footer_desc" id="footer_desc">
        <div class="descColumn">
            <span class="descColumnTitle">关于我们</span>
            <a href="#nowhere">我们是谁</a>
            <a href="#nowhere">成功案例</a>
            <a href="#nowhere">加入我们</a>
        </div>
        <div class="descColumn">
            <span class="descColumnTitle">开发者</span>
            <a href="#nowhere">快速接入设备</a>
            <a href="#nowhere">开发文档</a>
            <a href="#nowhere">服务协议</a>
        </div>
        <div class="descColumn">
            <span class="descColumnTitle">语言</span>
            <a href="#nowhere">中文</a>
            <a href="#nowhere">English</a>
            <a href="#nowhere">日本語</a>
            <a href="#nowhere">Deutsch</a>
        </div>
        <div class="descColumn">
            <span class="descColumnTitle">友情链接</span>
            <a href="#nowhere">中移物联网</a>
            <a href="#nowhere">物联网专网</a>
            <a href="#nowhere">开源中国</a>
            <a href="#nowhere">arduine中文社区</a>
            <a href="#nowhere">贯众开放平台</a>
            <a href="#nowhere">电子发烧友</a>
            <a href="#nowhere">更多>></a>
        </div>
        <div class="descColumn">
            <span class="descColumnTitle">APP下载</span>
            <a href="#nowhere"><img src="http://how2j.cn/tmall/img/site/ma.png"></a>
        </div>
    </div>
    <div style="clear:both"></div>
</div>
</body>
</html>
