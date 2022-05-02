<template>
  <div>
    <div id="page-wrapper">
      <div>
        <b-form class="row">
          <div class="row">
            <div class="col-lg-12">
              <h1 class="page-header">Quản lý Blog</h1>
            </div>
            <!-- /.col-lg-12 -->
          </div>
          <b-form-group class="col-lg-6 col-md-12">
            <el-input
              placeholder="Nhập tiêu đề blog"
              v-model="formSearch.title"
            ></el-input>
          </b-form-group>

          <div class="col-lg-6 col-md-12" style="text-align: center">
            <b-button type="button" variant="primary" @click="search"
              >Tìm kiếm</b-button
            >
            <el-button type="button" @click="test()">Thêm mới</el-button>
          </div>
        </b-form>
      </div>
      <div>
        <el-table :data="items" style="width: 100%">
          <el-table-column prop="id" label="Mã tin tức" width="150">
          </el-table-column>
          <el-table-column prop="title" label="tiêu đề" width="150">
          </el-table-column>
          <el-table-column prop="createAt" label="Ngày tạo" width="140">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.createAt ? formatDates(`${scope.row.createAt}`) : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="updateAt" label="Ngày cập nhật">
            <template slot-scope="scope">
              <span>
                {{
                  scope.row.createAt ? formatDates(`${scope.row.createAt}`) : ""
                }}</span
              >
            </template>
          </el-table-column>
          <el-table-column label="Thao tác">
            <template slot-scope="scope">
              <el-button type="primary" @click="handleDetail(scope.row)"
                >Chi tiết</el-button
              >
              <el-button type="danger" @click="handleDelete(scope.row.id)"
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
    <el-dialog
      :title="active == 'create' ? 'Thêm mới tin tức' : 'Chi tiết tin tức'"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="newCreate">
        <el-form-item label="Tiêu đề :" :label-width="formLabelWidth">
          <el-input v-model="newCreate.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Nội dung :" :label-width="formLabelWidth">
          <ckeditor
            v-model="newCreate.content"
            :config="editorConfig"
          ></ckeditor>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">Hủy</el-button>
        <el-button type="primary" @click="createNew">Lưu</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
// import moment from 'moment'
import { format, fromUnixTime } from 'date-fns'
import { ro } from 'date-fns/locale'
export default {
  created () {
    console.log('a')
    this.search()
  },
  methods: {
    formatDates (cellValue) {
      // let a = new Date(cellValue);
      const dataFormat = format(fromUnixTime(cellValue), 'dd/MM/yyyy')
      return dataFormat
    },
    handleDetail (row) {
      this.active = 'edit'
      this.dialogFormVisible = true
      this.newCreate = {
        id: null,
        content: null,
        title: null

      }
      this.fileList = []
      this.fileIdList = []
      // this.productCreate = Object.assign({}, row);
      setTimeout(() => {
        this.newCreate.id = row.id
        this.newCreate.title = row.title
        this.newCreate.content = row.content
      }, '100')
      // console.log(this.userCreate)
    },
    handleDelete (id) {
      debugger
      console.log(id)
      axios.delete(this.baseURL + '/new/delete/' + id).then(
        (res) => {
          this.$toast.open('Xóa tin tức ' + id + ' thành công !')
          this.search()
        },
        (error) => {
          console.log(error)
          this.$toast.error(
            'Không xóa được tin tức !'
          )
        }
      )
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
      this.userCreate = {}
      this.fileList = []
    },

    search () {
      this.items = []

      this.fileList = []
      axios
        .get(
          this.baseURL +
            `/new/search?name=${this.formSearch.title}&?page=${this.page}&size=${this.size}`
        )
        .then((response) => {
          this.items = response.data.content
          this.totalElements = response.data.totalElements
        })
        .catch((e) => {
          // this.errors.push(e);
        })
    },

    createNew () {
      // const usernameRegex = new RegExp('^[a-z0-9A-Z]{1,}$')
      if (this.newCreate.title === '' || this.newCreate.title == null) {
        this.$toast.error('Tiêu đề không được trống!')
        return
      }
      if (this.newCreate.content === '' || this.newCreate.content == null) {
        this.$toast.error('Nội dung không được để trống!')
        return
      }

      axios
        .post(this.baseURL + `/new/`, this.newCreate)
        .then((response) => {
          if (this.active === 'create') {
            this.$toast.open('Thêm mới tin tức thành công !')
          } else {
            this.$toast.open('Sửa tin tức thành công !')
          }
          this.dialogFormVisible = false
          this.newCreate = {}
          this.active = 'create'
          this.search()
          // this.dialogFormVisible = !this.dialogFormVisibles
        })
        .catch((e) => {
          this.$toast.error('Thêm mới không thành công!')
          // this.errors.push(e);
        })
    },

    formatInput (evt) {
      evt = evt || window.event
      var charCode = evt.which ? evt.which : evt.keyCode
      if (
        (charCode > 31 && (charCode < 48 || charCode > 57)) ||
        charCode === 32
      ) {
        evt.preventDefault()
      } else {
        return true
      }
    },
    NumberToString (value, k) {
      if (value == null || value === '') {

      } else {
        value = this.stringToNumber(value)
        if (!isNaN(value)) {
          let val = (value / 1).toFixed().replace('.', ',')
          let string = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')
          this.productCreate.price = string
          return string
        } else {
        }
      }
    },
    stringToNumber (string) {
      if (string == null || string === '') {
        return null
      } else {
        if (typeof string === 'number') {
          return string
        }
        return parseInt(string.replace(/[.,\/#!$%\^&\*;:{}=\_`~()]/g, ''))
      }
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
      // baseURL: 'http://192.168.1.208:9999/wear_shop/api',
      baseURL: 'http://localhost:9999/wear_shop/api',
      dialogFormVisible: false,
      items: [],
      formSearch: {
        title: ''

      },
      newCreate: {
        id: null,
        content: null,
        title: null
      },
      formLabelWidth: '150px',
      editorConfig: {

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
.custum-image-table {
  width: 200px;
}
</style>
