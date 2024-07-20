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
        <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus"></i></el-button>
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
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">编辑</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
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
        destroy-on-close
        :close-on-click-modal="false" >
        <!-- 用于提示新增用户时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户姓名" prop="name"> <el-input v-model="ruleForm.name"></el-input> </el-form-item>
          <el-form-item label="登录名" prop="loginId"> <el-input v-model="ruleForm.loginId"></el-input> </el-form-item>
          <el-form-item label="密码" prop="password"> <el-input v-model="ruleForm.password"></el-input> </el-form-item>
          <el-form-item label="联系方式"prop="phone"> <el-input v-model="ruleForm.phone"></el-input> </el-form-item>
          <el-form-item label="所属用户组" prop="userGroup">
            <el-select v-model="ruleForm.userGroupName" placeholder="请选择所属用户组" @change="handleGroupChange1">
              <el-option
                v-for="group in userGroups"
                :key="group.id"
                :label="group.name"
                :value="group.id">
              </el-option>
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
        destroy-on-close
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="用户姓名"> 
            <el-input v-model="editForm.name" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="登录名">
            <el-input v-model="editForm.loginId" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="editForm.password" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="editForm.phone" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="所属用户组">
            <el-select v-model="editForm.userGroupName" placeholder="请选择所属用户组" :disabled="!isEditing" @change="handleGroupChange2">
              <el-option
                v-for="group in userGroups"
                :key="group.id"
                :label="group.name"
                :value="group.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
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
      tableData: [],
      showTable:[],
      //编辑弹窗的状态
      isEditing: false,
      total:0,
      pageNum:1,
      pageSize:6,
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      //多选用的
      multipleSelection: [],
      userGroups: [],  // 用于保存用户组数据
      editForm: { // 添加 editForm 即编辑弹窗里的表单定义
        id: '',
        name: '',
        loginId: '',
        password: '',
        phone: '',
        userGroupName: '',
        userGroupId:''
      },
      //规则，即添加里的验证规则
      ruleForm: {
        name: '',
        loginId: '',
        password: '',
        phone: '',
        userGroupName: '',
        userGroupId:''
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
        userGroupName: [
          { required: true, message: '请选择所属用户组', trigger: 'change' }
        ]
      }

    };
  },
  created(){
      //请求分页查询数据
      this.load();
      this.showTable = [...this.tableData];
      this.loadUserGroups();
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
    //让userGroupId跟着userGroupName一起变
    handleGroupChange1(value) {
      let selectedGroup = this.userGroups.find(group => group.id === value);
      if (selectedGroup) {
        this.ruleForm.userGroupId = selectedGroup.id;
        this.eruleForm.userGroupName = selectedGroup.name;
      }
    },
    //让userGroupId跟着userGroupName一起变
    handleGroupChange2(value) {
      let selectedGroup = this.userGroups.find(group => group.id === value);
      if (selectedGroup) {
        this.editForm.userGroupId = selectedGroup.id;
        this.editForm.userGroupName = selectedGroup.name;
      }
    },

    //新增按钮跳出弹窗
    handleAdd(){
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
          this.request.post("/user/add",this.ruleForm).then(res=>{
            if(res.code == '200'){
              this.$message.success('新增用户数据成功！');
              this.load();
            } else {
              this.$message.error('新增用户数据失败，原因：' + res.msg);
            }
          })
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
    handleEdit1(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.dialogVisible2 = true;
    },
    //删除按钮的删除接口以及逻辑
    handleDelete(row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request.put('/user/delete', {
          id: row.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除用户数据成功！');
            this.load();
          }else{
            this.$message.error('删除用户数据失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
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
        //console.log(res);
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //获取全部已激活用户组数据
    loadUserGroups(){
      this.request.get("/user/groups").then(res=>{
        //console.log('将要获取用户组信息');
        //console.log(res);
        if(res.code=='200'){
          this.userGroups = res.data
        }else{
          this.$message.error('获取用户组数据失败，原因：'+res.msg);
        }
      })
    }, 
    //编辑弹窗里的编辑按钮
    handleEdit2() {
      this.isEditing = true;
    },
    //编辑弹窗里的确定按钮
    handleConfirm() {
      // 在这里处理提交逻辑
      this.request.put("/user/update",this.editForm).then(res=>{
        if(res.code=='200'){
          this.$message.success('编辑用户数据成功！');
          this.load();
          this.isEditing = false;
          this.dialogVisible2=false;
        }else{
          this.$message.error('编辑用户数据数据失败，原因：'+res.msg);
        }
      })
    },



  },

};
</script>
