<%--
  Created by IntelliJ IDEA.
  User: gaozhou
  Date: 2017/1/18
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<style type="text/css">
    .swiper-container {
        width: 100%;
        height: 100%;
    }

    .swiper-wrapper {
        background: #fff;
    }

    .swiper-slide {
        text-align: center;
        font-size: 18px;
        background: #000;

        /* Center slide text vertically */
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        -webkit-justify-content: center;
        justify-content: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        -webkit-align-items: center;
        align-items: center;
    }

    .gallery-thumbs {
        box-sizing: border-box;
        padding: 0px;
        background: #fff;
    }

    .gallery-thumbs .swiper-slide {
        width: 100%;
        height: 100%;
        opacity: 0.4;
        background: #fff;
    }

    .gallery-thumbs .swiper-slide-active {
        opacity: 1;
    }

    .gallery-thumbs .img-wrapper {
        width: 150px;
        height: 150px;
    }

    .gallery-thumbs img {
        width: 100%;
    }

    .gallery-slide {
        /*border: 2px solid red;*/
        width: 200px;
        height: 100%;
        float: right;
    }

    .gallery-main {
        /*border: 2px solid red;*/
        margin-right: 200px;
    }
    .img-wrapper,
    .swiper-zoom-container {
        background: #fff;
    }
</style>

<div id="${imagesId}">
    <div class="gallery-slide">
        <div class="swiper-container gallery-thumbs image1">
            <div class="swiper-wrapper">
                <c:forEach items="${gallery_images}" var="v" varStatus="r">
                    <div class="swiper-slide">
                        <div class="img-wrapper">
                            <img src="${ctx}/upload/${v.url}">
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="gallery-main">
        <div class="swiper-container gallery-right">
            <div class="swiper-wrapper">
                <c:forEach items="${gallery_images}" var="v" varStatus="r">
                    <div class="swiper-slide">
                        <div class="swiper-zoom-container">
                            <img src="${ctx}/upload/${v.url}">
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="swiper-pagination swiper-pagination-white"></div>
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>
    </div>
</div>


<script>
    $(function () {
        var galleryThumbs = new Swiper('#${imagesId} .gallery-thumbs', {
            direction: 'vertical',
            spaceBetween: 10,
            centeredSlides: true,
            slidesPerView: 2,
            touchRatio: 0.2,
            slideToClickedSlide: true,
            loop:false,
            loopedSlides: 5
        });
        var galleryRight = new Swiper('#${imagesId} .gallery-right', {
            nextButton: '#${imagesId} .swiper-button-next',
            prevButton: '#${imagesId} .swiper-button-prev',
            pagination: '#${imagesId} .swiper-pagination',
            zoom: true,
            loop:false,
            loopedSlides: 5
        });
        galleryRight.params.control = galleryThumbs;
        galleryThumbs.params.control = galleryRight;
    });
</script>