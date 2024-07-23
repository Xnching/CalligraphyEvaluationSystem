<template>
  <div class="academic-calendar">
    <div class="header">
      <h2>当前年度: {{ aYear }}年</h2>
      <el-button type="success" @click="editing ? toggleEditing() : handleEdit()">
        {{ editing ? '确认' : '编辑' }}</el-button>
    </div>
    <div class="semesters">
      <div class="semester">
        <h3>第一学期</h3>
        <p>
          <span class="label">开始时间:</span>
          <el-date-picker v-model="firstSemesterStart" type="date" placeholder="选择日期" :picker-options="{
              disabledDate(time) {
                const currentYear = new Date().getFullYear();
                return time.getFullYear() < currentYear - 1 || time.getFullYear() > currentYear + 1;
              }
            }" v-if="editing" @change="handleDateChange('firstSemesterStart')"></el-date-picker>
          <span v-if="!editing">{{ formatDate(firstSemesterStart) }}</span>
        </p>
        <p>
          <span class="label">结束时间:</span>
          <el-date-picker v-model="firstSemesterEnd" type="date" placeholder="选择日期" :picker-options="{
              disabledDate(time) {
                const currentYear = new Date().getFullYear();
                return time.getFullYear() < currentYear - 1 || time.getFullYear() > currentYear + 1;
              }
            }" v-if="editing" @change="handleDateChange('firstSemesterEnd')"></el-date-picker>
          <span v-if="!editing">{{ formatDate(firstSemesterEnd) }}</span>
        </p>
      </div>
      <div class="semester">
        <h3>第二学期</h3>
        <p>
          <span class="label">开始时间:</span>
          <el-date-picker v-model="secondSemesterStart" type="date" placeholder="选择日期" :picker-options="{
              disabledDate(time) {
                const currentYear = new Date().getFullYear();
                return time.getFullYear() < currentYear - 1 || time.getFullYear() > currentYear + 1;
              }
            }" v-if="editing" @change="handleDateChange('secondSemesterStart')"></el-date-picker>
          <span v-if="!editing">{{ formatDate(secondSemesterStart) }}</span>
        </p>
        <p>
          <span class="label">结束时间:</span>
          <el-date-picker v-model="secondSemesterEnd" type="date" placeholder="选择日期" :picker-options="{
              disabledDate(time) {
                const currentYear = new Date().getFullYear();
                return time.getFullYear() < currentYear - 1 || time.getFullYear() > currentYear + 1;
              }
            }" v-if="editing" @change="handleDateChange('secondSemesterEnd')"></el-date-picker>
          <span v-if="!editing">{{ formatDate(secondSemesterEnd) }}</span>
        </p>
      </div>
    </div>
    <div class="current-semester">
      <h2>{{ currentSemester }}</h2>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    const currentYear = new Date().getFullYear();
    return {
      editing: false,
      aYear: currentYear,
      semester: '',
      firstSemesterStart: new Date(2024, 0, 1),  // 月份是从0开始的，所以9月是8
      firstSemesterEnd: new Date(2024, 6, 11),
      secondSemesterStart: new Date(2024, 7, 14),
      secondSemesterEnd: new Date(2024, 11, 1),
    }
  },
  computed: {
    currentSemester() {
      const now = new Date();
      if (this.isSameOrAfter(now, this.firstSemesterStart) && this.isSameOrBefore(now, this.firstSemesterEnd)) {
        return `当前学期: ${this.aYear-1 }年- ${this.aYear}年第二学期`;
      } else if (this.isSameOrAfter(now, this.secondSemesterStart) && this.isSameOrBefore(now, this.secondSemesterEnd)) {
        return `当前学期: ${this.aYear}年- ${this.aYear+1}年第一学期`;
      } else {
          return '当前不在学期内';
      }


    }
  },
  created(){
    this.load();
  },
  methods: {
    handleEdit(){
      this.editing=true;
    },
    toggleEditing() {
      this.editing = !this.editing;
      if (!this.editing) {
        // 确认按钮被点击，发送请求到后端
        const academicYear1 = {
          id:1,
          firstStart: this.firstSemesterStart.toISOString(),
          firstEnd: this.firstSemesterEnd.toISOString(),
          secondStart: this.secondSemesterStart.toISOString(),
          secondEnd: this.secondSemesterEnd.toISOString()
        };
        console.log(academicYear1);
        // 这里发送请求到后端，将 academicYear 发送到后端
        this.request.post("/academic-year/update",academicYear1).then(res=>{
          if(res.code == '200'){
            this.$message.success('修改日期数据成功！');
            this.load();
          } else {
            this.$message.error('修改日期数据失败，原因：' + res.msg);
          }
        })
      }
    },
    isSameOrAfter(date1, date2) {
      const d1 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate());
      const d2 = new Date(date2.getFullYear(), date2.getMonth(), date2.getDate());
      return d1.getTime() >= d2.getTime();
    },
    isSameOrBefore(date1, date2) {
      const d1 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate());
      const d2 = new Date(date2.getFullYear(), date2.getMonth(), date2.getDate());
      return d1.getTime() <= d2.getTime();
    },
    formatDate(date) {
      return `${date.getMonth() + 1}月${date.getDate()}日`;
    },
    //获取每个学期的开始与结束时间
    load(){
      this.request.get("/academic-year/page").then(res=>{
        if(res.code == '200'){
          this.firstSemesterStart=new Date(res.data.firstStart);
          this.firstSemesterEnd=new Date(res.data.firstEnd);
          this.secondSemesterStart=new Date(res.data.secondStart);
          this.secondSemesterEnd=new Date(res.data.secondEnd);
        } else {
          this.$message.error('获取日期数据失败，原因：' + res.msg);
        }
      })

    }
  }
}
</script>

<!-- style部分保持不变 -->

  
  <style scoped>
  .academic-calendar {
    font-family: Arial, sans-serif;
    border: 1px solid #ccc;
    padding: 20px;
    border-radius: 5px;
  }
  
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .semesters {
    display: flex;
    gap: 20px;
  }
  
  .semester {
    flex: 1;
    border: 1px solid #eee;
    padding: 15px;
    border-radius: 5px;
  }
  
  .semester h3 {
    margin-top: 0;
  }
  
  .label {
    font-weight: bold;
  }
  
  .current-semester {
    margin-top: 20px;
    text-align: center;
    font-size: 1.2em;
  }
  </style>