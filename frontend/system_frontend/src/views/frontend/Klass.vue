<template>
    <div>
      <!-- 下面为表格的筛选-->
      <div style="text-align: left;margin-left: 30px;margin-top: 25px">
        <!--下面是学校选择器，从区域然后具体选到学校-->
        <select v-model="selectedProvince" @change="onProvinceChange" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择省份</option>
            <option v-for="province in provinces" :key="province.id" :value="province.id">
                {{ province.name }}
            </option>
        </select>
        <select v-model="selectedCity" @change="onCityChange" v-if="cities.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择城市</option>
            <option v-for="city in cities" :key="city.id" :value="city.id">
                {{ city.name }}
            </option>
        </select>
        <!--注意是county！！！不是country-->
        <select v-model="selectedCounty" @change="onCountyChange" v-if="counties.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择区/县</option>
            <option v-for="county in counties" :key="county.id" :value="county.id">
                {{ county.name }}
            </option>
        </select>
        <select v-model="selectedSchoolType" @change="onSchoolTypeChange" v-if="schoolTypes.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择学校类型</option>
            <option v-for="schoolType in schoolTypes" :key="schoolType.id" :value="schoolType.id">
                {{ schoolType.name }}
            </option>
        </select>
        <select v-model="selectedSchool" v-if="schools.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择学校</option>
            <option v-for="school in schools" :key="school.id" :value="school.id">
                {{ school.name }}
            </option>
        </select>

      </div>
        


      <div style="padding:10px; margin:10px; margin-bottom: -5px;margin-top: 5px;">
        <el-input style="width:300px ;margin-left:-80px" 
        suffix-icon="el-icon-search" 
        placeholder="请输入"
        v-model="inputVal"
        @keyup.enter.native="Search_table()"
        clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:535px" type="primary">搜索</el-button>
        <el-button type="primary" @click="handleEdit1">新增<i class="el-icon-circle-plus"></i></el-button>
      </div>       

      <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="ID"
          label="用户ID"
          width="95">
        </el-table-column>
        <el-table-column
          prop="name"
          label="用户姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="loginName"
          label="登录名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          width="120">
        </el-table-column>
        <el-table-column
          prop="userGroup"
          label="所属用户组"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="phone"
          label="联系方式"
          width="120">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">编辑</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="20">
        </el-pagination>
      </div>

      <!-- 新增系统用户的弹窗 -->
      <el-dialog
        title="请输入新增系统用户的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 用于提示新增用户时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户姓名" prop="name"> <el-input v-model="ruleForm.name"></el-input> </el-form-item>
          <el-form-item label="登录名" prop="loginName"> <el-input v-model="ruleForm.loginName"></el-input> </el-form-item>
          <el-form-item label="密码" prop="password"> <el-input v-model="ruleForm.password"></el-input> </el-form-item>
          <el-form-item label="联系方式"prop="phone"> <el-input v-model="ruleForm.phone"></el-input> </el-form-item>
          <el-form-item label="所属用户组" prop="userGroup">
            <el-select v-model="ruleForm.userGroup" placeholder="请选择所属用户组" >
              <el-option label="用户组 A" value="groupA"></el-option>
              <el-option label="用户组 B" value="groupB"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit1()">确定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑按钮的弹窗 -->
      <el-dialog
        title="点击修改信息"
        :visible.sync="dialogVisible2"
        width="40%"
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="用户姓名"> 
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="登录名">
            <el-input v-model="editForm.loginName"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="editForm.password"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="editForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="所属用户组">
            <el-select v-model="editForm.userGroup" placeholder="请选择所属用户组">
              <el-option label="用户组 A" value="groupA"></el-option>
              <el-option label="用户组 B" value="groupB"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit2">确定</el-button>
          </span>
        </template>
      </el-dialog>

    

    </div>
</template>


