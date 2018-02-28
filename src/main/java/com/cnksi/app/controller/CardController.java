package com.cnksi.app.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.net.URLDecoder;

import com.cnksi.app.model.*;
import com.cnksi.util.WordUtil;
import com.cnksi.util.wordUtil.model.CRString;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import freemarker.template.TemplateException;
import org.dom4j.DocumentException;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kconf.controller.KController;

/**
 *
 */
public class CardController extends KController {

    public CardController() {
        super(Card.class);
    }

    public void index() {

        KWebQueryVO queryParam = super.doIndex();

        setAttr("page", Card.me.paginate(queryParam));

        render("list.jsp");
    }


    public void edit() {

        super.doEdit();

        String idValue = getPara("id", getPara());
        Card record = null;
        if (idValue != null) {
            record = Card.me.findById(idValue);
        } else {
            record = new Card();

        }
        setAttr("record", record);

        render("form.jsp");
    }


    public void save() {
        Card record = getModel(Card.class, "record");
        CardType cardType = CardType.me.findByPropertity("`key`", record.get("type"));
        record.set("flow", cardType.get("flow"));
        if (record.get("id") != null) {
            String[] showAbles = new String[]{"showgcmc", "showsjdw", "showysdw", "showysrq", "showsccj", "showsbxh", "showscgh", "showccbh", "showbdsmc", "showsbmcbh"};
            for (String showAble : showAbles) {
                if (record.get(showAble) == null) {
                    record.set(showAble, 0);
                }
            }
            record.update();
        } else {
            record.save();
        }


        bjuiAjax(200, true);
    }


    public void delete() {
        Card record = Card.me.findById(getPara());
        if (record != null) {
            record.set("enabled", 1).update();
            bjuiAjax(200);
        } else {
            bjuiAjax(300);
        }

    }


    public void export() throws IOException {
        KWebQueryVO queryParam = super.doIndex();
        Page<Card> p = Card.me.paginate(queryParam);
        String xlsid = getPara("xlsid", "-1");
        super.export(xlsid, p.getList());
    }


    public void importxlsed() {
        String errorFile = "", msg = "";
        try {
            ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
            if (!result.isVerfiyFail()) {
                for (Map<String, Object> map : result.getList()) {
                    Card record = new Card();
                    record.put(map);
                    record.save();
                }
            } else {
                errorFile = result.getSaveFile().getName();
            }
        } catch (ExcelImportException e) {
            msg = "导入错误：" + e.getMessage();
        }
        bjuiAjax(StrKit.notBlank(msg) ? 300 : 200).put("errorFile", errorFile);

    }

    private Map<String, String> getBaseInfoByCardId(String id) {
        Task task = Task.me.findByPropertity("cards", id);
        TaskCard taskCard = TaskCard.me.findByPropertity("tid", task.get("id"));
        String projectId = task.get("pid");
        ProProject project = ProProject.me.findById(projectId);
        return new HashMap<String, String>(){{
            put("projectName", project.get("name"));
            put("provideName", task.get("productor"));
            put("deviceType", task.get("devicetype"));
            put("acceptanceDate", task.get("time"));
            put("isbn", taskCard.get("ccbh"));
            put("acceptanceDepartment", taskCard.get("ysdw"));
        }};
    }

    private List<Object> getAcceptanceItemsCardId(String id) {
        String cardId = id;
        List<Object> acceptanceItems = new ArrayList<>();
        for (CardItemType cardItemType : CardItemType.me.findListByPropertity("cid", cardId)) {
            List<Object> cardInfoItems = new ArrayList<>();
            for (CardItem cardItem : CardItem.me.findListByPropertity("citid", cardItemType.get("id"))) {
                List<CardStandard> cardStandards = CardStandard.me.findListByPropertity("ciid", cardItem.get("id"));
                ResultItem resultItem = ResultItem.me.findByPropertity("ciid", cardItem.get("id"));
                List<CardCopy> cardCopies = CardCopy.me.findListByPropertity("ciid", cardItem.get("id"));
                cardInfoItems.add(new HashMap<String, Object>() {{
                    put("cardItem", cardItem);
                    put("resultItem", resultItem);
                    put("cardCopies", cardCopies);
                    put("cardStandards", cardStandards);
                }});
            }
            acceptanceItems.add(new HashMap<String, Object>() {{
                put("cardItemType", cardItemType);
                put("cardInfoItems", cardInfoItems);
            }});
        }
        return acceptanceItems;
    }

