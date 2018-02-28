<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<script src="${ctx }/public/js/print/jquery-1.4.4.min.js"></script>
<script src="${ctx }/public/js/print/jquery.jqprint-0.3.js"></script>

<style type="text/css" >
    #button{
        text-align: right;
        margin-right: 5%;
    }
</style>

<div id="container">
${content}
</div>
<div id="button">
    <a href="javascript:print();"><span>打印</span></a>
</div>

<script type="text/javascript" >
    function print() {
        $("#container").jqprint();
    }
    print();
</script>