<script>
export default {
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        ID:"10000",
        name: "孙岳平",
        loginName: "335508880",
        password: "12345678",
        userGroup: "盘成小学竞赛评阅教师组",
        phone: "17715062004"
      })),
      showTable:[],
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      //多选用的
      multipleSelection: [],
      editForm: { // 添加 editForm 即编辑弹窗里的表单定义
        ID: '',
        name: '',
        loginName: '',
        password: '',
        phone: '',
        userGroup: ''
      },
      //规则，即添加里的验证规则
      ruleForm: {
        name: '',
        loginName: '',
        password: '',
        phone: '',
        userGroup: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' }
        ],
        loginName: [
          { required: true, message: '请输入登录名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          { pattern: /^[0-9]+$/, message: '联系方式必须为数字', trigger: 'blur' }
        ],
        userGroup: [
          { required: true, message: '请选择所属用户组', trigger: 'change' }
        ]
      },


      //为区域列表的数据
      provinces: [
        { id: 1, name: '北京市', cities: [
          { id: 11, name: '北京市', counties: [
            { id: 111, name: '东城区' ,schoolTypes:[
                {id: 1111 ,name:'小学',schools:[
                    {id:11111,name:'北京市东城区第一小学'},
                    {id:11112,name:'北京市东城区第二小学'},
                ]},{id: 1112 ,name:'初中',schools:[
                    {id:11121,name:'北京市东城区第一初中'},
                    {id:11122,name:'北京市东城区第二初中'},
                ]}
            ]},
            { id: 112, name: '西城区' ,schoolTypes:[
                {id: 1121 ,name:'小学',schools:[
                    {id:11211,name:'北京市西城区第一小学'},
                    {id:11212,name:'北京市西城区第二小学'},
                ]},{id: 1122 ,name:'初中',schools:[
                    {id:11221,name:'北京市西城区第一初中'},
                    {id:11222,name:'北京市西城区第二初中'},
                ]}
            ]},
          ]},
          { id: 12, name: '朝阳区', counties: [
            { id: 121, name: '望京街道' ,schoolTypes:[
                {id: 1121 ,name:'小学',schools:[
                    {id:12211,name:'北京市西城区第一小学'},
                    {id:12212,name:'北京市西城区第二小学'},
                ]},{id: 1222 ,name:'初中',schools:[
                    {id:12221,name:'北京市西城区第一初中'},
                    {id:12222,name:'北京市西城区第二初中'},
                ]}
            ]},
            { id: 122, name: '三里屯街道' ,schoolTypes:[
                {id: 1221 ,name:'小学',schools:[
                    {id:12211,name:'北京市西城区第一小学'},
                    {id:11212,name:'北京市西城区第二小学'},
                ]},{id: 1222 ,name:'初中',schools:[
                    {id:12221,name:'北京市西城区第一初中'},
                    {id:12222,name:'北京市西城区第二初中'},
                ]}
            ]},
          ]},
        ]},
        { id: 2, name: '上海市', cities: [
          { id: 21, name: '上海市', counties: [
            { id: 211, name: '黄浦区' ,schoolTypes:[
                {id: 2121 ,name:'小学',schools:[
                    {id:21211,name:'上海市黄浦区第一小学'},
                    {id:21212,name:'上海市黄浦区第二小学'},
                ]},{id: 2122 ,name:'初中',schools:[
                    {id:21221,name:'上海市黄浦区第一初中'},
                    {id:21222,name:'上海市黄浦区第二初中'},
                ]}
            ]},
            { id: 212, name: '徐汇区' ,schoolTypes:[
                {id: 2121 ,name:'小学',schools:[
                    {id:21211,name:'上海市徐汇区第一小学'},
                    {id:21212,name:'上海市徐汇区第二小学'},
                ]},{id: 2122 ,name:'初中',schools:[
                    {id:21221,name:'上海市徐汇区第一初中'},
                    {id:21222,name:'上海市徐汇区第二初中'},
                ]}
            ]},
          ]},
          { id: 22, name: '浦东新区', counties: [
            { id: 221, name: '陆家嘴街道' ,schoolTypes:[
                {id: 2221 ,name:'小学',schools:[
                    {id:22211,name:'上海市浦东新区第一小学'},
                    {id:22212,name:'上海市浦东新区第二小学'},
                ]},{id: 2222 ,name:'初中',schools:[
                    {id:22221,name:'上海市浦东新区第一初中'},
                    {id:22222,name:'上海市浦东新区第二初中'},
                ]}
            ]},
            { id: 222, name: '世纪大道街道' ,schoolTypes:[
                {id: 2221 ,name:'小学',schools:[
                    {id:22211,name:'上海市世纪大道街道第一小学'},
                    {id:22212,name:'上海市世纪大道街道第二小学'},
                ]},{id: 2222 ,name:'初中',schools:[
                    {id:22221,name:'上海市世纪大道街道第一初中'},
                    {id:22222,name:'上海市世纪大道街道第二初中'},
                ]}
            ]},
          ]},
        ]},
      ], // 省份数据
      cities: [], // 城市数据
      counties: [], // 区/县数据
      schoolTypes:[],
      schools:[],
      selectedProvince: '', 
      selectedCity: '', 
      selectedCounty: '' ,
      selectedSchoolType:'',
      selectedSchool:''

    };
  },
  watch: {
    //用于实现搜索栏搜索的
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
  },

  methods: {
    //实现表格前面的多选框的
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    //
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //实现搜索栏多属性搜索的
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let name= e.name;
        let loginName= e.loginName;
        let password= e.password;
        let userGroup= e.userGroup;
        let phone= e.phone;
        if (ID.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (name.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (loginName.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (password.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (userGroup.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (phone.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },

    //新增按钮跳出弹窗
    handleEdit1(){
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.resetFields(); // 清除表单验证并重置表单
      }
      this.dialogVisible1 = true;
    },

    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          // 表单验证通过，提交数据

          // 重置表单
          this.$refs.ruleForm.resetFields(); 
          this.dialogVisible1 = false;  //关闭弹窗
          // 在此处添加你的提交逻辑，例如发送数据到服务器

        } else {
          return false; // 阻止表单提交
        }
      });
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit2(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.dialogVisible2 = true;
    },

    //编辑用户弹窗确定提交的方法
    handleSubmit2() {
      // 在此处处理表格提交逻辑
      
      this.dialogVisible2 = false; // 关闭弹窗
    },

    //当省份选择改变时 (onProvinceChange)，根据选择的省份过滤出对应的城市列表，并清空下级区域数据。
    onProvinceChange() {
      const selectedProvinceId = parseInt(this.selectedProvince, 10);
      const selectedProvince = this.provinces.find(p => p.id === selectedProvinceId);
      this.cities = selectedProvince ? selectedProvince.cities : [];
      this.counties = []; 
      this.schoolTypes = [];
      this.schools = [];
      this.selectedCity = ''; 
      this.selectedCounty = ''; 
      this.selectedSchoolType = '';
      this.selectedSchool = '';
    },
    //当城市选择改变时 (onCityChange)，根据选择的城市过滤出对应的区/县列表。
    onCityChange() {
      const selectedCityId = parseInt(this.selectedCity, 10);
      const selectedCity = this.cities.find(c => c.id === selectedCityId);
      this.counties = selectedCity ? selectedCity.counties : [];
      this.schoolTypes = [];
      this.schools = [];
      this.selectedCounty = ''; 
      this.selectedSchoolType = '';
      this.selectedSchool = '';
    },
    //区县
    onCountyChange(){
        const selectedCountyId = parseInt(this.selectedCounty, 10);
        const selectedCounty = this.counties.find(c => c.id === selectedCountyId);
        this.schoolTypes = selectedCounty ? selectedCounty.schoolTypes : [];
        this.schools = [];
        this.selectedSchoolType = '';
        this.selectedSchool = '';
    },
    //学校类型
    onSchoolTypeChange(){
        const selectedSchoolTypeId = parseInt(this.selectedSchoolType, 10);
        const selectedSchoolType = this.schoolTypes.find(s => s.id === selectedSchoolTypeId);
        this.schools = selectedSchoolType ? selectedSchoolType.schools: [];
        this.selectedSchool = '';
    },

    //分页用的功能
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {}
  },
  //把tableData数值赋给showTable
  created(){
    this.showTable = [...this.tableData];
  }
};
</script>
