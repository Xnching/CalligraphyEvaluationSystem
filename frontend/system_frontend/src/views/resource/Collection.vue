<template>
    <div>
      <!--用来搜索合集的搜索框-->
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
          <el-input style="width:300px ;margin-left:-20px" 
          suffix-icon="el-icon-search" 
          placeholder="请输入要查找的合集标题"
          v-model="inputVal"
          @keyup.enter.native="Search_table()"
          clearable>
          </el-input>
          <el-button style="margin-left:20px ;margin-right:435px" type="primary">搜索</el-button>
          <el-button type="primary" @click="handleEdit1" style="margin-left: auto; margin-right: 120px;">新增合集<i class="el-icon-circle-plus"></i></el-button>
      </div>
    
      
      <!--下面是图片展示区域-->  
      <div class="image-grid"> 
          <div v-for="image in paginatedImages" :key="image.id" class="image-item">
              <img :src="image.src" :alt="image.title" @click="handleEdit2(image)">
              <p style="text-align: center;">{{ image.title }}</p> <!-- 添加这一行来显示图片标题 -->
          </div>
      </div>

      <div class="pagination">
          <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
          <span>{{ currentPage }} / {{ totalPages }}</span>
          <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
      </div>

    

      <!-- 新增合集的弹窗 -->
      <el-dialog
      title="请输入合集的信息"
      :visible.sync="dialogVisible1"
      width="80%"
      :close-on-click-modal="false" 
      style="text-align: left">
        <el-form ref="videoForm" :model="videoData" label-width="150px">
          <el-form-item label="合集封面添加：">
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
              accept=".jpg,.jpeg,.png"
              :auto-upload="false">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                  <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
          </el-form-item>
          <el-form-item label="合集标题：">
              <el-input v-model="videoData.title" size="small"></el-input>
          </el-form-item>
          <el-form-item label="合集简介：">
              <el-input type="textarea" v-model="videoData.intro" size="small"></el-input>
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
          <el-form-item label="添加单集视频：">
                <el-transfer
                  filterable
                  :filter-method="filterMethod"
                  filter-placeholder="请输入单集视频标题"
                  v-model="value"
                  :data="simpleData">
              </el-transfer>

          </el-form-item>
        </el-form>
        
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit1()">确定</el-button>
            </span>
        </template>
      </el-dialog>



      <!--合集详情的弹窗-->
      <el-dialog
      title="此处编辑合集"
      :visible.sync="dialogVisible2"
      width="70%"
      :close-on-click-modal="false" >
        <!--合集详细信息表单-->
        <el-form ref="videoForm" :model="videoData" label-width="160px">
          <el-form-item label="合集封面：">
            <!-- 显示图片 -->
            <img v-if="selectedImage" :src="selectedImage.src" :alt="selectedImage.title" />
            <!-- 更换图片 -->
            <input type="file" @change="onFileChange">
          </el-form-item>
          <el-form-item label="合集标题：">
            <el-input v-model="videoData.title" size="small"></el-input>
          </el-form-item>
          <el-form-item label="合集简介：">
            <el-input type="textarea" v-model="videoData.intro" size="small"></el-input>
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
          
            
          <el-form-item label="合集中单集视频：">
            <!--合集里面单集表格-->
            <el-table
              ref="multipleTable"
              :data="tableData"
              stripe
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                prop="title"
                label="单集标题"
                width="280">
              </el-table-column>
              <el-table-column fixed="right" label="操作">                         
                <template slot-scope="scope">
                    <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
                    <el-button type="primary" size="small"  icon="el-icon-delete">增加</el-button>
                </template>
              </el-table-column> 
              <el-table-column fixed="right" label="修改顺序">                         
                <template slot-scope="scope">
                    <el-button  size="small" icon="el-icon-edit">上移</el-button>
                    <el-button  size="small"  icon="el-icon-delete">下移</el-button>
                </template>
              </el-table-column> 

            </el-table>

          </el-form-item>
        </el-form>

        
          
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible2 = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit2()">确定</el-button>
            </span>
        </template>
      </el-dialog>


    </div>
</template>


