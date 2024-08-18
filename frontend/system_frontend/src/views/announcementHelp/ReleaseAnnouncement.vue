<template>
  <div class="announcement-form">
    <el-form ref="announcementForm" :model="form" label-width="100px">
      <el-form-item label="公告名：">
        <el-input v-model="form.name" placeholder="请输入公告名"></el-input>
      </el-form-item>
      <el-form-item label="公告类型：">
        <el-select v-model="form.type" placeholder="请选择公告类型">
          <el-option label="系统公告" value="系统公告"></el-option>
          <el-option label="竞赛公告" value="竞赛公告"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布对象：">
        <el-select v-model="form.target" placeholder="请选择发布对象">
          <el-option label="学生" value="学生"></el-option>
          <el-option label="教师" value="教师"></el-option>
          <el-option label="学生和教师" value="学生和教师"></el-option>
          <el-option label="管理员" value="管理员"></el-option>
          <el-option label="全体" value="全体"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间：">
        <el-radio-group v-model="form.releaseType">
          <el-radio label="立即发布">立即发布</el-radio>
          <el-radio label="定时发布">定时发布</el-radio>
        </el-radio-group>
        <el-time-picker
            v-if="form.releaseType === 'scheduled'"
            v-model="form.releaseTime"
            placeholder="选择发布时间"
            :picker-options="pickerOptions"
            style="margin: auto;">
        </el-time-picker>
      </el-form-item>
      <el-form-item label="公告封面">
        <el-upload
          v-if="form.type === '竞赛公告'"
          class="upload-demo"
          drag
          ref="singleImage"
          action="https://jsonplaceholsts/"
          multiple
          :limit="1"
          accept=".png,.jpeg,.jpg"
          style=" text-align: center;"
          :on-success="handleExcelImportSuccess"
          :on-error="handleExcelImportError"
          :auto-upload="false">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将需要录入的字、部首的图片拖到此处，或<em>点击上传</em></div>
        </el-upload>
      </el-form-item>
      <el-form-item label="公告内容：">
        <WangEditor ref="editor"></WangEditor>
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
  components: {
    WangEditor
  },
  data() {
    return {
      form: {
        name: '',
        type: '',
        target: '',
        releaseType: '立即发布',
        releaseTime: '',
        content: ''
      },
      pickerOptions: {
        selectableRange: '00-00-00 23:59:59'
      }
    };
  },
  methods: {
    confirm() {
      // 获取编辑器内容
      this.form.content = this.$refs.editor.valueHtml;
      let formData = new FormData();
      formData.append('file', this.$refs.singleImage.uploadFiles[0].raw);
      formData.append('form', JSON.stringify(this.form));
      // 发送请求到后端
      this.request.post("/announcement/release", formData,{
					headers: {
							'Content-Type': 'multipart/form-data'
					}
			})
        .then(res => {
          if (res.code === '200') {
            this.$message.success('公告发布成功');
          } else {
            this.$message.error('公告发布失败，原因：' + res.msg);
          }
        })
      .catch(error => {
          this.$message.error('公告发布失败，原因：' + error.message);
      });
    },
    cancel() {
      // 重置表单字段
      this.$refs.announcementForm.resetFields();
      // 清空表单数据
      this.form.title = '';
      this.form.type = '';
      this.form.target = '';
      this.form.releaseType = 'immediate';
      this.form.scheduledTime = '';
      this.form.content = '';
      // 清空编辑器内容
      this.$refs.editor.valueHtml = '';
      this.$message.success('表单已清空');
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
