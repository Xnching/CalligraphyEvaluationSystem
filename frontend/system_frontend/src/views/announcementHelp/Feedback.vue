<template>
  <div>
    <el-table :data="feedbacks" style="width: 100%">
      <el-table-column 
      prop="userType" 
      label="用户类型">
      </el-table-column>
      <el-table-column 
      prop="feedbackType" 
      label="反馈类型">
      </el-table-column>
      <el-table-column 
      prop="effectiveness" 
      label="有效性">
      </el-table-column>
      <el-table-column 
      prop="phone" 
      label="反馈人电话">
      </el-table-column>
      <el-table-column 
      prop="man" 
      label="编辑人">
      </el-table-column>
      <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="primary" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">回复</el-button>
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


    <!--反馈内容弹窗-->
    <el-dialog title="用户反馈内容" :visible.sync="dialogVisible" style=" text-align: center;">
      <div class="dialog-container">
        <el-form ref="editForm" :model="editForm" label-width="80px">
            <el-form-item label="反馈内容:">
              <el-input type="textarea" v-model="editForm.feedbackContent"></el-input>
            </el-form-item>
            <h3>照片区域</h3>
            <el-image
              style="width: 100px; height: 100px"
              :src="editForm.imgUrl"
              :fit="fit">
            </el-image>
            <el-form-item label="用户类型:">
              <el-input v-model="editForm.userType"></el-input>
            </el-form-item>
            <el-form-item label="反馈性质:">
              <el-input v-model="editForm.feedbackType"></el-input>
            </el-form-item>
            <el-form-item label="电话:">
              <el-input v-model="editForm.phone"></el-input>
            </el-form-item>
            <el-form-item label="回复内容:">
              <el-input type="textarea" v-model="editForm.returnContent"></el-input>
            </el-form-item>

        </el-form>
        
        <el-button type="primary" @click="submitReply">回复</el-button>
        <el-button type="warning" @click="submitReply">设为无效反馈</el-button>
      </div>
    </el-dialog>
  </div>


</template>

<script>
export default {
  data() {
    return {
      feedbackContent: '文本',
      replyContent: '',
      dialogVisible:false,
      feedbacks: Array(8).fill().map(() => ({
        userType:'学生',
        feedbackType:'体验反馈',
        phone:'12345678',
        effectiveness:'未评判',
        man:'张三丰',
      })),
      editForm:{
        feedbackContent: '',
        userType: '',
        feedbackType: '',
        phone: ''
      },
      imgUrl: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
    };
  },
  methods: {
    submitReply() {
      //alert('回复已提交');
      this.dialogVisible=false;
    },
    handleEdit(row){
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.dialogVisible=true;
    }
  }
};
</script>

<style scoped>

</style>
