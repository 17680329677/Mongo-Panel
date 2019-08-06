(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-284d2c98"],{1310:function(e,t,o){"use strict";o.d(t,"a",function(){return i}),o.d(t,"c",function(){return l}),o.d(t,"e",function(){return n}),o.d(t,"b",function(){return r}),o.d(t,"d",function(){return s});var a=o("b775");function i(e){return Object(a["a"])({url:"/mongo/command/execute",method:"post",data:e})}function l(){return Object(a["a"])({url:"/mongo/getCollectionList",method:"get"})}function n(e,t){return Object(a["a"])({url:"/mongo/getEntityNumBySkill",method:"get",params:{skillId:e,skillVersion:t}})}function r(){return Object(a["a"])({url:"/mongo/getAttributesByName",method:"get"})}function s(){return Object(a["a"])({url:"/mongo/getCurrentKnowledgeVersion",method:"get"})}},"908b":function(e,t,o){"use strict";var a=o("9eba"),i=o.n(a);i.a},"9eba":function(e,t,o){},b8b0:function(e,t,o){"use strict";var a=o("f84c"),i=o.n(a);i.a},be2b:function(e,t,o){"use strict";o.r(t);var a=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"app-container"},[o("el-collapse",{model:{value:e.activeName,callback:function(t){e.activeName=t},expression:"activeName"}},[o("el-collapse-item",{attrs:{title:"命令 Command",name:"1"}},[o("el-form",{ref:"form",attrs:{model:e.form,"label-position":"left",size:"small",inline:!0,"label-width":"80"}},[o("el-form-item",{attrs:{label:"database"}},[o("el-input",{model:{value:e.form.database,callback:function(t){e.$set(e.form,"database",t)},expression:"form.database"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"collection"}},[o("el-select",{attrs:{placeholder:"请选择集合"},model:{value:e.form.collection,callback:function(t){e.$set(e.form,"collection",t)},expression:"form.collection"}},e._l(e.collections,function(e){return o("el-option",{attrs:{label:e,value:e}})}),1)],1),e._v(" "),o("el-form-item",{attrs:{label:"operate"}},[o("el-select",{attrs:{placeholder:"请选择操作"},on:{change:e.onOptChange},model:{value:e.form.operate,callback:function(t){e.$set(e.form,"operate",t)},expression:"form.operate"}},[o("el-option",{attrs:{label:"----请选择操作----",value:"",selected:!0}}),e._v(" "),o("el-option",{attrs:{label:"find",value:"find"}}),e._v(" "),o("el-option",{attrs:{label:"count",value:"count"}})],1)],1),e._v(" "),o("el-form-item",{directives:[{name:"show",rawName:"v-show",value:e.diagnosticFlag,expression:"diagnosticFlag"}],attrs:{label:"diagnostic"}},[o("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:"选为true则进行诊断分析",placement:"right-start"}},[o("el-radio-group",{attrs:{size:"small"},model:{value:e.form.diagnostic,callback:function(t){e.$set(e.form,"diagnostic",t)},expression:"form.diagnostic"}},[o("el-radio",{attrs:{border:"",label:"true",value:"true"},model:{value:e.form.diagnostic,callback:function(t){e.$set(e.form,"diagnostic",t)},expression:"form.diagnostic"}}),e._v(" "),o("el-radio",{attrs:{border:"",label:"false",value:"false"},model:{value:e.form.diagnostic,callback:function(t){e.$set(e.form,"diagnostic",t)},expression:"form.diagnostic"}})],1)],1)],1)],1),e._v(" "),o("el-form",{ref:"form",attrs:{model:e.form,"label-position":"left",size:"small","label-width":"80px"}},[o("el-form-item",{attrs:{label:"filter",prop:"desc"}},[o("el-tooltip",{staticClass:"item",attrs:{effect:"dark",content:"过滤条件需填写标准的json格式，否则无法解析，eg: {status: 'Enable'}",placement:"right-start"}},[o("el-input",{staticStyle:{width:"500px",height:"100%"},attrs:{type:"textarea"},model:{value:e.form.filter,callback:function(t){e.$set(e.form,"filter",t)},expression:"form.filter"}})],1)],1)],1),e._v(" "),o("el-form",{ref:"formInteger",attrs:{model:e.form,rules:e.Rules,"label-position":"left",size:"small",inline:!0,"label-width":"80px"}},[o("el-form-item",{attrs:{label:"limit",prop:"limit"}},[o("el-input",{ref:"limit",model:{value:e.form.limit,callback:function(t){e.$set(e.form,"limit",t)},expression:"form.limit"}})],1),e._v(" "),o("el-form-item",{attrs:{label:"skip",prop:"skip"}},[o("el-input",{ref:"skip",model:{value:e.form.skip,callback:function(t){e.$set(e.form,"skip",t)},expression:"form.skip"}})],1)],1),e._v(" "),o("el-button",{staticStyle:{"text-align":"center"},attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("执 行")])],1),e._v(" "),o("el-collapse-item",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{title:"结果 Results",name:"2"}},[o("div",{staticClass:"editor-container"},[o("json-editor",{ref:"jsonEditor",model:{value:e.value,callback:function(t){e.value=t},expression:"value"}})],1)])],1)],1)},i=[],l=o("fa7e"),n=o("1310"),r=o("61f7"),s="{}",c={name:"index",components:{JsonEditor:l["a"]},data:function(){var e=function(e,t,o){Object(r["b"])(t)?o():o(new Error("请输入正整数"))};return{activeName:"1",loading:!1,diagnosticFlag:!1,Rules:{limit:[{required:!0,trigger:"blur",validator:e}],skip:[{required:!0,trigger:"blur",validator:e}]},collections:[],form:{database:"kg",collection:"",operate:"",diagnostic:"false",filter:"",limit:0,skip:0},jsonData:"",value:JSON.parse(s)}},watch:{jsonData:function(e,t){this.value=e}},methods:{onSubmit:function(){var e=this;this.$refs.formInteger.validate(function(t){if(!t)return console.log("验证未通过"),!1;e.loading=!0,Object(n["a"])(e.form).then(function(t){200===t.code?(e.$message.success("执行成功"),e.jsonData=t.data,e.loading=!1):(e.$message.warning(t.message),e.loading=!1)}).catch(function(t){console.log(t),e.loading=!1})})},onOptChange:function(){"find"===this.form.operate?this.diagnosticFlag=!0:this.diagnosticFlag=!1},getCollections:function(){var e=this;Object(n["c"])().then(function(t){200===t.code&&(e.collections=t.data)}).catch(function(e){console.log(e)})}},mounted:function(){this.getCollections()}},u=c,f=(o("908b"),o("2877")),m=Object(f["a"])(u,a,i,!1,null,"3cc6b18a",null);t["default"]=m.exports},f84c:function(e,t,o){},fa7e:function(e,t,o){"use strict";var a=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"json-editor"},[o("textarea",{ref:"textarea"})])},i=[],l=o("56b3"),n=o.n(l);o("0dd0"),o("a7be"),o("acdf"),o("f9d4"),o("8822"),o("d2de");o("ae67");var r={name:"JsonEditor",props:["value"],data:function(){return{jsonEditor:!1}},watch:{value:function(e){var t=this.jsonEditor.getValue();e!==t&&this.jsonEditor.setValue(JSON.stringify(this.value,null,2))}},mounted:function(){var e=this;this.jsonEditor=n.a.fromTextArea(this.$refs.textarea,{lineNumbers:!0,mode:"application/json",gutters:["CodeMirror-lint-markers"],theme:"rubyblue",lint:!0}),this.jsonEditor.setValue(JSON.stringify(this.value,null,2)),this.jsonEditor.on("change",function(t){e.$emit("changed",t.getValue()),e.$emit("input",t.getValue())})},methods:{getValue:function(){return this.jsonEditor.getValue()}}},s=r,c=(o("b8b0"),o("2877")),u=Object(c["a"])(s,a,i,!1,null,"fad11014",null);t["a"]=u.exports}}]);