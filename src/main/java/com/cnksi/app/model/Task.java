package com.cnksi.app.model;

import com.cnksi.app.controller.TaskController;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.kit.PathKit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Task extends KBaseModel<Task> {
	private static final String public_upload = PathKit.getWebRootPath() + File.separator + "upload" + File.separator;

	public static final Task me = new Task();

	public static final String[] identityStrings = new String[]{"pid", "cards", "deviceno", "devicetype"};
	public static final String[] mergeColumns = new String[]{"name", "pid", "pname", "flow", "flowname", "type", "cards", "deviceno", "devicetype", "productor", "time", "status", "result"};

	private int hash_code = -1;
	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public int identity() {
		if (hash_code == -1) {
			hash_code = String.join("", getIdentityStrings()).hashCode();
		}
		return hash_code;
	}

	public String[] getIdentityStrings() {
		return Arrays.stream(identityStrings).map(x -> get(x)).toArray(String[]::new);
	}

	public static List<Task> getTaskByProjectId(String id) {
		return Task.me.findListByPropertitys(new String[]{"enabled", "pid"}, new Object[]{0, id}, Logical.AND);
	}

	public static Task getTaskInfoById(String id) {
		Task task = Task.me.findById(id);

		List<TaskCard> taskCards = TaskCard.me.find("select * from " + TaskCard.me.getTableName() + " where tid = ? and enabled = 0", id);
		task.put("_taskCards", taskCards);

		int totalResultIssueCount = 0;
		for (TaskCard taskCard : taskCards) {
			taskCard.put("_card", Card.me.findById(taskCard.getStr("cid")));

			List<CardItemType> cardItemTypes = taskCard.getCardItemTypes();
			taskCard.put("_cardItemTypes", cardItemTypes);

			List<ResultSignname> resultSignnames = ResultSignname.me.find("select * from " + ResultSignname.me.getTableName() + " where tcid = ? and enabled = 0", taskCard.getStr("id"));
			List<ResultItem> resultItems = ResultItem.me.find("select * from " + ResultItem.me.getTableName() + " where tcid = ? and enabled = 0", taskCard.getStr("id"));
			List<ResultIssue> resultIssues = ResultIssue.me.find("select * from " + ResultIssue.me.getTableName() + " where tcid = ? and enabled = 0", taskCard.getStr("id"));
			List<ResultItemCopy> resultItemCopys = ResultItemCopy.me.find("select * from " + ResultItemCopy.me.getTableName() + " where tcid = ? and enabled = 0", taskCard.getStr("id"));

			for (CardItemType cardItemType : cardItemTypes) {
				List<ResultSignname> signs = resultSignnames.stream()
						.filter(x -> cardItemType.getStr("id").equals(x.getStr("citid")))
						.reduce(new ArrayList<ResultSignname>(), (acc, x) -> {
							if (Files.isRegularFile(Paths.get(public_upload + x.get("signimg")))) {
								x.put("_exists", true);
							}
							cardItemType.put("_resultSignname", x);
							acc.add(x);
							return acc;
						}, (x, y) -> x);
				cardItemType.put("_resultSignnames", signs);

				List<CardItem> cardItems = CardItem.me.find("select * from " + CardItem.me.getTableName() + " where citid = ? and enabled = 0", cardItemType.getStr("id"));
				cardItemType.put("_cardItems", cardItems);

				for (CardItem cardItem : cardItems) {
					processCardItem(cardItem, resultItems, resultItemCopys);

					long resultIssueCount = resultIssues.stream()
							.filter(x -> cardItem.getStr("id").equals(x.getStr("ciid")))
							.count();
					cardItem.put("_resultIssueCount", resultIssueCount);
					totalResultIssueCount += resultIssueCount;
				}
			}
		}

		task.put("_totalResultIssueCount", totalResultIssueCount);

		return task;
	}

	private static void processCardItem(CardItem cardItem, List<ResultItem> resultItems, List<ResultItemCopy> resultItemCopys) {
		resultItems.stream()
				.filter(x -> cardItem.getStr("id").equals(x.getStr("ciid")))
				.forEach(x -> {
					cardItem.put("_resultItem", x);

					List<ResultItemCopy> subResultItemCopys = resultItemCopys.stream()
							.filter(y -> x.getStr("id").equals(y.getStr("riid")))
							.collect(Collectors.toList());

					cardItem.put("_resultItemCopys", subResultItemCopys);
				});
	}

}
