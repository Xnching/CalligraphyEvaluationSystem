<template>
    <div>
        <h1>这是一个主页</h1>
        <WangEditor></WangEditor>
        
    </div>
</template>
<script>
import WangEditor from '@/components/wangEditor.vue';
export default {
    name:"Home",
    components:{
        WangEditor
    },
    created(){
        this.load();
    },
    data(){
        return{
            valueHtml:'',
        }
    },
    methods:{
        load(){
            this.valueHtml="<p>测试<p>";
        },
    }
}
</script>

<style scoped>

</style>
