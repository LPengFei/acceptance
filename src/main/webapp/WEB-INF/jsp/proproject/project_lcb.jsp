<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<style>
    .project-scale{margin-top: 50px}
    .project-scale:first-child{margin-top: 0px}
   #pro_lcbC td{padding-left: 15px ;}
</style>



<div id="pro_lcbC" style="padding-top: 0px;background-color: #f8f8f8;width: 100%">
<div class="pro_wrap" style="width: 100%">

    <table class="table table-bordered project-scale" >
        <thead >
        <tr class="pro_lcb_title">
            <th width="30" align="left"><span cool="open" style="margin: 2px 10px 0px 10px;float: left" class="glyphicon glyphicon-minus-sign cursorP"></span>
               <span style="float: left;margin-top: 5px">可研初设审查</span> </th>
            <th width="70" align="right"><a class="data_load">数据导入</a><a class="pro_add">添加</a></th>
        </tr>
        </thead>
        <tbody id="">
        <tr class="">
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        <tr>
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        </tbody>
    </table>

    <table class="table table-bordered project-scale">
        <thead >
        <tr class="pro_lcb_title">
            <th width="30" align="left"><span cool="open" style="margin: 2px 10px 0px 10px;float: left" class="glyphicon glyphicon-minus-sign cursorP"></span>
                <span style="float: left;margin-top: 5px">产内验收</span> </th>
            <th width="70" align="right"><a class="data_load">数据导入</a><a class="pro_add">添加</a></th>
        </tr>
        </thead>
        <tbody id="">
        <tr class="">
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        <tr>
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        </tbody>
    </table>

    <table class="table table-bordered project-scale" >
        <thead >
        <tr class="pro_lcb_title">
            <th width="30" align="left"><span cool="open" style="margin: 2px 10px 0px 10px;float: left" class="glyphicon glyphicon-minus-sign cursorP"></span>
                <span style="float: left;margin-top: 5px">到货验收</span> </th>
            <th width="70" align="right"><a class="data_load">数据导入</a><a class="pro_add">添加</a></th>
        </tr>
        </thead>
        <tbody>
        <tr class="">
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        <tr>
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        </tbody>
    </table>

    <table class="table table-bordered project-scale" >
        <thead >
        <tr class="pro_lcb_title">
            <th width="30" align="left"><span cool="open" style="margin: 2px 10px 0px 10px;float: left" class="cursorP glyphicon glyphicon-minus-sign"></span>
                <span style="float: left;margin-top: 5px">中间验收</span> </th>
            <th width="70" align="right"><a class="data_load">数据导入</a><a class="pro_add">添加</a></th>
        </tr>
        </thead>
        <tbody id="">
        <tr class="">
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        <tr>
            <td>预计可研审查时间：2015年03月20日</td>
            <td>实际可审查时间：<span>2015年03月10日</span><span style="margin-left: 20px">2015年03月10日</span>
                <span style="margin-left: 20px">2015年03月10日</span>
            </td>
        </tr>
        </tbody>
    </table>


</div>

</div>

<script>

    $(function(){
        $(document).on('click','#pro_lcbC .cursorP',function(){
            $(this).parents('thead').siblings('tbody').toggle('50');
            var cool=$(this).attr('cool');
            if(cool == 'open'){
                $(this).removeClass().addClass('glyphicon glyphicon-plus-sign cursorP').attr('cool','colse');
            } else {
                $(this).removeClass().addClass('glyphicon glyphicon-minus-sign cursorP').attr('cool','open');
            }
        })
    })

</script>