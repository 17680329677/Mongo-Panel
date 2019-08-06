<template>
  <div  class="app-container">
    <el-collapse v-model="activeNames">

      <el-collapse-item title="快捷命令 Command" name="1" v-loading="loading_bar" v-show="commandShow">
        <el-form ref="form" label-position="left" size="small" :inline="true" label-width="80" @keyup.enter.native="onSubmit">

          <el-form-item label="name" v-if="operateType === 'getEntityByNameAndSkill'">
            <el-tooltip class="item" effect="dark" content="此处可填写多个name，请使用中文或英文逗号将多个name分隔开， eg：思必驰，高始兴" placement="top">
              <el-input v-model="name" style="width: 300px"></el-input>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="skillId" v-if="operateType === 'getEntityByNameAndSkill'">
            <el-tooltip class="item" effect="dark" content="此处可填写多个skillId，请使用中文或英文逗号将多个skillId分隔开， eg：101165,101166" placement="top">
              <el-input v-model="skillId" style="width: 300px"></el-input>
            </el-tooltip>
          </el-form-item>

          <el-form-item label="ruleId" v-show="operateType === 'getRuleById'">
            <el-input v-model="ruleId" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="Concept" v-show="operateType === 'getRuleById'">
            <el-autocomplete v-model="ruleConcept" :fetch-suggestions="querySearch"></el-autocomplete>
          </el-form-item>
          <el-form-item label="Attribute" v-show="operateType === 'getRuleById'">
            <el-autocomplete v-model="ruleAttr" :fetch-suggestions="queryAttrs"></el-autocomplete>
          </el-form-item>

          <el-form-item label="skillID" v-if="operateType === 'getEntityNumBySkillVersion'">
            <el-tooltip class="item" effect="dark" content="根据填写skillID, 多个ID用逗号分隔" placement="top">
              <el-input v-model="skillParam.skillId" style="width: 300px"></el-input>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="skillVersion" v-if="operateType === 'getEntityNumBySkillVersion'">
            <el-tooltip class="item" effect="dark" content="根据所选查询依据填写skillID 或 skillVersion，多个参数用逗号分隔" placement="top">
              <el-input v-model="skillParam.skillVersion" style="width: 300px"></el-input>
            </el-tooltip>
          </el-form-item>

          <el-form-item label="conceptName" v-show="operateType === 'getAttributesByName'">
            <el-autocomplete v-model="conceptName" :fetch-suggestions="querySearch" @select="handleSelected"></el-autocomplete>
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="onSubmit" v-if="operateType!=='getAttributesByName'" style="text-align: right" circle></el-button>
        </el-form>

      </el-collapse-item>

      <el-collapse-item title="结果 Results" name="2" v-loading="loading">
        <el-tag type="success" v-html="'实体总数：' + resultCount" v-if="operateType === 'getEntityNumBySkillVersion'"></el-tag>
        <el-container v-if="operateType === 'getAttributesByName'">
          <el-aside>
            <el-table :data="tableData" border style="width: 100%" @row-click="showAttr" height="600px">
              <el-table-column prop="attr" label="属性"></el-table-column>
            </el-table>
          </el-aside>
          <el-main>
            <div class="editor-container">
              <json-editor ref="jsonEditor" v-model="value" />
            </div>
          </el-main>
        </el-container>
        <div class="editor-container" v-if="operateType !== 'getAttributesByName'">
          <json-editor ref="jsonEditor" v-model="value" />
        </div>
      </el-collapse-item>

    </el-collapse>
  </div>
</template>

