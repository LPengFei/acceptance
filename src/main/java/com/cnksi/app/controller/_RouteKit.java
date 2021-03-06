package com.cnksi.app.controller;

import com.jfinal.config.Routes;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configRoute(Routes me) {
 *    _RouteKit.mapping(me); 
 * 	} 
 </pre>
 */
public class _RouteKit {

	public static void mapping(Routes me) {
		me.add("/app/card",  CardController.class,"/card");
		me.add("/app/cardcopy",  CardCopyController.class,"/cardcopy");
		me.add("/app/carditem",  CardItemController.class,"/carditem");
		me.add("/app/carditemtype",  CardItemTypeController.class,"/carditemtype");
		me.add("/app/cardstandard",  CardStandardController.class,"/cardstandard");
		me.add("/app/cardtype",  CardTypeController.class,"/cardtype");
		me.add("/app/countdevice",  CountDeviceController.class,"/countdevice");
		me.add("/app/counttask",  CountTaskController.class,"/counttask");
		me.add("/app/devicefield",  DeviceFieldController.class,"/devicefield");
		me.add("/app/devicefieldvalue",  DeviceFieldValueController.class,"/devicefieldvalue");
		me.add("/app/deviceimg",  DeviceImgController.class,"/deviceimg");
		me.add("/app/devicetype",  DeviceTypeController.class,"/devicetype");
		me.add("/app/docfield",  DocFieldController.class,"/docfield");
		me.add("/app/docfieldvalue",  DocFieldValueController.class,"/docfieldvalue");
		me.add("/app/docfile",  DocFileController.class,"/docfile");
		me.add("/app/docgroupvalue",  DocGroupValueController.class,"/docgroupvalue");
		me.add("/app/pmsbdz",  PmsBdzController.class,"/pmsbdz");
		me.add("/app/pmsdevice",  PmsDeviceController.class,"/pmsdevice");
		me.add("/app/pmsdict",  PmsDictController.class,"/pmsdict");
		me.add("/app/pmsspacing",  PmsSpacingController.class,"/pmsspacing");
		me.add("/app/promilestone",  ProMilestoneController.class,"/promilestone");
		me.add("/app/proprogress",  ProProgressController.class,"/proprogress");
		me.add("/app/proproject",  ProProjectController.class,"/proproject");
		me.add("/app/proprojectattachment",  ProProjectAttachmentController.class,"/proprojectattachment");
		me.add("/app/proprojectattachmentimage",  ProProjectAttachmentImageController.class,"/proprojectattachmentimage");
		me.add("/app/proprojectscal",  ProProjectScalController.class,"/proprojectscal");
		me.add("/app/reldeviceissue",  RelDeviceIssueController.class,"/reldeviceissue");
		me.add("/app/reldevicetask",  RelDeviceTaskController.class,"/reldevicetask");
		me.add("/app/resultissue",  ResultIssueController.class,"/resultissue");
		me.add("/app/resultissueimportant",  ResultIssueImportantController.class,"/resultissueimportant");
		me.add("/app/resultissuelog",  ResultIssueLogController.class,"/resultissuelog");
		me.add("/app/resultitem",  ResultItemController.class,"/resultitem");
		me.add("/app/resultitemcopy",  ResultItemCopyController.class,"/resultitemcopy");
		me.add("/app/resultsignname",  ResultSignnameController.class,"/resultsignname");
		me.add("/app/supervise",  SuperviseController.class,"/supervise");
		me.add("/app/sysconfig",  SysConfigController.class,"/sysconfig");
		me.add("/app/task",  TaskController.class,"/task");
		me.add("/app/taskcard",  TaskCardController.class,"/taskcard");
		me.add("/app/tasksupervise",  TaskSuperviseController.class,"/tasksupervise");
	}
}

