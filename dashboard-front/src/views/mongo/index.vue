<template>
    <div  class="app-container">
      <el-collapse v-model="activeName">
        <el-collapse-item title="命令 Command" name="1">
          <el-form ref="form" :model="form" label-position="left" size="small" :inline="true" label-width="80">
            <el-form-item label="database">
              <el-input v-model="form.database"></el-input>
            </el-form-item>
            <el-form-item label="collection">
              <el-select v-model="form.collection" placeholder="请选择集合">
                <el-option v-for="collection in collections" :label="collection" :value="collection"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="operate">
              <el-select v-model="form.operate" placeholder="请选择操作" @change="onOptChange">
                <el-option label="----请选择操作----" value="" :selected="true"></el-option>
                <el-option label="find" value="find"></el-option>
                <el-option label="count" value="count"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="diagnostic" v-show="diagnosticFlag">
              <el-tooltip class="item" effect="dark" content="选为true则进行诊断分析" placement="right-start">
                <el-radio-group v-model="form.diagnostic" size="small">
                  <el-radio border label="true" value="true" v-model="form.diagnostic"></el-radio>
                  <el-radio border label="false" value="false" v-model="form.diagnostic"></el-radio>
                </el-radio-group>
              </el-tooltip>
            </el-form-item>
          </el-form>

          <el-form ref="form" :model="form" label-position="left" size="small" label-width="80px">
            <el-form-item label="filter" prop="desc">
              <el-tooltip class="item" effect="dark" content="过滤条件需填写标准的json格式，否则无法解析，eg: {status: 'Enable'}" placement="right-start">
                <el-input type="textarea" style="width: 500px; height: 100%" v-model="form.filter"></el-input>
              </el-tooltip>
            </el-form-item>
          </el-form >

          <el-form ref="formInteger" :model="form" :rules="Rules" label-position="left" size="small" :inline="true" label-width="80px">
            <el-form-item label="limit" prop="limit">
              <el-input v-model="form.limit" ref="limit"></el-input>
            </el-form-item>
            <el-form-item label="skip" prop="skip">
              <el-input v-model="form.skip" ref="skip"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" style="text-align: center" @click="onSubmit">执 行</el-button>
        </el-collapse-item>

        <el-collapse-item title="结果 Results" name="2" v-loading="loading">
          <div class="editor-container">
            <json-editor ref="jsonEditor" v-model="value" />
          </div>
        </el-collapse-item>

      </el-collapse>
    </div>
</template>

<script>
    import JsonEditor from '../../components/JsonEditor'
    import {excuteCommand, getCollections} from "@/api/mongo/mongo"
    import { isInteger } from '@/utils/validate'

    var jsonData = '{}';
    export default {
      name: "index",
      components: { JsonEditor },
      data() {
        const validateInteger = (rule, value, callback) => {
          if (!isInteger(value)) {
            callback(new Error('请输入正整数'))
          } else {
            callback()
          }
        };
        return {
          activeName: '1',
          loading: false,
          diagnosticFlag: false,
          Rules: {
            limit: [{ required: true, trigger: 'blur', validator: validateInteger }],
            skip: [{ required: true, trigger: 'blur', validator: validateInteger }]
          },
          collections: [],
          form: {
            database: 'kg',
            collection: '',
            operate: '',
            diagnostic: 'false',
            filter: '',
            limit: 0,
            skip: 0
          },
          jsonData: '',
          value: JSON.parse(jsonData)
        }
      },
      watch: {
        jsonData: function (newValue, oldValue) {
          this.value = newValue;
        }
      },
      methods: {
        onSubmit() {
          this.$refs.formInteger.validate(valid => {
            if (valid) {
              this.loading = true;
              excuteCommand(this.form).then(res => {
                if(res.code === 200) {
                  this.$message.success("执行成功");
                  this.jsonData = res.data;
                  this.loading = false;
                } else {
                  this.$message.warning(res.message)
                  this.loading = false;
                }
              }).catch((error) => {console.log(error); this.loading=false;})
            } else {
              console.log("验证未通过");
              return false;
            }
          })
        },
        onOptChange() {
          if (this.form.operate === 'find') {
            this.diagnosticFlag = true;
          } else {
            this.diagnosticFlag = false;
          }
        },
        getCollections() {
          getCollections().then(res => {
            if (res.code === 200) {
              this.collections = res.data;
            }
          }).catch((error) => {console.log(error);})
        }

      },
      mounted() {
        this.getCollections();
      }
    }
</script>

<style scoped>
  .editor-container{
    position: relative;
    height: 100%;
  }
</style>
