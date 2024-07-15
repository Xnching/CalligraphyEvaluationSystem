<template>
  <div class="container" >
    <div style=" text-align: right;">
      <el-button type="primary" @click="handleNew" style="margin-right: 130px">新增</el-button>
    </div>
    <el-container>
      <el-aside width="100%">
        <el-table :data="tableData" 
        style="width: 100%"
        stripe>
          <el-table-column prop="question" label="问题"></el-table-column>
          <el-table-column prop="answer" label="答案"></el-table-column>
          <el-table-column prop="man" label="编辑人"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        
      </el-aside>
      <el-main>
        <el-dialog :visible.sync="dialogVisible" width="50%">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="问题">
              <el-input v-model="form.question"></el-input>
            </el-form-item>
            <el-form-item label="答案">
              <el-input v-model="form.answer" type="textarea"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleSave">确定</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [], // 表格数据
      currentPage: 1, // 当前页码
      pageSize: 10, // 每页数量
      total: 0, // 总记录数
      form: {
        question: '',
        answer: '',
      },
      tableData: Array(8).fill().map(() => ({
        question:"如何找回作业",
        answer: "反馈管理处提交反馈帮助找回作业",
        man:'哈洛德',

      })),
      dialogVisible: false, // 弹窗显示状态4
      editingIndex: null, // 当前编辑行的索引
    };
  },
  methods: {
    // 处理分页大小变化
    handleSizeChange(val) {
      this.pageSize = val;
    },
    // 处理当前页码变化
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    // 弹出新增弹窗
    handleNew() {
      this.dialogVisible = true;
      this.form = {
        question: '',
        answer: '',
      };
    },
    // 保存新增或编辑的数据
    handleSave() {
      // 在这里处理保存逻辑，可以发送到后端
      this.dialogVisible = false;
      if (this.editingIndex !== null) {
        // 编辑操作，更新表格数据
        this.$set(this.tableData, this.editingIndex, {
          question: this.form.question,
          answer: this.form.answer,
        });
        this.editingIndex = null;
      } else {
        // 新增操作，添加新数据到表格
        this.tableData.push({
          question: this.form.question,
          answer: this.form.answer,
        });
      }
    },
    // 处理编辑操作
    handleEdit(index, row) {
      this.dialogVisible = true;
      this.form = { ...row };
      this.editingIndex = index;
    },
    // 处理删除操作
    handleDelete(index, row) {
      // 在这里处理删除逻辑，可以发送到后端
      this.tableData.splice(index, 1);
    },
  },
  // 可以在这里添加生命周期钩子等其他逻辑
};
</script>
