<template>

  <div>


    <!--评审弹窗-->
    <el-dialog
      :visible.sync="dialogVisible"
      width="50%"
      :close-on-click-modal="false"
    >
      <el-form label-position="left" label-width="80px">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <img :src="imageSrc" alt="作业图片" style="width: 100%; ">
        </el-form-item>
        <!-- 作业名称 -->
        <el-form-item label="作业名称:">
          <el-input v-model="homeworkName" placeholder="作业名称"></el-input>
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="author" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 作者年级 -->
        <el-form-item label="作者年级:">
          <el-input v-model="grade" placeholder="作者年级"></el-input>
        </el-form-item>
        <!-- 推荐教师 -->
        <el-form-item label="推荐教师:">
          <el-input v-model="recommendedTeacher" placeholder="推荐教师"></el-input>
        </el-form-item>
        <!-- 作业类型 -->
        <el-form-item label="作业类型:">
          <el-input v-model="homeworkType" placeholder="作业类型"></el-input>
        </el-form-item>
        <!-- 智能评分评语 -->
        <el-form-item label="智能评分:">
          <el-input v-model="autoScore" placeholder="智能评分"></el-input>
        </el-form-item>
        <el-form-item label="智能评语:">
          <el-input type="textarea" v-model="autoScore" placeholder="智能评语"></el-input>
        </el-form-item>
        <!-- 教师的评分评语 -->
        <el-form-item label="教师评分:">
          <el-input v-model="teacherScore" placeholder="教师评分"></el-input>
        </el-form-item>
        <el-form-item label="教师评语:">
          <el-input type="textarea" v-model="teacherEvaluation" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 通过/不通过按钮 -->
        <el-form-item>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="warning"@click="dialogVisible = false">不通过</el-button>
          <el-button type="primary" @click="handlePass">通过</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


  <!--主页面-->
  <div>
    <div style="padding:10px; margin:10px; margin-bottom: -5px;">
      <el-input style="width:300px"
                suffix-icon="el-icon-search"
                placeholder="请输入"
                v-model="inputVal"
                @keyup.enter.native="Search_table()"
                clearable>
      </el-input>
      <el-button style="margin-left:20px ;margin-right:535px" type="primary">搜索</el-button>
    </div>

    <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          prop="ID"
          label="作品ID"
          width="115">
      </el-table-column>
      <el-table-column
          prop="homeworkName"
          label="作业名称"
          width="220">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作者"
          width="140">
      </el-table-column>
      <el-table-column
          prop="recommendTeacher"
          label="推荐教师"
          width="140">
      </el-table-column>
      <el-table-column
          prop="workType"
          label="作业类型"
          width="140">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">评审</el-button>
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
        ID:"3",
        homeworkName: "一年级第一次专项作业",
        name: "张三",
        recommendTeacher: "李四",
        workType: "综合作业",
        
      })),
      dialogVisible: false,
      showTable:[],
      imageSrc:'/images/copybook/1.jpg',
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
    handleEdit2(row) {
      // 然后显示弹窗
      this.dialogVisible = true;
      // 如果需要，可以根据row设置弹窗中的其他内容
    },

    // 处理点击通过按钮
    handlePass() {
      // 这里添加处理通过按钮点击的逻辑
      // 比如更新作业状态等
      this.dialogVisible = false; // 关闭弹窗
    },
    
    
    //实现搜索栏多属性搜索的
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let homeworkName= e.homeworkName;
        let name= e.name;
        let recommendTeacher=e.recommendTeacher;
        let workType=e.workType;
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
        if (homeworkName.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (recommendTeacher.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (workType.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
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
<style scoped>

</style>
