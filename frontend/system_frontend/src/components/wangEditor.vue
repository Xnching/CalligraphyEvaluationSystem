<script>
import '../../node_modules/@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import request from '@/utils/request';
export default {
  components: { Editor, Toolbar },
  setup() {
    const show=ref(true)
    // 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()

    // 内容 HTML
    const valueHtml = ref('')

    // 模拟 ajax 异步获取内容
    onMounted(() => {
      show.value=true
    })

    const toolbarConfig = {
      excludeKeys: [
        'insertVideo',
        'uploadVideo',
        'group-video']
    }
    const editorConfig = {
      placeholder: '请输入内容...',
      MENU_CONF: {
        // 所有的菜单配置，都要在 MENU_CONF 属性下
        // 配置上传图片
        uploadImage: {
          server: request.defaults.baseURL + '/file/image',
          // form-data fieldName，后端接口参数名称，默认值wangeditor-uploaded-image
          fieldName: "file",
          // 其他配置项
          maxFileSize: 10 * 1024 * 1024, // 2MB
          maxNumberOfFiles: 5,
          allowedFileTypes: ['image/jpeg', 'image/png', 'image/gif'],
          // 自定义上传参数
          meta: {
              token: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : ''
          },
          // 自定义上传头部
          headers: {
            'Authorization': 'Bearer ' + (localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : '')
          },
          // 上传成功后的回调函数
          onSuccess: (file, res) => {
              console.log('上传成功', res);
          },
          // 上传失败后的回调函数
          onFailed: (file, res) => {
              console.log('上传失败', res);
          },
          // 上传错误后的回调函数
          onError: (file, err, res) => {
              console.log('上传错误', err, res);
          }
        }
      }
    };

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {
      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor // 记录 editor 实例，重要！
    }

    const setHtml=(html)=>{
      
      const editor = editorRef.value
      
      editor.dangerouslyInsertHtml(html);
    }

    return {
      editorRef,
      valueHtml,
      mode: 'default', // 或 'simple'
      toolbarConfig,
      editorConfig,
      handleCreated,
      setHtml
    }
  }
}
</script>
<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      :mode="mode"
    />
    <Editor
      style="height: 500px; overflow-y: hidden"
      v-model="valueHtml"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="handleCreated"
    />
  </div>
</template>