<script>
  import JsonEditor from '../../components/JsonEditor'
  import {excuteCommand, getEntityNumBySkill, getAttributesByName, getCurrentKnowledgeVersion} from "@/api/mongo/mongo";

  var jsonData = '{}';
  export default {
    name: "index",
    components: { JsonEditor },
    data() {
      return {
        activeNames: ['1', '2'],
        operateType: '',
        loading: false,
        loading_bar: false,
        commandShow: false,
        knowledgeVersion: '',
        name: '',
        skillId: '',
        ruleId: '',
        ruleConcept: '',
        ruleAttr: '',
        skillParam: {
          skillId: '',
          skillVersion: ''
        },
        conceptName: '',
        form: {
          database: 'kg',
          collection: '',
          operate: '',
          diagnostic: false,
          filter: '',
          limit: 0,
          skip: 0
        },
        conceptList: '',
        jsonData: '',
        conceptScheme: '',
        attrName: '',
        tableData: [],
        resultCount: 0,
        value: JSON.parse(jsonData)
      }
    },
    watch: {
      jsonData: function (newValue, oldValue) {
        this.value = newValue;
      },
      operateType: function (newValue,oldValue) {
        this.jsonData = null;
        if (this.operateType === 'getEntityByNameAndSkill') {
          this.commandShow = true;
        } else if (this.operateType === 'getEnableSkillCount') {
          this.commandShow = false;
          this.form.collection = 'skill';
          this.form.filter = "{status: \'Enable\'}"
          this.form.operate = 'count';
          this.excuteCommand(this.form);
        } else if (this.operateType === 'getRuleById') {
          this.commandShow = true;
          this.loading_bar = true;
          this.getKnowledgeVersion();
          getAttributesByName().then(res => {
            if (res.code === 200) {
              this.conceptScheme = res.data;
              var arr = new Array();
              for (var key in res.data[0]) {
                arr.push({value: key});
              }
              this.conceptList = arr;
            }
            this.loading_bar = false;
          }).catch((error) => {console.log(error); this.loading=false;})
        } else if (this.operateType === 'getEntityNumBySkillVersion') {
          this.commandShow = true;
        } else if (this.operateType === 'getAttributesByName') {
          this.loading_bar = true;
          this.commandShow = true;
          getAttributesByName().then(res => {
            if (res.code === 200) {
              this.conceptScheme = res.data;
              var arr = new Array();
              for (var key in res.data[0]) {
                arr.push({value: key});
              }
              this.conceptList = arr;
            }
            this.loading_bar = false;
          }).catch((error) => {console.log(error); this.loading=false;})
        }
      }
    },
    methods: {
      onSubmit() {
        if (this.operateType === 'getEntityByNameAndSkill') {
          this.form.collection = 'entity';
          this.form.operate = 'find';
          var nameArr = this.name.replace(/，/g,",").split(',');
          var nameList = '';
          for (let i = 0; i < nameArr.length; i++) {
            nameList = nameList + "\'" + nameArr[i] + "\',"
          }
          var skillArr = this.skillId.replace(/，/g,",").split(',');
          var skillList = '';
          for (let i = 0; i < skillArr.length; i++) {
            skillList = skillList + "\'" + skillArr[i] + "\',"
          }
          if (this.skillId !== null && this.skillId !== '') {
            this.form.filter = "{\'bav2._identifiers.value.strings\': {$in:[" + nameList + "]}, \'skillId\': {$in:[" + skillList + "]}}";
          } else {
            this.form.filter = "{\'bav2._identifiers.value.strings\': {$in:[" + nameList + "]}}";
          }
          this.excuteCommand(this.form);
        } else if (this.operateType === 'getRuleById') {
          this.form.collection = 'rule';
          this.form.operate = 'find';
          if (this.ruleId === '') {
            this.form.filter = "{knowledgeVersion: \'" + this.knowledgeVersion + "\', ruleDefinition: {$regex: \'.*" + this.ruleConcept + ".*" + this.ruleAttr + ".*\'" + "}}";
          } else {
            this.form.filter = "{knowledgeVersion: \'" + this.knowledgeVersion + "\', ruleId: \'" + this.ruleId + "\', ruleDefinition: {$regex: \'.*" + this.ruleConcept + ".*" + this.ruleAttr + ".*\'" + "}}";
          }
          this.excuteCommand(this.form);
        } else if (this.operateType === 'getEntityNumBySkillVersion') {
          this.loading = true;
          getEntityNumBySkill(this.skillParam.skillId, this.skillParam.skillVersion).then(res => {
            if (res.code === 200) {
              this.$message.success("执行成功");
              this.jsonData = res.data;
              let count = 0;
              for (let i = 0; i < res.data.length; i++) {
                count += res.data[i]['count'];
              }
              this.resultCount = count;
            }
            this.loading = false;
          }).catch((error) => {console.log(error); this.loading = false;})
        }
      },
      excuteCommand(data) {
        this.loading = true;
        excuteCommand(this.form).then(res => {
          if(res.code === 200) {
            this.$message.success("执行成功");
            this.jsonData = res.data;
          }
          this.loading = false;
        }).catch((error) => {console.log(error); this.loading = false;})
      },
      querySearch(queryString, callback) {
        var lists = this.conceptList;
        var results = queryString ? lists.filter(this.createFilter(queryString)) : lists;
        callback(results);
      },
      createFilter(queryString) {
        return (list) => {
          return (list.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      queryAttrs(queryString, callback) {
        let lists = this.conceptScheme[0][this.ruleConcept];
        this.attrName = (typeof(lists['attributes']) !== "undefined") ? 'attributes' :  (typeof(lists['attributeSchemaMap']) !== "undefined") ? 'attributeSchemaMap' : '';
        if (this.attrName === '') {
          this.$message.info("无法获取属性列表！");
        } else {
          let arr = new Array();
          for (var attr in lists[this.attrName]) {
            arr.push({value: attr});
          }
          callback(arr);
        }
      },
      handleSelected(item) {
        this.jsonData = this.conceptScheme[0][item.value];
        var arr = new Array();
        this.attrName = (typeof(this.jsonData['attributes']) !== "undefined") ? 'attributes' :  (typeof(this.jsonData['attributeSchemaMap']) !== "undefined") ? 'attributeSchemaMap' : '';
        if (this.attrName === '') {
          this.$message.info("无法获取属性列表！");
        } else {
          for (var attr in this.jsonData[this.attrName]) {
            arr.push({attr: attr});
          }
        }
        this.sortByKey(arr, 'attr');
        this.tableData = arr;
      },
      sortByKey(array,key){
        return array.sort(function(a,b){
          var x = a[key].toLowerCase();
          var y = b[key].toLowerCase();
          return((x<y)?-1:((x>y)?1:0));
        })
      },
      showAttr(row) {
        this.jsonData = this.conceptScheme[0][this.conceptName][this.attrName][row.attr];
      },
      getKnowledgeVersion() {
        getCurrentKnowledgeVersion().then(res => {
          this.knowledgeVersion = res.data;
        })
      }
    },
    created() {
      let pathArr = this.$route.path.split('/');
      this.operateType = pathArr[pathArr.length-1];
    }
  }
</script>

<style scoped>
  .editor-container{
    position: relative;
    height: 100%;
  }
</style>

