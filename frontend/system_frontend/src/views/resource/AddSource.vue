<template>
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!--添加视频面板-->
      <el-tab-pane label="视频添加" name="video" style="width: 70%;">
        <el-form ref="videoForm" :model="videoData" label-width="160px">
          <el-form-item label="视频封面添加：">
            <el-upload
              class="upload-demo"
              action="#"
              ref="videoImage"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              multiple
              drag
              :limit="1"
              :auto-upload="false"
              accept=".jpg,.jpeg,.png">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="视频文件添加：">
            <!-- 注意：视频上传通常使用更复杂的组件或第三方库 -->
            <el-upload
              class="upload-demo"
              action="#"
              ref="video"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              multiple
              drag
              :limit="1"
              accept=".mp4,.mov,.wmv,.flv,.avi,.avchd,.webm,.mkv"
              :auto-upload="false">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传视频文件</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="视频标题：">
            <el-input v-model="videoData.name" size="small"></el-input>
          </el-form-item>
          <el-form-item label="视频简介：">
            <el-input type= "textarea" v-model="videoData.summary" size="small"></el-input>
          </el-form-item>
          <el-form-item label="视频书法知识类型：">
            <el-select v-model="videoData.firstTypeId"  @change="onFirstChange1">
              <el-option label="教学指导" value="1"></el-option>
              <el-option label="书法知识" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
            <el-select v-model="videoData.secondTypeId" v-if="secondTypes1.length">
              <el-option v-for="type in secondTypes1" :key="type.id" :label="type.name" :value="type.id"></el-option>
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
              v-model="videoData.isRecommended"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          <el-form-item>

            <el-button @click="clear1">清空</el-button>
            <el-button type="primary" @click="handleSubmit1">确认添加</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <!--添加文章面板-->
      <el-tab-pane label="文章添加" name="article" style="width: 70%;">
        <el-form ref="articleForm" :model="articleData" label-width="160px">
          <el-form-item label="文章封面添加：">
            <el-upload
              class="upload-demo"
              action="#"
              ref="articleImage"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              multiple
              drag
              :limit="1"
              :auto-upload="false"
              accept=".jpg,.jpeg,.png">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="文章书法知识类型：">
            <el-select v-model="articleData.firstTypeId" @change="onFirstChange2">
              <el-option label="教学指导" value="1"></el-option>
              <el-option label="书法知识" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
            <el-select v-model="articleData.secondTypeId" v-if="secondTypes2.length">
              <el-option v-for="type in secondTypes2" :key="type.id" :label="type.name" :value="type.id"></el-option>
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
            <el-input v-model="articleData.name" size="small"></el-input>
          </el-form-item>
          <el-form-item label="文章内容：">
            <wang-editor ref="myEditor" v-model="valueHtml"></wang-editor>
          </el-form-item>
          <el-form-item label="是否为推荐：">
            <el-switch
              style="display: block"
              v-model="articleData.isRecommended"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          <el-form-item>
            <el-button @click="clear2">清空</el-button>
            <el-button type="primary" @click="handleSubmit2">确认添加</el-button>
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
        name: '',
        summary:'',
        firstTypeId: '',
        secondTypeId: '',
        tag: '',
        isRecommended:'false',
      },
      articleData:{
        name:'',
        firstTypeId: '',
        secondTypeId: '',
        tag: '',
        isRecommended:'false',
      },
      secondTypes1: [],
      secondTypes2: [],
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      valueHtml:'',
    };
  },
  methods: {
    //导入成功
    handleExcelImportSuccess(response, file, fileList){ 
      //console.log("到了导入成功里面去了");
      if(response.code=='200'){
        this.$message.success("导入成功");
      }else{
        this.$message.error("导入失败，原因为："+response.msg);
      }
    },
    //导入失败
    handleExcelImportError(err, file, fileList){ 
      //console.log(err);
      this.$message.error("导入失败，原因为"+err);
    },
    onFirstChange1(){
      const firstTypeId = parseInt(this.videoData.firstTypeId,10);
      this.getSecond1(firstTypeId);
      this.videoData.secondTypeId='';
    },

    onFirstChange2(){
      const firstTypeId = parseInt(this.articleData.firstTypeId,10);
      this.getSecond2(firstTypeId);
      this.articleData.secondTypeId='';
    },
    getSecond1(val){
      console.log("打印下值多少"+val);
      this.request.get("/calligraphy-facts/seconds",{
        params: {
          id: val
        }
      }).then(res => {
        if(res.code == '200'){
          this.secondTypes1= res.data;
        }else{
          this.$message.error('获取二级类型失败，原因：'+res.msg);
        }
      });
      return null;
    },
    getSecond2(val){
      console.log("打印下值多少"+val);
      this.request.get("/calligraphy-facts/seconds",{
        params: {
          id: val
        }
      }).then(res => {
        if(res.code == '200'){
          this.secondTypes2=res.data;
        }else{
          this.$message.error('获取二级类型失败，原因：'+res.msg);
        }
      });
      return null;
    },
    clear1(){
      // 重置videoData对象
      this.videoData = {
        name: '',
        summary: '',
        firstTypeId: null,
        secondTypeId: null,
        isRecommended: false
      };

      // 清空已上传的文件
      this.$refs.videoImage.clearFiles();
      this.$refs.video.clearFiles();

      // 清空标签
      this.dynamicTags = [];
    },
    clear2(){
      console.log("为什么没情况");
      // 重置articleData对象
      this.articleData = {
        name: '',
        firstTypeId: null,
        secondTypeId: null,
        isRecommended: false
      };

      // 清空已上传的文件
      this.$refs.articleImage.clearFiles();

      // 清空标签
      this.dynamicTags = [];
    },
    handleClick(){
      if(this.activeName=='video'){
        this.clear2();
      }
      if(this.activeName=='article'){
        this.clear1();
      }
      
    },
    handleSubmit1(){
      this.$confirm('确认添加该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端接口
        this.videoData.tag = this.dynamicTags.join('@');
        let formData = new FormData();
        formData.append('videoData', JSON.stringify(this.videoData));
        formData.append('image',this.$refs.videoImage.uploadFiles[0].raw);
        formData.append('videoFile',this.$refs.video.uploadFiles[0].raw);
        
        this.request.post('/video/add',formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
					}
        }).then(response => {
            this.handleExcelImportSuccess(response);
        }).catch(error => {
            this.handleExcelImportError(error);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消添加'
        });          
      });
    },
    handleSubmit2(){
      this.$confirm('确认添加该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log("看看有没有");
        this.articleData.tag = this.dynamicTags.join('@');
        console.log("看看有没有");
        // 尝试获取HTML内容
        console.log("看下组件");
        console.log(this.$refs.myEditor);
        let htmlContent = this.$refs.myEditor.valueHtml;
        console.log("看看有没有");
        console.log(htmlContent);
        let formData = new FormData();
        formData.append('content',JSON.stringify(htmlContent));
        console.log("看看有没有");

        formData.append('article',JSON.stringify(this.articleData));
        console.log("看看有没有");

        formData.append('image',this.$refs.articleImage.uploadFiles[0].raw)
        console.log(formData);

        // 调用后端删除接口
        this.request.post('/article/add',formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if(res.code == '200'){
              this.$message.success('添加文章成功！');
          }else{
              this.$message.error('添加文章失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
            type: 'info',
            message: '已取消添加'
        });          
      });
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
