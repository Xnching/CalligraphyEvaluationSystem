<script>
// 引入 wangEditor
import wangEditor from '@/components/wangEditor.vue'
export default {
  components:{
    wangEditor
  },
  data () {
    return {
      formModel: {
        name: '',
        composing: '横版',
        font: '楷书',
        difficulty: 0
      },
      fontOptions: [
        {
          value: '楷书',
          label: '楷书'
        },
        {
          value: '行书',
          label: '行书'
        },
        {
          value: '草书',
          label: '草书'
        },
        {
          value: '隶书',
          label: '隶书'
        }
      ],
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

  
  methods: {
    
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
      >
        <el-option
          v-for="item in composingOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-select v-model="formModel.font" size="large" style="width: 240px">
        <el-option
          v-for="item in fontOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <wangEditor></wangEditor>
      </div>
      <div class="right">
        <img src="" alt="这是左侧文字填写完后,后台排版后生成的一张图片" />
      </div>
    </div>
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
}
</style>
