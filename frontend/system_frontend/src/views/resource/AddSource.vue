<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="视频添加" name="first">视频添加</el-tab-pane>
      <el-tab-pane label="文本添加" name="second">文本添加</el-tab-pane>
    </el-tabs>
    <div v-if="currentPage === 'video'">
      <el-form ref="videoForm" :model="videoData" label-width="120px">
        <el-form-item label="封面添加">
          <el-upload
              class="upload-demo"
              action="#"
              :on-success="handleFileUploadSuccess"
              :before-upload="beforeUpload"
              :file-list="videoData.coverList"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频添加">
          <!-- 注意：视频上传通常使用更复杂的组件或第三方库 -->
          <el-upload
              class="upload-demo"
              action="#"
              :on-success="handleVideoUploadSuccess"
              :before-upload="beforeVideoUpload"
              :file-list="videoData.videoList"
              multiple
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频名">
          <el-input v-model="videoData.name" size="small"></el-input>
        </el-form-item>
        <el-form-item label="视频书法知识类型">
          <el-select v-model="videoData.knowledgeType">
            <el-option label="书法知识" value="书法知识"></el-option>
            <el-option label="教学指导" value="教学指导"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="详细类型">
          <el-select v-model="videoData.detailType">
            <el-option v-for="type in detailTypes" :key="type.value" :label="type.label" :value="type.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自定义类型">
          <el-input v-model="videoData.customType" @keyup.enter="addCustomTag('video')" size="small"></el-input>
          <el-tag
              v-for="(tag, index) in videoData.tags"
              :key="index"
              closable
              @close="handleTagClose(index, 'video')"
          >
            {{ tag }}
          </el-tag>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitVideo">确定</el-button>
          <el-button @click="cancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 这里重复上面视频的 form 部分但替换 model、方法和标签为文本相关的 -->
    <div v-if="currentPage === 'text'">
      <!-- ... -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeName: 'first',
      currentPage: 'video',
      videoData: {
        name: '',
        knowledgeType: '',
        detailType: '',
        customType: '',
        tags: [],
        coverList: [], // 用来保存封面图片的列表
        videoList: [] // 用来保存视频文件的列表
      },
      textData: {
        // ... 文本相关的数据
      },
      detailTypes: [ // 详细类型的选项
        { label: '书法作品', value: '书法作品' },
        { label: '汉字', value: '汉字' },
        { label: '人物传记', value: '人物传记' },
        { label: '文化', value: '文化' }
      ]
    };
  },
  methods: {
    showPage(page) {
      this.currentPage = page;
      this.$refs[page === 'video' ? 'videoForm' : 'textForm'].resetFields(); // 重置表单字段
    },
    handleFileUploadSuccess(response, file, fileList) {
      // 处理封面图片上传成功后的逻辑
    },
    beforeUpload(file) {
      // 上传封面图片前的逻辑处理，比如文件大小和类型检查
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传封面图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传封面图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    handleVideoUploadSuccess(response, file, fileList) {
      // 处理视频文件上传成功后的逻辑
    },
    beforeVideoUpload(file) {
      // 视频文件上传前的逻辑处理，比如文件大小和类型检查
    },
    addCustomTag(type) {
      // 添加自定义标签的方法（与之前一致）
    },
    submitVideo() {
      // 提交视频表单数据（与之前一致）
    },
    submitText() {
      // 提交文本表单数据（与之前一致）
    },
    cancel() {
      // 重置表单数据（与之前一致）
    },
    handleTagClose(index, type) {
      // 处理标签关闭的事件
      if (type === 'video') {
        this.videoData.tags.splice(index, 1);
      } else {
        // 处理文本相关的标签
      }
    }
  }
};
</script>

<style scoped>
.el-input__inner {
  /* 设置你想要的宽度 */
  width: 50px;
}
/* 在这里添加你的样式，Element UI 组件的样式通常不需要额外添加 */
</style>
