/*
 * Copyright (C) 2004 - 2016 UCWeb Inc. All Rights Reserved.
 * Description :
 *
 * Creation    : 2016/12/29
 * Author      : weihan.hwh@alibaba-inc.com
 */

package com.company;

import com.company.annotation.JsonField;

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
    @JsonField(name = InfoFlowJsonConstDef.DESC, elementClass = String.class)
    private List<String> desc;
    @JsonField(name = InfoFlowJsonConstDef.URL)
    private String url;
    @JsonField(name = InfoFlowJsonConstDef.TYPE)
    private int type;
    @JsonField(name = InfoFlowJsonConstDef.IMG)
    private String img;
    @JsonField(name = InfoFlowJsonConstDef.IS_FOLLOW)
    private boolean followed;//是否已经关注
}