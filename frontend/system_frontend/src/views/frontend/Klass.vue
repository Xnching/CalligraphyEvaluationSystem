<template>
    <div>
      <!-- 下面为表格的筛选-->
      <div style="text-align: left;margin-left: 30px;margin-top: 25px">
        
        <label for="provinceSelect">请先选择到学校:</label> 
        <!--下面是学校选择器，从区域然后具体选到学校-->
        <select v-model="selectedProvince" filterable @change="onProvinceChange" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择省份</option>
            <option v-for="province in provinces" :key="province.id" :value="province.id">
                {{ province.name }}
            </option>
        </select>
        <select v-model="selectedCity" filterable @change="onCityChange" v-if="cities.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择城市</option>
            <option v-for="city in cities" :key="city.id" :value="city.id">
                {{ city.name }}
            </option>
        </select>
        <!--注意是county！！！不是country-->
        <select v-model="selectedCounty" filterable @change="onCountyChange" v-if="counties.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择区/县</option>
            <option v-for="county in counties" :key="county.id" :value="county.id">
                {{ county.name }}
            </option>
        </select>
        <select v-model="selectedSchoolType" filterable @change="onSchoolTypeChange" v-if="schoolTypes.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择学校类型</option>
            <option v-for="schoolType in schoolTypes" :key="schoolType.id" :value="schoolType.id">
                {{ schoolType.name }}
            </option>
        </select>
        <select v-model="selectedSchool" v-if="schools.length" filterable @change="onSchoolChange" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择学校</option>
            <option v-for="school in schools" :key="school.id" :value="school.id">
                {{ school.name }}
            </option>
        </select>

      </div>
        


      <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
        <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
        <!-- 显示学校名称 -->
        <h1>{{ selectedSchool ? schools.find(s => s.id === selectedSchool).name : '' }}</h1> 
        <el-button type="primary" @click="handleEdit1" style="margin-left: auto; margin-right: 120px;">新增班级<i class="el-icon-circle-plus"></i></el-button>
      </div>    

      <el-table
        ref="multipleTable"
        :data="filteredTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        v-if="selectedSchool" >
        <el-table-column
          prop="name"
          label="班级名"
          width="195">
        </el-table-column>
        <el-table-column
          prop="grade"
          label="年级"
          width="290">
        </el-table-column>
        <el-table-column
          prop="numberCount"
          label="人数"
          width="140">
        </el-table-column>
        <el-table-column
          prop="teacher"
          label="教师"
          width="200">
        </el-table-column>
        
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">详情</el-button>
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
        title="请输入新增班级的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 下面选择不同的导入方案-->
        <el-tabs type="border-card">
          <el-tab-pane label="单个添加">
            
            <el-form ref="addForm" :model="addForm" label-width="100px">
              
              <el-form-item label="年级：">
                <el-select v-model="addForm.region" placeholder="请选择年级">
                  <el-option label="六三制小学一年级" value="6-3-1"></el-option>
                  <el-option label="六三制小学二年级" value="6-3-2"></el-option>
                  <el-option label="六三制小学三年级" value="6-3-3"></el-option>
                  <el-option label="六三制小学四年级" value="6-3-4"></el-option>
                  <el-option label="六三制小学五年级" value="6-3-5"></el-option>
                  <el-option label="六三制小学六年级" value="6-3-6"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="班级名称：">
                <el-input v-model="addForm.name"></el-input>
              </el-form-item>
              
            </el-form>

          </el-tab-pane>

          <el-tab-pane label="批量添加">
            <el-upload
              class="upload-demo"
              drag
              action="https://jsonplaceholder.typicode.com/posts/"
              multiple
              accept=".xls,.xlsx">
              <i class="el-icon-upload"></i>
              <div class="el-upload__tip" slot="tip">Excel文件格式以年级、班级名的顺序排列,年级规范为：六三制小学一年级</div>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              
            </el-upload>

          </el-tab-pane>
          
        </el-tabs>

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
        width="60%"
        :close-on-click-modal="false">
          <el-table
          ref="editTable"
          :data="editTableData"
          stripe
          tooltip-effect="dark"
          style="width: 100%">
          <el-table-column
            prop="id"
            label="编号"
            width="95">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="100">
          </el-table-column>
          <el-table-column
            prop="number"
            label="学籍号"
            width="190">
          </el-table-column>
          <el-table-column
            prop="password"
            label="密码"
            width="100">
          </el-table-column>
          <el-table-column
            prop="grade"
            label="年级"
            width="150">
          </el-table-column>
          <el-table-column
            prop="klass"
            label="班级"
            width="100">
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
        <template #footer>
          <span class="dialog-footer">
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
        name: "19班",
        grade: "六三制小学一年级",
        numberCount: "56",
        teacher: " 孙岳平",
        schoolId: 11111
      })),
      showTable:[],
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      editTableData: Array(8).fill().map(() => ({
        id: '111111',
        name: '王胜国',
        number:'202283290993',
        password: '123456',
        klass:'19班',
        grade:'六三制小学一年级'
      })),
      addForm:{
        name:'',
        region:''
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
  computed: {
    // 计算属性，根据选择的学校过滤班级数据
    filteredTableData() {
      if (!this.selectedSchool) return []; // 如果没有选择学校，返回空数组
      return this.tableData.filter(item => item.schoolId === this.selectedSchool); 
    },
  },

  methods: {
    
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

    // 当学校选择改变时，更新表格数据
    onSchoolChange() {
      // 你不需要在这里手动更新表格数据了，
      // 因为 filteredTableData 计算属性会自动根据 selectedSchool 的变化更新


    },

    //新增按钮跳出弹窗
    handleEdit1(){
      
      this.dialogVisible1 = true;
    },

    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {


      this.dialogVisible1 = false;  //关闭弹窗
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit2(row) {
      

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
