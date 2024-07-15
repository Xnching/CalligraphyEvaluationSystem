<template>
    <div>
      <!-- 下面为表格的筛选-->
      <div style="text-align: left;margin-left: 30px;margin-top: 25px;display: flex">
        
        <!--下面是属性选择器 ，可以手动输入搜索-->
        <el-select v-model="selectedFont" filterable placeholder="请选择字体" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in fontoptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-select v-model="selectedGrade" filterable placeholder="请选择年级" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in gradeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-select v-model="selectedAuthor" filterable placeholder="请选择作者" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in authorOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-select v-model="selectedSourceMan" filterable placeholder="请选择导入人" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in sourceManOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-button type="primary" @click="handleEdit1" style="margin-left: auto; margin-right: 120px;">新增字帖<i class="el-icon-circle-plus"></i></el-button>
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

      

        <!-- 新增字帖的弹窗 -->
        <el-dialog
        title="请导入新增字帖的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <el-tabs type="border-card">
                <!--单个添加-->
                <el-tab-pane label="单个添加">
                    <el-form ref="addForm" :model="addForm" label-width="100px">
                        <el-form-item label="字名：">
                            <el-input v-model="addForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="字帖字体：">
                            <el-select v-model="addForm.selectedFont" filterable placeholder="请选择字体" style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in fontoptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="年级：">
                            <el-select v-model="addForm.selectedGrade" filterable placeholder="请选择年级"
                            style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in gradeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="作者：">
                            <el-input v-model="addForm.author"></el-input>
                        </el-form-item>
                    </el-form>

                    <el-upload
                    class="upload-demo"
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    multiple
                    :limit="1"
                    accept=".png,.jpeg,.jpg"
                    style=" text-align: center;">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将需要录入的字帖的图片拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                </el-tab-pane>

                <!--批量添加-->
                <el-tab-pane label="批量添加" style=" text-align: center;">
                    <el-upload
                    class="upload-demo"
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    multiple
                    accept=".png,.jpeg,.jpg">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将需要录入的字帖的图片拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                    <el-upload
                    class="upload-demo"
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    multiple
                    :limit="1"
                    accept=".xls,.xlsx">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__tip" slot="tip">Excel文件格式以对应字帖文件名，字帖名，字体，年级，作者</div>
                        <div class="el-upload__text">将需要录入的字帖的对应的属性Excel拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                </el-tab-pane>
            
            </el-tabs>
            

            <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit1()">确定</el-button>
            </span>
            </template>
        </el-dialog>

        <!-- 编辑按钮的弹窗 -->
        <el-dialog
        title="编辑该字帖详细信息"
        :visible.sync="dialogVisible2"
        width="40%"
        :close-on-click-modal="false" >
            <img v-if="selectedImage" :src="selectedImage.src" :alt="selectedImage.title" style="width: 100%;">
            <el-form :model="editForm" label-width="120px">
                <el-form-item label="字帖名：">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="字帖字体：">
                    <el-input v-model="editForm.font"></el-input>
                </el-form-item>
                
                <el-form-item label="年级：">
                    <el-input v-model="editForm.grade"></el-input>
                </el-form-item>
                <el-form-item label="作者：">
                    <el-input v-model="editForm.author"></el-input>
                </el-form-item>
                <el-form-item label="导入人：">
                    <el-input v-model="editForm.sourceMan"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible2 = false">点击取消</el-button>
                <el-button type="primary" @click="handleSubmit2()">确认编辑</el-button>
                <el-button type="danger" @click="handleSubmit3()">删除</el-button>
                
            </span>
            </template>
        </el-dialog>

    

    </div>
</template>


<script>
export default {
    data() {
        return {
            fontoptions: [{
            value: '选项1',
            label: '楷体-王国胜'
            }, {
            value: '选项2',
            label: '楷体-陆终至'
            }, {
            value: '选项3',
            label: '楷体-战易维'
            }],
            gradeOptions: [{
            value: '选项1',
            label: '一年级'
            }, {
            value: '选项2',
            label: '二年级'
            }, {
            value: '选项3',
            label: '三年级'
            }],
            authorOptions:[{
            value: '选项1',
            label: '王朔之'
            }, {
            value: '选项2',
            label: '李伟'
            }, {
            value: '选项3',
            label: '朴鞑法'
            }],
            sourceManOptions: [{
            value: '选项1',
            label: '孙大侠'
            }, {
            value: '选项2',
            label: '张二侠'
            }, {
            value: '选项3',
            label: '吕四侠'
            }],
            selectedFont:'',
            selectedGrade:'',
            selectedAuthor:'',
            selectedSourceMan:'',
            //初始隐藏两个表单
            dialogVisible1: false,
            dialogVisible2: false,
            currentPage: 1,
            imagesPerPage: 10,
            showModal: false,
            selectedImage: null,
            
            images: [],
            
            addForm:{
                name:'',
                author:'',
                selectedFont:'',
                selectedEccentricity:'',
                selectedStructure:'',
                selectedGrade:'',
            },
            editForm:{
                name:'佰',
                grade:'五年级',
                sourceMan:'孙昭展',
                author:'庆册一',
                font:'楷体-鞣天源',
            },

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
        for (let i = 1; i <= 13; i++) {
        this.images.push({
            id: i,
            src: `/images/copybook/${i}.jpg`,
            title: `图片标题${i}`,
            description: `图片描述${i}`,
        });
        }
    },

    methods: {


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
      // 在此处处理确认修改
      
      this.dialogVisible2 = false; // 关闭弹窗
    },

    //删除的提交方法
    handleSubmit3() {
      // 在此处处理删除
      
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
  max-width: 600px;  /*  设置图片最大宽度  */
  max-height: 800px; /*  设置图片最大高度  */
}
</style>
