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
        <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
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
          prop="id"
          label="用户ID"
          width="95">
        </el-table-column>
        <el-table-column
          prop="name"
          label="用户姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="loginId"
          label="登录名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          width="120">
        </el-table-column>
        <el-table-column
          prop="userGroupName"
          label="所属用户组"
          width="220">
        </el-table-column>
        <el-table-column
          prop="phone"
          label="联系方式"
          width="170">
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

      <!-- 新增系统用户的弹窗 -->
      <el-dialog
        title="请输入新增系统用户的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <!-- 用于提示新增用户时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户姓名" prop="name"> <el-input v-model="ruleForm.name"></el-input> </el-form-item>
          <el-form-item label="登录名" prop="loginId"> <el-input v-model="ruleForm.loginId"></el-input> </el-form-item>
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
            <el-input v-model="editForm.loginId"></el-input>
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
      tableData: [],
      showTable:[],
      total:0,
      pageNum:1,
      pageSize:6,
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      //多选用的
      multipleSelection: [],
      editForm: { // 添加 editForm 即编辑弹窗里的表单定义
        ID: '',
        name: '',
        loginId: '',
        password: '',
        phone: '',
        userGroup: ''
      },
      //规则，即添加里的验证规则
      ruleForm: {
        name: '',
        loginId: '',
        password: '',
        phone: '',
        userGroup: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' }
        ],
        loginId: [
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
      }

    };
  },
  created(){
      //请求分页查询数据
      this.load();
      this.showTable = [...this.tableData];
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
      this.request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        console.log(res);
        this.tableData=res.data.records;
        this.total=res.data.total;
        console.log('执行了没');
      })
    } 

  },

};
</script>
