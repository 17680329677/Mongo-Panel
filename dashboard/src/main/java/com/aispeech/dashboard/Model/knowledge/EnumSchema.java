package com.aispeech.dashboard.Model.knowledge;

import com.aispeech.dashboard.Model.EntityAttribute;
import com.aispeech.dashboard.Model.EntityModel;
import com.aispeech.dashboard.Model.ValueType;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnumSchema {
    public String name;
    public List<String> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<EntityModel> toEntityModels(String skillId, String skillVersion) {
        List<EntityModel> entities = new ArrayList<>();
        values.forEach(v -> {
            EntityModel entityModel = new EntityModel();
            String id = MessageFormat.format("enum:{0}:{1}", name, v);
            String idHex = DigestUtils.md5DigestAsHex(id.getBytes(StandardCharsets.UTF_8));
            entityModel.setSkillId(skillId);
            entityModel.setSkillVersion(skillVersion);
            entityModel.setConcept(name);
            entityModel.setBaId(idHex);
            entityModel.setFullId(String.format("%s-%s-%s", idHex, skillId, skillVersion));

            HashMap<String, EntityAttribute> bav2 = new HashMap<>();

            // Set enum type
            EntityAttribute typeAttribute = new EntityAttribute();
            typeAttribute.setType(ValueType.STRING);
            typeAttribute.getValue().getStrings().add(name);

            // Set name
            EntityAttribute nameAttribute = new EntityAttribute();
            nameAttribute.setType(ValueType.STRING);
            nameAttribute.getValue().getStrings().add(v);

            // Set identifiers attribtues
            EntityAttribute identifiers = new EntityAttribute();
            EntityAttribute identifiersIgnoreCase = new EntityAttribute();
            identifiers.setType(ValueType.STRING);
            identifiersIgnoreCase.setType(ValueType.STRING);
            // TODO: Temporary save two types in identifier, in the future Identifiers will be remvoed.
            identifiers.getValue().getIdentifiers().add(v);
            identifiers.getValue().getStrings().add(v);
            identifiersIgnoreCase.getValue().getStrings().add(v.toLowerCase());

            bav2.put("type", typeAttribute);
            bav2.put("name", nameAttribute);
            bav2.put("_identifiers", identifiers);
            bav2.put("_identifiersIgnoreCase", identifiersIgnoreCase);
            entityModel.setBav2(bav2);

            entities.add(entityModel);
        });
        return entities;
    }
}
