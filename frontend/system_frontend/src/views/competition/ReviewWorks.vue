<template>
  <div>

    <div>
      <el-select v-model="selectedDivision" filterable placeholder="请选择导入人" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
        <el-option
          v-for="item in divisionOptions"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>

    </div>
    <el-tabs v-model="currentPage" v-if="selectedDivision">
      <el-tab-pane label="初级作品评阅" name="junior">
        <el-label v-if="isUrged" style="color: red; font-weight: bold;">您被催促了！请尽快批改此组别的作品！</el-label>
        <!-- 初级作品评阅的内容 -->
        <el-table
            ref="initialTableData"
            :data="initialTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
          <el-table-column
              prop="id"
              label="作品编号"
              width="125">
          </el-table-column>
          <el-table-column
              prop="name"
              label="作品名称"
              width="220">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="系统评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="initialScore"
              label="该教师初级评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">详情</el-button>
            </template>
          </el-table-column>
  
        </el-table>
  
        <div style="padding:10px">
          <el-pagination
            @size-change="initialSizeChange"
            @current-change="initialCurrentChange"
            :current-page="initialPageNum"
            :page-sizes="[10, 15, 20, 40]"
            :page-size="initialPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="initialTotal">
          </el-pagination>
        </div>

      </el-tab-pane>
      <el-tab-pane label="最终作品评阅" name="final">
        <!-- 最终作品评阅的内容 -->
        <el-label v-if="isUrged" style="color: red; font-weight: bold;">您被催促了！请尽快批改此组别的作品！</el-label>
        <el-table
            ref="finalTableData"
            :data="finalTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
            <el-table-column
              prop="id"
              label="作品编号"
              width="125">
          </el-table-column>
          <el-table-column
              prop="name"
              label="作品名称"
              width="220">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="系统评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="initialScore"
              label="初级评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="finalScore"
              label="我的最终评阅评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">修改/评阅</el-button>
            </template>
          </el-table-column>
  
        </el-table>
  
        <!--分页器-->
        <div style="padding:10px">
          <el-pagination
            @size-change="finalSizeChange"
            @current-change="finalCurrentChange"
            :current-page="finalPageNum"
            :page-sizes="[10, 15, 20, 40]"
            :page-size="finalPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="finalTotal">
          </el-pagination>
        </div>

      </el-tab-pane>
    </el-tabs>

  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedDivision:'',
      isUrged:'',
      initialTableData: [], // 初级作品评阅表格数据
      finalTableData: [], // 最终作品评阅表格数据
      initialPageNum: 1, // 初级作品评阅当前页码
      initialPageSize: 10, // 初级作品评阅每页显示条数
      initialTotal: 0, // 初级作品评阅总条数
      finalPageNum: 1, // 最终作品评阅当前页码
      finalPageSize: 10, // 最终作品评阅每页显示条数
      finalTotal: 0, // 最终作品评阅总条数
      divisionOptions:[],
      //搜索栏要用的
      currentPage: 'junior',
    };
  },
  created(){
    this.getDivision();
  },

  methods: {
    getDivision(){
      this.request.get("/review-work/teacher").then(res=>{
        if(res.code=='200'){
          this.divisionOptions=res.data;
        }else{
          this.$message.error('获取全部组别数据失败，原因：'+res.msg);
        }
      })
    },

    //初级作品评阅的评阅按钮
    handleEdit1(row){
      const id = row.juniorWorkID;
      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewWorks/junior/${id}`);
    },

    //最终作品评阅的按钮
    handleEdit2(row){
      const id = row.finalWorkID;
      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewWorks/final/${id}`);
    },
    
    

  }
};

</script>

<style>

</style>
