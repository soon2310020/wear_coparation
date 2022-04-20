<template>

<div>
   <div id="page-wrapper">
  <div >
    <b-form class="row" >
     <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Loại sản phẩm</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>

      <b-form-group class="col-lg-6 col-md-12" >
        <b-form-input
          id="category_name_search"
          v-model="formSearch.nameSearch"
          placeholder="Nhập tên loại sản phẩm..."
        ></b-form-input>
      </b-form-group>
      <div  class="col-lg-6 col-md-12">
      <b-button type="button" variant="primary" @click="search">Tìm kiếm</b-button>
         <el-button type="button" @click="test ">Thêm mới</el-button>
      </div>
    </b-form>
  </div>
  <div>
       <el-table
      :data="items"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="Mã loại sản phẩm"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="Tên loại sản phẩm"
        width="180">
      </el-table-column>
      <el-table-column
        prop="description"
        label="Mô tả">
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      current-page.sync="5"
      :page-size="100"
      layout="total, prev, pager, next"
      :total="items.length">
    </el-pagination>
  </div>

   </div>
  <el-dialog title="Thêm mới loại sản phẩm" :visible.sync="dialogFormVisible">
  <el-form :model="categoryCreate">
    <el-form-item label="Tên:" :label-width="formLabelWidth">
      <el-input v-model="categoryCreate.name" autocomplete="off" ></el-input>
    </el-form-item>
    <el-form-item label="Mô tả:" :label-width="formLabelWidth">
        <ckeditor v-model="categoryCreate.description" :config="editorConfig"></ckeditor>
    </el-form-item>
     <el-form-item label="Ảnh :" :label-width="formLabelWidth">
<el-upload
  class="upload-demo"
    drag
   action
  :auto-upload="false"
  :on-change="handleAddFile"
  :on-preview="handlePreview"
  :on-remove="handleRemove"
  :file-list="fileList"
  multiple>
  <i class="el-icon-upload"></i>
  <div class="el-upload__text">Drop file here or <em>click to upload</em></div>
  <div class="el-upload__tip" slot="tip">jpg/png files with a size less than 500kb</div>
</el-upload>
     </el-form-item>
 </el-form>
<el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>
<el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">Hủy</el-button>
    <el-button type="primary" @click="create">Lưu</el-button>
  </span>
</el-dialog>
</div>
</template>

<script>
import axios from 'axios'

export default {
  created () {
    this.search()
    console.log('a')
  },
  methods: {
    handleAddFile (file, fileList) {
      if (file.name.includes('jpg') || file.name.includes('jpeg') || file.name.includes('png')) {
        this.fileList = fileList
      } else {
        fileList.splice(fileList.length - 1, 1)
      }
    },
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file, fileList) {
      console.log(file, fileList)
    },
    handleSizeChange (val) {
      this.size = val
    },
    handleCurrentChange (val) {
      this.page = val
    },
    test () {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    search () {
      axios.get(this.baseURL + `/categories/category?name=` + this.formSearch.nameSearch + `&page=${this.page}&size=${this.size}`, {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-type': 'application/json'
        }}).then(response => {
        this.items = response.data.content
      })
        .catch(e => {
          // this.errors.push(e);
        })
    },
    create () {
      let formData = new FormData()
      // for (var i = 0; i < this.listFile.length; i++) {
      //   let file = this.listFile[i]
      //   formData.append('files[' + i + ']', file)
      // }
      formData.append('files', this.fileList)
      formData.append('req', (this.categoryCreate))
      axios
        .post(this.baseURL + `/categories/category`, formData, {
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-type': 'multipart/form-data'
          }})
        .then(response => {
          this.dialogFormVisible = false
          this.search()
        })
        .catch(e => {
          // this.errors.push(e);
        })
    }
  },
  name: 'Forms',
  data () {
    return {
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      baseURL: 'http://localhost:9999/wear_shop/api',
      dialogFormVisible: false,
      items: [],
      formSearch: {
        nameSearch: ''
      },
      categoryCreate: {},
      formLabelWidth: '120px',
      editorConfig: {
        // The configuration of the editor.
      },

      page: 0,
      size: 10
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
.upload-demo .el-upload__input{
  display: none !important;
}
</style>
