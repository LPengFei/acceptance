<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>

<script type="text/javascript">
    function card_info_filedownload(a) {
        $.fileDownload($(a).attr('href'), {
            failCallback: function (responseHtml, url) {
                if (responseHtml.trim().startsWith('{')) responseHtml = responseHtml.toObj()
                $(a).bjuiajax('ajaxDone', responseHtml)
            }
        })
    }
</script>

<style type="text/css">
    .table th, .table td {
        border: 1px solid #DDDDDD;
    }

    .ess-form > span > a:only-of-type {
        float: right;
    }

    .table th, .table td {
        border: 1px solid #DDDDDD;
    }
    @media (max-width: 1300px) {
       #table_{width: 1500px}
    }
    

    /*.icon0 img {*/
        /*margin-top:5px;*/
    /*}*/

    /*.icon0 {*/
        /*width: 50px;*/
        /*height: 30px;*/
        /*!*padding-left: 43px;*!*/
        /*float: right;*/
        /*line-height: 34px;*/
        /*position: relative;*/
        /*cursor: pointer;*/
    /*}*/

    /*.icon1 {*/
        /*position: absolute;*/
        /*left: 0px;*/
        /*width: 30px;*/
        /*height: 20px;*/
    /*}*/

    /*.icon2 {*/
        /*position: absolute;*/
        /*width: 16px;*/
        /*height: 16px;*/
        /*background-color: #C81623;*/
        /*font-size: 12px;*/
        /*line-height: 14px;*/
        /*text-align: center;*/
        /*color: #fff;*/
        /*right: -10px;*/
        /*top: -4px;*/
        /*border-radius: 8px;*/
    /*}*/
</style>

<div class="ess-form" style="border: none;">
    <c:forEach var="taskCard" items="${task._taskCards }">
        <span style="font-size: large; display: block; margin-bottom: 10px;">
           	 <%-- 输变电工程
            <c:choose>
                <c:when test="${task.status eq 'add'}">
                    (新增)
                </c:when>
                <c:when test="${task.status eq 'doing'}">
                    (进行中)
                </c:when>
                <c:otherwise>
                    (完成)
                </c:otherwise>
            </c:choose> --%>
            <div class="">
				<span style="font-size: 18px" id="trip_title"></span>
				<form action="#" id="addTrip_record_form" method="post"
					enctype="multipart/form-data" style='display:none'>
					<input name="fileName" type="file"  id="excel_file">
					<button type="submit" id="excel_sub">提交</button>
				</form>
				  
			</div>
			<button type="button" class="btn btn-green" style="float: right"
					id="excel_file_btn">导入</button>
            <a class="btn btn-green"
               href="${ctx}/${appid}/task/exportFile?taskId=${taskCard.tid}&id=1"
               target="_blank">导出</a>
               
               
			
			
			
		</span>
        <div class="ess-form">
      
            <ul>
                <c:if test="${taskCard._card.showgcmc eq 1}">
                    <li>
                        <label>工程名称</label>
                        <div>
                            <label>${taskCard.gcmc}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsjdw eq 1}">
                    <li>
                        <label>设计单位</label>
                        <div>
                            <label>${taskCard.sjdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showysdw eq 1}">
                    <li>
                        <label>验收单位</label>
                        <div>
                            <label>${taskCard.ysdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showysrq eq 1}">
                    <li>
                        <label>验收日期</label>
                        <div>
                            <label>${taskCard.ysrq}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsccj eq 1}">
                    <li>
                        <label>生产厂家</label>
                        <div>
                            <label>${taskCard.sccj}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsbxh eq 1}">
                    <li>
                        <label>设备型号</label>
                        <div>
                            <label>${taskCard.sbxh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showscgh eq 1}">
                    <li>
                        <label>生产工号</label>
                        <div>
                            <label>${taskCard.sjdw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showccbh eq 1}">
                    <li>
                        <label>出厂编号</label>
                        <div>
                            <label>${taskCard.ccbh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showbdsmc eq 1}">
                    <li>
                        <label>变电所名称</label>
                        <div>
                            <label>${taskCard.bdsmc}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsbmcbh eq 1}">
                    <li>
                        <label>设备名称编号</label>
                        <div> 
                            <label>${taskCard.sbmcbh}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showjldw eq 1}">
                    <li>
                        <label>监理单位</label>
                        <div>
                            <label>${taskCard.jldw}</label>
                        </div>
                    </li>
                </c:if>
                <c:if test="${taskCard._card.showsgdw eq 1}">
                    <li>
                        <label>施工单位</label>
                        <div>
                            <label>${taskCard.sgdw}</label>
                        </div>
                    </li>
                </c:if>
            </ul>
            <div class="clearfix"></div>
        </div>
		<label style="border: none;font-size: 20px;text-align: right;width:100%;"> 总分：79</label> 
        <div class="table" id="table_" style="margin-top: 10px;">
            <table border="1" class="table">
                <tr class="title_tr">
                    <th width="3%" align="center">序号</th>
                    <th width="5%" align="center">技术监督专业</th>
                    <th width="7%" align="center">监督项目</th>
                    <th width="3%" align="center">关键项权重</th>
                    <th width="10%" align="center">监督要点</th>
                    <th width="19%" align="center">监督依据</th>
                    <th width="9%" align="center">监督要求</th>
                    <th width="9%" align="center">监督结果</th>
                    <th width="9%" align="center">评价标准</th>
                    <th width="3%" align="center">评价结果</th>
                    <th width="13%" align="center">条文解释</th>
                    <th width="5%" align="center">专业细分</th>
                </tr>
                    <c:forEach var="records" items="${records}" varStatus="status">
                        <tr>
                            <td align="center">${status.count}</td>
                            <td align="center"> ${records.specialty_supervise }</td>
                            <td align="center"> ${records.project_supervise }</td>
                            <td align="center"> ${records.item_key }</td>
                            <td align="center"> ${records.points_supervise }</td>
                            <td align="center"> ${records.basis_supervise }</td>
                            <td align="center"> ${records.require_supervise }</td>
                            <td align="center"> ${records.result_supervise }</td>
                            <td align="center"> ${records.evaluation_criterion }</td>
                          	<td align="center">  <a href="${ctx }/${appid }/task/superviseStandard?recordId=${records.id}"
                                       data-toggle="dialog" data-width="400" data-height="500" data-id="dialog-mask"
                                       data-mask="true" data-title="评价结果">
                                           ${records.total }
                                    </a></td>
                            <td align="center"> ${records.explana}</td>
                            <td align="center"> ${records.professional}</td>
                        </tr>
                    </c:forEach>
                </c:forEach> 
            </table>
        </div>
</div>


<script type="text/javascript">
    $(document).ready(function () {
        $('.fancybox').fancybox({
            padding: 0,
            openEffect: 'elastic'
        });
        $('.fancybox-buttons').fancybox({
            openEffect: 'none',
            closeEffect: 'none',

            prevEffect: 'none',
            nextEffect: 'none',

            afterLoad: function () {
                this.title = ['第 ', (this.index + 1), '/', this.group.length, ' 张'].join("");
            }
        });
        
      //导入excel数据
		$('#excel_file_btn').click(function(){
			$('#excel_file').click();
		});

		//上传数据解析
		$('#excel_file').change(function () {
			/* var fileName = $(this).val().substring(12,$(this).val().indexOf('.')); */
			var options = {
				url: "${ctx}/app/supervise/importFile",
				success: function () {
				}
			};
			$("#addTrip_record_form").ajaxForm(options).find('button[type=submit]').click();
		}); 
		
    });
    
    
</script>