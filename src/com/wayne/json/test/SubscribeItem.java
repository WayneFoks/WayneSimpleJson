package com.wayne.json.test;

import com.wayne.json.annotation.JsonField;

import java.util.List;

public class SubscribeItem {
    public static final int WEMEDIA = 0;
    public static final int TAG = 1;

    @JsonField(name = InfoFlowJsonConstDef.ID)
    private String id;

    @JsonField(name = InfoFlowJsonConstDef.NAME)
    private String name;//实际标签词

    @JsonField(name = InfoFlowJsonConstDef.TITLE)
    private String title;//展示用标签词

    @JsonField(name = InfoFlowJsonConstDef.SUB_TIME)
    private long sub_time; //用户添加时间

    @JsonField(name = InfoFlowJsonConstDef.DESC)
    private List<String> desc;

    @JsonField(name = InfoFlowJsonConstDef.URL)
    private String url;

    @JsonField(name = InfoFlowJsonConstDef.TYPE)
    private int type;

    @JsonField(name = InfoFlowJsonConstDef.IMG)
    private String img;

    @JsonField(name = InfoFlowJsonConstDef.IS_FOLLOW)
    private boolean followed;

    @JsonField(name = InfoFlowJsonConstDef.SUB_ITEM, clazz = SubscribeItem.class)
    private SubscribeItem subItem;
}
