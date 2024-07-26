<template>
    <div>

        <!--用来搜索字的搜索框-->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
            <el-input style="width:300px " 
            suffix-icon="el-icon-search" 
            placeholder="请输入要查找的字名"
            v-model="inputVal"
            @keyup.enter.native="Search_table()"
            clearable>
            </el-input>
            <el-button style="margin-left:20px ;margin-right:535px" type="primary">搜索</el-button>
        </div>
      <!-- 下面为表格的筛选-->
      <div style="text-align: left;margin-left: 30px;margin-top: 10px;display: flex">
        
        <!--下面是属性选择器 ，可以手动输入搜索-->
        <el-select v-model="selectedStructure" filterable placeholder="请选择结构" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in structureOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
        </el-select>
        <el-select v-model="selectedEccentricity" filterable placeholder="请选择偏旁部首" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in eccentricityOptions"
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
        <el-select v-model="selectedSource" filterable placeholder="请选择来源" style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in sourceOptions"
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
        <el-button type="primary" @click="handleEdit1" style="margin-left: auto; margin-right: 120px;">新增样本字<i class="el-icon-circle-plus"></i></el-button>
      </div>
        
        <!--下面是图片展示区域-->  
        <div class="image-grid"> 
            <div v-for="image in paginatedImages" :key="image.id" class="image-item">
                <img :src="image.src" :alt="image.title" @click="handleEdit2(image)">
            </div>
        </div>

        <!-- 分页栏-->
        <div style="padding:10px">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[20, 25, 30, 40]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

      

        <!-- 新增样本字的弹窗 -->
        <el-dialog
        title="请导入新增样本字的信息"
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
                        <el-form-item label="结构：">
                            <el-select v-model="addForm.selectedStructure" filterable placeholder="请选择结构" style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in structureOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="偏旁部首：">
                            <el-select v-model="addForm.selectedEccentricity" filterable placeholder="请选择偏旁部首" style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in eccentricityOptions"
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
                        <el-form-item label="来源：">
                            <el-input v-model="addForm.source"></el-input>
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
                        <div class="el-upload__text">将需要录入的字、偏旁部首的图片拖到此处，或<em>点击上传</em></div>
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
                        <div class="el-upload__text">将需要录入的字、偏旁部首的图片拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                    <el-upload
                    class="upload-demo"
                    drag
                    action="https://jsonplaceholder.typicode.com/posts/"
                    multiple
                    :limit="1"
                    accept=".xls,.xlsx">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__tip" slot="tip">Excel文件格式以对应字文件名，字名，结构，部首，年级，来源</div>
                        <div class="el-upload__text">将需要录入的字、偏旁部首的对应的属性Excel拖到此处，或<em>点击上传</em></div>
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
        title="该样本字详细信息"
        :visible.sync="dialogVisible2"
        width="30%"
        :close-on-click-modal="false" 
        style=" text-align: center;">
            <img v-if="selectedImage" :src="selectedImage.src" :alt="selectedImage.title" style="width: 100%;">
            <el-form :model="editForm" label-width="120px">
                <el-form-item label="样本字名：">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <el-form-item label="结构所属：">
                    <el-input v-model="editForm.structure"></el-input>
                </el-form-item>
                <el-form-item label="偏旁部首拥有：">
                    <el-input v-model="editForm.eccentricity"></el-input>
                </el-form-item>
                <el-form-item label="年级：">
                    <el-input v-model="editForm.grade"></el-input>
                </el-form-item>
                <el-form-item label="来源：">
                    <el-input v-model="editForm.source"></el-input>
                </el-form-item>
                <el-form-item label="导入人：">
                    <el-input v-model="editForm.sourceMan"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button type="danger" @click="handleSubmit2()">删除</el-button>
            </span>
            </template>
        </el-dialog>

    

    </div>
</template>


<script>
export default {
    data() {
        return {
            eccentricityOptions: [{
            value: '选项1',
            label: '草字头'
            }, {
            value: '选项2',
            label: '三点水'
            }, {
            value: '选项3',
            label: '绞丝旁'
            }],
            structureOptions: [{
            value: '选项1',
            label: '上下结构'
            }, {
            value: '选项2',
            label: '左右结构'
            }, {
            value: '选项3',
            label: '前后结构'
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
            sourceOptions: [{
            value: '选项1',
            label: '学生作品'
            }, {
            value: '选项2',
            label: '竞赛作品'
            }, {
            value: '选项3',
            label: '社会取得'
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
            addForm:{
                name:'',
                selectedEccentricity:'',
                selectedStructure:'',
                selectedGrade:'',
                source:'',
            },
            selectedEccentricity:'',
            selectedStructure:'',
            selectedGrade:'',
            selectedSource:'',
            selectedSourceMan:'',
            //初始隐藏两个表单
            dialogVisible1: false,
            dialogVisible2: false,
            currentPage: 1,
            imagesPerPage: 20,
            showModal: false,
            selectedImage: null,
            
            images: [],
        
            editForm:{
                name:'佰',
                structure:'左右结构',
                grade:'五年级',
                eccentricity:'单人旁',
                source:'学生作品',
                sourceMan:'孙昭展',
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

    
  },
  
};
</script>

<style scoped>
.image-grid { 
  display: grid;
  grid-template-columns: repeat(5, 1fr); 
  grid-template-rows: repeat(4, auto);   
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
</style>
