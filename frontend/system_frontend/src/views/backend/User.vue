<template>
    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
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
      }

    };
  },
  watch: {
    //用于实现搜索栏搜索的
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
    //再次开启新增用户表单时清除提示信息
    dialogVisible1(newVal) {
      if (newVal) { // 如果弹窗打开
        this.$refs.ruleForm.clearValidate(); // 清除表单验证提示信息
      }
    }
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
