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
        <select v-model="selectedSchool" filterable v-if="schools.length" @change="onSchoolChange" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
            <option value="">请选择学校</option>
            <option v-for="school in schools" :key="school.id" :value="school.id">
                {{ school.name }}
            </option>
        </select>

      </div>
        


      <div style="display: flex; align-items: center; 
      padding: 10px; margin: 10px; margin-bottom: -5px; 
      margin-top: 5px;"
      v-if="selectedSchool">
        <!-- 输入框 -->
        <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary"  @click="Search_table">搜索</el-button>
        <!-- 显示学校名称 -->
        <h1>{{ selectedSchool ? schools.find(s => s.id === selectedSchool).name : '' }}</h1> 
        <el-button type="primary" @click="handleAdd" style="margin-left: auto; margin-right: 120px;">新增教师<i class="el-icon-circle-plus"></i></el-button>
      </div>    

      <!-- 表格 -->
      <el-table
        ref="multipleTable"
        :data="tableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        v-if="selectedSchool" >
          <el-table-column
            prop="id"
            label="教师编号"
            width="150">
          </el-table-column>
          <el-table-column
            prop="workno"
            label="工号"
            width="205">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="130">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="电话号码"
            width="200">
          </el-table-column>
        
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">编辑</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px" v-if="selectedSchool">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

      <!-- 新增教师的弹窗 -->
      <el-dialog
        title="请输入新增教师的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 下面选择不同的导入方案-->
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="单个添加" name="single">
            
            <el-form ref="addForm" :model="addForm" label-width="100px">
              <el-form-item label="工号：">
                <el-input v-model="addForm.workno"></el-input>
              </el-form-item>
              <el-form-item label="教师姓名：">
                <el-input v-model="addForm.name"></el-input>
              </el-form-item>
              <el-form-item label="账户密码：">
                <el-input v-model="addForm.password"></el-input>
                <label>如果为空则默认同工号</label>
              </el-form-item>
              <el-form-item label="身份证号：">
                <el-input v-model="addForm.idNumber"></el-input>
              </el-form-item>
              <el-form-item label="电话号码：">
                <el-input v-model="addForm.phone"></el-input>
              </el-form-item>
              
            </el-form>

          </el-tab-pane>

          <el-tab-pane label="批量添加" name="batch" style=" text-align: center;">
            <el-upload
              class="upload-demo"
              drag
              action="https://jsonplaceholder.typicode.com/posts/"
              multiple
              :limit="1"
              accept=".xls,.xlsx"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              :before-upload="beforeUpload">
              <i class="el-icon-upload"></i>
              <div class="el-upload__tip" slot="tip">Excel文件的格式以工号、教师姓名、
                密码、身份证号、电话号码作为表的字段名,密码若为空则同工号。</div>
              <div class="el-upload__text">只接受Excel文件,将文件拖到此处,或<em>点击上传</em></div>
              
            </el-upload>

          </el-tab-pane>
          
        </el-tabs>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit1">确定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑按钮的弹窗 -->
      <el-dialog
        title="点击修改信息"
        :visible.sync="dialogVisible2"
        width="30%"
        destroy-on-close
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="工号：">
            <el-input v-model="editForm.workno" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名：">
            <el-input v-model="editForm.name" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="密码：">
            <el-input v-model="editForm.password" :disabled="!isEditing"></el-input>
            <label>如果为空则默认同工号</label>
          </el-form-item>
          <el-form-item label="身份证号：">
            <el-input v-model="editForm.idNumber" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="电话号码：">
            <el-input v-model="editForm.phone" :disabled="!isEditing"></el-input>
          </el-form-item>
        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="handleCancel">取消</el-button>
            <el-button type="primary" @click="isEditing ? handleConfirm() : handleEdit2()">
              {{ isEditing ? '确定' : '编辑' }}
            </el-button>
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
      pageNum:1,
      pageSize:5,
      total:0,
      activeName:'single',
      isEditing:false,
      tableData: [],
      showTable:[],
      //初始隐藏两个弹窗
      dialogVisible1: false,
      dialogVisible2: false,
      //新增表单里的数据
      addForm:{
        workno:'',
        name:'',
        idNumber:'',
        password:'',
        phone:'',
        schoolId:"",
      },
      //编辑表单里的数据
      editForm:{
        id:'',
        workno:'',
        name:'',
        idNumber:'',
        password:'',
        phone:'',
      },
      
      //为区域列表的数据
      provinces: [], // 省份数据
      cities: [], // 城市数据
      counties: [], // 区/县数据
      schoolTypes:[],
      schools:[],
      selectedProvince: '', 
      selectedCity: '', 
      selectedCounty: '' ,
      selectedSchoolType:'',
      selectedSchool:'',
      selectedSchoolId:''

    };
  },
  created(){
    //请求获取省份数据
    this.getPronvince();
  },
  watch: {

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
      this.load();
    },
    //编辑里的取消事件
    handleCancel(){
      this.isEditing = false;
      this.dialogVisible2 = false;
    },
    //编辑弹窗里的编辑事件
    handleEdit2(){
      this.isEditing = true;
    },
    clearAddForm(){
      this.addForm = {
        workno: '',
        name: '',
        password: '',
        idNumber: '',
        phone: ''
      };
    },
    //编辑弹窗里的确认事件
    handleConfirm(){
      this.request.put("/teacher/update",this.editForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改教师数据成功！');
          this.load();
        } else {
          this.$message.error('修改教师数据失败，原因：' + res.msg);
        }
      })
      this.isEditing = false;
    },
    //主表里的删除
    handleDelete(row){
      this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request.put('/teacher/delete', {
          id: row.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除教师数据成功！');
            this.load();
          }else{
            this.$message.error('删除教师数据失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    beforeUpload(file) {
      // 创建一个 FormData 对象来包装你的文件和数据
      let formData = new FormData();
      formData.append('file', file);
      formData.append('id', new Blob([JSON.stringify(this.selectedSchoolId)], {
        type: 'application/json'
      }));

      // 使用你的 axios 实例发送请求
      this.request.post('/teacher/batch-add',formData,{
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(response => {
        // 处理响应
        this.handleExcelImportSuccess(response);
      }).catch(error => {
        // 处理错误
        this.handleExcelImportError(error);
      });

      // 阻止 `el-upload` 组件的默认上传行为
      return false;
    },
    //导入成功
    handleExcelImportSuccess(response, file, fileList){ 
      //console.log("到了导入成功里面去了");
      if(response.code=='200'){
        this.$message.success("导入成功");
        this.load();
      }else{
        this.$message.error("导入失败，原因为："+response.msg);
      }
    },
    //导入失败
    handleExcelImportError(err, file, fileList){ 
      //console.log(err);
      this.$message.error("导入失败，原因为"+err);
    },
    //新增按钮跳出弹窗
    handleAdd(){
      this.clearAddForm();
      this.dialogVisible1 = true;
    },
    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {
      this.addForm.schoolId=this.selectedSchoolId;
      //console.log("打印下"+this.activeName);
      if(this.activeName == 'single'){
        this.request.post("/teacher/single-add",this.addForm).then(res=>{
          if(res.code == '200'){
            this.$message.success('新增教师数据成功！');
            this.load();
          } else {
            this.$message.error('新增教师数据失败，原因：' + res.msg);
          }
        })
      }
      this.dialogVisible1 = false;  //关闭弹窗
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit1(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      //console.log("看看gradeId是不是为空："+this.editForm.gradeId);
      if(this.editForm.gradeId)
        this.getKlasses(this.editForm.gradeId);
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
      this.counties = []; 
      this.selectedCity = ''; 
      this.selectedCounty = ''; 
      this.getCity(selectedProvinceId);
    },
    //当城市选择改变时 (onCityChange)，根据选择的城市过滤出对应的区/县列表。
    onCityChange() {
      const selectedCityId = parseInt(this.selectedCity, 10);
      this.selectedCounty = ''; 
      this.getCounty(selectedCityId);
    },
    //区县
    onCountyChange(){
        this.schoolTypes=[{
          id:1,name:'小学'
        },{
          id:2,name:'初中'
        },{
          id:3,name:'中小学'
        }
        ];
        this.schools = [];
        this.selectedSchoolType = '';
        this.selectedSchool = '';
    },
    //学校类型
    onSchoolTypeChange(){
        const selectedCountyId = parseInt(this.selectedCounty, 10);
        const selectedSchoolTypeId = parseInt(this.selectedSchoolType, 10);
        const selectedSchoolType = this.schoolTypes.find(s => s.id === selectedSchoolTypeId);
        this.getSchools(selectedCountyId,selectedSchoolType.name)
        this.selectedSchool = '';
    },
    // 当学校选择改变时，更新表格数据
    onSchoolChange() {
      this.selectedSchoolId=parseInt(this.selectedSchool,10);
      this.load();
    },

    //分页用的功能
    handleCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      this.load();
    },
    handleSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      this.load();
    },
    //请求分页查询数据
    load(){
      this.request.get("/teacher/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
          schoolId:this.selectedSchoolId
        }
      }).then(res=>{
        //console.log(res);
        if(res.code=='200'){
          console.log(res);
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //获取省份数据渲染省份下拉框
    getPronvince(){
      this.request.get("/region/provinces").then(res=>{
        if(res.code=='200'){
          this.provinces=res.data;
        }else{
          this.$message.error('获取全部省级地区数据失败，原因：'+res.msg);
        }

      })
    },
    getCity(val){
      const selectedProvinceId = val;
      this.request.get('/region/cities', { 
        params:{
          provinceId: selectedProvinceId 
        }
      }).then(res=>{
        if(res.code=='200'){
          //console.log(res);
          this.cities=res.data;
        }else{
          this.$message.error('获取全部市级地区数据失败，原因：'+res.msg);
        }
      })
    },
    getCounty(val){
      const selectedCityId = val;
      this.request.get('/region/counties', { 
        params:{
          cityId: selectedCityId
        }
      }).then(res=>{
        if(res.code=='200'){
          //console.log(res);
          this.counties=res.data;
        }else{
          this.$message.error('获取全部县级地区数据失败，原因：'+res.msg);
        }
      })
    },
    getSchools(val1,val2){
      const selectedCountyId =val1;
      const selectedSchoolType=val2;
      this.request.get('/school/schools', { 
        params:{
          countyId: selectedCountyId,
          type: selectedSchoolType
        }
      }).then(res=>{
        if(res.code=='200'){
          //console.log(res);
          this.schools=res.data;
          //console.log("schools是什么"+this.schools);
        }else{
          this.$message.error('获取当前地区全部学校数据失败，原因：'+res.msg);
        }
      })
    },
  },
  
};
</script>
