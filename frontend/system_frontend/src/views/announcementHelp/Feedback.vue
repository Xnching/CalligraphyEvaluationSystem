<template>
  <div class="page-container">
    <div class="left-pane">
      <div class="form-group">
        <label for="feedback">反馈内容:</label>
        <textarea id="feedback" v-model="feedbackContent"></textarea>
      </div>
      <div class="photo-area">
        <h3>照片区域</h3>
      </div>
      <div class="video-area">
        <h3>视频区域</h3>
      </div>
    </div>
    <div class="right-pane">
      <div class="user-info">
        <p>用户编号: *****</p>
        <p>反馈性质: ******</p>
        <p>电话: ******</p>
      </div>
      <div class="reply-area">
        <textarea v-model="replyContent" placeholder="输入回复文本"></textarea>
        <button @click="submitReply" class="reply-button">回复</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      feedbackContent: '文本',
      replyContent: ''
    };
  },
  methods: {
    submitReply() {
      alert('回复已提交');
    }
  }
};
</script>

<style scoped>
.page-container {
  display: flex;
  width: 80%;
  height: 80vh;
  margin: 0 auto;
  border: 1px solid #ccc;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.left-pane,
.right-pane {
  width: 50%;
  padding: 20px;
  box-sizing: border-box;
  border-right: 1px solid #ccc;
}

.left-pane {
  border-right: 1px solid #ccc;
}

.right-pane {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.form-group,
.photo-area,
.video-area,
.user-info,
.reply-area {
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 10px;
}

textarea {
  width: 100%;
  height: 150px;
  padding: 10px;
  box-sizing: border-box;
}

.photo-area,
.video-area {
  margin-top: 20px;
}

.user-info {
  text-align: center;
}

.reply-area {
  display: flex;
  flex-direction: column;
}

.reply-area textarea {
  flex-grow: 1;
  height: 200px;
  margin-bottom: 10px;
}

.reply-button {
  align-self: flex-end;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
