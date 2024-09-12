<script>
// 引入 wangEditor
export default {
  data () {
    return {
      textContent:'',
      formModel: {
        name: '',
        composing: '横版',
        fontId: '',
        difficulty: 0,
        idArray:[],
      },
      fontOptions: [],
      imgArray:[],
      composingOptions: [
        {
          label: '竖版',
          value: '竖版'
        },
        {
          label: '横版',
          value: '横版'
        }
      ],
      visible: false,
    }
  },

  mounted(){
    this.getFont();
  },
  watch:{
    textContent:{
      handler(newVal){
        this.request.get("/system-template/get-picture",{
        params:{
          textContent:newVal,
          fontId:this.formModel.fontId,
          composing:this.formModel.composing
        }
      }).then(res=>{
        if(res.code=='200'){
          this.imgArray=res.data;
          console.log(this.imgArray);
        }else{
          this.$message.error('获取在线预览图失败，原因：'+res.msg);
        }
      })
      },
      immediate: true // 立即执行一次
    },
  },
  methods: {
    //获取字体
    getFont(){
      this.request.get("/font/fonts").then(res=>{
        if(res.code=='200'){
          this.fontOptions=res.data;
        }else{
          this.$message.error('获取全部字体数据失败，原因：'+res.msg);
        }

      })
    },
    handleLayoutChange() {
      // 处理布局变化
      if (this.formModel.composing === '横板') {
        this.$el.querySelector('.image-gallery').style.flexDirection = 'column';
      } else {
        this.$el.querySelector('.image-gallery').style.flexDirection = 'row';
      }
      
      this.getOnlinePicture();
      
    },
    //获取在线预览图片
    getOnlinePicture(){
      this.request.get("/system-template/get-picture",{
        params:{
          textContent:this.textContent,
          fontId:this.formModel.fontId,
          composing:this.formModel.composing
        }
      }).then(res=>{
        if(res.code=='200'){
          this.imgArray=res.data;
          console.log(this.imgArray);
        }else{
          this.$message.error('获取在线预览图失败，原因：'+res.msg);
        }
      })
    },
    //获得字的id
    submitAdd(){
      this.formModel.idArray = this.imgArray.map(matrix =>
        matrix.map(row =>
          row.map(item => item.id)
        )
      );
      this.request.post("/system-template/comprehensive-add",this.formModel).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增系统模板成功！');
          this.textContent = '';
          this.imgArray = [];
        } else {
          this.$message.error('新增系统模板失败，原因：' + res.msg);
        }
      })
    },
  }
}
</script>

<template>
  <div class="box">
    <div class="top">
      <el-input
        v-model="formModel.name"
        placeholder="请输入模板名称"
        style="width: 240px"
      ></el-input>
      <el-select
        v-model="formModel.composing"
        size="large"
        style="width: 240px"
        @change="handleLayoutChange">
        <el-option
          v-for="item in composingOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"/>
      </el-select>
      <el-select v-model="formModel.fontId" size="large" style="width: 240px">
        <el-option
          v-for="item in fontOptions"
          :key="item.id"
          :label="item.name"
          :value="item.id"
          @change="getOnlinePicture"/>
      </el-select>
      难度：<el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <el-input type="textarea" v-model="textContent" style="height: 250px;"></el-input>
      </div>
      <div class="right">
        <div class="image-gallery">
          <div v-for="(matrix, matrixIndex) in imgArray" :key="matrixIndex" >
            <div v-if="formModel.composing === '竖版'" class="image-matrix">
              <div v-for="(colIndex, colIndexKey) in matrix" :key="colIndexKey" class="image-col">
                <div v-for="(row, rowIndex) in colIndex" :key="rowIndex" class="image-row">
                  <img v-if="row && row.content" :src="row.content" :alt="`Image ${matrixIndex}-${rowIndex}-${colIndex}`" />
                  <span v-else>{{ `Content not found at row ${row[colIndex]}, col ${colIndex}` }}</span>
                </div>
              </div>
            </div>
            <div v-else-if="formModel.composing === '横版'" >
              <div v-for="(row, rowIndex) in matrix" :key="rowIndex" class="image-row">
                <div v-for="(image, colIndex) in row" :key="colIndex" class="image-col">
                  <img :src="image.content" :alt="`Image ${matrixIndex}-${rowIndex}-${colIndex}`" />
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
    <el-button type="primary" @click="submitAdd()" style="margin-left:85%; margin-top: 50px;">确定生成</el-button>
  </div>
</template>
<style lang="scss" scoped>
.box {
  border: white 1px solid;
  .top {
    display: flex;
    justify-content: space-around;
  }
  .main {
    display: flex;
    justify-content: space-around;
    margin: 20px;
    .left {
      width: 45%;
    }
    .right {
      width: 45%;
      border: black 1px solid;
    }
  }
  .footer {
    text-align: center;
  }
  img {
    max-width: 50px;
    max-height: 50px;
  }
  .image-matrix {
    display: flex;
  }

  .image-row {
    display: flex;
    flex-direction: row;
  }

  .image-col {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
  }


}
</style>
