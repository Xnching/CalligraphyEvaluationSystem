<template>
  <div>

    <!--视频详情的弹窗-->
    <el-dialog
      title="书法知识-视频详情"
      :visible.sync="dialogVisible1"
      width="50%"
      @close="dialogVisible1 = false">
        <el-form :model="formData">
          <h1>视频内容：</h1>
          <el-form-item >

          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="视频标题：">
                <span>{{ formData.title }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="添加人：">
                <span>{{ formData.addedBy }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="书法知识类型：">
                <span>{{ formData.type }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="标签：">
                <span>{{ formData.customType }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="详细类型：">
                <span>{{ formData.detailType }}</span>
              </el-form-item>
            </el-col>
            <el-col>
              <el-switch
                style="display: block"
                v-model="recommendedValue1"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="推荐"
                inactive-text="不推荐">
              </el-switch>
            </el-col>
          </el-row>
        </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit1">修改</el-button>
      </span>
    </el-dialog>


    <!--文章详情的弹窗-->
    <el-dialog
      title="书法知识-文章详情"
      :visible.sync="dialogVisible2"
      width="50%"
      @close="dialogVisible2 = false">
        <el-form :model="formData">
          <h1>文章内容：</h1>
          <el-form-item >
            <WangEditor></WangEditor>
          </el-form-item>
          
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="文章标题：">
                <span>{{ formData.title }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="添加人：">
                <span>{{ formData.addedBy }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="书法知识类型：">
                <span>{{ formData.type }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="标签：">
                <span>{{ formData.customType }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="详细类型：">
                <span>{{ formData.detailType }}</span>
              </el-form-item>
            </el-col>
            <el-col>
              <el-switch
                style="display: block"
                v-model="recommendedValue2"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="推荐"
                inactive-text="不推荐">
              </el-switch>
            </el-col>
          </el-row>
        </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit2">修改</el-button>
      </span>
    </el-dialog>


    <!--下面为面板-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!--下面为视频管理面板-->
      <el-tab-pane label="视频管理" name="video">
        <div>
          <div style="padding:10px; margin:10px; margin-bottom: -5px;">
            <el-input style="width:300px"
                      suffix-icon="el-icon-search"
                      placeholder="请输入"
                      v-model="inputVal"
                      @keyup.enter.native="Search_table()"
                      clearable>
            </el-input>
            <el-button style="margin-left:20px ;margin-right:50px" type="primary">搜索</el-button>

            <!--下面是属性选择器 ，可以手动输入搜索-->

            <el-select v-model="selectedType" filterable placeholder="选择类型" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in Type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="selectedDetailedType" filterable placeholder="选择详细类型" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in detailedType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="selectedRecommended" filterable placeholder="是否为推荐" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in Recommended"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </div>

          <!--下面是图片展示区域-->
          <div class="image-grid">
            <div v-for="image in paginatedImages" :key="image.id" class="image-item">
              <img :src="image.src" :alt="image.title" @click="handleEdit1(image)">
              <p style=" text-align: center;">{{ image.title }}</p> <!-- 添加这一行来显示图片标题 -->
            </div>
          </div>

          <div class="pagination">
            <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
            <span>{{ currentPage }} / {{ totalPages }}</span>
            <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
          </div>
        </div>
      </el-tab-pane>

      <!--下面为文章管理面板-->
      <el-tab-pane label="文章管理" name="text">
        <div>
          <div style="padding:10px; margin:10px; margin-bottom: -5px;">
            <el-input style="width:300px"
                      suffix-icon="el-icon-search"
                      placeholder="请输入"
                      v-model="inputVal"
                      @keyup.enter.native="Search_table()"
                      clearable>
            </el-input>
            <el-button style="margin-left:20px ;margin-right:50px" type="primary">搜索</el-button>

            <!--下面是属性选择器 ，可以手动输入搜索-->

            <el-select v-model="selectedType" filterable placeholder="选择类型" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in Type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="selectedDetailedType" filterable placeholder="选择详细类型" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in detailedType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-select v-model="selectedRecommended" filterable placeholder="是否为推荐" style="width: 150px;margin-right: 20px;">
              <el-option
                  v-for="item in Recommended"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>

          </div>

          <!--下面是图片展示区域-->
          <div class="image-grid">
            <div v-for="image in paginatedImages" :key="image.id" class="image-item">
              <img :src="image.src" :alt="image.title" @click="handleEdit2(image)">
              <p style=" text-align: center;">{{ image.title }}</p> <!-- 添加这一行来显示图片标题 -->
            </div>
          </div>

          <div class="pagination">
            <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
            <span>{{ currentPage }} / {{ totalPages }}</span>
            <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
          </div>
        </div>

      </el-tab-pane>

    </el-tabs>
    

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
      inputVal:"",
      Type: [{
        value: '选项1',
        label: '教学指导'
      }, {
        value: '选项2',
        label: '书法知识'
      }],
      detailedType: [{
        value: '选项1',
        label: '上下结构'
      }, {
        value: '选项2',
        label: '左右结构'
      }, {
        value: '选项3',
        label: '半包围结构'
      }],
      Recommended: [{
        value: '选项1',
        label: '全部'
      }, {
        value: '选项2',
        label: '为推荐的'
      }, {
        value: '选项3',
        label: '不为推荐的'
      }],
      selectedType:'',
      selectedDetailedType:'',
      selectedRecommended:'',
      //初始隐藏两个表单
      dialogVisible2: false,
      dialogVisible1: false,
      currentPage: 1,
      imagesPerPage: 10,
      showModal: false,
      selectedImage: null,
      activeName:'video',
      images: [],
      formData:[],
      recommendedValue1:false,
      recommendedValue2:false,
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
    for (let i = 1; i <= 20; i++) {
      this.images.push({
        id: i,
        src: `/images/copybook/${i}.jpg`,
        title: `图片标题${i}`,
        description: `图片描述${i}`,
      });
    }
  },

  methods: {

    //点击编辑按钮跳出弹窗填充数据
    handleEdit1(image) {
      // 将选中的图片数据赋值给 selectedImage
      this.selectedImage = image;

      this.dialogVisible1 = true;
    },
    //点击编辑按钮跳出弹窗填充数据
    handleEdit2(image) {
      // 将选中的图片数据赋值给 selectedImage
      this.selectedImage = image;

      this.dialogVisible2 = true;
    },

    //编辑用户弹窗确定提交的方法
    handleSubmit1() {
      // 在此处处理删除逻辑

      this.dialogVisible1 = false; // 关闭弹窗
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


  },

};
</script>

<style scoped>
.image-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-template-rows: repeat(2, auto);
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
.clearfix::after {
  display: table;
  clear: both;
  content: "";
}

.image-preview {
  width: 100%;
  height: 200px; /* 或您希望的任何高度 */
  object-fit: cover;
}

.text-right {
  text-align: right;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
