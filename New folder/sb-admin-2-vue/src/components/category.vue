<template>
  <div>
    <div id="page-wrapper">
      <div>
        <b-form class="row">
          <div class="row">
            <div class="col-lg-12">
              <h1 class="page-header">Loại sản phẩm</h1>
            </div>
            <!-- /.col-lg-12 -->
          </div>

          <b-form-group class="col-lg-6 col-md-12">
            <b-form-input
              id="category_name_search"
              v-model="formSearch.nameSearch"
              placeholder="Nhập tên loại sản phẩm..."
            ></b-form-input>
          </b-form-group>
          <div class="col-lg-6 col-md-12">
            <b-button type="button" variant="primary" @click="search"
              >Tìm kiếm</b-button
            >
            <el-button type="button" @click="test()">Thêm mới</el-button>
          </div>
        </b-form>
      </div>
      <div>
        <el-table :data="items" style="width: 100%">
          <el-table-column prop="id" label="Mã loại sản phẩm" width="150">
          </el-table-column>
          <el-table-column prop="name" label="Tên loại sản phẩm" width="150">
          </el-table-column>
          <el-table-column prop="description" label="Mô tả"> </el-table-column>
          <el-table-column prop="createAt" label="Ngày tạo" width="140">
            <template slot-scope="scope">
              <span> {{scope.row.createAt ? formatDates(`${scope.row.createAt}`) : '' }}</span>
            </template>
          </el-table-column>
           <el-table-column prop="updateAt" label="Ngày cập nhật">
             <template slot-scope="scope">
              <span> {{scope.row.updateAt ? formatDates(`${scope.row.updateAt}`) : '' }}</span>
            </template>
           </el-table-column>
          <el-table-column label="Thao tác">
            <template slot-scope="scope">
              <el-button type="primary" @click="handleDetail(scope.row)"
                >Chi tiết</el-button
              >
              <el-button
                type="danger"
                @click="handleDelete(scope.row.id)"
                >Xóa</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          current-page.sync="5"
          :page-size="size"
          layout="total, prev, pager, next"
          :total="totalElements"
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog :title="active == 'create' ? 'Thêm mới loại sản phẩm' : 'Chi tiết loại sản phẩm'" :visible.sync="dialogFormVisible">
      <el-form :model="categoryCreate">
        <el-form-item label="Tên:" :label-width="formLabelWidth">
          <el-input v-model="categoryCreate.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Mô tả:" :label-width="formLabelWidth">
          <ckeditor
            v-model="categoryCreate.description"
            :config="editorConfig"
          ></ckeditor>
        </el-form-item>
        <el-form-item label="Ảnh :" :label-width="formLabelWidth">
          <el-upload
            class="upload-demo"
            action="#"
            list-type="picture-card"
            :auto-upload="false"
            :on-change="handleAddFile"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
          >
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{ file }">
              <!-- {{file}} -->

              <img
                class="el-upload-list__item-thumbnail"
                :src="'data:' + file.contentType + ';base64,' + file.content"
                alt=""
              />
              <span class="el-upload-list__item-actions">
                <span
                  v-if="!disabled"
                  class="el-upload-list__item-delete"
                  @click="handleRemove(file)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="" />
          </el-dialog>

          <!-- <el-upload
            class="upload-demo"
            drag
            action
            :auto-upload="false"
            :on-change="handleAddFile"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            multiple
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              Drop file here or <em>click to upload</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              jpg/png files with a size less than 500kb
            </div>
          </el-upload> -->
        </el-form-item>
      </el-form>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="" />
      </el-dialog>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="" />
      </el-dialog>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Hủy</el-button>
        <el-button type="primary" @click="create">Lưu</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import moment from "moment"
import { format, fromUnixTime } from "date-fns";

