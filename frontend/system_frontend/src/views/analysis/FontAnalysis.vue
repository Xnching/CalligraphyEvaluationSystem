<template>
<div>
 <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="样本字分析" name="first">
<div style="display: flex;">
  <div style="padding-right: 30px;border-right: 1px solid black;display: flex;flex-direction: column;justify-content: space-around">
    <div>
      <div>
        <span>样本字使用排行榜</span>
        <el-dropdown>
          <span class="el-dropdown-link">
            下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>总榜</el-dropdown-item>
            <el-dropdown-item>年榜</el-dropdown-item>
            <el-dropdown-item>月榜</el-dropdown-item>
            <el-dropdown-item>日榜</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-table :data="rankList">
        <el-table-column prop="rank" label="排行"></el-table-column>
        <el-table-column prop="id" label="编号"></el-table-column>
        <el-table-column prop="character" label="字"></el-table-column>
        <el-table-column prop="usageCount" label="使用次数"></el-table-column>
      </el-table>
    </div>
    <div>
      <div>
        <span>样本字使用排行榜</span>
        <el-dropdown>
          <span class="el-dropdown-link">
            下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>总榜</el-dropdown-item>
            <el-dropdown-item>年榜</el-dropdown-item>
            <el-dropdown-item>月榜</el-dropdown-item>
            <el-dropdown-item>日榜</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <el-table :data="rankList">
        <el-table-column prop="rank" label="排行"></el-table-column>
        <el-table-column prop="id" label="编号"></el-table-column>
        <el-table-column prop="character" label="字"></el-table-column>
        <el-table-column prop="usageCount" label="使用次数"></el-table-column>
      </el-table>
    </div>
  </div>
  <div style="display: flex;justify-content: space-between;flex-wrap: wrap;">
    <div id="chart1" style="width: 600px;height:400px;"></div>
    <div id="chart2" style="width: 600px;height:400px;"></div>
  </div>
</div>
   </el-tab-pane>
    <el-tab-pane label="模板字分析" name="second">
             <el-container>
          <el-main>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 模板字常用字排行
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData3"
                      style="width: 100%">
                      <el-table-column
                      prop="rank1"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id1"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name1"
                      label="字"
                      width="65">
                      </el-table-column>
                      <el-table-column
                      prop="count1"
                      label="使用次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <div id="chart5" style="width: 600px;height:400px;"></div>
           </el-col>
            <el-col :span="100">
              <div id="chart6" style="width: 600px;height:400px;"></div>
           </el-col>
           </el-row>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 各结构模板字数量排行
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData4"
                      style="width: 100%">
                      <el-table-column
                      prop="rank1"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id1"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name1"
                      label="结构名"
                      width="65">
                      </el-table-column>
                      <el-table-column
                      prop="count1"
                      label="使用次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                  录入模板字占常用字比例
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="99" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
          <el-col :span="100">
              <el-container>
                <el-header>
                  录入部首所占比占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="75" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
            <el-col :span="100">
              <div id="chart3" style="width: 600px;height:400px;"></div>
           </el-col>
            <el-col :span="100">
              <div id="chart4" style="width: 600px;height:400px;"></div>
           </el-col>
           </el-row>
          </el-main>
         </el-container>
             </el-tab-pane>   
        <el-tab-pane label="字帖与分类分析" name="third">
          </el-tab-pane>  
          <el-container>
          <el-main>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 系统字帖最受欢迎榜
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData5"
                      style="width: 100%">
                      <el-table-column
                      prop="rank1"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id1"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name1"
                      label="字帖名"
                      width="65">
                      </el-table-column>
                      <el-table-column
                      prop="count1"
                      label="使用次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                  专项练习占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="25" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                  综合练习占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="75" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
           </el-row>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 系统字帖促优效果榜单
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData6"
                      style="width: 100%">
                      <el-table-column
                      prop="rank1"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id1"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name1"
                      label="字帖名"
                      width="65">
                      </el-table-column>
                      <el-table-column
                      prop="count1"
                      label="使用次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                  楷书-言清治占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="25" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                  楷书-水波占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="75" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
           <el-col :span="100">
              <el-container>
                <el-header>
                 草书占比
                </el-header> 
                <el-main>
                  <el-progress type="circle":percentage="75" ></el-progress>
                </el-main>
              </el-container> 
           </el-col>
           </el-row>
          </el-main>
         </el-container>
        </el-tabs>
        <div style="display: flex;justify-content: space-between;flex-wrap: wrap;">
  </div>

        
