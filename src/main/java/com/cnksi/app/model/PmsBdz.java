package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class PmsBdz extends KBaseModel<PmsBdz> {

    public static final PmsBdz me = new PmsBdz();


    public Page<PmsBdz> paginate(KWebQueryVO queryParam) {
        LinkedList paramValues = new LinkedList();
        Page page = null;

        try {
            page = paginate(queryParam.getPageNumber(),
                    queryParam.getPageSize(),
                    "select pb.* ",
                    "from pms_bdz pb, pro_project pp where pb.enabled = 0 and pp.bdzid = pb.id and pp.enabled = 0 order by pb.id asc");
            return page;
        } catch (Exception var5) {
            var5.printStackTrace();
            throw var5;
        }
    }

    public List<PmsBdz> findAll() {
        String sql = String.format("select pb.* from pms_bdz pb, pro_project pp where pb.enabled = 0 and pp.bdzid = pb.id and pp.enabled = 0 order by pb.id asc");
        return this.find(sql);
    }


    @SuppressWarnings("rawtypes")
    @Override
    protected Class getCls() {
        return this.getClass();
    }

    public static String getPmsTreeJson(String bdzId, String id, String hierarchy) {
        return Optional.ofNullable(id).map(x -> {
            switch (hierarchy) {
                case "0": {
                    if (bdzId == null) {
                        return JsonKit.toJson(PmsBdz.me.findAll()
                                .stream()
                                .map(y -> y.put("id", y.getStr("id"))
                                        .put("name", y.getStr("pms_BDZMC"))
                                        .put("hierarchy", 1)
                                        .put("nocheck", true)
                                        .put("isParent", true)
                                        .put("projectId",
                                                Optional.ofNullable(ProProject.me
                                                        .findFirst("select pp.* from pms_bdz pb, pro_project pp where pb.enabled = 0 and pb.id = pp.bdzid and pb.id = ?", y.getStr("id")))
                                                        .map(z -> z.getStr("id"))
                                                        .orElseGet(() -> "")
                                        )
                                )
                                .collect(Collectors.toList()));
                    } else {
                        return JsonKit.toJson(Optional.of(PmsBdz.me.findById(bdzId))
                                .filter(y -> y.getInt("enabled") == 0)
                                .map(y -> y.put("name", y.getStr("pms_BDZMC"))
                                        .put("hierarchy", 1)
                                        .put("isParent", true)
                                        .put("projectId",
                                                Optional.ofNullable(ProProject.me
                                                        .findFirst("select pp.* from pms_bdz pb, pro_project pp where pb.enabled = 0 and pb.id = pp.bdzid and pb.id = ?", y.getStr("id")))
                                                        .map(z -> z.getStr("id"))
                                                        .orElseGet(() -> "")
                                        )
                                ).get());
                    }
                }
                case "1": {
                    return JsonKit.toJson(PmsSpacing.me.findListByPropertity("bid", x)
                            .stream()
                            .map(y -> y.put("id", y.getStr("id"))
                                    .put("name", y.getStr("pms_JGDYMC"))
                                    .put("hierarchy", 2)
                                    .put("nocheck", true)
                                    .put("isParent", true)
                            )
                            .collect(Collectors.toList()));
                }
                case "2": {
                    return (JsonKit.toJson(PmsDevice.me.findListByPropertity("sid", x)
                            .stream()
                            .map(y -> y.put("id", y.getStr("id"))
                                    .put("name", y.getStr("pms_SBMC"))
                                    .put("hierarchy", 3)
                                    .put("isParent", false))
                            .collect(Collectors.toList())));
                }
            }
            return x;
        }).orElseGet(
                () -> JsonKit.toJson(Arrays.asList(new Record()
                        .set("id", 0)
                        .set("name", PropKit.get("company"))
                        .set("nocheck", true)
                        .set("isParent", true)
                        .set("open", true)))
        );
    }
}
