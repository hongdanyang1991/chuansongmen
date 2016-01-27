package com.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoGeneration {
    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(1, "me.chuansong.greendao");
        addNews(schema);
        //addStats(schema);
        //new DaoGenerator().generateAll(schema, "/.../chuansongmen/app/src/main/java-gen");
        new DaoGenerator().generateAll(schema, "/Users/hong/Desktop/project/2015/chuansongmen/app/src/main/java-gen");
    }

    public static void addNews(Schema schema) {
        Entity news = schema.addEntity("GreenNews");
        news.addIdProperty();
        news.addStringProperty("newslist");
        news.addStringProperty("type");
    }
}
