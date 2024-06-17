<template>
    <div class="review-container">
      <!-- 上半部分 -->
      <div class="upper-section">
        <div class="back-button">
          <button @click="goBack">返回</button>
        </div>
        <div class="title-text">
            {{ teacherName }}
          初级作品评阅
        </div>
      </div>
  
      <!-- 下半部分 -->
      <div class="lower-section">
        <div class="left-part">
          <!-- 图片展示 -->
          <div class="image-display">
            <img src="/images/copybook/1.jpg" alt="作品图片" />
          </div>
          <!-- 文本框 -->
          <div class="text-box" style="width:780px">
            <textarea disabled>{{ defaultComment }}</textarea>
          </div>
        </div>
  
        <div class="right-part">
          <!-- 题目要求，竞赛名与组别，已阅份数，目标份数 -->
          <div class="info-section">
            <div>题目要求：{{ questionRequirement }}</div>
            <div>竞赛名与组别：{{ competitionName }}</div>
          </div>
          <!-- 评分，评语 -->
          <div class="input-section">
            <div>
              系统评分：<input type="number" v-model="score" />
            </div>
            <div>
              系统评语：<input type="number" v-model="score" />
            </div>
            <div>
              该教师评分：<input type="number" v-model="score" />
            </div>
            <div>
              该教师评语：<textarea v-model="comment"></textarea>
            </div>
          </div>
          <!-- 按钮区域 -->
          <div class="button-group">
            <button @click="previousItem">上一份</button>
            <button @click="nextItem">下一份</button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        imageSrc: 'path/to/your/image.jpg', // 图片路径
        defaultComment: '（系统自动评价）',
        questionRequirement: '***',
        competitionName: '***',
        reviewedCount: 0,
        targetCount: 0,
        score: '85',
        comment: '',
        isValid: false
      };
    },
    methods: {
      goBack() {
        // 处理返回按钮的逻辑
        this.$router.go(-1); // 假设使用了 Vue Router，返回上一页
      },
      previousItem() {
        // 处理上一份的逻辑
        console.log('Previous item clicked');
      },
      confirmReview() {
        // 处理确认按钮的逻辑
        if (this.isValid) {
          // 假设isValid标志表示数据是否有效，若有效则执行确认操作
          console.log('Review confirmed');
          // 提交数据等操作...
        }
      },
      cancelReview() {
        // 处理取消按钮的逻辑
        console.log('Review canceled');
        // 清除表单数据等操作...
      },
      nextItem() {
        // 处理下一份的逻辑
        console.log('Next item clicked');
      }
    },
    mounted() {
      // 通过 this.$route.params 获取路由参数
      this.id = this.$route.params.id;
      this.teacherName = this.$route.params.teacherName
     }
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
  
  button {
    padding: 5px 10px;
    margin-right: 5px;
    background-color: #007BFF; /* 修改按钮背景色 */
    color: white; /* 修改按钮文字颜色 */
    border: none; /* 移除边框 */
    border-radius: 5px; /* 添加圆角 */
  }
  </style>