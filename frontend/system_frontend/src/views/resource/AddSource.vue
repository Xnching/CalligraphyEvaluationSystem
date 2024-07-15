<template>
  <div>
    <el-tabs v-model="activeName">
      <!--添加视频面板-->
      <el-tab-pane label="视频添加" name="video" style="width: 70%;">
        <el-form ref="videoForm" :model="videoData" label-width="160px">
          <el-form-item label="视频封面添加：">
            <el-upload
              class="upload-demo"
              action="#"
              :on-success="handleFileUploadSuccess"
              :before-upload="beforeUpload"
              :file-list="videoData.coverList"
              multiple
              drag
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
              accept=".jpg,.jpeg,.png">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="视频文件添加：">
            <!-- 注意：视频上传通常使用更复杂的组件或第三方库 -->
            <el-upload
              class="upload-demo"
              action="#"
              :on-success="handleFileUploadSuccess"
              :before-upload="beforeUpload"
              :file-list="videoData.coverList"
              multiple
              drag
              :limit="1"
              accept=".mp4,.mov,.wmv,.flv,.avi,.avchd,.webm,.mkv"
              :on-exceed="handleExceed"
              :auto-upload="false">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="视频标题：">
            <el-input v-model="videoData.title" size="small"></el-input>
          </el-form-item>
          <el-form-item label="视频简介：">
            <el-input type= "textarea" v-model="videoData.intro" size="small"></el-input>
          </el-form-item>
          <el-form-item label="视频书法知识类型：">
            <el-select v-model="videoData.knowledgeType">
              <el-option label="书法知识:" value="书法知识"></el-option>
              <el-option label="教学指导:" value="教学指导"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
            <el-select v-model="videoData.detailType">
              <el-option v-for="type in detailTypes" :key="type.value" :label="type.label" :value="type.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="添加标签：">
            <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>

          <el-form-item label="是否为推荐：">
            <el-switch
              style="display: block"
              v-model="recommendedValue2"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          <el-form-item>

            <el-button>清空</el-button>
            <el-button type="primary" @click="handleSubmit1">确认添加</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <!--添加文章面板-->
      <el-tab-pane label="文章添加" name="second" style="width: 70%;">
        <el-form ref="videoForm" :model="videoData" label-width="160px">
          <el-form-item label="文章封面添加：">
            <el-upload
              class="upload-demo"
              action="#"
              :on-success="handleFileUploadSuccess"
              :before-upload="beforeUpload"
              :file-list="videoData.coverList"
              multiple
              drag
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
              accept=".jpg,.jpeg,.png">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="文章书法知识类型：">
            <el-select v-model="videoData.knowledgeType">
              <el-option label="书法知识:" value="书法知识"></el-option>
              <el-option label="教学指导:" value="教学指导"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
            <el-select v-model="videoData.detailType">
              <el-option v-for="type in detailTypes" :key="type.value" :label="type.label" :value="type.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="添加标签：">
            <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm">
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
          <el-form-item label="文章标题：">
            <el-input v-model="videoData.title" size="small"></el-input>
          </el-form-item>
          <el-form-item label="文章内容：">
            <wang-editor></wang-editor>
          </el-form-item>
          <el-form-item label="是否为推荐：">
            <el-switch
              style="display: block"
              v-model="recommendedValue2"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          <el-form-item>
            <el-button>清空</el-button>
            <el-button type="primary" @click="handleSubmit1">确认添加</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
    


  </div>
</template>

<script>
import WangEditor from '@/components/wangEditor.vue';

export default {
  components:{
    WangEditor,
  },
  data() {
    return {
      activeName: 'video',
      currentPage: 'video',
      videoData: {
        title: '',
        knowledgeType: '',
        detailType: '',
        customType: '',
        tags: [],
        coverList: [], // 用来保存封面图片的列表
        videoList: [] // 用来保存视频文件的列表
      },
      detailTypes: [ // 详细类型的选项
        { label: '书法作品', value: '书法作品' },
        { label: '汉字', value: '汉字' },
        { label: '人物传记', value: '人物传记' },
        { label: '文化', value: '文化' }
      ],
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      recommendedValue2:false,
      recommendedValue1:false,
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
    submitVideo() {
      // 提交视频表单数据（与之前一致）
    },
    submitText() {
      // 提交文章表单数据（与之前一致）
    },
    cancel() {
      // 重置表单数据（与之前一致）
    },


    //自定义标签的方法
    handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    
    //自定义标签的方法
    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    //自定义标签的方法
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue && this.dynamicTags.indexOf(inputValue) === -1) { // 检查标签是否已存在
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    }
  }
    
}
</script>

<style>
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