<script>
export default {
  data() {
      //下面这个是穿梭框的
      const generateData = _ => {
          const simpleData = [];
          const videos = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
          videos.forEach((video, index) => {
          simpleData.push({
              label: video,
              key: index,
          });
          });
          return simpleData;
      };
      return {
        detailTypes: [ // 详细类型的选项
          { label: '书法作品', value: '书法作品' },
          { label: '汉字', value: '汉字' },
          { label: '人物传记', value: '人物传记' },
          { label: '文化', value: '文化' }
        ],
        //下面三个是穿梭框的
        simpleData: generateData(),
        value: [],
        filterMethod(query, item) {
            return item.label.indexOf(query) > -1;
        },

        //下面三个是自定义标签所需要的
        dynamicTags: [],
        inputVisible: false,
        inputValue: '',
        //初始隐藏两个表单
        dialogVisible1: false,
        dialogVisible2: false,
        currentPage: 1,
        imagesPerPage: 15,  //此处控制一页有多少个
        showModal: false,
        selectedImage: null,
        videoData:[],
        images: [],
        tableData:Array(8).fill().map(() => ({
        title:"字的练习，笔画练习",
      })),
      };
  },
  watch: {
      
      
  },
  computed: {
      // 计算总页数
      totalPages() {
          // 使用 Math.ceil() 向上取整，确保即使图片数量无法整除每页数量也能显示所有图片
          return Math.ceil(this.images.length / this.imagesPerPage);
      },
      // 计算当前页需要显示的图片列表
      paginatedImages() {
          // 计算起始索引，(currentPage - 1) * imagesPerPage
          const startIndex = (this.currentPage - 1) * this.imagesPerPage;
          // 计算结束索引，startIndex + imagesPerPage
          const endIndex = startIndex + this.imagesPerPage;
          // 使用 slice() 方法截取需要显示的图片数组片段
          return this.images.slice(startIndex, endIndex);
      },
  },
  mounted() {
      // 在组件挂载后生成 images 数组
      for (let i = 1; i <= 35; i++) {
      this.images.push({
          id: i,
          src: `/images/word/${i}.png`,
          title: `图片标题${i}`,
          description: `图片描述${i}`,
      });
      }
  },

  methods: {
  
    //下面两个是修改视频封面的
    onFileChange(e) {
      let files = e.target.files || e.dataTransfer.files;
      if (!files.length)
        return;
      this.createImage(files[0]);
    },
    createImage(file) {
      let reader = new FileReader();
      let vm = this;
      reader.onload = (e) => {
        vm.coverImage = e.target.result;
      };
      reader.readAsDataURL(file);
    },



    //新增按钮跳出弹窗
    handleEdit1(){
      
      this.dialogVisible1 = true;
    },

    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {


      this.dialogVisible1 = false;  //关闭弹窗
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit2(image) {
      // 将选中的图片数据赋值给 selectedImage
      this.selectedImage = image;

      this.dialogVisible2 = true;
    },

    //编辑用户弹窗确定提交的方法
    handleSubmit2() {
      // 在此处处理删除逻辑
      
      this.dialogVisible2 = false; // 关闭弹窗
    },


    // 处理页码变化事件
    onPageChange(page) {
        // 更新当前页码
        this.currentPage = page;
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
    },
  
  },
  
};
</script>

<style scoped>
.el-transfer-panel__item {
  padding-left: 30px!important;
}
.image-grid { 
  display: grid;
  grid-template-columns: repeat(5, 1fr); 
  grid-template-rows: repeat(3, auto);   
  margin-top: 20px;
  margin-bottom: 20px;
  grid-gap: 50px;                  
  row-gap: 30px;                 
}
.image-item {
  border: 2px solid #ccc; /* 边框样式 */
  padding: 10px;          /* 可选：内边距 */
  position: relative;     /* 设置为相对定位 */ 
}

.image-item img {
  display: block;           /*  将图片设置为块级元素 */
  margin: 0 auto;          /*  水平居中 */
  max-width: 80%;          /*  设置最大宽度为容器的 80% */
  height: auto;            /*  高度自适应 */
}

/*  添加伪元素来创建正方形背景  */
.image-item::before {
  content: '';             
  display: block;
  padding-top: 100%;      /*  通过 padding-top 来控制正方形的高度  */
  position: absolute;      
  top: 0;
  left: 0;
  z-index: -1;            /*  将伪元素置于图片下方  */
}
.el-dialog img {
  max-width: 200px;  /*  设置图片最大宽度  */
  max-height: 300px; /*  设置图片最大高度  */
}
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
