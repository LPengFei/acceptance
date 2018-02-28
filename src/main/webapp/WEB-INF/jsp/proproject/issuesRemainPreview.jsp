<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<script src="${ctx }/public/js/print/jquery-1.4.4.min.js"></script>
<script src="${ctx }/public/js/print/jquery.jqprint-0.3.js"></script>

<style type="text/css">
    #button {
        text-align: right;
        margin-right: 5%;
    }
</style>

<div id="container">
    <style type="text/css">
        table {
            width: 96%;
        }
    </style>
    <p class="MsoNormal">
        <b>&nbsp;</b>
    </p>
    <p class="MsoNormal" align="center" style="text-align:center;">
        移交专用工器具清单<span></span>
    </p>
    <div align="center">
        <table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" style="border:none;">
            <tbody>
            <tr>
                <td width="59" style="border:solid windowtext 1.0pt;">
                    <p class="MsoNormal" align="center" style="text-align:center;">
                        <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">序号<span></span></span>
                    </p>
                </td>
                <td width="143" style="border:solid windowtext 1.0pt;">
                    <p class="MsoNormal" align="center" style="text-align:center;">
                        <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">内 &nbsp;容<span></span></span>
                    </p>
                </td>
                <td width="101" style="border:solid windowtext 1.0pt;">
                    <p class="MsoNormal" align="center" style="text-align:center;">
                        <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">责任单位<span></span></span>
                    </p>
                </td>
                <td width="101" style="border:solid windowtext 1.0pt;">
                    <p class="MsoNormal" align="center" style="text-align:center;">
                        <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">限期完成时间<span></span></span>
                    </p>
                </td>
            </tr>
            <c:forEach items="${resultIssues}" var="v" varStatus="s">
                <tr>
                    <td width="59" style="border:solid windowtext 1.0pt;">
                        <p class="MsoNormal" align="center" style="text-align:center;">
                            <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">${s.count}</span>
                        </p>
                    </td>
                    <td width="143" style="border:solid windowtext 1.0pt;">
                        <p class="MsoNormal" align="center" style="text-align:center;">
                            <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">${v.description}</span>
                        </p>
                    </td>
                    <td width="101" style="border:solid windowtext 1.0pt;">
                        <p class="MsoNormal" align="center" style="text-align:center;">
                            <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">${v.responsibility}</span>
                        </p>
                    </td>
                    <td width="101" style="border:solid windowtext 1.0pt;">
                        <p class="MsoNormal" align="center" style="text-align:center;">
                            <span style="font-size:12.0pt;line-height:150%;font-family:仿宋_GB2312;">${v.limittime}</span>
                        </p>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <p class="MsoNormal">
        &nbsp;
    </p>


    <script type="text/javascript">
        function showNativeDetail(id) {
            native.groupDetail(id);
        }
    </script>
</div>

<div id="button">
    <a href="javascript:print();"><span>打印</span></a>
</div>

<script type="text/javascript">
    function print() {
        $("#container").jqprint();
    }
    print();
</script>