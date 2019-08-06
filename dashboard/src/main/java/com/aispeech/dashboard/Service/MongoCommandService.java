package com.aispeech.dashboard.Service;

import com.aispeech.dashboard.Model.Command;
import com.aispeech.dashboard.Model.SkillModel;
import com.mongodb.async.client.MongoCollection;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

/**
 * @Author: hezhe.du
 * @Date: 2019/6/5 16:34
 */
public interface MongoCommandService {

    boolean commandRuleValidate(String command);

    List<String> getCollectionList();

    MongoCollection getMongoCollection(String collection);

    List<Object> commandGenerateAndRun(Command command);

    List<Document> aggregateGenerate(Document match, Document group, Document sort);

    BasicBSONObject filterProcess(String filter);

    List<Object> commandExecute(Command command);

    List<String> getEnableSkillVersion();

    List<Object> getEntityNumBySkill(String skillId, String skillVersion);

    List<Object> getAttributesByName();

    String getCurrentKnowledgeVersion();
}
