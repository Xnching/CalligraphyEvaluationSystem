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
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="Search_table">搜索</el-button>
        <!-- 显示学校名称 -->
        <h1>{{ selectedSchool ? schools.find(s => s.id === selectedSchool).name : '' }}</h1> 
        <el-button type="primary" @click="handleAdd" style="margin-left: auto; margin-right: 120px;">新增学生<i class="el-icon-circle-plus"></i></el-button>
      </div>    

      <el-table
        ref="multipleTable"
        :data="tableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        v-if="selectedSchool" >
        <el-table-column
            prop="id"
            label="学生编号"
            width="105">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="150">
          </el-table-column>
          <el-table-column
            prop="studentNumber"
            label="学籍号"
            width="190">
          </el-table-column>
          <el-table-column
            prop="phone"
            label="电话"
            width="120">
          </el-table-column>
          <el-table-column
            prop="grade"
            label="年级"
            width="200">
          </el-table-column>
          <el-table-column
            prop="klass"
            label="班级"
            width="100">
          </el-table-column>
        
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">编辑</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete"  @click="handleDelete(scope.row)">删除</el-button>
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

      <!-- 新增系统用户的弹窗 -->
      <el-dialog
        title="请输入新增学生的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 下面选择不同的导入方案-->
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="单个添加" name="single">
            
            <el-form ref="addForm" :model="addForm" label-width="100px">
              <el-form-item label="学籍号：">
                <el-input v-model="addForm.studentNumber"></el-input>
              </el-form-item>
              <el-form-item label="学生姓名：">
                <el-input v-model="addForm.name"></el-input>
              </el-form-item>
              <el-form-item label="学生性别：">
                <el-radio v-model="radio" :label="'男'" style="margin-left: 180px" value="男">男</el-radio>
                <el-radio v-model="radio" :label="'女'" value="女">女</el-radio>
              </el-form-item>
              <el-form-item label="年级：">
                <el-select v-model="addForm.grade" placeholder="请选择年级" @change="handleGradeChange1">
                  <el-option
                    v-for="grade in grades"
                    :key="grade.id"
                    :label="grade.name"
                    :value="grade.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="班级：">
                <el-select v-model="addForm.klass" placeholder="请选择班级" v-if="addKlasses.length" @change="handleKlassChange2">
                  <el-option
                    v-for="klass in addKlasses"
                    :key="klass.id"
                    :label="klass.name"
                    :value="klass.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="身份证号：">
                <el-input v-model="addForm.idNumber"></el-input>
              </el-form-item>
              <el-form-item label="密码：">
                <el-input v-model="addForm.password"></el-input>
                <label>如果为空则默认同学籍号</label>
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
              multiple
              action="http://jsonplaceholder.typicode.com/posts/"
              :limit="1"
              accept=".xls,.xlsx"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              :before-upload="beforeUpload">
              <i class="el-icon-upload"></i>
              <div class="el-upload__tip" slot="tip">Excel文件的格式为以学籍号、学生姓名、
                年级、班级、身份证号、密码、电话号码，性别作为表的字段名,密码若为空则同学籍号。</div>
              <div class="el-upload__text">只接受Excel文件，将文件拖到此处，或<em>点击上传</em></div>
              
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
            <el-form-item label="学籍号：">
                <el-input v-model="editForm.studentNumber" :disabled="!isEditing"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名：">
                <el-input v-model="editForm.name" :disabled="!isEditing"></el-input>
            </el-form-item>
            <el-form-item label="学生性别">
              <el-select v-model="editForm.gender" placeholder="请选择状态" :disabled="!isEditing">
                <el-option
                  :label="'男'"
                  :value="'男'">
                </el-option>
                <el-option
                  :label="'女'"
                  :value="'女'">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="年级：">
              <el-select v-model="editForm.grade" placeholder="请选择年级" @change="handleGradeChange2" :disabled="!isEditing">
                  <el-option
                    v-for="grade in grades"
                    :key="grade.id"
                    :label="grade.name"
                    :value="grade.id">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="班级：">
              <el-select v-model="editForm.klass" placeholder="请选择班级" @change="handleKlassChange2" :disabled="!isEditing">
                  <el-option
                    v-for="klass in klasses"
                    :key="klass.id"
                    :label="klass.name"
                    :value="klass.id">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="身份证号：">
                <el-input v-model="editForm.idNumber" :disabled="!isEditing"></el-input>
            </el-form-item>
            <el-form-item label="密码：">
                <el-input v-model="editForm.password" :disabled="!isEditing"></el-input>
                <label>如果为空则默认同学籍号</label>
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
      addKlasses:[],
      total:0,
      radio:'男',
      tableData: Array(8).fill().map(() => ({
        id:'111111',
        name: "夏征东",
        grade: "六三制小学一年级",
        number:'202283290399',
        password:'123456',
        klass:'19班',
        schoolId: 11111
      })),
      activeName:'single',
      isEditing:false,
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      //新增表单里的数据
      addForm:{
        studentNumber:'',
        name:'',
        schoolId:'',
        grade:'',
        klass:'',
        idNumber:'',
        gender:'',
        password:'',
        phone:'',
        gradeId:'',
        klassId:'',
      },
      editForm:{
        id:'',
        studentNumber:'',
        name:'',
        grade:'',
        klassId:'',
        klass:'',
        idNumber:'',
        password:'',
        phone:'',
        gradeId:'',
        gender:'',
      },
      //为区域列表的数据
      provinces: [], // 省份数据
      cities: [], // 城市数据
      counties: [], // 区/县数据
      schoolTypes:[],
      schools:[],
      grades:[],
      klasses:[],
      selectedProvince: '', 
      selectedCity: '', 
      selectedCounty: '' ,
      selectedSchoolType:'',
      selectedSchool:''
    };
  },
  //把tableData数值赋给showTable
  created(){
    //请求获取省份数据
    this.getPronvince();
    this.getGrades();
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
      const selectedSchoolId=parseInt(this.selectedSchool,10);
      const schoolId = this.schools.find(s => s.id === selectedSchoolId).id;
      this.load(schoolId);
    },
    //让我们知道选了什么年级
    handleGradeChange1(val){
      let selectedGrade = this.grades.find(grade => grade.id === val);
      if (selectedGrade) {
        this.addForm.gradeId = selectedGrade.id;
        this.request.get("/klass/klasses",{
          params:{
            schoolId: this.selectedSchoolId,
            gradeId: selectedGrade.id
          }
        }).then(res=>{
          if(res.code=='200'){
            this.addKlasses=res.data;
          }else{
            this.$message.error('获取全部省级地区数据失败，原因：'+res.msg);
          }
        })
      }
    },
    //让我们知道选了什么年级
    handleGradeChange2(val){
      let selectedGrade = this.grades.find(grade => grade.id === val);
      if (selectedGrade) {
        this.editForm.gradeId = selectedGrade.id;
        if(this.editForm.gradeId)
          this.getKlasses(this.editForm.gradeId);
      }
    },
    //让我们知道选了什么年级
    handleKlassChange1(val){
      let selectedKlass = this.klasses.find(klass => klass.id === val);
      if (selectedKlass) {
        this.addForm.klassId = selectedKlass.id;
      }
    },
    //让我们知道选了什么年级
    handleKlassChange2(val){
      let selectedKlass = this.klasses.find(klass => klass.id === val);
      if (selectedKlass) {
        this.editForm.klassId = selectedKlass.id;
      }
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
    //编辑弹窗里的确认事件
    handleConfirm(){
      this.request.put("/student/update",this.editForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改学生数据成功！');
          this.load(this.selectedSchoolId);
        } else {
          this.$message.error('修改学生数据失败，原因：' + res.msg);
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
        this.request.put('/student/delete', {
          id: row.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除学生数据成功！');
            this.load(this.selectedSchoolId);
          }else{
            this.$message.error('删除学生数据失败，原因：'+res.msg);
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
      this.request.post('/student/batch-add',formData,{
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
        const selectedSchoolId=parseInt(this.selectedSchool,10);
        const schoolId = this.schools.find(s => s.id === selectedSchoolId).id;
        this.load(schoolId);
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
      this.dialogVisible1 = true;
      this.radio = '男';
    },
    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {
      const selectedSchoolId=parseInt(this.selectedSchool,10);
      this.addForm.gender = this.radio;
      this.addForm.schoolId=selectedSchoolId;
      console.log("看看有没有学校id"+this.addForm.schoolId);
      //console.log("打印下"+this.activeName);
      if(this.activeName == 'single'){
        this.request.post("/student/single-add",this.addForm).then(res=>{
          if(res.code == '200'){
            this.$message.success('新增学生数据成功！');
            this.load(selectedSchoolId);
          } else {
            this.$message.error('新增学生数据失败，原因：' + res.msg);
          }
        })
      }
      this.dialogVisible1 = false;  //关闭弹窗
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit1(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      console.log("看看gradeId是不是为空："+this.editForm.gradeId);
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
      const schoolId = this.schools.find(s => s.id === this.selectedSchoolId).id;

      this.load(schoolId);
    },

    //分页用的功能
    handleCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      const selectedSchoolId=parseInt(this.selectedSchool,10);
      const schoolId = this.schools.find(s => s.id === selectedSchoolId).id;
      this.load(schoolId);
    },
    handleSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      const selectedSchoolId=parseInt(this.selectedSchool,10);
      const schoolId = this.schools.find(s => s.id === selectedSchoolId).id;
      this.load(schoolId);
    },
    //请求分页查询数据
    load(val){
      const schoolId = val;
      this.request.get("/student/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
          schoolId:schoolId
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
          console.log(res);
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
          console.log("schools是什么"+this.schools);
        }else{
          this.$message.error('获取当前地区全部学校数据失败，原因：'+res.msg);
        }
      })
    },
    getGrades(){
      this.request.get("/grade/grades").then(res=>{
        if(res.code=='200'){
          this.grades=res.data;
        }else{
          this.$message.error('获取年级数据失败，原因：'+res.msg);
        }

      })
    },
    //传入年级id
    getKlasses(val){
      this.request.get("/klass/klasses",{
        params:{
          schoolId: this.selectedSchoolId,
          gradeId: val
        }
      }).then(res=>{
        if(res.code=='200'){
          this.klasses=res.data;
        }else{
          this.$message.error('获取全部省级地区数据失败，原因：'+res.msg);
        }

      })
    }
  },
  
};
</script>
