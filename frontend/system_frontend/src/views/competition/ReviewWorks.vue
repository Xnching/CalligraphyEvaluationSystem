<template>
  <div>
    <div v-if="showMainPage">

      <div>
        <el-select v-model="selectedDivision" filterable placeholder="请选择组别" @clear="clearSelection" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
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
                label="我评阅的初级评分"
                show-overflow-tooltip>
            </el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">详情</el-button>
              </template>
            </el-table-column>

          </el-table>

          <div style="padding:10px">
            <el-button type="success" size="small" icon="el-icon-edit"  @click="nextGroup">下一组作品</el-button>
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
    <junior-review v-if="showJuniorReview" :submission="initialSubmission" @go-back="showMainPage = true;
     showJuniorReview = false" :division="divisionInformation" @submit-review="saveReview"></junior-review>
    <final-review v-if="showFinalReview" :submission="finalSubmission" @go-back="showMainPage = true; 
    showFinalReview = false" :division="divisionInformation"></final-review>
  </div>
  
</template>

<script>
import FinalReview from './ReviewWorks/finalView.vue';
import JuniorReview from './ReviewWorks/juniorView.vue';
export default {
  components: {
    JuniorReview,
    FinalReview
  },
  data() {
    return {
      selectedDivision:'',
      isUrged:'',
      initialTableData: [], // 初级作品评阅表格数据
      finalTableData: [], // 最终作品评阅表格数据
      initialPageSize: 10, // 初级作品评阅每页显示条数
      finalPageNum: 1, // 最终作品评阅当前页码
      finalPageSize: 10, // 最终作品评阅每页显示条数
      finalTotal: 0, // 最终作品评阅总条数
      divisionOptions:[],
      //搜索栏要用的
      currentPage: 'junior',
      divisionInformation:{},
      initialSubmission:{},
      finalSubmission:{},
      reviews: [], // 存储评阅结果的数组
      showMainPage:true,
      showFinalReview:false,
      showJuniorReview:false,
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
    //获取组别的竞赛信息
    getInformation(){
      this.request.get("/division/information",{
        params:{
          divisionId:this.selectedDivision
        }
      }).then(res=>{
        if(res.code=='200'){
          this.divisionInformation=res.data;
        }else{
          this.$message.error('获取全部组别数据失败，原因：'+res.msg);
        }
      })
    },
    //获取作品评语进行初级评阅
    getInitial(){
      this.request.get("/review-work/initial-page",{
        params:{
          pageSize:this.initialPageSize,
          divisionId:this.selectedDivision
        }
      }).then(res=>{
        if(res.code=='200'){
          this.initialTableData = res.data.records;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //分页用的功能
    finalCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      this.load();
    },
    finalSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      this.load();
    },
    //获取作品用于最终评阅
    getFianl(){
      this.request.get("/review-work/final-page",{
        params:{
          pageSize:this.initialPageSize,
          pageNum:this.finalPageNum,
          divisionId:this.selectedDivision
        }
      }).then(res=>{
        if(res.code=='200'){
          this.finalTableData = res.data.records;
          this.finalTotal = res.data.total;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //根据组别选择变化进行刷新数据
    selectChange(){
      this.getInformation();
      this.getInitial();
      this.getFianl();
    },
    clearSelection() {
      // 清除选择时不做任何操作
      this.selectedDivision='';
    },

    //初级作品评阅的评阅按钮
    handleEdit1(row){
      this.initialSubmission=row;
      this.showMainPage = false;
      this.showJuniorReview = true;
    },

    //最终作品评阅的按钮
    handleEdit2(row){
      this.finalSubmission = row;
      this.showMainPage = false;
      this.showJuniorReview = true;
    },
    //获取批改结果列表
    saveReview(review) {
      const existingReview = this.reviews.find(r => r.id === review.id);
      if (existingReview) {
        existingReview.mineScore = review.mineScore;
        existingReview.mineEvaluation = review.mineEvaluation;
      } else {
        this.reviews.push(review);
      }
    },
    //将此批初级评阅结果提交上去，然后换下一批
    nextGroup(){
      this.request.put("/review-work/initial-review",this.reviews).then(res=>{
        if(res.code=='200'){
          this.$message.success('评阅成功！');
          this.getInitial();
        }else{
          this.$message.error('编辑数据数据失败，原因：'+res.msg);
        }
      })

    },

  }
};

</script>

<style>

</style>
