<template>
  <div>
    <!--视频详情的弹窗-->
    <el-dialog
      title="书法知识-视频详情"
      :visible.sync="dialogVisible"
      width="50%"
      destroy-on-close
      @close="dialogVisible = false">
        <el-form :model="videoData">
          <h1>视频内容：</h1>
          <el-form-item >
            <video-player
              ref="videoPlayer"
              :playsinline="true"
              :options="playerOptions"
            ></video-player>

          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="视频标题：">
                <el-input v-model="videoData.name" :disabled="!isEditing"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="添加人：">
                <el-input v-model="videoData.importer" :disabled="!isEditing"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="书法知识类型：">
                <el-select v-model="videoData.firstTypeId"  @change="onFirstChange" :disabled="!isEditing">
                  <el-option label="教学指导" value="1"></el-option>
                  <el-option label="书法知识" value="4"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="标签：">
                <el-input v-model="videoData.tag" :disabled="!isEditing"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="详细类型：">
                <el-select v-model="videoData.secondTypeId" v-if="secondTypes.length" :disabled="!isEditing">
                  <el-option v-for="type in secondTypes" :key="type.id" :label="type.name" :value="type.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col>
              <el-switch
                style="display: block"
                v-model="videoData.isRecommended"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="推荐"
                inactive-text="不推荐"
                :disabled="!isEditing">
              </el-switch>
            </el-col>
          </el-row>
        </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>

        <el-button type="primary" @click="isEditing ? handleSubmit1() : handleEdit2()">
          {{ isEditing ? '确定' : '编辑' }}
        </el-button>
        <el-button type="danger" @click="handleDelete()">删除</el-button>
      </span>
    </el-dialog>


    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px"
                  suffix-icon="el-icon-search"
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:50px" type="primary" @click="Search_table">搜索</el-button>

        <!--下面是属性选择器 ，可以手动输入搜索-->
        <el-select v-model="selectedSecondType" filterable placeholder="选择详细类型" @change="selectChange"  style="width: 150px;margin-right: 20px;">
          <el-option
              v-for="item in secondTypes"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
        <el-select v-model="selectedRecommended" filterable placeholder="是否为推荐" @change="selectChange" style="width: 150px;margin-right: 20px;">
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
        <div v-for="image in images" :key="image.id" class="image-item">
          <img :src="image.pictureUrl" :alt="image.name" @click="handleEdit1(image)">
          <p style=" text-align: center;">{{ image.name }}</p> <!-- 添加这一行来显示图片标题 -->
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
    </div>
  </div>
</template>
<script>

import { videoPlayer } from "vue-video-player";
import 'video.js/dist/video-js.css'

export default {
  name:"VideoTab",
  components: {videoPlayer},
  data() {
    return {
      inputVal:"",
      secondTypes: [],
      Recommended: [ {
        value: true,
        label: '为推荐的'
      }, {
        value: false,
        label: '不为推荐的'
      }],
      selectedSecondType:'',
      selectedRecommended:null,
      //初始隐藏两个表单
      dialogVisible: false,
      images: [],
      videoData:{
        id:'',
        name:'',
        firstTypeId:'',
        secondTypeId:'',
        tag:'',
        isRecommended:'',
        content:'',
        importer:'',
        pictureUrl:'',
      },
      isEditing:false,
      pageNum:1,
      pageSize:20,
      total:0,
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0],
        autoplay: false,
        muted: false,
        loop: false,
        preload: "auto",
        language: "zh-CN",
        aspectRatio: "16:9",
        fluid: true,
        sources: [
          {
            type: "video/mp4",
            src: "",
          },
        ],
        poster: "", // 封面地址
        notSupportedMessage: "加载中......", //允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true, // 当前时间和持续时间的分隔符
          durationDisplay: true, // 显示持续时间
          remainingTimeDisplay: true, // 是否显示剩余时间功能
          fullscreenToggle: true, // 是否显示全屏按钮
        },
      },

    };
  },
  created(){
    this.load();
    this.getAllSecond();
  },
  

  methods: {
    Search_table(){
      this.load();
    },
    //点击图片跳出弹窗
    handleEdit1(image) {
      // 将选中的图片数据赋值给 selectedImage
      this.videoData = image;
      this.playerOptions.sources[0].src = image.content;
      this.dialogVisible = true;
      this.isEditing=false;
    },
    //开始编辑
    handleEdit2() {
      // 将选中的图片数据赋值给 selectedImage
      this.isEditing=true;
    },

    //弹窗的确定提交的方法
    handleSubmit1() {
      this.request.put("/video/update",this.videoData).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改视频数据成功！');
          this.load();
        } else {
          this.$message.error('修改视频数据失败，原因：' + res.msg);
        }
      })
      this.isEditing = false;
    },
    handleDelete(){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
				//console.log("看看是否有id"+this.videoData.id);
        this.request.put('/video/delete', {
          id: this.videoData.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除视频数据成功！');
            this.load();
						this.isEditing=false;
      			this.dialogVisible = false; // 关闭弹窗
          }else{
            this.$message.error('删除视频数据失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });

    },

    //分页用的功能
    handleCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      this.load();
    },
    handleSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      this.load();
    },
    onFirstChange(){
      const firstTypeId = parseInt(this.videoData.firstTypeId,10);
      this.getSecond(firstTypeId);
      this.videoData.secondTypeId='';
    },
    selectChange(){
			this.load();
		},
    //请求分页查询数据
    load(){
      this.request.get("/video/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
					secondTypeId:this.selectedSecondType,
					isRecommended:this.selectedRecommended === true // 确保传递布尔值
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.images=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部视频数据失败，原因：'+res.msg);
        }
      })
    },
    getSecond(val){
      console.log("打印下值多少"+val);
      this.request.get("/calligraphy-facts/seconds",{
        params: {
          id: val
        }
      }).then(res => {
        if(res.code == '200'){
          this.secondTypes= res.data;
        }else{
          this.$message.error('获取二级类型失败，原因：'+res.msg);
        }
      });
      return null;
    },
    getAllSecond(){
      this.request.get("/calligraphy-facts/all-seconds").then(res => {
        if(res.code == '200'){
          this.secondTypes=res.data;
        }else{
          this.$message.error('获取二级类型失败，原因：'+res.msg);
        }
      });
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
  height: 200px; 
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
