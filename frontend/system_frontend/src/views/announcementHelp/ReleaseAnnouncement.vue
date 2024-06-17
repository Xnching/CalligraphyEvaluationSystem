<template>
  <div class="announcement-form">
    <el-form ref="announcementForm" :model="form" label-width="100px">
      <el-form-item label="公告名：">
        <el-input v-model="form.title" placeholder="请输入公告名"></el-input>
      </el-form-item>
      <el-form-item label="发布对象：">
        <el-select v-model="form.target" placeholder="请选择发布对象">
          <el-option label="所有人" value="all"></el-option>
          <el-option label="特定人群" value="specific"></el-option>
          <!-- 添加更多选项 -->
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间：">
        <el-radio-group v-model="form.releaseType">
          <el-radio label="immediate">立即发布</el-radio>
          <el-radio label="scheduled">定时发布</el-radio>
        </el-radio-group>
        <el-time-picker
            v-if="form.releaseType === 'scheduled'"
            v-model="form.scheduledTime"
            placeholder="选择发布时间"
            :picker-options="pickerOptions"
        >
        </el-time-picker>
      </el-form-item>
      <el-form-item label="发布内容：">
        <el-input type="textarea" v-model="form.content" :rows="5" placeholder="请输入发布内容"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="confirm">确认</el-button>
        <el-button @click="cancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        title: '',
        target: 'all',
        releaseType: 'immediate',
        scheduledTime: '',
        content: ''
      },
      pickerOptions: {
        selectableRange: '00:00:00-23:59:59'
      }
    };
  },
  methods: {
    confirm() {
      this.$refs.announcementForm.validate((valid) => {
        if (valid) {
          alert('表单验证通过，提交公告');
          // 此处可以进行表单数据的提交逻辑
        } else {
          alert('表单验证失败，请检查输入');
          return false;
        }
      });
    },
    cancel() {
      this.$message({
        message: '已取消',
        type: 'info'
      });
      // 此处可以进行取消操作的逻辑
    }
  }
};
</script>

<style scoped>
.announcement-form {
  max-width: 400px;
  margin: 0 auto;
}
.el-form-item__label {
  text-align: left !important;
}
</style>
