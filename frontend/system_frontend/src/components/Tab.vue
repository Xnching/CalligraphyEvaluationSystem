
<!-- 标签页组件  -->
<template>
    <div class="tab" @wheel="handleScroll">
        <ul ref="tab">
            <router-link v-for="(item,index) in list"
                         :key="index"
                         :to="item.path"
                         tag="li"
                         :class="{active:item.active}">
                {{ item.name }}
                <i class="el-icon-close" v-if="item.path!=='/'" @click.stop="closeTab(index)"></i>
            </router-link>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "tab",
        watch: {
            // 监听路由变化
            // active 当前项高亮组合
            $route(to, from) {
                // 将页签列表每个页签的状态设置为false
                this.list.forEach(item => {
                    item.active = false
                })
                // 组装当前路由对象
                let f = {
                    active: false,
                    name: to.name,
                    path: to.path
                }
                // 检测路由对象是否存在于页签数组
                let flag = this.list.find(item => {
                    return JSON.stringify(item) == JSON.stringify(f)
                })

                // 如果不存在，为undefined，则将状态设置为true
                if (!flag) {
                    f.active = true
                    this.list.push(f)
                } else { // 如果存在，则将此项状态设置为true
                    flag.active = true
                }
            }
        },
        data() {
            return {
                // 页签对象数组
                list: [],
            }
        },
        methods: {
            // 关闭点击标签页
            closeTab(index) {
                // 当前页面地址
                let nowPath = this.$route.path
                // 点击的页面的地址
                let path = this.list[index].path
                // 删除点击的页签
                this.list.splice(index, 1)
                // 关闭页面是当前页面
                if (nowPath === path) {
                    // 跳转上一个页面
                    let toPath = this.list[index - 1].path
                    this.$router.push({path: toPath})
                }
            },
            //标签页上下滚动改成左右滚动
            handleScroll(event) {
                event.preventDefault();
                const scrollAmount = event.deltaY;
                const container = this.$refs.tab;
                container.scrollLeft += scrollAmount;
            }
        },
        created() {
            // 初始化页签
            this.list.push({
                active: true,
                name: this.$route.name,
                path: this.$route.path
            })
        }
    }
</script>

<style scoped lang="less">
    .tab {
        width: 100%;
        background-color: #fff;
        line-height: 30px;
        text-align: left;
        margin-bottom: -10px;
        margin-top: -20px;
        ::-webkit-scrollbar{/*隐藏滚动条但是保留滚动*/ 
            display: none;
        }
        ul {
            display: flex;
            align-items: center;
            justify-content: flex-start;
            overflow-x: auto; /* 添加水平滚动效果 */
            white-space: nowrap; /* 防止标签换行 */
            li {
                margin-right: 20px;
                cursor: pointer;
                padding-left: 10px;
                padding-right: 10px;
                border: 1px solid #ccc;
                transition: all .2s ease-in-out;
                &.active {
                    border: 1px solid #42b983;
                }
            }
        }
    }

</style>