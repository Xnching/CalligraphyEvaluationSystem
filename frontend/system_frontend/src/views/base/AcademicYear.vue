<template>
    <div class="academic-calendar">
      <div class="header">
        <h2>{{ academicYear }}</h2>
        <el-button type="success">编辑</el-button>
      </div>
      <div class="semesters">
        <div class="semester">
          <h3>第一学期</h3>
          <p>
            <span class="label">开始时间:</span>
            {{ firstSemesterStart }}
          </p>
          <p>
            <span class="label">结束时间:</span>
            {{ firstSemesterEnd }}
          </p>
        </div>
        <div class="semester">
          <h3>第二学期</h3>
          <p>
            <span class="label">开始时间:</span>
            {{ secondSemesterStart }}
          </p>
          <p>
            <span class="label">结束时间:</span>
            {{ secondSemesterEnd }}
          </p>
        </div>
      </div>
      <div class="current-semester">
        <h2>{{ semester }}</h2>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        academicYear: '当前年度: 2024年',
        semester: '当前学期: 2023年-2024年第一学期',
        firstSemesterStart: '9月1日',
        firstSemesterEnd: '1月11日',
        secondSemesterStart: '2月14日',
        secondSemesterEnd: '7月1日'
      }
    }
  }
  </script>
  
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