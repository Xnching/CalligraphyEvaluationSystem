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
    </div>

    <!-- 编辑按钮的弹窗 -->
    <el-dialog
        title="点击修改信息"
        :visible.sync="dialogVisible"
        width="60%"
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="竞赛公告封面：" v-if="editForm.type === '竞赛公告'"> 
            <img v-if="editForm.type === '竞赛公告'" :src="editForm.pictureUrl" :alt="editForm.name" style="width: 100%;">
          </el-form-item>
          <el-form-item label="公告名："> 
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="公告类型：">
            <el-select v-model="editForm.type" placeholder="请选择公告类型">
              <el-option label="系统更新公告" value="system"></el-option>
              <el-option label="竞赛公告" value="competition"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布对象：">
            <el-select v-model="editForm.target" placeholder="请选择发布对象">
              <el-option label="学生" value="学生"></el-option>
              <el-option label="教师" value="教师"></el-option>
              <el-option label="学生和教师" value="学生和教师"></el-option>
              <el-option label="管理员" value="管理员"></el-option>
              <el-option label="全体" value="全体"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布状态：">
            <el-radio-group v-model="editForm.state">
              <el-radio label="已发布" ></el-radio>
              <el-radio label="待发布" ></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="发布时间：">
            <el-input v-model="editForm.releaseTime"></el-input>
          </el-form-item>
          <el-form-item label="公告内容：">
            <el-form-item >
              <wang-editor ref="myEditor" v-model="valueHtml"></wang-editor>
            </el-form-item>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="danger" @click="handleDelete2">删除</el-button>
          </span>
        </template>
      </el-dialog>


    <!--主表格-->
    <el-table
        ref="multipleTable"
        :data="tableData"
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
          label="公告名"
          width="100">
      </el-table-column>
      <el-table-column
          prop="type"
          label="公告类型"
          width="120">
      </el-table-column>
      <el-table-column
          prop="target"
          label="发布对象"
          width="120">
      </el-table-column>
      <el-table-column
          prop="state"
          label="发布状态"
          width="120">
      </el-table-column>
      <el-table-column
          prop="releaseTime"
          label="发布时间"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="publisher"
          label="发布人"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">查看</el-button>
          <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete2(scope.row)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页栏-->
    <div style="padding:10px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[20, 25, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>  
</template>

<script>
import WangEditor from '@/components/wangEditor.vue';

export default {
  components:{
    WangEditor
  },
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: [],
      dialogVisible: false,
      editForm: {
        pictureUrl: '', // URL of the competition announcement cover image
        name: '', // Announcement name
        type: '', // Announcement type
        target: '', // Target audience
        state: '', // Release state
        releaseTime: '', // Release time
        valueHtml: '' // Announcement content
      },
      pageNum:1,
      valueHtml:'',
      pageSize:20,
      total:0,
      sanitizedContent:'',
    };
  },
  created(){
    this.load();
  },

  methods: {
    
    Search_table() {
      this.load();
    },
    handleEdit1(row){
      this.editForm = row;
      this.getDetail(row.id);
      this.dialogVisible=true;
    },
    getDetail(val){
      this.request.get("/announcement/detail",{
        params:{
          id:val
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.$refs.myEditor.setHtml(res.data.message);
        }else{
          this.$message.error('获取数据失败，原因：'+res.msg);
        }
      })
    },
    handleDelete1(row){
      this.delete(row.id);
    },
    handleDelete2(){
      this.delete(this.editForm.id);
      this.dialogVisible=false;
    },
    delete(val){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/announcement/delete',
          method: 'delete',
          data: {
            id: val
          }
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除数据成功！');
            this.load();
          }else{
            this.$message.error('删除数据失败，原因：'+res.msg);
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
      this.request.get("/announcement/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    
  }
};

</script>