    public void download() {
        String template = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "template_doc" + File.separator + "taskCard.xml";
        String tempFileName = Card.getPrimarykey();
        String genDoc = PathKit.getWebRootPath() + File.separator + "WEB-INF" + File.separator + "upload" + File.separator + tempFileName + ".doc";
        try {
            String cardId = getPara("cardId", getPara());
            Map<String, Object> param = new HashMap<>();
            Card card = Card.me.findById(cardId);
            setAttr("card", card);
            Map<String, String> baseInfo = getBaseInfoByCardId(cardId);
            param.put("cardTitle", card.get("name"));
            param.put("cardName", baseInfo.get("cardName"));
            param.put("projectName", baseInfo.get("projectName"));
            param.put("provideName", baseInfo.get("provideName"));
            param.put("deviceType", baseInfo.get("deviceType"));
            param.put("isbn", "123456789");
            param.put("acceptanceDepartment", baseInfo.get("acceptanceDepartment"));
            param.put("acceptanceDate", baseInfo.get("acceptanceDate"));
//            param.put("baseInfo", getBaseInfoByCardId(cardId));
            param.put("acceptanceItems", getAcceptanceItemsCardId(cardId));
            WordUtil.generateWord(param, template, genDoc);
            if ( Files.exists(Paths.get(genDoc)) ) {
                renderFile(tempFileName + ".doc");
            }
        } catch(DocumentException|TemplateException|IOException e) {
            bjuiAjax(300);
        } finally {

        }
    }

    public void info() {
        String cardId = getPara("cardId", getPara());
        setAttr("card", Card.me.findById(cardId));
        setAttr("baseInfo", getBaseInfoByCardId(cardId));
        setAttr("acceptanceItems", getAcceptanceItemsCardId(cardId));
        render("info.jsp");
    }

    public void selectCardsByCardTypeKeyAndDeviceTypeName() {
        try {
            String cardTypeKey = getPara("cardTypeKey", getPara());
            String deviceTypeName = URLDecoder.decode(getPara("deviceTypeName", getPara()), "UTF-8");
            String deviceTypeId = DeviceType.me.findByPropertity("name", deviceTypeName).get("id");
            String sql = "select * from card where enabled = 0 and flow = (select flow from card_type where card_type.key = ?) and card.dtid = ? order by id";
            List<Card> cards = Card.me.find(sql, cardTypeKey, deviceTypeId);


            boolean showCardItemTypes = cardTypeKey.equals("hidden_check") || cardTypeKey.equals("middle_check");

            String selectedCardId = null;
            String taskId = getPara("taskId", getPara());
            Task task = null;
            Set<String> selectedCardItemTypes = new HashSet<>();
            if ( taskId != null && !taskId.equals("") ) {
                task = Task.me.findById(taskId);
                selectedCardId = task.get("cards");
                String idString = TaskCard.me.findByPropertity("tid", taskId).get("citids");
                selectedCardItemTypes = new HashSet<String>(Arrays.asList((idString == null ? "" : idString).split(",")));
            }
            for ( Card card : cards ) {
                List<CardItemType> cardItemTypes = CardItemType.me.findListByPropertity("cid", card.get("id"));
                if ( showCardItemTypes ) {
                    for (CardItemType cardItemType : cardItemTypes) {
                        if (taskId == null) {
                            cardItemType.put("_selected", true);
                        } else if (selectedCardItemTypes.contains(cardItemType.get("id"))) {
                            cardItemType.put("_selected", true);
                        }
                    }
                }
                card.put("_cardItemTypes", cardItemTypes);
                if ( card.get("id").equals(selectedCardId) ) {
                    card.put("_selected", true);
                }
            }
            setAttr("showCardItemTypes", showCardItemTypes);
            setAttr("cards", cards);
            render("checkboxs.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
