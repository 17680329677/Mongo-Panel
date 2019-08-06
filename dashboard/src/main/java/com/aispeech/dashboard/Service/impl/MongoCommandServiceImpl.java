package com.aispeech.dashboard.Service.impl;

import com.aispeech.dashboard.DashboardApplication;
import com.aispeech.dashboard.Model.*;
import com.aispeech.dashboard.Model.enums.CommandKeyWord;
import com.aispeech.dashboard.Model.knowledge.KnowledgeSchema;
import com.aispeech.dashboard.Service.MongoCommandService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Sorts.descending;

/**
 * @Author: hezhe.du
 * @Date: 2019/6/5 0005 16:50
 */

@Service
public class MongoCommandServiceImpl implements MongoCommandService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardApplication.class);
    private static final Map<String, KnowledgeSchema> knowledgeCache = new HashMap<>();
    private static final String KNOWLEDGE_VERSION_FIELD_NAME = "knowledgeVersion";

    @Autowired
    MongoDatabase database;

    @Override
    public boolean commandRuleValidate(String command) {
        // TODO 结合权限做命令过滤
        for (CommandKeyWord keyWord : CommandKeyWord.values()) {
            int index = command.toLowerCase().indexOf(keyWord.getOpt().toLowerCase());
            if (index != -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getCollectionList() {
        List<String> collectionList = new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        database.listCollectionNames().forEach((c) -> {String coll = c; collectionList.add(coll);}, (v, i) -> {countDownLatch.countDown();});
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return collectionList;
    }

    @Override
    public MongoCollection getMongoCollection(String collection) {
        if ("skill".equals(collection)) {
            MongoCollection<SkillModel> skillCollection = database.getCollection("skill", SkillModel.class);
            return skillCollection;
        } else if ("entity".equals(collection)) {
            MongoCollection<EntityModel> entityCollection = database.getCollection("entity", EntityModel.class);
            return entityCollection;
        } else if ("knowledge".equals(collection)) {
            MongoCollection<KnowledgeModel> knowledgeCollection = database.getCollection("knowledge", KnowledgeModel.class);
            return knowledgeCollection;
        } else if ("rule".equals(collection)) {
            MongoCollection<RuleModel> ruleCollection = database.getCollection("rule", RuleModel.class);
            return ruleCollection;
        } else {
            return null;
        }
    }

    @Override
    public BasicBSONObject filterProcess(String filter) {
        if ("".equals(filter) || filter.isEmpty()) {
            return new BasicBSONObject();
        }
        try {
            JSONObject filterObject = JSON.parseObject(filter);
            HashMap<String, Object> map = new ObjectMapper().readValue(filterObject.toJSONString(), HashMap.class);
            System.out.println(map);
            BasicBSONObject bsonObject = new BasicBSONObject();
            bsonObject.putAll(map);
            return bsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            throw new JSONException("过滤条件解析错误");
        }
    }

    @Override
    public List<Object> commandGenerateAndRun(Command command) throws JSONException {
        MongoCollection collection = getMongoCollection(command.getCollection());
        final CountDownLatch countLatch = new CountDownLatch(1);
        List<Object> resultList = new ArrayList<>();

        if ("".equals(command.getFilter()) || command.getFilter().isEmpty() || command.isDiagnostic()) {
            if (command.isDiagnostic()) {
                MongoCollection col = database.getCollection(command.getCollection());
                col.find().modifiers(new Document("$explain", true)).forEach(
                        (r) -> resultList.add(r),
                        (v, i) -> countLatch.countDown()
                );
            } else if ("find".equals(command.getOperate())) {
                collection.find().limit(command.getLimit()).skip(command.getSkip()).forEach(
                        (e) -> resultList.add(e),
                        (v,i) -> countLatch.countDown()
                );
            } else if ("count".equals(command.getOperate())) {
                Map<String, Object> countMap = new HashMap<>();
                collection.countDocuments((v, i) -> {countMap.put("count", v); resultList.add(countMap); countLatch.countDown();});
            } else {
                return null;
            }
        } else {
            BasicBSONObject bsonFilter = filterProcess(command.getFilter());
            if ("find".equals(command.getOperate())) {
                collection.find(new Document(bsonFilter)).limit(command.getLimit()).skip(command.getSkip()).forEach(
                        (e) -> resultList.add(e),
                        (v,i) -> countLatch.countDown()
                );
            } else if ("count".equals(command.getOperate())) {
                Map<String, Object> countMap = new HashMap<>();
                collection.countDocuments(new Document(bsonFilter), (v, i) -> {countMap.put("count", v); resultList.add(countMap);countLatch.countDown();});
            } else {
                return null;
            }
        }
        try {
            countLatch.await();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getEnableSkillVersion() {
        List<String> versionIds = new ArrayList<>();
        MongoCollection<SkillModel> skillCollection = database.getCollection("skill", SkillModel.class);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        List<String> fiels = new ArrayList<>();
        fiels.add("version");
        Bson projection = Projections.include(fiels);
        skillCollection.find(new Document("status", "Enable")).projection(projection).forEach(
                (s) -> versionIds.add(s.getVersion()),
                (v, i) -> countDownLatch.countDown()
        );
        try {
            countDownLatch.await();
            return versionIds;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Document> aggregateGenerate(Document match, Document group, Document sort) {
        List<Document> aggregateList = new ArrayList<>();
        aggregateList.add(new Document("$match", match));
        aggregateList.add(new Document("$group", group));
        aggregateList.add(new Document("$sort", sort));
        return aggregateList;
    }

    @Override
    public List<Object> getEntityNumBySkill(String skillId, String skillVersion) {
        List<String> versionIds = getEnableSkillVersion();
        List<Object> resultList = new ArrayList<>();
        List<Document> aggregateList = new ArrayList<>();
        Document sub_match = new Document();
        Document sub_group = new Document();
        Document _idDoc = new Document();
        _idDoc.put("skillVersion", "$skillVersion");
        _idDoc.put("skillId", "$skillId");
        sub_group.put("_id", _idDoc);
        sub_group.put("count", new Document("$sum", 1));
        if ("".equals(skillId) && "".equals(skillVersion)) {
            sub_match.put("skillVersion", new Document("$in", versionIds));
            aggregateList = aggregateGenerate(sub_match, sub_group, new Document("count", 1));
        } else {
            List<String> idList = new ArrayList<>(Arrays.asList(skillId.replaceAll("，", ",").split(",")));
            List<String> versionList = new ArrayList<>(Arrays.asList(skillVersion.replaceAll("，", ",").split(",")));
            idList = idList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
            versionList = versionList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
            if (versionList.isEmpty()) {
                sub_match.put("skillVersion", new Document("$in", versionIds));
            } else {
                versionList.retainAll(versionIds);
                if (!versionList.isEmpty()) {
                    sub_match.put("skillVersion", new Document("$in", versionList));
                }
            }

            if (!idList.isEmpty()) {
                sub_match.put("skillId", new Document("$in", idList));
            }
            aggregateList = aggregateGenerate(sub_match, sub_group, new Document("count", 1));
        }
        MongoCollection entityCollection = database.getCollection("entity");
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        entityCollection.aggregate(aggregateList).forEach(
                (r) -> resultList.add(r),
                (v, i) -> countDownLatch.countDown()
        );
        try {
            countDownLatch.await();
            return resultList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> getAttributesByName() {
        List<Object> resultList = new ArrayList<>();
        MongoCollection<KnowledgeModel> collection = database.getCollection("knowledge", KnowledgeModel.class);

        if (knowledgeCache.size()>0){
            String currentKnowledgeVersion = getCurrentKnowledgeVersion(collection);
            if (knowledgeCache.containsKey(currentKnowledgeVersion)){
                resultList.add(knowledgeCache.get(currentKnowledgeVersion).getConceptSchemaMap());
                return resultList;
            }
        }
        List<String> fields = new ArrayList<>();
        fields.add("_id");
        fields.add("gitTag");
        Bson projection = Projections.exclude(fields);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        collection.find().sort(descending(KNOWLEDGE_VERSION_FIELD_NAME)).projection(projection).first(
                (v, i) -> {
                    resultList.add(v.getKnowledgeSchema().getConceptSchemaMap());
                    knowledgeCache.put(v.getKnowledgeVersion(), v.getKnowledgeSchema());
                    countDownLatch.countDown();
                }
        );
        try {
            countDownLatch.await();
            return resultList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCurrentKnowledgeVersion(MongoCollection<KnowledgeModel> collection){
        final String[] version = {""};
        try {
            final CountDownLatch findLatch = new CountDownLatch(1);
            collection.find().projection(Projections.include(KNOWLEDGE_VERSION_FIELD_NAME)).sort(descending(KNOWLEDGE_VERSION_FIELD_NAME)).first((v, i) -> {
                if (i != null) {
                    logger.error("@t0:lpIHru: " + "Query knowledge version error {}", i.getMessage());
                } else if (v != null) {
                    version[0] = v.getKnowledgeVersion();
                }
                findLatch.countDown();
            });

            findLatch.await();
        } catch (InterruptedException e) {
            logger.error("@t0:HtiBPz: " + "find max knowledge version error {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return version[0];
    }

    @Override
    public String getCurrentKnowledgeVersion() {
        MongoCollection<KnowledgeModel> collection = database.getCollection("knowledge", KnowledgeModel.class);
        return getCurrentKnowledgeVersion(collection);
    }

    @Override
    public List<Object> commandExecute(Command command) throws JSONException {
        List<Object> result = commandGenerateAndRun(command);
         return result;
    }
}
