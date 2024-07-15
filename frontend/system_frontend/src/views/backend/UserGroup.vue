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
          prop="ID"
          label="用户组ID"
          width="195">
        </el-table-column>
        <el-table-column
          prop="name"
          label="用户组名"
          width="270">
        </el-table-column>
        <el-table-column
          prop="count"
          label="人数"
          width="190">
        </el-table-column>
        <el-table-column
          prop="state"
          label="状态"
          width="220">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit2(scope.row)">编辑</el-button>
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

      <!-- 新增用户组的弹窗 -->
      <el-dialog
        title="请输入新增用户组的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false"
        overflow-x:auto >
        <!-- 用于提示新增用户时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户组名" prop="name"> <el-input v-model="ruleForm.name"></el-input> </el-form-item>

          <div style="display:flex">

            <!--权限表组件-->
            <PermissionTable>
            </PermissionTable>

          
          </div>
          
          <el-radio v-model="radio" :label="1" style="margin-left: 180px">未激活</el-radio>
          <el-radio v-model="radio" :label="2">激活</el-radio>
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
          <el-form-item label="用户组名"> 
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="人数">
            <el-input v-model="editForm.count"></el-input>
          </el-form-item>
          
          <PermissionTable></PermissionTable>

          <el-form-item label="状态">
            <el-input v-model="editForm.state"></el-input>
          </el-form-item>
          
          <el-radio v-model="radio2" :label="1" style="margin-left: 180px">未激活</el-radio>
          <el-radio v-model="radio2" :label="2">激活</el-radio>
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
import PermissionTable from '@/components/PermissionTable.vue'
export default {
  components:{
    PermissionTable
  },
  data() {
    return {
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        ID:"10000",
        name: "盘城小学竞赛评阅组",
        count:"36",
        state:"已激活"
      })),
      showTable:[],
      //下面两个是用来控制两个表单的隐藏与出现
      dialogVisible1:false,
      dialogVisible2:false,
      radio:1,
      radio2:"",
      editForm:{
        name:'',
        count:'',
        state:''
      },
      ruleForm:{
        name:'',
        state:''
      },
      rules:{
        name: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' }
        ],
      },
      

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
    //实现搜索栏多属性搜索的
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let name= e.name;
        let count=e.count;
        let state=e.state;
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
        if (count.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (state.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },

    //第一个表单的跳出方法，就是点击新增
    handleEdit1(){
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.resetFields(); // 清除表单验证并重置表单
      }
      this.dialogVisible1 = true;
      this.radio = 1;
    },
    //新增用户组的表单，提交前要判断下数据，就是新增表单里的确定按钮
    handleSubmit1(){
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
    
    //第二个表单的跳出方法，就是点击编辑
    handleEdit2(row){
      this.dialogVisible2 = true;
    },

    //编辑表单中的确定的提交方法
    handleSubmit2(){

      this.dialogVisible2 = false;
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