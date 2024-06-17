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
    </div>
    <!-- ... 省略其他代码 ... -->

    <el-dialog
        title="编辑公告"
        :visible.sync="dialogVisible"
        :before-close="handleDialogClose"
        width="50%"
    >
      <el-form :model="editForm" ref="editForm"  label-width="120px">
        <!-- ... 省略表单项 ... -->
        <el-form-item prop="content" label="公告内容">
          <el-input type="textarea" v-model="editForm.content"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="handleDialogCancel">取消</el-button>
      <el-button type="primary" @click="handleDialogConfirm">确认</el-button>
    </span>
    </el-dialog>

    <!-- ... 省略其他代码 ... -->
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
          prop="announcementID"
          label="编号"
          width="95">
      </el-table-column>
      <el-table-column
          prop="announcementName"
          label="公告名"
          width="100">
      </el-table-column>
      <el-table-column
          prop="publishObject"
          label="发布对象"
          width="120">
      </el-table-column>
      <el-table-column
          prop="publishState"
          label="发布状态"
          width="120">
      </el-table-column>
      <el-table-column
          prop="publishDate"
          label="发布时间"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="announcementType"
          label="公告类型"
          width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">编辑</el-button>
          <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

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
  </div>
</template>

<script>
export default {
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        announcementID:"10000",
        announcementName: "系统更新2.0",
        publishObject: "全体",
        publishState: "已发布",
        publishDate: "2024-1-10",
        announcementType: "系统公告",
      })),
      showTable:[],
      multipleSelection: [],
      dialogVisible: false,
      editForm:[],
    };
  },
  watch: {
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
  },

  methods: {
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
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let name= e.name;
        let count= e.count;
        let competitionObject=e.competitionObject;
        let group=e.group;
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
        if (competitionObject.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (group.includes(res)) {
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
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {},
    handleEdit2(row) {
      this.dialogVisible = true;
      this.$set(this.editForm, 'announcementName', row.announcementName);
      // ... 将其他字段赋值给editForm ...
    },
    handleDialogClose() {
      this.dialogVisible = false;
      this.resetForm('editForm');
    },
    handleDialogCancel() {
      this.dialogVisible = false;
      this.handleDialogClose();
    },
    handleDialogConfirm() {
      this.dialogVisible = false;
      this.$refs.editForm.validate(async valid => {
        if (valid) {
          try {
            const response = await axios.post('/api/announcement/update', this.editForm);
            // 处理响应数据，关闭弹窗等
            this.handleDialogClose();
            // 提示用户更新成功或进行其他操作
          } catch (error) {
            // 处理请求错误，显示错误信息给用户
            console.error(error);
          }
        } else {
          console.log('表单验证失败！');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};

</script>
