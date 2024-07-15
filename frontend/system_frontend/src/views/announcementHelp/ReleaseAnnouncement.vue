<template>
  <div class="announcement-form">
    <el-form ref="announcementForm" :model="form" label-width="100px">
      <el-form-item label="公告名：">
        <el-input v-model="form.title" placeholder="请输入公告名"></el-input>
      </el-form-item>
      <el-form-item label="公告类型：">
        <el-select v-model="form.target" placeholder="请选择公告类型">
          <el-option label="系统公告" value="system"></el-option>
          <el-option label="竞赛公告" value="competition"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布对象：">
        <el-select v-model="form.target" placeholder="请选择发布对象">
          <el-option label="学生" value="student"></el-option>
          <el-option label="教师" value="teacher"></el-option>
          <el-option label="学生和教师" value="both"></el-option>
          <el-option label="管理员" value="manger"></el-option>
          <el-option label="所有人" value="all"></el-option>
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
            style="margin: auto;"
        >
        </el-time-picker>
      </el-form-item>
      <el-form-item label="公告内容：">
        <WangEditor></WangEditor>
      </el-form-item>
      <el-form-item style=" text-align: center;">
        <el-button type="primary" @click="confirm">确认</el-button>
        <el-button @click="cancel">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import WangEditor from '@/components/wangEditor.vue';

export default {
  components:{
    WangEditor
  },
  data() {
    return {
      form: {
        title: '',
        target: '',
        releaseType: 'immediate',
        scheduledTime: '',
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


      });
      // 此处可以进行取消操作的逻辑
    }
  }
};
</script>

<style scoped>
.announcement-form {
  max-width: 90%;
  margin: 0 auto;
}
.el-form-item__label {
  text-align: left !important;
}
</style>
