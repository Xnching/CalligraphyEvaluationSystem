<template>
  <div class="review-container">
    <!-- 上半部分 -->
    <div class="upper-section">
      <div class="back-button">
        <el-button type="primary" @click="$emit('go-back')">返回</el-button>
      </div>
      <div class="title-text">
        最终作品评阅
      </div>
    </div>
    <!-- 下半部分 -->
    <div class="lower-section">
      <div class="left-part">
        <!-- 图片展示 -->
         作品图片：
        <div class="image-display">
          <div v-for="image in submission.imageList" :key="image" class="image-item">
            <img :src="image" :alt="image">
          </div>
        </div>
        <!-- 文本框 -->
        <div class="text-box" style="width:680px">
          智能评分：
          <el-input type="number" v-model="submission.systemScore" disabled ></el-input>
          智能评语：
          <el-input type="textarea" disabled v-model="submission.systemEvaluation"></el-input>
          初级评分：
          <el-input type="number" v-model="submission.initialScore" disabled ></el-input>
          初级评语：
          <el-input type="textarea" disabled v-model="submission.initialEvaluation"></el-input>
        </div>
      </div>
      <div class="right-part">
        <!-- 题目要求，竞赛名与组别，已阅份数，目标份数 -->
        <div class="info-section">
          <div>组别要求：
            <el-input type="textarea" v-model="division.requirement"></el-input>
          </div>
        </div>
        <!-- 评分，评语 -->
        <div class="input-section">
          <div>
            我的评分：<el-input type="number" v-model="mineScore"></el-input>
          </div>
        </div>
        <!-- 按钮区域 -->
        <div class="button-group">
          <el-button type="success" @click="reviewWork">提交评阅</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    division:Object,
    submission: Object
  },
  data(){
    return{
      mineScore:'',
    }
  },
  
  methods: {
    reviewWork(){
      this.request.post("/review-work/final-review",{
        params:{
          submissionId:this.submission.id,
          score:this.mineScore
        }
        }).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增用户数据成功！');
          this.load();
        } else {
          this.$message.error('新增用户数据失败，原因：' + res.msg);
        }
      })
    },
  },

};
</script>
<style scoped>
.review-container {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  height: 100%;
}

.upper-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #007BFF; /* 添加背景色 */
  color: white; /* 修改文字颜色 */
}

.back-button {
  margin-left: 10px;
}

.title-text {
  font-weight: bold;
  text-align: center;
  font-size: 1.5em; /* 增大字体大小 */
}

.lower-section {
  display: flex;
  justify-content: space-between;
  padding: 20px; /* 增大内边距 */
}

.left-part,
.right-part {
  display: flex;
  flex-direction: column;
  padding: 10px;
  background-color: white; /* 添加背景色 */
  border-radius: 5px; /* 添加圆角 */
  box-shadow: 0 0 10px rgba(0,0,0,0.1); /* 添加阴影 */
}

.image-display img {
  max-width: 100%;
  height: auto;
  border-radius: 5px; /* 添加圆角 */
}

.info-section div {
  margin-bottom: 5px;
  font-size: 1.2em; /* 增大字体大小 */
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

</style>