</div>
</template>
<script>
import * as echarts from 'echarts';
export default {
      data() {
        return {
          rankList: [
            { rank: 1, id: '001', character: '和', usageCount: 120 },
            { rank: 2, id: '002', character: '爱', usageCount: 110 },
            { rank: 3, id: '003', character: '平', usageCount: 105 },
            { rank: 4, id: '004', character: '安', usageCount: 100 },
            { rank: 5, id: '005', character: '乐', usageCount: 95 },
          ],
          tableData1: [{
            rank1: '1',
            id1: '1234',
            name1: '南',
            count1: '333'
          }, {
            rank1: '2',
            id1: '1235',
            name1: '京',
            count1: '444'
          }, {
            rank1: '3',
            id1: '1236',
            name1: '信',
            count1: '773'
          }, {
            rank1: '4',
            id1: '1237',
            name1: '息',
            count1: '555'
          }, {
            rank1: '5',
            id1: '1238',
            name1: '工',
            count1: '345'
          }],
          tableData2: [
            { rank1: '1', id1: '1234', name1: '上下', count1: '999' },
            { rank1: '2', id1: '1235', name1: '左右', count1: '998' },
            { rank1: '3', id1: '1236', name1: '品字', count1: '997' },
            { rank1: '4', id1: '1237', name1: '半包围', count1: '996' },
            { rank1: '5', id1: '1238', name1: '独体字', count1: '995' },
            { rank1: '6', id1: '1239', name1: '全包围', count1: '994' },
          ],
          structuredTableData: [],
          sampleUsageData: [
            { date: '2023-01-01', count: 120 },
            { date: '2023-01-02', count: 132 },
            { date: '2023-01-03', count: 101 },
            { date: '2023-01-04', count: 134 },
            { date: '2023-01-05', count: 90 },
            { date: '2023-01-06', count: 230 },
            { date: '2023-01-07', count: 210 },
          ],
          calligraphyUsageData: [
            { date: '2023-01-01', count: 150 },
            { date: '2023-01-02', count: 230 },
            { date: '2023-01-03', count: 224 },
            { date: '2023-01-04', count: 218 },
            { date: '2023-01-05', count: 135 },
            { date: '2023-01-06', count: 147 },
            { date: '2023-01-07', count: 260 },
          ],
          tableData3: [{
            rank1:'1',
            id1:'1234',
            name1:'南',
            count1:'999'
          }, {
            rank1:'2',
            id1:'1235',
            name1:'京',
            count1:'998'
          },{
            rank1:'3',
            id1:'1236',
            name1:'信',
            count1:'997'
          },{
            rank1:'4',
            id1:'1237',
            name1:'息',
            count1:'996'
          },{
            rank1:'5',
            id1:'1238',
            name1:'工',
            count1:'995'
          },
          ],
          tableData4: [{
            rank1:'1',
            id1:'1234',
            name1:'上下',
            count1:'999'
          }, {
            rank1:'2',
            id1:'1235',
            name1:'左右',
            count1:'998'
          },{
            rank1:'3',
            id1:'1236',
            name1:'品字',
            count1:'997'
          },{
            rank1:'4',
            id1:'1237',
            name1:'半包围',
            count1:'996'
          },{
            rank1:'5',
            id1:'1238',
            name1:'独体字',
            count1:'995'
          },
          ],
          tableData5: [{
            rank1:'1',
            id1:'1234',
            name1:'南',
            count1:'999'
          }, {
            rank1:'2',
            id1:'1235',
            name1:'京',
            count1:'998'
          },{
            rank1:'3',
            id1:'1236',
            name1:'信息',
            count1:'997'
          },{
            rank1:'4',
            id1:'1237',
            name1:'工程',
            count1:'996'
          },{
            rank1:'5',
            id1:'1238',
            name1:'大学',
            count1:'995'
          },
          ],
          tableData6: [{
            rank1:'1',
            id1:'1234',
            name1:'南',
            count1:'999'
          }, {
            rank1:'2',
            id1:'1235',
            name1:'京',
            count1:'998'
          },{
            rank1:'3',
            id1:'1236',
            name1:'信息',
            count1:'997'
          },{
            rank1:'4',
            id1:'1237',
            name1:'工程',
            count1:'996'
          },{
            rank1:'5',
            id1:'1238',
            name1:'大学',
            count1:'995'
          },
          ]
        }
      },
      created(){
        this.structuredTableData = this.getStructuredTableData();
      },
      mounted(){
        this.createChart();
      },
      methods: { 
      handleClick(tab, event) {
        console.log(tab, event);
         },

        createChart() {
          // 基于准备好的dom，初始化echarts实例
          const myChart1 = echarts.init(document.getElementById('chart1'));

          // 指定图表的配置项和数据
          const option1 = {
            title: {
              text: '样本字使用排行榜'
            },
            tooltip: {},
            xAxis: {
              type: 'category',
              data: this.tableData1.map(item => item.name1)
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: this.tableData1.map(item => item.count1),
              type: 'bar'
            }]
          };

          // 使用刚指定的配置项和数据显示图表。
          myChart1.setOption(option1);

          const myChart2 = echarts.init(document.getElementById('chart2'));
          const option2 = {
            title: {
              text: '字形结构分布',
              left: 'center'
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'vertical',
              left: 'left',
            },
            series: [
              {
                name: '字形结构',
                type: 'pie',
                radius: '50%',
                data: this.structuredTableData.map(item => ({
                  value: item.count1,
                  name: item.name1
                })),
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };
          myChart2.setOption(option2);

          var myChart3 = echarts.init(document.getElementById('chart3'));
          var option3 = {
            title: {
              text: '近期模板字使用次数',
              left: 'center'
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data: ['使用次数'],
              left: 'left',
            },
            xAxis: {
              type: 'category',
              data: this.sampleUsageData.map(item => item.date)
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              name: '使用次数',
              type: 'line',
              data: this.sampleUsageData.map(item => item.count)
            }]
          };
          myChart3.setOption(option3);

          var myChart4 = echarts.init(document.getElementById('chart4'));
          var option4 = {
            title: {
              text: '近期模板中模板字使用次数',
              left: 'center'
            },
            tooltip: {
              trigger: 'axis'
            },
            legend: {
              data: ['使用次数'],
              left: 'left',
            },
            xAxis: {
              type: 'category',
              data: this.calligraphyUsageData.map(item => item.date)
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              name: '使用次数',
              type: 'line',
              data: this.calligraphyUsageData.map(item => item.count)
            }]
          };
          myChart4.setOption(option4);

          const myChart5 = echarts.init(document.getElementById('chart5'));

          // 指定图表的配置项和数据
          const option5 = {
            title: {
              text: '各模板字平均分数'
            },
            tooltip: {},
            xAxis: {
              type: 'category',
              data: ['南','京','信','息','工','程']
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: ['96','88','93','78','92','85'],
              type: 'bar'
            }]
          };

          // 使用刚指定的配置项和数据显示图表。
          myChart5.setOption(option5);

          const myChart6 = echarts.init(document.getElementById('chart6'));
          const option6 = {
            title: {
              text: '使用模板字后分数提高比例',
              left: 'center'
            },
            tooltip: {},
            xAxis: {
              type: 'category',
              data: ['南','京','信','息','工','程']
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: ['5','7','5','11','5','7'],
              type: 'line'
            }]
          };
          myChart6.setOption(option6);
        },
        getStructuredTableData() {
          const order = ["上下", "左右", "品字", "半包围", "全包围", "独体字"];
          return order.map(structure => this.tableData2.find(item => item.name1 === structure)).filter(Boolean);
        }
      },
      }
</script>

<style scoped>

</style>