export default {
  created () {
    this.search()
    console.log('a')
  },
  methods: {
    formatDates(cellValue){
      // let a = new Date(cellValue);
      const dataFormat = format(fromUnixTime(cellValue), "dd/MM/yyyy");
      return dataFormat;
    },
    handleDetail (row) {
      this.active = 'edit'
      this.fileList = []
      this.fileIdList = []
      this.dialogFormVisible = true
      this.categoryCreate = Object.assign({}, row)
      if (this.categoryCreate.file != null) {
        let array = this.categoryCreate.file.trim().split(' ')
        console.log(array)
        array.forEach((element) => {
          axios.get(this.baseURL + `/${element}`).then(
            (res) => {
              this.fileList.push(res.data)
              this.fileIdList.push(res.data.id)
            },
            (error) => {
              console.log(error)
            }
          )
        })
      }
    },
    handleDelete (id) {
      axios.delete(this.baseURL + '/categories/category/' + id).then(
        (res) => {
          this.$toast.open('Xóa sản phẩm ' + id + ' thành công !')
          this.search()
        },
        (error) => {
          console.log(error)
          this.$toast.error('Tồn tại sản phẩm có loại sản phẩm trên không thể xóa !')
        }
      )
    },
    handleAddFile (file, fileList) {
      if (
        file.name.includes('jpg') ||
        file.name.includes('jpeg') ||
        file.name.includes('png')
      ) {
        // this.fileList = []
        const fileReader = new FileReader()
        fileReader.readAsDataURL(file.raw)
        fileReader.onload = (e) => {
          const dataRaw = e.target.result
          const base64Data = dataRaw.substr(
            dataRaw.indexOf('base64,') + 'base64,'.length
          )
          const fileUpload = {
            content: base64Data,
            contentType: file.raw.type,
            name: file.name,
            url: '',
            extendsion: 'Create',
            id: null
          }
          console.log(fileUpload)
          axios.post(this.baseURL + '/upload', fileUpload).then(
            (res) => {
              this.fileList.push(res.data)
              this.fileIdList.push(res.data.id)
            },
            (error) => {
              console.log(error)
              this.$toast.error('File quá nặng vui lòng chọn dưới 5 MB !')
              fileList.splice(fileList.length - 1, 1)
            }
          )
        }
      } else {
        this.$toast.error('Vui lòng chọn định dạng ảnh!')
        fileList.splice(fileList.length - 1, 1)
      }
    },
    handleRemove (file) {
      let index = this.fileList.findIndex(x => x.id === file.id)
      console.log(index)
      this.fileList.splice(index, 1)
      // console.log(file, fileList);
    },
    handlePreview (file, fileList) {
      console.log(file, fileList)
    },
    handleSizeChange (val) {
      this.size = val
      this.search()
    },
    handleCurrentChange (val) {
      this.page = val - 1
      this.search()
    },
    test () {
      this.dialogFormVisible = !this.dialogFormVisible
    },
    cancel () {
      this.dialogFormVisible = !this.dialogFormVisible
      this.categoryCreate = {}
      this.fileList = []
    },
    search () {
      axios
        .get(
          this.baseURL +
            `/categories/category?name=` +
            this.formSearch.nameSearch +
            `&page=${this.page}&size=${this.size}`,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
              'Content-type': 'application/json'
            }
          }
        )
        .then((response) => {
          this.items = response.data.content
          this.totalElements = response.data.totalElements
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },
    create () {
      if (this.categoryCreate.name === '' || this.categoryCreate.name == null) {
        this.$toast.error('Không được để trống tên loại sản phẩm !')
        return
      }
      this.categoryCreate.fileId = this.fileIdList
      // let formData = new FormData();
      // // for (var i = 0; i < this.listFile.length; i++) {
      // //   let file = this.listFile[i]
      // //   formData.append('files[' + i + ']', file)
      // // }
      // formData.append("files", this.fileIdList);
      // formData.append("req", this.categoryCreate);
      axios
        .post(this.baseURL + `/categories/category`, this.categoryCreate, {
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-type': 'application/json'
          }
        })
        .then((response) => {
          if (this.active === 'create') { this.$toast.open('Thêm mới thành công !') } else { this.$toast.open('Sửa thành công !') }
          // this.$toastr.zs('SUCCESS MESSAGE', 'Thêm mới thành công !')
          this.dialogFormVisible = false
          this.categoryCreate = {}
          this.active = 'create'
          this.search()
          // this.dialogFormVisible = !this.dialogFormVisibles
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    }
  },
  name: 'Forms',
  data () {
    return {
      fileList: [],
      active: 'create',
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      baseURL: 'http://192.168.1.208:9999/wear_shop/api',
      // baseURL: 'http://localhost:9999/wear_shop/api',
      dialogFormVisible: false,
      items: [],
      fileIdList: [],
      formSearch: {
        nameSearch: ''
      },
      categoryCreate: {},
      formLabelWidth: '120px',
      editorConfig: {
        // The configuration of the editor.
      },
      totalElements: 0,
      page: 0,
      size: 10
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style >
.upload-demo .el-upload__input {
  display: none !important;
}
</style>
