<template>
    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px " 
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
        style="width: 100%">
        <el-table-column
          prop="id"
          label="学校id"
          width="75">
        </el-table-column>
        <el-table-column
          prop="name"
          label="学校名"
          width="215">
        </el-table-column>
        <el-table-column
          prop="address"
          label="区域与位置"
          width="250">
        </el-table-column>
        <el-table-column
          prop="type"
          label="学校类型"
          width="90">
        </el-table-column>
        <el-table-column
          prop="leader"
          label="校负责人"
          width="150">
        </el-table-column>
        <el-table-column
          prop="leaderPhone"
          label="联系方式"
          width="110">
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
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

      <!-- 新增学校的弹窗 -->
      <el-dialog
        title="请输入新增系统用户的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 用于提示新增学校时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            
            <!--下面是区域选择器-->
            <el-form-item label="请选择区域:" prop="address" >
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
                <select v-model="selectedCounty" v-if="counties.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
                <option value="">请选择区/县</option>
                <option v-for="county in counties" :key="county.id" :value="county.id">
                    {{ county.name }}
                </option>
                </select>
            </el-form-item>
            
          <el-form-item label="详细地址:" prop="detailAddress"> <el-input v-model="ruleForm.detailAddress"></el-input> </el-form-item>
          <el-form-item label="学校类别" prop="schoolType">
            <el-select v-model="ruleForm.schoolType" placeholder="学校类别" >
                <el-option label="小学" value="primary"></el-option>
                <el-option label="初中" value="juniorHigh"></el-option>
                <el-option label="中小学" value="pj"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学校名称:" prop="name"> <el-input v-model="ruleForm.name"></el-input> </el-form-item>
          <el-form-item label="校负责人:" prop="leader"> <el-input v-model="ruleForm.leader"></el-input> </el-form-item>
          <el-form-item label="联系方式:" prop="leaderPhone"> <el-input v-model="ruleForm.leaderPhone"></el-input> </el-form-item>
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
            <!--下面是区域选择器-->
            <el-form-item label="请选择区域:" prop="address" >
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
                <select v-model="selectedCounty" v-if="counties.length" style="height: calc(2em + 10px); margin: 0px 10px; border: 1px solid #ccc;">
                    <option value="">请选择区/县</option>
                    <option v-for="county in counties" :key="county.id" :value="county.id">
                        {{ county.name }}
                    </option>
                </select>
            </el-form-item>

          <el-form-item label="详细地址："> 
            <el-input v-model="editForm.address"></el-input>
          </el-form-item>
          <el-form-item label="学校类别：">
            <el-select v-model="editForm.type" placeholder="请选择学校类别">
              <el-option label="用户组 A" value="groupA"></el-option>
              <el-option label="用户组 B" value="groupB"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学校名称：">
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="校负责人：">
            <el-input v-model="editForm.leader"></el-input>
          </el-form-item>
          <el-form-item label="联系方式：">
            <el-input v-model="editForm.leaderPhone"></el-input>
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
      total:0,
      pageNum:1,
      pageSize:6,
      //搜索栏要用的
      inputVal:"",
      tableData:[],
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      editForm: { // 添加 editForm 即编辑弹窗里的表单定义
        id: '',
        name: '',
        type:'',
        address:'',
        leader:'',
        leaderPhone:'',
      },
      //规则，即添加里的验证规则
      ruleForm: {
        detailAddress: '',
        schoolType: '',
        name: '',
        leader: '',
        leaderPhone: ''
      },
      rules: {
        detailAddress: [
          { required: true, message: '请输入详细地址', trigger: 'blur' }
        ],
        schoolType: [
          { required: true, message: '请选择学校类型', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入学校名称', trigger: 'blur' }
        ],
        leaderPhone: [
          { required: true, message: '请输入联系方式', trigger: 'blur' },
          { pattern: /^[0-9]+$/, message: '联系方式必须为数字', trigger: 'blur' }
        ],
        leader: [
          { required: true, message: '请输入学校负责人', trigger: 'change' }
        ], 
        
      },


      //为区域列表的数据
      provinces: [
        { id: 1, name: '北京市', cities: [
          { id: 11, name: '北京市', counties: [
            { id: 111, name: '东城区' },
            { id: 112, name: '西城区' },
          ]},
          { id: 12, name: '朝阳区', counties: [
            { id: 121, name: '望京街道' },
            { id: 122, name: '三里屯街道' },
          ]},
        ]},
        { id: 2, name: '上海市', cities: [
          { id: 21, name: '上海市', counties: [
            { id: 211, name: '黄浦区' },
            { id: 212, name: '徐汇区' },
          ]},
          { id: 22, name: '浦东新区', counties: [
            { id: 221, name: '陆家嘴街道' },
            { id: 222, name: '世纪大道街道' },
          ]},
        ]},
      ], // 省份数据
      cities: [], // 城市数据
      counties: [], // 区/县数据
      selectedProvince: '', 
      selectedCity: '', 
      selectedCounty: '' 

    };
  },
  created(){
      //请求分页查询数据
      this.load();
  },

  methods: {
    //实现搜索栏多属性搜索的
    Search_table() {
      this.load();
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

        //直接在此处判是否为空，之所以不像其他规则，是因为地区选择的v-model没法更改，他要满足下面的省份选择改变的逻辑
        if (!this.selectedProvince || !this.selectedCity || !this.selectedCounty) {
            // 区域未选择完整，阻止表单提交并给出提示
            this.$message.error('请选择完整的区域信息！'); // 使用 Element UI 的 Message 组件给出错误提示
            return false; 
        }
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
      this.selectedCity = ''; 
      this.selectedCounty = ''; 
    },
    //当城市选择改变时 (onCityChange)，根据选择的城市过滤出对应的区/县列表。
    onCityChange() {
      const selectedCityId = parseInt(this.selectedCity, 10);
      const selectedCity = this.cities.find(c => c.id === selectedCityId);
      this.counties = selectedCity ? selectedCity.counties : [];
      this.selectedCounty = ''; 
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
      this.request.get("/school/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        console.log(res);
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },

    
  },
  
};
</script>
